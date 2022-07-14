package com.musongzi.core.base.manager.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventStrategy {

    boolean isIgnore() default false;


}
