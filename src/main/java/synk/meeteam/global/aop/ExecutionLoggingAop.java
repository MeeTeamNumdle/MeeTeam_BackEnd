package synk.meeteam.global.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import synk.meeteam.security.CustomAuthUser;

@Aspect
@Component
@Log4j2
public class ExecutionLoggingAop {

    // 모든 패키지 내의 controller package에 존재하는 클래스
    @Around("execution(* synk.meeteam.domain..api..*(..))")
    public Object logExecutionTrace(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        RequestMethod httpMethod = RequestMethod.valueOf(request.getMethod());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = 0L;
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            CustomAuthUser customAuthUser = (CustomAuthUser) authentication.getPrincipal();
            userId = customAuthUser.getUser().getId();
        }

        String className = pjp.getSignature().getDeclaringType().getSimpleName();
        String methodName = pjp.getSignature().getName();
        String task = className + "." + methodName;

        log.info("[Call Method] " + httpMethod.toString() + ": " + task + " | Request userId=" + userId.toString());

        Object[] paramArgs = pjp.getArgs();
        String loggingMessage = "";
        int cnt = 1;
        for (Object object : paramArgs) {
            if (Objects.nonNull(object)) {
                String paramName = "[param" + cnt +"] " + object.getClass().getSimpleName();
                String paramValue = " [value" + cnt +"] " + object;
                loggingMessage += paramName + paramValue + "\n";
                cnt++;
            }
        }
        log.info("{}", loggingMessage);
        // 해당 클래스 처리 전의 시간
        StopWatch sw = new StopWatch();
        sw.start();

        Object result = null;

        // 해당 클래스의 메소드 실행
        try{
            result = pjp.proceed();
        }
        catch (Exception e){
            log.warn("[ERROR] " + task + " 메서드 예외 발생 : " + e.getMessage());
            throw e;
        }

        // 해당 클래스 처리 후의 시간
        sw.stop();
        long executionTime = sw.getTotalTimeMillis();

        log.info("[ExecutionTime] " + task + " --> " + executionTime + " (ms)");

        return result;
    }

}
