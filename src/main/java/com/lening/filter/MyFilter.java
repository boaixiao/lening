package com.lening.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * servlet过滤器
 * 一般不用注解，配置文件
 */
public class MyFilter implements Filter {
    /**
     * 读取参数
     * 过滤器要是再web.xml中配置参数，读取的话，再init中读取
     * @param filterConfig
     * @throws ServletException
     */
    Set<String> notCheckUrl= new HashSet<String>();
    public void init(FilterConfig filterConfig) throws ServletException {
        String url = filterConfig.getInitParameter("notCheckUrl");
        //遍历完之后是一个string数组用","隔开
        for (String s : url.split(",")) {
            //add(s.trim())防止有空格产生
            notCheckUrl.add(s.trim());
        }
    }

    /**
     * 判断
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        /**
         * 要是这个请求过来的url是不需要过滤的，不管怎么样都直接放行
         */
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        System.out.println(uri);
        //判断如果notCheckUrl包含uri就直接放行
        if(notCheckUrl.contains(uri)){
            chain.doFilter(request, response);
        }else{
            //要过滤,首先判断她有没有登录，要是没有登录就去登录需
            Object ub = req.getSession().getAttribute("ub");
            if(ub==null){
                //直接去登录
                req.getRequestDispatcher("/index.html").forward(request,response);
            }else{
                chain.doFilter(request, response);
            }
        }
    }

    public void destroy() {

    }
}
