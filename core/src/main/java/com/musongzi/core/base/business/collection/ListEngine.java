package com.musongzi.core.base.business.collection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ListEngine {

    String title() default "";

    boolean isEnableReFresh() default true;

    boolean isEnableLoadMore() default true;

    int emptyLoadRes() default 0;

    String modelKey() default "";

    String emptyString() default "暂无数据，请下拉刷新一下";

    boolean isEnableEventBus() default false;


    public static final String B = "bundle";

}
