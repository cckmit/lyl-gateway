package com.lyl.gateway.client.http.springmvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LylSpringMvcClient {

    String path();
    String ruleName() default "";
    String desc() default "";
    String rpcType() default "http";
    boolean enabled() default true;
    boolean registerMetaData() default false;

}
