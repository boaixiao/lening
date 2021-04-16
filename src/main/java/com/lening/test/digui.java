package com.lening.test;

import org.junit.Test;

public class digui {
    /**
     * 计算阶乘
     */
    @Test
    public void test(){
        Long jiecheng = ceshi(7L);
        System.out.println(jiecheng);
    }

    public Long getjiecheng(Long x){
        if(x==1){
            return 1L;
        }
        return x*getjiecheng(x-1);
    }

    // 1 1 2 3 5 8 13 21
    public Long ceshi(Long x){
            if(x==1||x==2){
                return 1L;
            }
        return ceshi(x-1)+ceshi(x-2);
    }
}
