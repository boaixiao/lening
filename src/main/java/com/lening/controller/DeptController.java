package com.lening.controller;

import com.lening.entity.DeptBean;
import com.lening.service.DeptService;
import com.lening.utils.Page;
import com.lening.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 创作时间：2021/4/8 9:01
 * 作者：李增强
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    @RequestMapping("/getDeptListConn")
    public Page<DeptBean> getDeptListConn(@RequestBody DeptBean deptBean,
                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "3") Integer pageSize){
        return deptService.getDeptListConn(deptBean,pageNum,pageSize);
    }

    @RequestMapping("/getDeptByDeptId")
    public DeptBean getDeptByDeptId(Long deptid){

        return deptService.getDeptByDeptId(deptid);
    }

    @RequestMapping("/saveDeptPost")
    public ResultInfo saveDeptPost(Long deptid,@RequestBody Long[] postids){
        try {
        deptService.saveDeptPost(deptid,postids);
        return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
        return new ResultInfo(false,"编辑失败");
        }
    }

}
