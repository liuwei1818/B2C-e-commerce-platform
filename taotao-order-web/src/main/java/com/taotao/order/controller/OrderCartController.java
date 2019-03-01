package com.taotao.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import taotao.common.utils.CookieUtils;
import taotao.common.utils.JsonUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;

@Controller
public class OrderCartController {
    
    @Value("${CART_KEY}")
    private String CART_KEY;
    
    @Autowired
    private OrderService orderService;
    
    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        //用户必须是登录状态
        //取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        System.out.println(user.getUsername());
        //根据用户信息取收货地址列表，使用静态数据
        //把收货地址取出传递给页面
        //从cookie中取购物车展示到页面
        List<TbItem> cartList = getCartItemList(request);
        request.setAttribute("cartList", cartList);
        //返回逻辑视图
        return "order-cart";
    }
    
    private List<TbItem> getCartItemList(HttpServletRequest request) {
        //从cookie中去购物车商品列表
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if(StringUtils.isBlank(json)) {
            //如果没有内容，返回一个空的列表
            return new ArrayList<>();
        }
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }
    
    //生成订单处理
    @RequestMapping(value="/order/create",method=RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,Model model) {
        //生成订单
        TaotaoResult result = orderService.createOrder(orderInfo);
        //返回逻辑视图
        model.addAttribute("orderId",result.getData().toString());
        model.addAttribute("payment", orderInfo.getPayment());
        //预计三天后送达
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
        return "success";
    }
}
