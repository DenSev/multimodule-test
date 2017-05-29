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

    private final static Logger LOG = LoggerFactory.getLogger(Injector.class);
    private final static Map<Class<?>, Object> container = new HashMap<>();

    private Object getBean(Class clazz) {
        if (clazz.isAnnotationPresent(Wireable.class)) {
            try {
                if (container.containsKey(clazz)) {
                    LOG.info("Retreiving instance of class {} from container", clazz.getName());
                    return container.get(clazz);
                } else {
                    LOG.info("Creating new instance of class: {}", clazz.getName());
                    Object instance = clazz.newInstance();
                    container.put(clazz, instance);
                    return instance;
                }
            } catch (ReflectiveOperationException roe) {
                LOG.error("Error creating new instance of {}", clazz.getName());
                throw new RuntimeException("");
            }
        } else {
            LOG.error("Cannot wire a non wireable class: {}", clazz.getName());
            throw new RuntimeException("Cannot wire a non wireable class");
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
        //reflections.get
    }
}
