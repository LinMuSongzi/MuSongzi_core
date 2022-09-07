package com.musongzi.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*** created by linhui * on 2022/8/26 */

@Retention(RetentionPolicy.SOURCE)
public @interface EventInterface {

    boolean isExtends() default false;

}
