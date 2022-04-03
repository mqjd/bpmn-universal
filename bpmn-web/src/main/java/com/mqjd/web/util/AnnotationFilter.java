package com.mqjd.web.util;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

public class AnnotationFilter implements Predicate<Class<?>> {
    private final Class<? extends Annotation> annotationType;

    public AnnotationFilter(Class<? extends Annotation> annotationType) {
        this.annotationType = annotationType;
    }

    @Override
    public boolean test(Class<?> clz) {
        return clz.getAnnotation(annotationType) != null;
    }
}
