package ru.alidi.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RejectDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) {

        System.out.println("Реджект");
    }

}
