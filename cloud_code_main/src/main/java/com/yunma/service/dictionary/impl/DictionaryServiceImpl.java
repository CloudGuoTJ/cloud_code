package com.yunma.service.dictionary.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yunma.dao.dictionary.DictionaryDao;
import com.yunma.service.dictionary.DictionaryService;
import com.yunma.vo.dictionary.DictionaryVo;

@Service
public class DictionaryServiceImpl implements DictionaryService{
	
	@Resource
	private DictionaryDao dictionaryDao; 

	@Override
	public DictionaryVo getDictionaryByItemName(String itemName) {
		return dictionaryDao.getDictionaryByItemName(itemName);
	}

}
