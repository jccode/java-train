package com.github.jccode.javatrain.annotationdemo.config;

import java.lang.reflect.Method;

public class EventHandlerEndpoint {

    private Object bean;

    private Method method;

    private String eventType;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
