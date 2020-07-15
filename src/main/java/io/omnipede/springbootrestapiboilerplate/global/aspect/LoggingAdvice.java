package io.omnipede.springbootrestapiboilerplate.global.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

/**
 * Logging 용 aspect
 */
@Component
@Aspect
public class LoggingAdvice {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(* io.omnipede.springbootrestapiboilerplate.domain.topic.*.*(..))")
    public void topicPointcut() {}

    @Pointcut("execution(* io.omnipede.springbootrestapiboilerplate.global.exception.*.*(..))")
    public void globalExceptionHandler() {}

    /**
     * Pointcut 여러개를 합치려면 다음과 같이 코딩한다.
     */
    @Pointcut("topicPointcut() || globalExceptionHandler()")
    public void allPointcuts() {}

    /**
     * Class name, method name 를 debug 레벨로 로깅
     */
    @Around("globalExceptionHandler()")
    public Object globalAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        // Log method name
        logger.debug("start - " + className + " / " + methodName);
        // Proceed and get return value
        Object procedureResult = null;
        final StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            procedureResult = pjp.proceed();
            stopWatch.stop();
        } catch (Throwable e) {
            stopWatch.stop();
            // If exception occured, then log and throw the exception.
            logger.debug("exception - " + className + " / " + methodName + " / " + e.getClass().getSimpleName() + " / " + e.getMessage() + " :: " + stopWatch.getTotalTimeMillis() + "ms");
            throw e;
        }
        String returnValue = objectMapper.writeValueAsString(procedureResult);
        // Log return value
        logger.debug("finished - " + className + " / " + methodName + " / return = " + returnValue + " :: " + stopWatch.getTotalTimeMillis() + "ms");
        return procedureResult;
    }

    /**
     * Class name, method name, parameter 를 debug 레벨로 로깅
     */
    @Around("topicPointcut()")
    public Object domainAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();
        String arguments = objectMapper.writeValueAsString(pjp.getArgs());

        // Log argument with method name
        logger.debug("start - " + className + " / " + methodName + " / args = " + arguments);
        // Proceed and get return value
        Object procedureResult = null;
        final StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            procedureResult = pjp.proceed();
            stopWatch.stop();
        } catch (Throwable e) {
            stopWatch.stop();
            // If exception occured, then log and throw the exception.
            logger.debug("exception - " + className + " / " + methodName + " / " + e.getClass().getSimpleName() + " / " + e.getMessage() + " :: " + stopWatch.getTotalTimeMillis() + "ms");
            throw e;
        }
        String returnValue = objectMapper.writeValueAsString(procedureResult);
        // Log return value
        logger.debug("finished - " + className + " / " + methodName + " / return = " + returnValue + " :: " + stopWatch.getTotalTimeMillis() + "ms");
        return procedureResult;
    }
}
