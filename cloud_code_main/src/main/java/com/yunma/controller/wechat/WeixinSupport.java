package com.yunma.controller.wechat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public abstract class WeixinSupport extends com.github.sd4324530.fastweixin.servlet.WeixinSupport{

	@RequestMapping(method = RequestMethod.GET)
//  @ResponseBody
  protected final String bind(HttpServletRequest request,HttpServletResponse response) throws IOException {
      if (isLegal(request)) {
//          绑定微信服务器成功
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
}
