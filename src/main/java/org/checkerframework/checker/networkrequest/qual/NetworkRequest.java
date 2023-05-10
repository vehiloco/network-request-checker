package org.checkerframework.checker.networkrequest.qual;

import org.checkerframework.framework.qual.InheritedAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@InheritedAnnotation
public @interface NetworkRequest {
    String[] value() default {};
}
