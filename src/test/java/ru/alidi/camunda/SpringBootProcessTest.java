package ru.alidi.camunda;

import org.awaitility.core.ConditionFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SpringBootProcessTest {

    final String PROCESS_KEY = "test1-process";

    Map<String, Object> variables;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    private static final ConditionFactory WAIT = await()
            .atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(500))
            .pollDelay(Duration.ofMillis(50));

    @Test
    public void test() {

        variables = Map.of(
                "CLIENT_ID_KEY", "test_client_id",
                "ORDER_ITEMS_COUNT", 7000
        );

        var processInstance = runtimeService.startProcessInstanceByKey(PROCESS_KEY, variables);

        assertNotNull(processInstance);

        BpmnAwareTests.assertThat(processInstance).isStarted();
        BpmnAwareTests.assertThat(processInstance).hasVariables("CLIENT_ID_KEY");

        waitUntilActivityWillBeExecuted(processInstance.getProcessInstanceId(), "Ждем юзера");

        completeUserTask(processInstance.getProcessInstanceId(), "Ждем юзера", Map.of());

        WAIT.untilAsserted(() -> {
            BpmnAwareTests.assertThat(processInstance).isEnded();
        });

    }

    public void waitUntilActivityWillBeExecuted(String processInstanceId, String taskName) {

        WAIT.untilAsserted(() -> {

            var task = taskService.createTaskQuery()
                                  .active()
                                  .processInstanceId(processInstanceId)
                                  .list()
                                  .stream()
                                  .filter(t -> t.getName().equals(taskName))
                                  .findFirst()
                                  .orElse(null);

            assertNotNull(task);
        });
    }

    public void completeUserTask(String processInstanceId, String taskName, Map<String, Object> variables) {

        String taskId = taskService.createTaskQuery()
                                   .processInstanceId(processInstanceId)
                                   .list()
                                   .stream()
                                   .filter(t -> t.getName().equals(taskName))
                                   .findFirst()
                                   .orElseThrow(() -> new RuntimeException(
                                           "Could not find task with name %s on processInstanceId %s".formatted(
                                                   taskName,
                                                   processInstanceId)
                                   )).getId();

        taskService.complete(taskId, variables);
    }

}
