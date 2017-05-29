package com.densev.multimodule.injector;

import com.densev.multimodule.injector.annotation.Wireable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Created by Dzianis_Sevastseyenk on 05/29/2017.
 */
@SupportedAnnotationTypes("com.densev.multimodule.injector.annotation.Wireable")
public class AnnotationProcessor extends AbstractProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotationProcessor.class);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (final Element element : roundEnv.getElementsAnnotatedWith(Wireable.class)) {
            LOG.info("Wireable class found: {}", element.getSimpleName());
        }
        return true;
    }
}
