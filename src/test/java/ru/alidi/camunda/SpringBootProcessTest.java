package ru.alidi.camunda;

import org.awaitility.core.ConditionFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SpringBootProcessTest {

    final String PROCESS_KEY = "test1-process";

    Map<String, Object> variables;

    @Autowired
    RuntimeService runtimeService;

    private static final ConditionFactory WAIT = await()
            .atMost(Duration.ofSeconds(15))
            .pollInterval(Duration.ofMillis(500))
            .pollDelay(Duration.ofMillis(1));

    @Test
    public void test() {

        variables = new HashMap<>();
        variables.put("CLIENT_ID_KEY", "test_client_id");

        var processInstance = runtimeService.startProcessInstanceByKey(PROCESS_KEY, variables);

        assertNotNull(processInstance);

        BpmnAwareTests.assertThat(processInstance).isStarted();
        BpmnAwareTests.assertThat(processInstance).hasVariables("CLIENT_ID_KEY");

        WAIT.untilAsserted(() -> {
            BpmnAwareTests.assertThat(processInstance).isEnded();
        });

    }
}
