package ru.alidi.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SayHelloDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) {

        var var = (String) execution.getVariable("CLIENT_ID_KEY");
        var = var.toUpperCase();
        execution.setVariable("CLIENT_ID_KEY", var);
    }

}
