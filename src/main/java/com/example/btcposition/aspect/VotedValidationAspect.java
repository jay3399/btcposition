package com.example.btcposition.aspect;

import com.example.btcposition.infrastructure.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class VotedValidationAspect {

    private final JwtTokenUtil jwtTokenUtil;

    @Before("@annotation(com.example.btcposition.aspect.VotedValidation)")
    public void validation(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        jwtTokenUtil.isVoted(request);
    }



}
