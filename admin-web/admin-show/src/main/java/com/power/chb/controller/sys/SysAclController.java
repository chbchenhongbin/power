package com.power.chb.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.power.chb.dto.sys.SysDeptTreeDto;
import com.power.chb.model.sys.SysDept;
import com.power.chb.ro.sys.SysAclRo;
import com.power.chb.ro.sys.SysDeptRo;
import com.power.chb.service.sys.SysAclService;
import com.power.chb.service.sys.SysDeptService;
import com.power.chb.service.sys.SysTreeService;
import com.power.chb.vo.sys.dept.SysDeptVo;
import com.power.exception.BizException;
import com.power.result.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@Api(value="权限点管理",tags={"权限点管理"})
@RestController
@Slf4j
@RequestMapping("/api/acl")
public class SysAclController {

        @Resource
        private SysAclService sysAclService;

        @ApiOperation("权限点管理 添加")
        @PostMapping(value = "/save")
        public ResultEntity save(SysAclRo ro) throws BizException {
            int row= sysAclService.saveBean(ro);
            return row>0?ResultEntity.success("保存成功"):ResultEntity.failed("保存失败");
        }


        @ApiOperation("权限点管理 修改")
        @PostMapping(value = "/update")
        public ResultEntity update(SysAclRo ro) throws BizException {
            int row= sysAclService.updateBean(ro);
            return row>0?ResultEntity.success("保存成功"):ResultEntity.failed("保存失败");
        }


}
