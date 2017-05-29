package com.densev.multimodule.injector;

import com.densev.multimodule.injector.annotation.Wireable;
import com.densev.multimodule.injector.annotation.Wired;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
public enum Injector {

    INSTANCE;

    private final Logger LOG = LoggerFactory.getLogger(Injector.class);
    private final Map<Class<?>, Object> container;

    Injector() {
        container = new HashMap<>();
        //inject();
    }

    public <T> T getBean(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Wireable.class)) {
            //String classname = ((Wireable) clazz.getAnnotation(Wireable.class)).value();
            return getBeanInstance(clazz);
        } else {
            LOG.error("Cannot wire a non wireable class: {}", clazz.getName());
            throw new RuntimeException("Cannot wire a non wireable class");
        }
    }

    public <T> T getBeanInstance(Class<T> clazz) {
        try {
            if (container.containsKey(clazz)) {
                LOG.info("Retrieving instance of class {} from container", clazz.getName());
                return (T) container.get(clazz);
            } else {
                LOG.info("Creating new instance of class: {}", clazz.getName());
                Object instance = clazz.newInstance();
                container.put(clazz, instance);
                //TODO: experiment with wiring order
                wire(instance, clazz);
                return (T) instance;
            }
        } catch (ReflectiveOperationException roe) {
            LOG.error("Error creating new instance of {}", clazz.getName());
            throw new RuntimeException("");
        }
    }

    public void wire(Object obj, Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Wired.class))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(obj, getBean(field.getType()));
                    } catch (IllegalAccessException iae) {
                        LOG.error("Error wiring field {} of class {}", field.getName(), clazz.getName(), iae);
                    }
                });
    }


    public void inject() {
        Reflections reflections = new Reflections("com.densev.multimodule.injector", null);
        reflections.getTypesAnnotatedWith(Wireable.class)
                .forEach(type -> {
                    getBeanInstance(type);
                });
        System.out.println(container.toString());
        //reflections.get
    }
}
