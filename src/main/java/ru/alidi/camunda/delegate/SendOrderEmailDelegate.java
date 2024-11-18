package ru.alidi.camunda.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.time.Duration;

public class SendOrderEmailDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) throws InterruptedException {

        Thread.sleep(Duration.ofSeconds(5));

        System.out.println("Отправили емайл о заказе");
    }

}

