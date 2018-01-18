package com.github.jccode.javatrain.annotationdemo.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Event handler registry
 *
 */
public class EventHandlerRegistry {

    private final Map<String, EventHandlerEndpoint> handlers = new ConcurrentHashMap<>();

    public void registerEventHandler(String eventType, EventHandlerEndpoint endpoint) {
        handlers.put(eventType, endpoint);
    }

    public EventHandlerEndpoint getEventHandler(String eventType) {
        return handlers.get(eventType);
    }

}
