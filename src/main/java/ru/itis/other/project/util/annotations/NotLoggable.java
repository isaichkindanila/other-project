package ru.itis.other.project.util.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that method should not be logged at all in {@link ru.itis.other.project.aspects.LoggingInterceptorAspect}.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotLoggable {
}
