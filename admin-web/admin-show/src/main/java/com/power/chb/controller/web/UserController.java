package com.power.chb.controller.web;


import com.power.chb.service.sys.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Api(value="用户登陆",tags={"用户登陆"})
public class UserController {

    @Resource
    private SysUserService sysUserService;


    @ApiOperation("用户-登陆")
    @RequestMapping("/login")    @PostMapping(value = "/save")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //TODO 登陆

    }

    @ApiOperation("用户-退出")
    @RequestMapping("/out")    @PostMapping(value = "/save")
    public void out(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //TODO 登陆

    }
}
