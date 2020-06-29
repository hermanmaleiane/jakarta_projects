package com.challenge.herman.util;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.*;

@Interceptor
@DelayMe
public class DelayExecution implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(DelayExecution.class.getName());

    @AroundInvoke
    public Object delayMethodExecution(InvocationContext invocationCtx) throws Exception {

        DelayMe delayMe = invocationCtx.getMethod().getAnnotation(DelayMe.class);

        LOGGER.log(Level.INFO, format("Method %s is being delayed for %s", invocationCtx.getMethod().getName(),
                format("%d sec", TimeUnit.MILLISECONDS.toSeconds(delayMe.time()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(delayMe.time())))));

        try {
            TimeUnit.MILLISECONDS.sleep(delayMe.time());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        LOGGER.log(Level.INFO, "Delay is finished");

        return invocationCtx.proceed();

    }
}
