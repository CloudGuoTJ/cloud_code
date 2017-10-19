package com.yunma.service.dictionary;

import com.yunma.vo.dictionary.DictionaryVo;

public interface DictionaryService {

	/**
	 * 根据字典项名获取整个字典信息
	 * @param itemName
	 * @return
	 */
	public DictionaryVo getDictionaryByItemName(String itemName);
}
