package com.github.jccode.javatrain.annotationdemo;

import com.github.jccode.javatrain.annotationdemo.annotation.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SampleHandlers {

    @EventHandler("foo")
    public void fooHandler() {
        System.out.println("foo");
    }

    @EventHandler(type = "bar")
    public void barHandler(String args) {
        System.out.println("bar "+args);
    }

    @EventHandler("zoo")
    public void zooHandler(List<Integer> scores) {
        System.out.println("zoo " + scores);
    }
}
