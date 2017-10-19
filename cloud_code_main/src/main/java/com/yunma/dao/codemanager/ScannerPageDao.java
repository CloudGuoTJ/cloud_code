package com.yunma.dao.codemanager;

import java.util.List;
import java.util.Map;

import com.yunma.entity.codemanager.CodemanagerEntity;
import com.yunma.model.ModeFunctionFile;
import com.yunma.vo.codemanager.CodeManagerVo;

public interface ScannerPageDao {

	/**
	 * 新增扫码页
	 * @param entity
	 * @return
	 */
	int addCodeManager(CodemanagerEntity entity);
	
	/**
	 * 修改扫码页
	 * @param entity
	 * @return
	 */
	int updateCodeManager(CodemanagerEntity entity);
	
	/**
	 * 删除扫码页
	 * @param entity
	 * @return
	 */
	int deleteCodeManager(Integer id);

	/**
	 * 分页查询防伪扫描管理页面活动
	 * @param vendorId
	 * @return
	 */
	List<CodeManagerVo> getCodeManagerAll(Map<String,Object> map);
	
	/**
	 * 分页查询溯源扫码管理页面活动
	 * @param vendorId
	 * @return
	 */
	List<CodeManagerVo> getCodeManagerAllForTracy(Map<String,Object> map);
	
	/**
	 * 查询防伪扫描页总数
	 * @return
	 */
	int countScannerPage(Integer vendorId);
	
	/**
	 * 查询溯源扫描页总数
	 * @return
	 */
	int countScannerPageForTracy(Integer vendorId);
	
	/**
	 * 查询单个
	 * @param id
	 * @return
	 */
	CodeManagerVo getCodeManagerById(Integer id);
	
	/**
	 * 修改活动状态
	 * @param id
	 * @return
	 */
	int updateScaPageStatus(Integer id);
	
	/**
	 * 新增模板功能文件
	 * @param file
	 * @return
	 */
	int saveModeFunFile(ModeFunctionFile file);
	
	/**
	 * 修改所选取的模板的状态
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
	
	
	
	
}
