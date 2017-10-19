package com.yunma.service.codemanager;

import com.yunma.entity.codemanager.CodemanagerEntity;
import com.yunma.model.ModeFunctionFile;
import com.yunma.utils.PageBean;
import com.yunma.vo.codemanager.CodeManagerVo;

public interface ScannerPageService {
	
	
	/**
	 * 新增扫码活动（防伪溯源同一个接口）
	 * @param entity
	 * @return
	 */
	int addCodeManager(CodemanagerEntity entity);
	
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	int updateCodeManager(CodemanagerEntity entity);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int deleteCodeManager(Integer id);
	
	/**
	 * 分页显示防伪扫码活动
	 * @param page
	 * @param keyword
	 * @param vendorId
	 * @return
	 */
	public PageBean getCodeManagerAll(PageBean page,String keyword,Integer vendorId);
	
	/**
	 * 分页显示溯源扫码活动
	 * @param page
	 * @param keyword
	 * @param vendorId
	 * @return
	 */
	public PageBean getCodeManagerAllForTracy(PageBean page,String keyword,Integer vendorId);
	
	/**
	 * 查询单个扫码活动
	 * @param id
	 * @return
	 */
	CodeManagerVo getCodeManagerById(Integer id);
	
	/**
	 * 修改扫码活动状态
	 * @param id
	 * @return
	 */
	int updateScaPageStatus(Integer id);
	
	
	int saveModeFunFile(ModeFunctionFile file);
	
	/**
	 * 修改模板状态
	 * @param modeId
	 * @return
	 */
	int updateModeStatus(Integer modeId);
	
	/**
	 * 修改模板使用状态
	 * @param modeId
	 * @return
	 */
	int updateModeStatus1(Integer modeId);
	
	/**
	 * 活动状态为1时改变活动状态
	 * @param id
	 * @return
	 */
	int updateScaPageStatus1(Integer id);
	
	/**
	 * 分页显示防伪扫码活动的订单
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	PageBean getOrderInfoForActiv(PageBean pageBean,Integer vendorId);
	
	/**
	 * 分页显示溯源扫码活动的订单
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	PageBean getOrderInfoForTracyActiv(PageBean pageBean,Integer vendorId);
	
	/**
	 * 修改防伪扫码活动订单的状态
	 * @param orderId
	 * @return
	 */
	int updateOrderInfoForActiv(Integer orderId);
	
	/**
	 * 修改溯源扫码活动订单的状态
	 * @param orderId
	 * @return
	 */
	int updateOrderInfoForTracingActiv(Integer orderId);

}
