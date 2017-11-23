package com.github.jccode.javatrain.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AopDemo {

    public static void main(String[] args) {
        SpringApplication.run(AopDemo.class, args);
    }

    @Autowired
    private Service service;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("hello");
            service.serve();
        };
    }


}
