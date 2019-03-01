package com.taotao.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import taotao.common.utils.CookieUtils;
import taotao.common.utils.JsonUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
/*
 * 判断用户是否登录拦截器
 * */
public class LoginInterceptor implements HandlerInterceptor {
   
    @Value("${SSO_URL}")
    private String SSO_URL;
    
    @Autowired
    private UserService userService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //执行handler之前先执行此方法
        //1.从cookie中取token信息
        String token = CookieUtils.getCookieValue(request, "TOKEN_KEY");
        //2.如果取不到跳转到sso登录页面，需要把当前请求的url作为参数传递给sso，sso登陆成功后跳转回请求页面
        if (StringUtils.isBlank(token)) {
            //取当前请求的url
            String requestURL = request.getRequestURL().toString();
            //跳转到登录页面
            response.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
            //拦截
            return false;
        }
        //3.取到token，调用sso系统服务判断用户是否登录
        TaotaoResult taotaoResult = userService.getUserByToken(token);
        //4.如果用户未登录，未取到用户信息，跳转到sso登录页面
        if (taotaoResult.getStatus() != 200) {
            //取当前请求的url
            String requestURL = request.getRequestURL().toString();
            //跳转到登录页面
            response.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
            //拦截
            return false;
        }
        //5.如果取到，放行
        //把用户信息放到request中
        TbUser user = (TbUser) taotaoResult.getData();
        request.setAttribute("user", user);
        //返回值true：放行，false：拦截
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // handler执行之后，modelAndView返回之前

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        // modelAndView返回之后，异常处理

    }

}
