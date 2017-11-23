package com.github.jccode.javatrain.aop;

import org.springframework.stereotype.Component;

@Component
public class Service {

    @LogExecutionTime
    public void serve() throws InterruptedException {
        System.out.println("serve");
        Thread.sleep(300);
    }
}
