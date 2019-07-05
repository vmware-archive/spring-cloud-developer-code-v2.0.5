package io.pivotal.pal.tracker.projects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "project.logging.timer.enabled", havingValue = "true")
@Aspect
@Component
public class LoggingTimerAspect {
    private final Logger logger =
            LoggerFactory.getLogger(this.getClass());

    @Around("execution(public io.pivotal.pal.tracker.projects.ProjectInfo " +
            "io.pivotal.pal.tracker.projects.ProjectController.get(long))")
    public ProjectInfo logTimingAdvice(ProceedingJoinPoint pjp)
            throws Throwable {
        long startTimeMs = System.currentTimeMillis();

        long projectId = (Long)pjp.getArgs()[0];
        ProjectInfo projectInfo = (ProjectInfo)pjp.proceed();

        logger.info("returning project info for project id {}," +
                        "execution took {} milliseconds",
                projectId, System.currentTimeMillis() - startTimeMs);

        return projectInfo;
    }

}
