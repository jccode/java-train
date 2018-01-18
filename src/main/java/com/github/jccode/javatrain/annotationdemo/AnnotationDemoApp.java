package com.github.jccode.javatrain.annotationdemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

@SpringBootApplication
public class AnnotationDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(AnnotationDemoApp.class, args);
    }

    @Autowired
    private SampleHandlers sampleHandlers;


    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            /*
            Method[] methods = SampleHandlers.class.getDeclaredMethods();
            System.out.println("invoke");
            System.out.println(methods[0]);
            methods[0].invoke(sampleHandlers);
            */
        };
    }

}
