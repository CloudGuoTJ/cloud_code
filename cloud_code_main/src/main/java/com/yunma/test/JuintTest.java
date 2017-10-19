package com.yunma.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.common.util.Radix;
import com.yunma.entity.coupon.wd.WdConfig;
import com.yunma.entity.coupon.wd.WdCoupon;
import com.yunma.model.WeekDays;
import com.yunma.service.couponWd.WdConfigService;
import com.yunma.service.couponWd.WdCouponService;
import com.yunma.utils.coupon.WdUtil;

public class JuintTest {
	
	private ApplicationContext context = null;
	
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}

	@Test
	public void test() {
		WdConfigService service = (WdConfigService)context.getBean("wdConfigServiceImpl");
		List<WdConfig> list = service.getWdConfigAll();
		if (list != null && list.size() > 0) {
			for (WdConfig wdConfig : list) {
				wdConfig.setAccessToken(WdUtil.getAccessToken(wdConfig));
				service.updateWdConfig(wdConfig);
			}
			System.out.println("更新完毕");
		}
	}
	
	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		List<String> weekDay = new ArrayList<String>();
		Calendar calendar1 = Calendar.getInstance();// 日历对象 开始时间
		for(int i=1; i < 8; i++){
//		System.out.println(i);
			calendar1.add(Calendar.DATE, -1 );
			String times = sdf.format(calendar1.getTime());
			weekDay.add(times);
			}
				
		/**
		 * 获取所有生成二维码的
		 */	
//		List<WeekDays> weekDate  = new ArrayList<WeekDays>();
		WeekDays weekdays = new WeekDays();
		weekdays.setDate1( weekDay.get(6));
		weekdays.setDate2( weekDay.get(5));
		weekdays.setDate3( weekDay.get(4));
		weekdays.setDate4( weekDay.get(3));
		weekdays.setDate5( weekDay.get(2));
		weekdays.setDate6( weekDay.get(1));
		weekdays.setDate7( weekDay.get(0));		
//		weekDate.add(weekdays);
		System.out.println("第一天:"+weekDay.get(6));
		List<String> days = new ArrayList<String>();
		days.add(weekdays.getDate1());
		days.add(weekdays.getDate2());
		days.add(weekdays.getDate3());
		days.add(weekdays.getDate4());
		days.add(weekdays.getDate5());
		days.add(weekdays.getDate6());
		days.add(weekdays.getDate7());
		String sql = "SELECT A.scanTime, A.coun FROM (SELECT DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') AS SCANTIME, COUNT(1) COUN " +
				"FROM tb_anti_fake_" + 2 + " DOC " +
				"WHERE DATE_FORMAT(DOC.SCANTIME, '%Y%m%d') <='" + days.get(6) +"' AND  " +
				" DATE_FORMAT(DOC.SCANTIME, '%Y%m%d')>='" + days.get(0) + "' " +
				" GROUP BY scanTime " +
				"UNION (SELECT '"+days.get(0)+"', 0) " +
				"UNION (SELECT '"+days.get(1)+"', 0) " +
				"UNION (SELECT '"+days.get(2)+"', 0) " +
				"UNION (SELECT '"+days.get(3)+"', 0) " +
				"UNION (SELECT '"+days.get(4)+"', 0) " +
				"UNION (SELECT '"+days.get(5)+"', 0) " +
				"UNION (SELECT '"+days.get(6)+"', 0)) A " +
				"GROUP BY A.scanTime " +
				"ORDER BY A.scanTime DESC " ;
		System.out.println(sql);
	}
	
		
}
