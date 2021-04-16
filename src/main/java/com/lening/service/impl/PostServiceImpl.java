package com.lening.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lening.entity.*;
import com.lening.mapper.MeunMapper;
import com.lening.mapper.PostMapper;
import com.lening.service.PostService;
import com.lening.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;


    @Resource
    private MeunMapper meunMapper;

    public Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize) {
        //没有默认值的话，这地方容易出现空指针
        PageHelper.startPage(pageNum, pageSize);

        //条件查询
        PostBeanExample example = new PostBeanExample();
        PostBeanExample.Criteria criteria = example.createCriteria();
        //前台是否输入条件
        if(postBean!=null){
            if(postBean.getPostname()!=null&&postBean.getPostname().length()>=1){
                criteria.andPostnameLike("%"+postBean.getPostname()+"%");
            }
        }
        List<PostBean> list = postMapper.selectByExample(example);
        //分页，pagehelper的分页
        PageInfo pageInfo = new PageInfo(list);
        //总记录数查询出来，类型转换一下，分页助手的long类型，我们要转成Integer类型
        Long total = pageInfo.getTotal();
        //使用我们自己的分页工具类，我们接受的值类型不一样，转化一下
        Page<PostBean> page = new Page(pageInfo.getPageNum()+"", total.intValue(), pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    public List<MeunBean> getMeunListById(Long postid) {
        //所有的全查菜单
        List<MeunBean> list = meunMapper.selectByExample(null);
        //然后再把当前职位拥有的那些菜单，去中间表去查.
        List<Long> meunids = postMapper.getPostMeunids(postid);
        //要是原来有菜单的需要回显ztree回显
        if(meunids!=null&&meunids.size()>=1){
            for (Long meunid : meunids) {
                for (MeunBean bean : list) {
                    if(meunid.equals(bean.getCid())){
                        bean.setChecked(true);
                        break;
                    }
                }
            }
        }
        return list;
    }

    public void savePostMeun(Long postid, Long[] ids) {
        /**
         * 先删后加,中间表的数据
         */
        postMapper.deletePostMeunByPostid(postid);
        if(ids!=null&&ids.length>=1){
            for (Long meunid : ids) {
                postMapper.savePostMeun(postid,meunid);
            }
        }
    }
}
