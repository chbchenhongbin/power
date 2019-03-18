package com.power.chb.controller.sys;

import com.power.chb.ro.sys.SysRoleRo;
import com.power.chb.ro.sys.SysUserRo;
import com.power.chb.service.sys.SysRoleService;
import com.power.chb.service.sys.SysUserService;
import com.power.exception.BizException;
import com.power.result.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value="角色管理",tags={"角色管理"})
@RestController
@Slf4j
@RequestMapping("/api/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @ApiOperation("角色-添加")
    @PostMapping(value = "/save")
    public ResultEntity save(SysRoleRo ro) throws BizException {
        int row= sysRoleService.saveBean(ro);
        return row>0?ResultEntity.success("保存成功"):ResultEntity.failed("保存失败");
    }


    @ApiOperation("角色-修改")
    @PostMapping(value = "/update")
    public ResultEntity update(SysRoleRo ro) throws BizException {
        int row= sysRoleService.updateBean(ro);
        return row>0?ResultEntity.success("修改成功"):ResultEntity.failed("修改失败");
    }



}
