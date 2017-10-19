package com.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service基类
 */
public abstract class BaseService {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
}
