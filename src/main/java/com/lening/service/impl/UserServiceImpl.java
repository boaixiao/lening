package com.lening.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.DeptBean;
import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.entity.UserBeanExample;

import com.lening.mapper.DeptMapper;
import com.lening.mapper.MeunMapper;
import com.lening.mapper.UserMapper;
import com.lening.service.UserService;
import com.lening.utils.MD5key;
import com.lening.utils.Page;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private MeunMapper meunMapper;

    public List<UserBean> getUserList() {

        return userMapper.selectByExample(null);
    }

    public Page<UserBean> getUserListConn(UserBean ub, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        UserBeanExample example = new UserBeanExample();
        UserBeanExample.Criteria criteria = example.createCriteria();
        if(ub!=null){
            //按照用户名模糊
            if(ub.getUname()!=null&&ub.getUname().length()>=1){
                criteria.andUnameLike("%"+ub.getUname()+"%");
            }
            if(ub.getAge()!=null){
                criteria.andAgeGreaterThanOrEqualTo(ub.getAge());
            }
            if(ub.getEage()!=null){
                criteria.andAgeLessThanOrEqualTo(ub.getEage());
            }
        }
        List<UserBean> list = userMapper.selectByExample(example);
        PageInfo<UserBean> pageInfo = new PageInfo<UserBean>(list);
        Long total = pageInfo.getTotal();
        Page page = new Page(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    /**
     * ztree回显
     * @return
     */
    public List<MeunBean> getMeunList() {
        Long[] ids={1L,2L,3L};
        List<MeunBean> list = meunMapper.selectByExample(null);
        for (Long id : ids) {
            for (MeunBean mb : list) {
                if(id.equals(mb.getCid())){
                    mb.setChecked(true);
                    break;
                }
            }
        }
        return list;
    }

    public UserBean getUserVoById(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        /**
         * 去查询这个用户有那些部门，咱们的设计是一对多，所以页面待会儿要不用复选框接收，
         * 要是不用下拉框接收，但是下拉框要支持多选
         */
        Long[] deptids = userMapper.getUserDeptidsById(id);
        userBean.setDeptids(deptids);
        List<DeptBean> deptBeanList = deptMapper.selectByExample(null);
        userBean.setDlist(deptBeanList);
        return userBean;
    }

    public UserBean getLogin(UserBean ub) {
        //先用户用户名或者手机号，都是用用户名接收的，有可能用户输入的手机号，都是strint类型，无所谓
        if(ub!=null){
            List<UserBean> list = userMapper.getLogin(ub);
            if(list!=null&&list.size()==1){
                //到了这里表示用用户名或者手机号码查询到了
                //开始比对密码，比对密码之前需要加盐加密
                //加密算法有很多，常用的md5，bscript等
                UserBean userBean = list.get(0);
                //页面用户输入的密码，进行加密加盐后和数据库里面的密码进行比较，相等正确，不相等就错误
                String pwd = ub.getPwd();
                //也就是这里的加盐加密规则和注册的要一致，就好了，都是一个人负责的
                pwd = userBean.getPwdsalt()+pwd+userBean.getPwdsalt();
                MD5key md5key = new MD5key();
                String newpwd = md5key.getkeyBeanofStr(pwd);
                //相等就返回，其他都是空
                if(newpwd.equals(userBean.getPwd())){
                    return userBean;
                }
            }
        }
        return null;
    }

    public void saveUserDept(Long id, Long[] deptids) {
        /***
         *  修改科室，要把该用户原来的科室删掉，再次新增
         *  还需要把原来的职位也删除掉（科室都变了，职位没了）
         */
        userMapper.deleteUserPostById(id);
        userMapper.deleteUserDeptById(id);
        if(deptids!=null&&deptids.length>=1){
            for (Long deptid : deptids) {
                userMapper.insertUserDept(id,deptid);
            }
        }
    }

    @Test
    public void test(){
        String pwd="123456";
        pwd="1234"+pwd+"1234";
        MD5key md5key = new MD5key();
        String newpwd = md5key.getkeyBeanofStr(pwd);
        System.out.println(newpwd);
    }
}
