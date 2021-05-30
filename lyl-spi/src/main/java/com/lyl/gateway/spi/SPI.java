package com.lyl.gateway.spi;

import java.lang.annotation.*;

/**
 * @ClassName SPI
 * @Description
 * @Author Administrator
 * @Date 2021/5/29 19:46
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SPI {

    String value() default "";

}
