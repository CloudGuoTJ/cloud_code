package com.github.sd4324530.fastweixin.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 微信公众平台交互操作基类，提供几乎所有微信公众平台交互方式
 * 基于springmvc框架，方便使用此框架的项目集成
 *
 * @author peiyu
 */
@Controller
public abstract class WeixinControllerSupport extends WeixinSupport {

	
    /**
     * 绑定微信服务器
     *
     * @param request 请求
     * @return 响应内容
     */
    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
    protected final String bind(HttpServletRequest request,HttpServletResponse response) throws IOException {
        if (isLegal(request)) {
//            绑定微信服务器成功
            PrintWriter writer = response.getWriter();
            writer.write(request.getParameter("echostr"));
            writer.close();
            return null;
        } else {
            //绑定微信服务器失败
            PrintWriter writer = response.getWriter();
            writer.write("");
            writer.close();
            return null;
        }
    }

    /**
     * 微信消息交互处理
     *
     * @param request http 请求对象
     * @return 响应给微信服务器的消息报文
     * @throws ServletException 异常
     * @throws IOException      IO异常
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    protected final String process(HttpServletRequest request) throws ServletException, IOException {
        if (!isLegal(request)) {
            return "";
        }
        return processRequest(request);
    }
}