package com.mqjd.web.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RestHandler {
    String value();

    RequestMethod method();

    boolean block() default false;
}
