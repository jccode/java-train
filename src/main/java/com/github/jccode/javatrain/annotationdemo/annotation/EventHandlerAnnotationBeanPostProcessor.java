package com.github.jccode.javatrain.annotationdemo.annotation;

import com.github.jccode.javatrain.annotationdemo.config.EventHandlerEndpoint;
import com.github.jccode.javatrain.annotationdemo.config.EventHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean post-processor that registers methods annotated with {@link EventHandler}.
 *
 */
public class EventHandlerAnnotationBeanPostProcessor
        implements BeanPostProcessor, SmartInitializingSingleton, BeanFactoryAware, Ordered {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private BeanFactory beanFactory;
    private final Set<Class<?>> nonAnnotatedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(64));

    private EventHandlerRegistry eventHandlerRegistry = new EventHandlerRegistry();

    @Override
    public void afterSingletonsInstantiated() {
        System.out.println("------ afterSingletonsInstantiated -------");

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("------- postProcessAfterInitialization -------");
        if (!nonAnnotatedClasses.contains(bean.getClass())) {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            Map<Method, EventHandler> annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                    (MethodIntrospector.MetadataLookup<EventHandler>) method -> AnnotationUtils.findAnnotation(method, EventHandler.class));
            if (annotatedMethods.isEmpty()) {
                nonAnnotatedClasses.add(targetClass);
                if (logger.isTraceEnabled()) {
                    logger.trace("No @EventHandler annotations found on bean type: " + bean.getClass());
                }
            }
            else {
                for (Map.Entry<Method, EventHandler> entry : annotatedMethods.entrySet()) {
                    processEventHandler(entry.getValue(), entry.getKey(), bean, beanName);
                }
                if (logger.isDebugEnabled()) {
                    logger.debug(annotatedMethods.size() + " @EventHandler methods processed on bean '" + beanName + "': " + annotatedMethods);
                }
            }
        }
        return bean;
    }

    private void processEventHandler(EventHandler eventHandler, Method method, Object bean, String beanName) {
        String eventType = eventHandler.type();
        EventHandlerEndpoint endpoint = new EventHandlerEndpoint();
        endpoint.setEventType(eventType);
        endpoint.setBean(bean);
        endpoint.setMethod(method);
        eventHandlerRegistry.registerEventHandler(eventType, endpoint);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
