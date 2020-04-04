package ru.itis.other.project.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.other.project.util.annotations.DoNotLog;
import ru.itis.other.project.util.annotations.NotLoggable;

import java.lang.annotation.Annotation;

@Aspect
@Component
@Slf4j(topic = "interceptor")
public class LoggingInterceptorAspect {

    @Value("${logging.interceptor.hiddenParameter}")
    private String hiddenParameter;

    @Value("${logging.interceptor.logStackTrace}")
    private boolean logStackTrace;

    private boolean shouldLog(Annotation[] annotations) {
        for (var annotation : annotations) {
            if (annotation instanceof DoNotLog) {
                return false;
            }
        }

        return true;
    }

    @Around("execution(public * ru.itis.other.project.services.*.*(..)) && !@annotation(ru.itis.other.project.util.annotations.NotLoggable)")
    public Object intercept(ProceedingJoinPoint pjp) throws Throwable {
        // if trace is disabled there is no point in logging anything
        if (!log.isTraceEnabled()) {
            return pjp.proceed();
        }

        // cast signature to MethodSignature to get argument types
        var signature = (MethodSignature) pjp.getSignature();
        var method = signature.getMethod();

        // @NotLoggable methods should not be logged
        if (method.getAnnotation(NotLoggable.class) != null) {
            return pjp.proceed();
        }

        var args = pjp.getArgs();
        var annotations = method.getParameterAnnotations();

        // String representations of method parameters
        var params = new String[args.length];

        for (int i = 0; i < args.length; i++) {
            // do not include parameters annotated with @DoNotLog
            if (shouldLog(annotations[i])) {
                params[i] = String.valueOf(args[i]);
            } else {
                params[i] = hiddenParameter;
            }
        }

        // String representation of invocation signature
        var invocationSignature = signature.getDeclaringType().getSimpleName() + "." + signature.getName();

        // for logging invocation time
        long start = System.currentTimeMillis();

        try {
            log.trace(invocationSignature + " <<< [" + String.join(", ", params) + "]");
            var result = pjp.proceed();

            // log result if method does not return void and is not annotated with @DoNotLog
            var resultString = (method.getReturnType() == Void.TYPE)
                    ? "<void>" : (method.getAnnotation(DoNotLog.class) == null)
                    ? String.valueOf(result) : hiddenParameter;

            long elapsed = System.currentTimeMillis() - start;
            log.trace(invocationSignature + " >>> " + resultString + " (" + elapsed + " ms)");

            return result;
        } catch (Throwable t) {
            long elapsed = System.currentTimeMillis() - start;

            if (logStackTrace) {
                log.trace(invocationSignature + " ERR (" + elapsed + " ms)" + t.toString(), t);
            } else {
                log.trace(invocationSignature + " ERR (" + elapsed + " ms)" + t.toString());
            }

            throw t;
        }
    }
}
