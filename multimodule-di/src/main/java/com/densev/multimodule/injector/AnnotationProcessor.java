package com.densev.multimodule.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.inject.Singleton;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@SupportedAnnotationTypes("javax.inject.Singleton")
public class AnnotationProcessor extends AbstractProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotationProcessor.class);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (final Element element : roundEnv.getElementsAnnotatedWith(Singleton.class)) {
            LOG.info("Singleton class found: {}", element.getSimpleName());
        }
        return true;
    }
}
