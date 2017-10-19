package com.common.util;


import java.math.BigDecimal;
import java.util.*;

import com.yunma.entity.redEnvelope.RedEnvRule;

/**
 * Created by Administrator on 2016/5/24.
 */
public class HongBaoUtils {

    public static List<Integer> process(int money, int count, int max, int min) {
        if (count == 0) {
            return null;
        }
        BigDecimal minBig = new BigDecimal(min);
        BigDecimal maxBig = new BigDecimal(max);
        
        BigDecimal moneyBig = new BigDecimal(money);
        BigDecimal countBig = new BigDecimal(count);
        BigDecimal avg = moneyBig.divide(countBig, BigDecimal.ROUND_HALF_UP);
        
        /*if (avg.compareTo(minBig) == -1 || avg.compareTo(maxBig) == 1) {
        	return null;
        }*/
        /*if (avg < min || avg > max) {
            return null;
        }*/

        List<Integer> list = new ArrayList<Integer>();
        int extra = money - min * count;
        int current = 0;
        double v = 0;
        int limit = max - min;
        Random random = new Random();
        while (count > 0) {
            current = (int) (random.nextDouble() * limit);
            v = (double) (extra - current) / (double) (count - 1);
            if (count == 1) { //当count为1时，直接把剩余的钱赋给最后一个
            	if (extra + min > max) {
            		list.add(max);
            	} else {
            		list.add(extra + min);
            	}
                
            	//list.add(max);
                count--;
            } else if (v > limit) { //当平均值大于限制的时候，current直接为limit
                extra -= limit;
                list.add(limit + min);
                 count--;
            } else if (extra >= current) {
                extra -= current;
                list.add(current + min);
                count--;
            } else if (extra == 0) {
                count--;
                list.add(min);
            } else {
                count--;
                list.add(extra + min);
                extra = 0;
            }
        }
        return list;
    }

    /**
     * 定量红包算法——中奖率生成红包
     * @param total 红包总数
     * @param count 中奖数
     * @param money 奖金
     * @return
     */
    public static List<Integer> winningRate(int total, int count, double money){
        double[] arr = new double[total];
        Random rand = new Random();

        int i = 0;
        while (i < count){
            int random = rand.nextInt(total);

            if (arr[random] == 0) {
                arr[random] = money;

                i++;
            }
        }

        //将数组转换为泛型集合
        List<Integer> list = new ArrayList<Integer>();
        for(i = 0; i < total; i++){
            list.add((int) (arr[i] * 100));
        }

        return list;
    }

    /**
     * 定量红包算法——时间生成红包
     * @param rule 规则
     * @return
     */
    /*public static List<Integer> quantitative(RedEnvRule rule){
        if(rule == null){
            return null;
        }

        int a = rule.getOneCheckedNum(), b = rule.getTwoCheckedNum(), c = rule.getThreeCheckedNum();//中奖数
        double x = rule.getOneMaxMoney(), y = rule.getTwoMaxMoney(), z = rule.getThreeMaxMoney();//奖金

        double[] arr = new double[a + b + c];//指定数组范围
        Random rand = new Random();//随机数

        int i = 0, length = arr.length;
        for (; i < length;) {
            //获取一个随机数
            int random = rand.nextInt(length);

            //该数组下标已生成大于0的随机数则返回重新生成
            if(arr[random] > 0){
                continue;
            }

            if(a > 0){
                arr[random] = x;//将数量a的金额x生成到随机下标中，并数量a递减
                a--;
            } else if (b > 0){
                arr[random] = y;//将数量b的金额y生成到随机下标中，并数量b递减
                b--;
            } else if (c > 0){
                arr[random] = z;//将数量c的金额z生成到随机下标中，并数量c递减
                c--;
            }

            i++;
        }

        //将数组转换为泛型集合
        List<Integer> list = new ArrayList<Integer>();
        for(i = 0; i < arr.length; i++){
            list.add((int) (arr[i] * 100));//保存在已分为单位的Integer类型集合中
        }

        return list;
    }*/

    /**
     * 定量红包算法——获取下一次中奖时间
     * @param date 中奖时间
     * @param num 总奖数/天
     */
    public static Date getCanReceiveTime(Date date, int num){
        //设定最后一个中奖时间为当天23点整之后才可领取
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 000);

        //当前中奖时间与指定时间cal的时间间隔（分钟）
        long l = cal.getTime().getTime() - date.getTime();
        int min = (int) (l / (60 * 1000));

        //下一次中奖时间间隔（分钟）
        int interval = min / num;

        l = date.getTime() + interval * 60 * 1000;

        int random = 0;
        if(num == 1){
            Random rand = new Random();
            random = rand.nextInt(30 * 60 * 1000);
        }

        return new Date(l + random);
    }

    /**
     * 定量红包算法——生成第二天的第一个中奖时间
     */
    public static Date getCanReceiveTimeOne(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        //当天凌晨后加半小时内随机时间
        Random rand = new Random();
        int random = rand.nextInt(30 * 60 * 1000) + 1;

        return new Date(cal.getTime().getTime() + random);
    }

    /**
     * 计算参与红包的二维码的数量
     * @param type
     * @param count
     * @param num
     * @return
     */
    public static int calculateEnvelopeCount(String type, int count, int num) {
        if (type.contains("红包")) {
            count += num;
        }
        return count;
    }
}
