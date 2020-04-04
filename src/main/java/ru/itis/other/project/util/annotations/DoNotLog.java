package ru.itis.other.project.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that method result or method parameter should not be logged in {@link ru.itis.other.project.aspects.LoggingInterceptorAspect}.
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoNotLog {
}
