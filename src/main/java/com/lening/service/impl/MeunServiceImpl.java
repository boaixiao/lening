package com.lening.service.impl;

import com.lening.entity.MeunBean;
import com.lening.entity.MeunBeanExample;
import com.lening.mapper.MeunMapper;
import com.lening.service.MeunService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeunServiceImpl implements MeunService {

    @Resource
    private MeunMapper meunMapper;

    public List<MeunBean> getMeunListByPid(Long pid) {
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        return meunMapper.selectByExample(example);
    }

    public void saveMeun(MeunBean meunBean) {
        if(meunBean!=null){
            if(meunBean.getCid()!=null){
                /**
                 * 这个修改是遇到实体类中字段值为空的时候，不修改，一般密码等
                 */
                meunMapper.updateByPrimaryKeySelective(meunBean);
                /**
                 * 实体类传递过来是什么，把数据库中全部修改为实体类中的值，即便实体类中为空，也把数据库中修改为空
                 * meunMapper.updateByPrimaryKey(meunBean);
                 */
            }else{
                //添加
                meunMapper.insertSelective(meunBean);
            }
        }
    }

    List<Long> ids = new ArrayList<Long>();
    public void deleteMeunByCid(Long cid) {

         getMeunListByPidDelete(cid);
         for (Long cid1 : ids){
             meunMapper.deleteByPrimaryKey(cid1);
         }
    }


    private void getMeunListByPidDelete(Long pid) {
        ids.add(pid);
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MeunBean> list = meunMapper.selectByExample(example);
        if(list!=null&&list.size()>=1){
            for(MeunBean bean :list){
                getMeunListByPidDelete(bean.getCid());
            }
        }
    }
}
