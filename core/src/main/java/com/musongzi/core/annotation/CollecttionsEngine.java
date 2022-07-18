package com.musongzi.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CollecttionsEngine {

    String title() default "";

    boolean isEnableReFresh() default true;

    boolean isEnableLoadMore() default true;

    int emptyLoadRes() default 0;

    String modelKey() default "";

    String emptyString() default "暂无数据，请下拉刷新一下";

    boolean isEnableEventBus() default false;

    boolean openLazyLoad() default false;

    public static final String B = "CollecttionsEngine_bundle";

}
