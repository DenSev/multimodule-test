package com.densev.multimodule.aop;

import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dzianis_Sevastseyenk on 06/27/2017.
 */
@Aspect
@Component
public class LoggerAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggerAspect.class);

    @Autowired
    private LogContainer logContainer;

    @Around("execution(public * com.densev.multimodule.aop.RepositoryTest.search(..))")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {

        final Object[] args = pjp.getArgs();
        final String methodName = pjp.getSignature().getName();
        LOG.info("hijacking method: {} with arguments: {}", methodName, args);

        final Object result = pjp.proceed();

        logContainer.add(new LogInfo(Lists.newArrayList(args), result));

        return result;
    }

}
