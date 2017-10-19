package com.common.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class TxtExportUtils {

    private static final String ENTER = "\r\n";

    /**
     * 导出txt文档
     *
     * @param response
     * @param list     ：要导出的每一条记录
     * @param txtName  　：文件名
     * @param prefix   ：每一条记录前面需要加上的前缀
     * @param plus     　：txt文档开始部分
     */
    public static void exportTxt(HttpServletResponse response, List<String> list, String txtName, String prefix, String plus) {
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            txtName = URLEncoder.encode(txtName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String((txtName + ".txt").getBytes("GBK"), "iso8859-1"));
            response.setContentType("text/plain");

            PrintWriter output = response.getWriter();
            //txt文档开头部分
            if (StringUtils.isNotBlank(plus))
                output.print(plus + ENTER);

            //把内容写入文件
            for (int i = 0; i < list.size(); i++) {
                output.print(prefix + list.get(i) + ENTER);
            }
            output.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 导出溯源码去除前缀追加空格
     * @param response
     * @param list
     * @param txtName
     * @param prefix
     * @param plus
     */
    public static void exportTxtPriavate(HttpServletResponse response, List<String> list, String txtName, String prefix, String plus) {
        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            txtName = URLEncoder.encode(txtName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String((txtName + ".txt").getBytes("GBK"), "iso8859-1"));
            response.setContentType("text/plain");
            
            
            
            
            PrintWriter output = response.getWriter();
            //txt文档开头部分
            if (StringUtils.isNotBlank(plus))
                output.print(plus + ENTER);

            //把内容写入文件
            for (int i = 0; i < list.size(); i++) {
            	if(list.get(i) == "  "){
            		prefix = "  ";
            		output.print(prefix + list.get(i) + ENTER);
            	}else{ 
            		String prefixPri = "https://ym-a.top/t/";
            		output.print(prefixPri + list.get(i) + ENTER);
            	}
            }
            output.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
