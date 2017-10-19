package com.yunma.utils.coupon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.yunma.entity.coupon.Coupon;

/**
 * Created by Administrator on 2017/5/13.
 */
public class RuleUtil {

/*public static List<Coupon> GetWxCoupnListByRule(String[] blacklist,String[] whitelist,List<Coupon> list1){*/
	public static List<Coupon> GetWxCoupnListByRule(Coupon coupon, List<Coupon> list1, String[] blacklist, String[] whitelist){
		List<Coupon> list=new ArrayList<Coupon>();
		System.out.println("开始：");
		for (Coupon coupon2 : list1) {
			System.out.println(coupon2);
		}
		
    if (blacklist==null&&whitelist!=null){
        //白名单
        for (int i=0;i<list1.size();i++){
            for (int j=0;j<whitelist.length;j++){
                if ((list1.get(i).getVendorId()+"").equals(whitelist[j])||(list1.get(i).getVendorId()+"")==whitelist[j]){
                    list.add(list1.get(i));
                    /*i--;*/
                }
            }
        }
    }else if (whitelist==null&&blacklist!=null){
    	List<Integer> index = new ArrayList<Integer>();
        //黑名单
        /*for (int i=0;i<list1.size();i++){
            for (int j=0;j<blacklist.length;j++){
                if ((list1.get(i).getVendorId()+"").equals(blacklist[j])||(list1.get(i).getVendorId()+"")==blacklist[j]){
                   list1.remove(i);
                    i--;
                	//index.add(i);
                } else {
                	index.add(i);
                }
            }
        }*/
       //删除黑名单中的数据
        List<Integer> list2 = new ArrayList<Integer>();
        for (Coupon lists : list1) {
        	list2.add(lists.getVendorId());
        }
        Iterator<Integer> iter = list2.iterator();
        while (iter.hasNext()) {
        	Integer temp = iter.next();
        	for (int j=0;j<blacklist.length;j++){
                if ((temp+"").equals(blacklist[j])){
                	iter.remove();
                }
            }
        }
        //将删除后的数组添加到list中
        for (int i=0;i<list2.size();i++){
        	for (int j=0 ; j<list1.size() ; j++) {
        		if (list2.get(i) == list1.get(j).getVendorId()) {
        			System.out.println(list2.get(i));
        			list.add(list1.get(j));
        		}
        	}
        }

    }/*else if(whitelist!=null&&blacklist!=null){
        //黑白共存

        for (int i=0;i<list1.size();i++){
            for (int j=0;j<whitelist.length;j++){
                if ((list1.get(i).getVendorId()+"").equals(whitelist[j])||(list1.get(i).getVendorId()+"")==whitelist[j]){
                    list.add(list1.get(i));
                }
            }
        }

        for (int i=0;i<list1.size();i++){
            for (int j=0;j<blacklist.length;j++){
                if ((list1.get(i).getVendorId()+"").equals(blacklist[j])||(list1.get(i).getVendorId()+"")==blacklist[j]){
                    list1.remove(i);
                }
            }
        }
        list=list1;

    }*/else if(whitelist==null&&blacklist==null){
        list=list1;
        //黑白都没有
    }
    else {
        //白黑名单
        for (int i=0;i<list1.size();i++){
            for (int j=0;j<whitelist.length;j++){
                if ((list1.get(i).getVendorId()+"").equals(whitelist[j])||(list1.get(i).getVendorId()+"")==whitelist[j]){
                    list.add(list1.get(i));
                    /*i--;*/
                }
            }
        }
        /*for (int i=0;i<list1.size();i++){
            for (int j=0;j<blacklist.length;j++){
                if ((list1.get(i).getVendorId()+"").equals(blacklist[j])||(list1.get(i).getVendorId()+"")==blacklist[j]){
                    list1.remove(i);
                    i--;
                	index.add(i);
                }
            }
        }*/
        List<Integer> list2 = new ArrayList<Integer>();
        for (Coupon lists : list1) {
        	list2.add(lists.getVendorId());
        }
        Iterator<Integer> iter = list2.iterator();
        while (iter.hasNext()) {
        	Integer temp = iter.next();
        	for (int j=0;j<blacklist.length;j++){
                if ((temp+"").equals(blacklist[j])){
                	iter.remove();
                }
            }
        }
        //将删除后的数组添加到list中
        for (int i=0;i<list2.size();i++){
        	System.out.println(list2.get(i));
            list.add(list1.get(i));
        }


    }

    if (list.size()<=3){
    	if (coupon != null) {
    		list.add(coupon);
    	}
        return list;
    }else {
        List<Coupon> list3=new ArrayList<Coupon>();
        List<Integer> index= new ArrayList<Integer>();
        index.add(-1);
        index.add(-1);
        index.add(-1);

        List<Integer> sd=NunUtil(0,list.size()-1,index);
        for (int i=0;i<sd.size();i++){
            try {
                list3.add( list.get(sd.get(i)));
            }catch (Exception e){
                System.out.println(e);
            }

        }
        if (coupon != null) {
        	list3.add(coupon);
        }
        System.out.println("最终");
        for (Coupon coupons : list3) {
			System.out.println(coupons);
		}
        return list3;
    }
}
    private static List<Integer> NunUtil(int min,int max,List<Integer> index){

            //循环Index数组
            for (int i=0;i<index.size();i++){
                if (index.get(i)!=-1){
                    continue;
                }
                //生产随机数
                int re=min + (int)(Math.random() * ((max - min) + 1));
                //循环判断是否存在了
                if (index.contains(re)){
                    index=NunUtil( min, max, index);
                }else {
                    index.set(i,re);
                }
/*
               for (int as:index){
                   if (re==as){
                       index= NunUtil( min, max, index);
                       break;
                   }

                   index[i]=re;
               }
*/

            }
        return index;
    }
    
    /*public static void main(String[] args) {
    	Coupon coupon1 = new Coupon("1","测试1",3);
    	Coupon coupon2 = new Coupon("2","测试2",2);
    	Coupon coupon3 = new Coupon("3","测试3",2);
    	Coupon coupon4 = new Coupon("4","测试4",4);
    	Coupon coupon5 = new Coupon("5","测试5",1);
    	Coupon coupon6 = new Coupon("6","测试6",3);
    	List<Coupon> list = new ArrayList<Coupon>();
    	list.add(coupon1);
    	list.add(coupon2);
    	list.add(coupon3);
    	list.add(coupon4);
    	list.add(coupon5);
    	list.add(coupon6);
    	
    	Collections.shuffle(list);
    	
    	Set<Integer> set = new HashSet<Integer>();
    	for (Coupon coupon : list) {
    		set.add(coupon.getVendorId());
		}
    	for (Integer integer : set) {
			System.out.println(integer);
		}
    	list.clear();
    	//list.addAll(set);
		
		for (Coupon integer : list) {
			System.out.println(integer + " ");
		}
	}*/

}
