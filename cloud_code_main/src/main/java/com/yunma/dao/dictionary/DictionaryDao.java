package com.yunma.dao.dictionary;

import org.springframework.stereotype.Repository;

import com.yunma.dao.BaseMapper;
import com.yunma.vo.dictionary.DictionaryVo;

@Repository("dictionaryDao")
public interface DictionaryDao extends BaseMapper{

	/**
	 * 根据字典项名获取整个字典信息
	 * @param itemName
	 * @return
	 */
	public DictionaryVo getDictionaryByItemName(String itemName);

}
