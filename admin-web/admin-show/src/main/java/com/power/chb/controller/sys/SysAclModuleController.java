package com.power.chb.controller.sys;
import com.power.chb.dto.sys.SysAclModuleDto;
import com.power.chb.model.sys.SysAclModule;
import com.power.chb.ro.sys.SysAclModuleRo;
import com.power.chb.service.sys.SysAclModuleService;
import com.power.chb.service.sys.SysTreeService;
import com.power.cutils.IpUtil;
import com.power.cutils.RequestHolder;
import com.power.result.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value="权限管理",tags={"权限管理"})
@RestController
@Slf4j
@RequestMapping("/api/aclModule")
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleService;

    @Resource
    private SysTreeService sysTreeService;

    @ApiOperation("权限模块-添加")
    @PostMapping(value = "/save")
    public ResultEntity save(SysAclModuleRo ro, HttpServletRequest request){

        String ip=IpUtil.getUserIP(request);
        int row= sysAclModuleService.saveBean(ro);
        return row>0? ResultEntity.success("保存成功"):ResultEntity.failed("保存失败");
    }


    @ApiOperation("权限模块-修改")
    @PostMapping(value = "/update",consumes = "application/json")
    public ResultEntity update(SysAclModuleRo ro, HttpServletRequest request){
        String ip=IpUtil.getUserIP(request);
        int row= sysAclModuleService.updateBean(ro);
        return row>0? ResultEntity.success("保存成功"):ResultEntity.failed("保存失败");
    }

    @ApiOperation("权限模块-树")
    @GetMapping(value = "/tree")
    public ResultEntity<List<SysAclModuleDto>> tree(){
        List<SysAclModuleDto> list= sysTreeService.listAclModule();
        return   ResultEntity.success(list);
    }

    @ApiOperation("权限模块-详情")
    @GetMapping(value = "/info")
    public ResultEntity<SysAclModule> info(Integer id ){

        if(id==null)
        {
            return   ResultEntity.failed("参数的参数不能空");
        }
        SysAclModule sysAclModule = sysAclModuleService.selectOneAclModule(id);
        return   ResultEntity.success(sysAclModule);
    }


}
