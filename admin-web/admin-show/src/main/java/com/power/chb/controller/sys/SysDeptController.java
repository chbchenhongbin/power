package com.power.chb.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.power.chb.dto.sys.SysDeptTreeDto;
import com.power.chb.model.sys.SysDept;
import com.power.chb.ro.sys.SysDeptRo;
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


@Api(value="部门管理",tags={"部门管理"})
@RestController
@Slf4j
@RequestMapping("/api/dept")
public class SysDeptController {

        @Resource
        private SysDeptService sysDeptService;

         @Resource
        private SysTreeService sysTreeService;

        @ApiOperation("部门管理 添加")
        @PostMapping(value = "/save")
        public ResultEntity save(SysDeptRo ro) throws BizException {
            int row= sysDeptService.savaBean(ro);
            return row>0?ResultEntity.success("保存成功"):ResultEntity.failed("保存失败");
        }

        @ApiOperation("部门管理 树")
        @GetMapping(value = "/tree")
        public ResultEntity<List<SysDeptTreeDto>> tree()   {
            List<SysDeptTreeDto> list=sysTreeService.listDept();
            String treeNodesJson = JSONArray.toJSONString(list);
            return  ResultEntity.success(list);
        }

        @ApiOperation("部门管理 修改")
        @PostMapping(value = "/update")
        public ResultEntity update(SysDeptRo ro) throws BizException {
            int row= sysDeptService.updateBean(ro);
            return row>0?ResultEntity.success("修改成功"):ResultEntity.failed("修改失败");
        }

        @ApiOperation("部门管理 列表")
        @PostMapping(value = "/list")
        public ResultEntity< List<SysDept> > list(){
                List<SysDept> list=sysDeptService.sysDeptList("");
            return ResultEntity.success(list);
        }

        @ApiOperation("部门管理 列表")
        @GetMapping(value = "/getOneById")
        public ResultEntity<SysDeptVo> getOneById(Integer id){
            SysDeptVo vo=sysDeptService.selectOne(id);
            return ResultEntity.success(vo);
        }



}
