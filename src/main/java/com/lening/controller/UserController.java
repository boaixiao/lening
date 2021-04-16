package com.lening.controller;

import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.entityvo.UserVo;
import com.lening.service.UserService;
import com.lening.utils.Page;
import com.lening.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 人员管理
     * @return
     */
    @RequestMapping("/getUserList")
    public List<UserBean> getUserList(){

        return userService.getUserList();
    }

  @RequestMapping("/getUserListConn")
    public Page<UserBean> getUserListConn(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize){
        UserBean ub = new UserBean();
        ub.setUname("陶");
        ub.setAge(18);
        return userService.getUserListConn(ub,pageNum,pageSize);
    }

    @RequestMapping("/getLogin")
    public ResultInfo getLogin(@RequestBody UserBean ub, HttpServletRequest request){
        UserBean userBean = userService.getLogin(ub);
        if(userBean==null){
            return new ResultInfo(false,"登录失败，用户名或者密码错误");
        }else{
            request.getSession().setAttribute("ub",userBean);
            return new ResultInfo(true,"登录成功");
        }
    }

    /**
     * 菜单
     * @return
     */
    @RequestMapping("/getMeunList")
    public List<MeunBean> getMeunList(){

        return userService.getMeunList();
    }

    @RequestMapping("/getUserVoById")
    public UserBean getUserVoById(Long id){

        return userService.getUserVoById(id);
    }

    @RequestMapping("/saveUserDept")
    public ResultInfo saveUserDept(Long id,@RequestBody Long[] deptids){
        try {
            userService.saveUserDept(id,deptids);
            return  new ResultInfo(true,"编辑成功");
        }catch (Exception e){
        return  new ResultInfo(false,"编辑失败");
        }
    }
}
