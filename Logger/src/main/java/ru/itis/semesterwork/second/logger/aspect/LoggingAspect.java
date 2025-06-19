package ru.itis.semesterwork.second.logger.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger("MY_CUSTOM_ASPECT_LOGGER");

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void servicePointcut() {
    }

    private final String REPOSITORY_ANNOTATIONS = """
            @within(org.springframework.stereotype.Repository) ||
            @within(org.springframework.data.repository.NoRepositoryBean)""";

    @Pointcut(REPOSITORY_ANNOTATIONS)
    public void repositoryPointcut() {
    }

    private final String CONTROLLER_ANNOTATIONS = """
            @within(org.springframework.web.bind.annotation.RestController) ||
            @within(org.springframework.stereotype.Controller)
            """;

    @Pointcut(CONTROLLER_ANNOTATIONS)
    public void controllerPointcut() {
    }

    @Around("controllerPointcut()")
    public Object controllerLogging(ProceedingJoinPoint jp) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String method = request.getMethod();
        String url = request.getRequestURL().toString();

        log.info("Received request with method: {} and url: {}", method, url);
        Object result = jp.proceed();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getResponse();
        if (response != null) {
            log.info("Response status to request with method: {} and url: {} - {}", method, url, response.getStatus());
        }
        return result;
    }

    @Around("servicePointcut()")
    public Object serviceLogging(ProceedingJoinPoint jp) throws Throwable {

        Object result = null;
        if (log.isDebugEnabled()) {
            log.debug("Service method {} is called with args: {}", jp.getSignature().getName(), jp.getArgs());
        }

        result = jp.proceed();
        if (log.isDebugEnabled() && result != null) {
            log.debug("Service method {} returns: {}", jp.getSignature().getName(), result);
        }
        return result;
    }

    @Around("repositoryPointcut()")
    public Object repositoryLogging(ProceedingJoinPoint jp) throws Throwable {

        Object result = null;
        long currentMillis = System.nanoTime();
        long afterMethodMillis;
        if (log.isDebugEnabled()) {
            log.debug("Repository method {} is called with args: {}", jp.getSignature().getName(), jp.getArgs());
        }
        result = jp.proceed();

        afterMethodMillis = System.nanoTime();
        if (log.isDebugEnabled() && result != null) {
            log.debug("Repository method {} returns: {}. Needed time = {}", jp.getSignature().getName(), result,
                    afterMethodMillis - currentMillis);
        }
        return result;
    }

    @AfterThrowing(value = "controllerPointcut()", throwing = "e")
    public void controllerLogging(JoinPoint jp, Throwable e) {
        if (log.isDebugEnabled()) {
            log.debug("Exception in service method {} with message: {}", jp.getSignature().getName(), e.getMessage());
        }
    }

    @AfterThrowing(value = "servicePointcut()", throwing = "e")
    public void serviceLogging(JoinPoint jp, Throwable e) {
        if (log.isDebugEnabled()) {
            log.debug("Exception in service method {} with message: {}", jp.getSignature().getName(), e.getMessage());
        }
    }

    @AfterThrowing(value = "repositoryPointcut()", throwing = "e")
    public void repositoryLogging(JoinPoint jp, Throwable e) {
        if (log.isDebugEnabled()) {
            log.debug("Exception in repository method {} with message: {}", jp.getSignature().getName(), e.getMessage());
        }
    }
}
