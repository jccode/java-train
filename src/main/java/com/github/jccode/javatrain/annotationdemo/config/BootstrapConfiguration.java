package com.github.jccode.javatrain.annotationdemo.config;

import com.github.jccode.javatrain.annotationdemo.annotation.EventHandlerAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BootstrapConfiguration
 *
 */
@Configuration
public class BootstrapConfiguration {

    @Bean(name = "com.github.javatrain.annotationdemo.internal.EventHandlerAnnotationBeanPostProcessor")
    public EventHandlerAnnotationBeanPostProcessor eventHandlerAnnotationBeanPostProcessor() {
        return new EventHandlerAnnotationBeanPostProcessor();
    }

    @Bean(name = "com.github.javatrain.annotationdemo.internal.EventHandlerRegistry")
    public EventHandlerRegistry eventHandlerRegistry() {
        return new EventHandlerRegistry();
    }
}
