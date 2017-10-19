package com.yunma.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 二维码分表切面
 */
@Aspect
public class SecutrityCodeAspect {
	
	@SuppressWarnings("unused")

	//定义切入点，提供一个方法，这个方法的名字就是改切入点的id  
	//com.yunma.service.secutrityCode.impl.SecurityCodeServiceImpl
	@Pointcut("execution(* com.yunma.service.product.*.*(..))")  
	public void a() {}//保留此方法
	
	//针对指定的切入点表达式选择的切入点应用前置通知  
    @Before("execution(* com.yunma.service.product.*.*(..))") 
	public void before(JoinPoint call) {
    	String className = call.getClass().getName();
    	String methodName = call.getSignature().getName();
    	Object [] object = call.getArgs();
    	for (Object obj : object) {
			System.out.println("params:"+obj.toString());
		}
    	
		System.out.println("123123");
		System.out.println("className: " + className);
		System.out.println("methodName: " + methodName);
	}
	
	/*private static String[] getFieldsName(Class cls,String clazzName,String methodName) {
		Map<String,Object> map = new HashMap<String,Object>();
		Class clazz = cls;
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String 
		}
	}*/
}
