package com.yunma.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class testMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		shuffle(); 
	}
	public static void shuffle(){    
        int[] x = {1,2,3,4,5,6,7,8,9};    
        List list = new ArrayList();    
        for(int i = 0;i < x.length;i++){    
            System.out.print(x[i]+", ");    
            list.add(x[i]);    
        }    
        System.out.println();    
            
        Collections.shuffle(list);    
            
        Iterator ite = list.iterator();    
       while(ite.hasNext()){    
            System.out.print(ite.next().toString()+", ");    
        }    
    }    

}
