package com.power.exception;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.io.InputStreamSource;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

@Aspect
@Slf4j
public class LogParamsAspectj {

    @Pointcut("execution(* com.power..*ServiceImpl.*(..))")
    public void serviceAspectj(){

    }

    @Around("serviceAspectj()")
    public Object doServiceAspectj(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        }catch (Exception ex){
            //如果出现异常，记录当前的参数数据。
            logParams(joinPoint);
            throw  ex;
        }
        return result;
    }



    @Pointcut("execution(* com.power..*Controller.*(..))")
    public void controllerAspectj(){
        System.out.println("aaa");
    }

    @Around("controllerAspectj()")
    public Object doControllerAspectj(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            System.out.println("aaa");
            result = joinPoint.proceed(joinPoint.getArgs());
        }catch (Exception ex){
            //如果出现异常，记录当前的参数数据。
            logParams(joinPoint);
            throw  ex;
        }
        return result;
    }


    private void logParams(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        log.info("----异常参数列表：" + joinPoint.getSignature().toString());
        try {
            for (int i = 0; i < args.length; i++) {
                try {
                    if (args[i] instanceof InputStreamSource) {
                        // 不记录上传文件
                    } else if (args[i] instanceof InputStream) {
                        //不记录文件流
                    } else if (args[i] instanceof OutputStream) {
                        //不记录文件流
                    } else if (args[i] instanceof ServletRequest) {
                        //不记请求
                    } else if (args[i] instanceof ServletResponse) {
                        //不记相应
                    } else {

                    }
                } catch (Exception ex) {
                    log.info("参数打印失败["+i+"]：", ex);
                }
            }
        } catch (Exception e) {
            log.info("参数打印失败：", e);
        }
    }

    public static void main(String[] args){
        String[] t=new String[]{"1","2"};

    }
}
