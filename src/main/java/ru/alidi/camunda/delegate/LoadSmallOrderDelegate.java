package ru.alidi.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class LoadSmallOrderDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) {

        var var = (int) execution.getVariable("ORDER_ITEMS_COUNT");

        System.out.println("Заказ маленький. %s элементов.".formatted(var));
    }

}
