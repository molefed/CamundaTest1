package ru.alidi.camunda.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class LoadBigOrderDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) {

        var var = (int) execution.getVariable("ORDER_ITEMS_COUNT");

        System.out.println("Заказ большой. %s элементов.".formatted(var));

        if (var > 1000) {

            System.out.println("Кидаем исключение LOAD_BIG_ORDER_ERROR.");

            throw new BpmnError("LOAD_BIG_ORDER_ERROR", "Какая-то ошибка");
        }
    }

}
