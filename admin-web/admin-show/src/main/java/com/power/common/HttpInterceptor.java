package com.power.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 *
 */
@Slf4j
public class HttpInterceptor  extends HandlerInterceptorAdapter  {


    /**
     * 前置检查
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String url = request.getRequestURI().toString();
        Map parameterMap = request.getParameterMap();
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        return true;
    }

    // controller处理完成
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        long startTime = (Long) request.getAttribute("requestStartTime");

        long endTime = System.currentTimeMillis();

        long executeTime = endTime - startTime;

        // log it
        if (executeTime > 1000) {
            System.out.println(" 执行耗时 : "
                    + executeTime + "ms");
        } else {
            System.out.println(" 执行耗时2 : "
                    + executeTime + "ms");
        }

    }

}
