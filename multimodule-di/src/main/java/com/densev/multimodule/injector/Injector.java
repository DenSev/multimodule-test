package com.densev.multimodule.injector;

import com.densev.multimodule.injector.annotation.Wireable;
import com.densev.multimodule.injector.annotation.Wired;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
public enum Injector {

    INSTANCE;

    private final String basePackage = "com.densev.multimodule.injector";
    private final Logger LOG = LoggerFactory.getLogger(Injector.class);
    private final Map<Class<?>, Object> container;

    Injector() {
        container = new HashMap<>();
        //inject();
    }

    public <T> T getBean(Class<T> clazz) {
        //FIXME: temporary
        //String classname = ((Wireable) clazz.getAnnotation(Wireable.class)).value();
        return getBeanInstance(clazz);

    }

    public <T> T getNewInstance(Class<T> clazz) throws ReflectiveOperationException {
        if (clazz.isAnnotationPresent(Wireable.class)) {

            LOG.debug("Creating new instance of class: {}", clazz.getName());
            return clazz.newInstance();

        } else {
            LOG.error("Cannot wire a non wireable class: {}", clazz.getName());
            throw new RuntimeException("Cannot wire a non wireable class");
        }
    }

    public <T> T getNewSubTypeInstance(Class<T> clazz) throws ReflectiveOperationException {
        LOG.debug("Attempting to find implementation for interface {}", clazz.getName());
        Reflections reflections = new Reflections(basePackage, null);
        Set<Class<? extends T>> subtypes = reflections.getSubTypesOf(clazz);
        if (subtypes.size() > 1) {
            LOG.error("Found more than 1 candidate for {} interface implementation", clazz.getName());
            throw new RuntimeException("Found more than 1 candidate for" + clazz.getName() + "interface implementation");
        }
        return getNewInstance(new ArrayList<>(subtypes).get(0));
    }

    public <T> T getBeanInstance(Class<T> clazz) {
        try {
            if (container.containsKey(clazz)) {
                LOG.debug("Retrieving instance of class {} from container", clazz.getName());
                return (T) container.get(clazz);
            } else {
                Object instance;
                if (clazz.isInterface()) {
                    instance = getNewSubTypeInstance(clazz);
                } else {
                    instance = getNewInstance(clazz);
                }
                container.put(clazz, instance);
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
        Reflections reflections = new Reflections(basePackage, null);
        reflections.getTypesAnnotatedWith(Wireable.class)
            .forEach(type -> {
                getBeanInstance(type);
            });
        //reflections.get
    }
}
