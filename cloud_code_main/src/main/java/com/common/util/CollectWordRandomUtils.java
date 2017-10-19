package com.common.util;

import java.util.HashMap;
import java.util.Map;

public class CollectWordRandomUtils {
	
	 /** 
     * 概率选择 
     * @param keyChanceMap key为唯一标识，value为该标识的概率，是去掉%的数字 
     * @return 被选中的key。未选中返回null 
     */  
     public static String chanceSelect(Map<String, Double> keyChanceMap) {  
          if(keyChanceMap == null || keyChanceMap.size() == 0)  
               return null;  
            
          Double sum = 0.0;  
          for (Double value : keyChanceMap.values()) {  
               sum += value;  
          }  
          
          // 随机生成从0到1的双精度的小数
          Double rand = Math.random();  
            
          for (Map.Entry<String, Double> entry : keyChanceMap.entrySet()) {  
               rand -= entry.getValue();  
               // 选中  
               if(rand <= 0) {  
                    return entry.getKey();  
               }  
          }  
            
          return null;  
     }  
     
     public static void main(String[] args) {
    	 Map<String, Double> keyChanceMap = new HashMap<String, Double>();  
         keyChanceMap.put("a", 0.20);  
         keyChanceMap.put("b", 0.20);
         keyChanceMap.put("c", 0.20);  
         keyChanceMap.put("d", 0.20);
         keyChanceMap.put("e", 0.010);
         
         Map<String, Double> count = new HashMap<String, Double>();  
         for (int i = 0; i < 100; i++) {  
              String key = CollectWordRandomUtils.chanceSelect(keyChanceMap);  
              
              //System.out.println(key);
              
              if(count.containsKey(key)) {  
                   count.put(key, count.get(key) + 1);  
              }  
              else {  
                   count.put(key, 1.0);  
              }  
         }  
           
         // print  
         for (String key : count.keySet()) {  
              System.out.println(key + ":" + count.get(key));  
         }  
	}

}
