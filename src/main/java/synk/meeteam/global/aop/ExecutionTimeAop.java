package synk.meeteam.global.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Log4j2
public class ExecutionTimeAop {

    // 모든 패키지 내의 controller package에 존재하는 클래스
    @Around("execution(* *..api.*.*(..))")
    public Object calculateExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        // 해당 클래스 처리 전의 시간
        StopWatch sw = new StopWatch();
        sw.start();

        // 해당 클래스의 메소드 실행
        Object result = pjp.proceed();

        // 해당 클래스 처리 후의 시간
        sw.stop();
        long executionTime = sw.getTotalTimeMillis();

        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String task = className + "." + methodName;

        log.info("[ExecutionTime] " + task + " --> " + executionTime + " (ms)");

        return result;
    }

}
