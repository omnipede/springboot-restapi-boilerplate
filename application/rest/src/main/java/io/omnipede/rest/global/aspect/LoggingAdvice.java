package io.omnipede.rest.global.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * Logging 용 aspect
 * @deprecated
 */
@Deprecated
// @Component
// @Aspect
public class LoggingAdvice {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAdvice.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(* io.omnipede.springbootrestapiboilerplate.domain.purchase.*.*.*(..))")
    public void purchasePointcut() {}

    @Pointcut("execution(* io.omnipede.springbootrestapiboilerplate.domain.topic.*.*(..))")
    public void topicPointcut() {}

    // Pointcut of all methods inside domain folder
    @Pointcut("execution(* io.omnipede.springbootrestapiboilerplate.domain..*.*(..))")
    public void domainPointcut() {}

    // Pointcut of all methods inside global/exception folder
    @Pointcut("execution(* io.omnipede.springbootrestapiboilerplate.global.exception.*.*(..))")
    public void globalExceptionHandler() {}

    /**
     * Pointcut 여러개를 합치려면 다음과 같이 코딩한다.
     */
    @Pointcut("purchasePointcut() || topicPointcut()")
    public void allPointcuts() {}

    /**
     * Class name, method name 를 debug 레벨로 로깅
     * 아직 완벽하지 않음 ... 디버그 할 때 잠시 사용할 것
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
     * 마찬가지로 아직 완벽하진 않음 ... 디버그 할 때 잠시 사용할 것
     */
    @Around("domainPointcut()")
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
