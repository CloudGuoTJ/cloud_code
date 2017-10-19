package com.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Excel公共视图类
 */
public class ExcelView extends AbstractExcelView {
	private String name;//文件名
	private String tableName;//表名
	private String[] titles;//字段
	private String[] values;//值
	private List list;//数据列表

	/**
	 * 创建对象时初始化值
	 * @param name 文件名
	 * @param tableName 表名
	 * @param titles 字段
	 * @param values 值
	 * @param list 数据列表
	 */
	public ExcelView(String name, String tableName, String[] titles, String[] values, List list) {
		this.name = name;
		this.tableName = tableName;
		this.titles = titles;
		this.values = values;
		this.list = list;
	}

	/**
	 * 构建视图文档
	 * @param model
	 * @param workbook
	 * @param request
	 * @param response
	 */
	public void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) {
		// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
		response.setContentType("APPLICATION/OCTET-STREAM");
		try {
			response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(name + ".xls", "UTF-8"));

			// 产生Excel表头
			HSSFSheet sheet = workbook.createSheet(tableName);
			HSSFRow header = sheet.createRow(0); // 第0行

			// 产生标题列
			for (int i = 0; i < titles.length; i++){
				header.createCell(i).setCellValue(titles[i]);
			}

			/*HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));*/

			// 填充数据
			int rowNum = 1;
			for (int i = 0; i < list.size(); i++) {
				JSONObject obj = JSONObject.parseObject(JSONObject.toJSON(list.get(i)) + "");

				HSSFRow row = sheet.createRow(rowNum++);
				for(int j = 0; j < values.length; j++){
					row.createCell(j).setCellValue(obj.get(values[j]) + "");
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.info("构建视图文档时：", e);
		} catch (Exception e) {
			logger.info("构建视图文档时：", e);
		}
	}
}
