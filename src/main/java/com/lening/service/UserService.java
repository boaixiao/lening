package com.lening.service;

import com.lening.entity.MeunBean;
import com.lening.entity.UserBean;
import com.lening.entityvo.UserVo;
import com.lening.utils.Page;

import java.util.List;

public interface UserService {
    List<UserBean> getUserList();

    Page<UserBean> getUserListConn(UserBean ub, Integer pageNum, Integer pageSize);

    List<MeunBean> getMeunList();

    UserBean getUserVoById(Long id);

    UserBean getLogin(UserBean ub);

    void saveUserDept(Long id, Long[] deptids);
}
