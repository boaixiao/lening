package com.lening.service;

import com.lening.entity.DeptBean;
import com.lening.utils.Page;

/**
 * 创作时间：2021/4/8 9:03
 * 作者：李增强
 */
public interface DeptService {
    Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize);

    DeptBean getDeptByDeptId(Long deptid);

    void saveDeptPost(Long deptid, Long[] postids);
}
