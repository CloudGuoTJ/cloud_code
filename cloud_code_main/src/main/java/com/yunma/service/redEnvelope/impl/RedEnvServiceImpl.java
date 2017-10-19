package com.yunma.service.redEnvelope.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.HongBaoUtils;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.dao.redEnvelope.RedEnvDao;
import com.yunma.dao.securityCode.SecurityCodeDao;
import com.yunma.entity.product.ProductOrder;
import com.yunma.entity.redEnvelope.RedEnv;
import com.yunma.entity.redEnvelope.RedEnvRule;
import com.yunma.entity.redEnvelope.RedEnvRuleArea;
import com.yunma.entity.redEnvelope.RedEnvelope;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.service.redEnvelope.RedEnvService;
import com.yunma.utils.PageBean;

@Service
public class RedEnvServiceImpl implements RedEnvService{

	@Resource
	private RedEnvDao redEnvDao;
	
	@Resource
	private SecurityCodeDao securityCodeDao;
	
	@Resource
	private ProductOrderDao productOrderDao;
	
	@Override
	public int addRedEnvRule(RedEnvRule rule) {
		return redEnvDao.addRedEnvRule(rule);
	}
	
	@Override
	public int hasRedEnvRuleName(Integer vendorId,String ruleName) {
		return redEnvDao.hasRedEnvRuleName(vendorId,ruleName);
	}
	
	@Override
	public List<RedEnvRule> getRedEnvRuleByVendorId(Integer vendorId) {
		return redEnvDao.getRedEnvRuleByVendorId(vendorId);
	}
	
	@Override
	public RedEnvRule getRedEnvRuleById(Integer id) {
		return redEnvDao.getRedEnvRuleById(id);
	}

	@Override
	public int addRedEnv(ProductOrder order, RedEnvRule rule, RedEnv redEnv,JSONObject result,Double money) {
		BigDecimal countBigDecimal = new BigDecimal(order.getProductCount());
        BigDecimal moneyBigDecimal = new BigDecimal(Double.toString(redEnv.getMoney()));
        BigDecimal oneHundredBigDecimal = new BigDecimal(100);
        
        BigDecimal productCountBigDecimal = new BigDecimal(order.getProductCount());
        
		BigDecimal oneRate = new BigDecimal(Double.toString(rule.getOneRate()));
        BigDecimal oneMinMoney = new BigDecimal(Double.toString(rule.getOneMinMoney()));
        BigDecimal oneMaxMoney = new BigDecimal(Double.toString(rule.getOneMaxMoney()));
        BigDecimal twoRate = new BigDecimal(Double.toString(rule.getTwoRate()));
        BigDecimal twoMinMoney = new BigDecimal(Double.toString(rule.getTwoMinMoney()));
        BigDecimal twoMaxMoney = new BigDecimal(Double.toString(rule.getTwoMaxMoney()));
        BigDecimal threeRate = new BigDecimal(Double.toString(rule.getThreeRate()));
        BigDecimal threeMinMoney = new BigDecimal(Double.toString(rule.getThreeMinMoney()));
        BigDecimal threeMaxMoney = new BigDecimal(Double.toString(rule.getThreeMaxMoney()));
        BigDecimal fourRate = new BigDecimal(Double.toString(rule.getFourRate()));
        BigDecimal fourMinMoney = new BigDecimal(Double.toString(rule.getFourMinMoney()));
        BigDecimal fourMaxMoney = new BigDecimal(Double.toString(rule.getFourMaxMoney()));
        BigDecimal fiveRate = new BigDecimal(Double.toString(rule.getFiveRate()));
        BigDecimal fiveMinMoney = new BigDecimal(Double.toString(rule.getFiveMinMoney()));
        BigDecimal fiveMaxMoney = new BigDecimal(Double.toString(rule.getFiveMaxMoney()));
        
        int level = rule.getRuleLvel();
        
        //中奖率求和
        BigDecimal  RateSum = oneRate.add(twoRate).add(threeRate).add(fourRate).add(fiveRate);
        //中奖的二维码数量
        int rateCount = countBigDecimal.multiply(RateSum).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
        //int rateCount = RateSum.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();

        if (rateCount <= 0) {
            //result.setErrorCode(-1);
            //result.setMsg("您选择的订单具有红包功能的二维码数量为零或规则中奖率设置不正确，请重新确认！");
        	return -1;
        }
        
        //1、确定每一级红包的中奖数量
        int oneCount = 0;
        int twoCount = 0;
        int threeCount = 0;
        int fourCount = 0;
        int fiveCount = 0;
        
        if (level == 5) {
        	/*oneCount = oneRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	twoCount = twoRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	threeCount = threeRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	fourCount = fourRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	fiveCount = fiveRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();*/
            oneCount = countBigDecimal.multiply(oneRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            twoCount = countBigDecimal.multiply(twoRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            threeCount = countBigDecimal.multiply(threeRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            fourCount = countBigDecimal.multiply(fourRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            fiveCount = rateCount - (oneCount + twoCount + threeCount + fourCount);
        } else if (level == 4) {
        	/*oneCount = oneRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	twoCount = twoRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	threeCount = threeRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	fourCount = fourRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();*/
            oneCount = countBigDecimal.multiply(oneRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            twoCount = countBigDecimal.multiply(twoRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            threeCount = countBigDecimal.multiply(threeRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            fourCount = rateCount - (oneCount + twoCount + threeCount);
        } else if (level == 3) {
        	/*oneCount = oneRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	twoCount = twoRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	threeCount = threeRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();*/
            oneCount = oneHundredBigDecimal.multiply(oneRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            twoCount = countBigDecimal.multiply(twoRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            threeCount = rateCount - oneCount - twoCount;
        } else if (level == 2) {
        	/*oneCount = oneRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();
        	twoCount = twoRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();*/
            oneCount = countBigDecimal.multiply(oneRate).divide(oneHundredBigDecimal, BigDecimal.ROUND_HALF_UP).intValue();
            twoCount = rateCount - oneCount;
        } else {
        	/*oneCount = oneRate.divide(oneHundredBigDecimal).multiply(productCountBigDecimal).intValue();*/
            oneCount = rateCount;
        }
        
      //各个等级的平均奖金金额
        BigDecimal oneAvgMoney = (oneMaxMoney.subtract(oneMinMoney)).multiply(new BigDecimal(oneCount));
        BigDecimal twoAvgMoney = (twoMaxMoney.subtract(twoMinMoney)).multiply(new BigDecimal(twoCount));
        BigDecimal threeAvgMoney = (threeMaxMoney.subtract(threeMinMoney)).multiply(new BigDecimal(threeCount));
        BigDecimal fourAvgMoney = (fourMaxMoney.subtract(fourMinMoney)).multiply(new BigDecimal(fourCount));
        BigDecimal fiveAvgMoney = (fiveMaxMoney.subtract(fiveMinMoney)).multiply(new BigDecimal(fiveCount));
        BigDecimal totalAvgMoney = oneAvgMoney.add(twoAvgMoney).add(threeAvgMoney).add(fourAvgMoney).add(fiveAvgMoney);

        //各个等级的最小奖金金额
        BigDecimal basicOneMoney = oneMinMoney.multiply(oneHundredBigDecimal).multiply(new BigDecimal(oneCount));
        BigDecimal basicTwoMoney = twoMinMoney.multiply(oneHundredBigDecimal).multiply(new BigDecimal(twoCount));
        BigDecimal basicThreeMoney = threeMinMoney.multiply(oneHundredBigDecimal).multiply(new BigDecimal(threeCount));
        BigDecimal basicFourMoney = fourMinMoney.multiply(oneHundredBigDecimal).multiply(new BigDecimal(fourCount));
        BigDecimal basicFiveMoney = fiveMinMoney.multiply(oneHundredBigDecimal).multiply(new BigDecimal(fiveCount));
        BigDecimal minMoneyCount = oneMinMoney.multiply(new BigDecimal(oneCount))
                .add(twoMinMoney.multiply(new BigDecimal(twoCount)))
                .add(threeMinMoney.multiply(new BigDecimal(threeCount)))
                .add(fourMinMoney.multiply(new BigDecimal(fourCount)))
                .add(fiveMinMoney.multiply(new BigDecimal(fiveCount)));//新建红包时允许填写的中奖金额的最小值

        /*BigDecimal maxMoneyCount = oneMaxMoney.multiply(new BigDecimal(oneCount))
                .add(twoMaxMoney.multiply(new BigDecimal(twoCount)))
                .add(threeMaxMoney.multiply(new BigDecimal(threeCount)))
                .add(fourMaxMoney.multiply(new BigDecimal(fourCount)))
                .add(fiveMaxMoney.multiply(new BigDecimal(fiveCount)));//新建红包时允许填写的中奖金额的最大值
*/
        BigDecimal maxMoneyCount = 
        		(oneMaxMoney.multiply(oneRate).multiply(countBigDecimal)
        		.add(twoMaxMoney.multiply(twoRate).multiply(countBigDecimal))
        		.add(threeMaxMoney.multiply(threeRate).multiply(countBigDecimal))
        		.add(fourMaxMoney.multiply(fourRate).multiply(countBigDecimal))
        		.add(fiveMaxMoney.multiply(fiveRate).multiply(countBigDecimal))
        		.divide(oneHundredBigDecimal));//新建红包时允许填写的中奖金额的最大值
        
        if (moneyBigDecimal.compareTo(minMoneyCount) == -1) {
            /*result.setErrorCode(-1);
            result.setMsg("您设置的金额太小，发放失败！");*/
            return-2;
        }
        
        //判断发放红包规则是否大于该订单中奖金额
        if (moneyBigDecimal.compareTo(maxMoneyCount) == 1) {
        	/*result.setErrorCode(-1);
            result.setMsg("您设置的金额太大，发放失败！");*/
            return -3;
        }
        
      //填写的红包金额 * 100
//      int newMoney = (int) (_money * 100);
      BigDecimal newMoney = moneyBigDecimal.multiply(oneHundredBigDecimal);
      List<Integer> redEnvelopeList = new ArrayList<Integer>();
      if (level == 5) {
          //填写的红包金额减去需要的最小金额
          BigDecimal extraNewMoney = newMoney.subtract(minMoneyCount.multiply(oneHundredBigDecimal));
          BigDecimal newOneMoney = basicOneMoney;
          BigDecimal newTwoMoney = basicTwoMoney;
          BigDecimal newThreeMoney = basicThreeMoney;
          BigDecimal newFourMoney = basicFourMoney;
          BigDecimal newFiveMoney = basicFiveMoney;
          //如果分母不为0
          if (totalAvgMoney.compareTo(new BigDecimal(0)) != 0) {
              newOneMoney = extraNewMoney.multiply(oneAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicOneMoney);
              newTwoMoney = extraNewMoney.multiply(twoAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicTwoMoney);
              newThreeMoney = extraNewMoney.multiply(threeAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicThreeMoney);
              newFourMoney = extraNewMoney.multiply(fourAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicFourMoney);
              newFiveMoney = extraNewMoney.multiply(fiveAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicFiveMoney);
          }
          if (oneCount != 0) {
              List<Integer> redEnvelopeList1 = HongBaoUtils.process(newOneMoney.intValue(), oneCount, oneMaxMoney.multiply(oneHundredBigDecimal).intValue(), oneMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList1);
          }
          if (twoCount != 0) {
              List<Integer> redEnvelopeList2 = HongBaoUtils.process(newTwoMoney.intValue(), twoCount, twoMaxMoney.multiply(oneHundredBigDecimal).intValue(), twoMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList2);
          }
          if (threeCount != 0) {
              List<Integer> redEnvelopeList3 = HongBaoUtils.process(newThreeMoney.intValue(), threeCount, threeMaxMoney.multiply(oneHundredBigDecimal).intValue(), threeMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList3);
          }
          if (fourCount != 0) {
              List<Integer> redEnvelopeList4 = HongBaoUtils.process(newFourMoney.intValue(), fourCount, fourMaxMoney.multiply(oneHundredBigDecimal).intValue(), fourMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList4);
          }
          if (fiveCount != 0) {
              List<Integer> redEnvelopeList5 = HongBaoUtils.process(newFiveMoney.intValue(), fiveCount, fiveMaxMoney.multiply(oneHundredBigDecimal).intValue(), fiveMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList5);
          }
      } else if (level == 4) {
          BigDecimal extraNewMoney = newMoney.subtract(minMoneyCount.multiply(oneHundredBigDecimal));
          BigDecimal newOneMoney = basicOneMoney;
          BigDecimal newTwoMoney = basicTwoMoney;
          BigDecimal newThreeMoney = basicThreeMoney;
          BigDecimal newFourMoney = basicFourMoney;
          //如果分母不为0
          if (totalAvgMoney.compareTo(new BigDecimal(0)) != 0) {
              newOneMoney = extraNewMoney.multiply(oneAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicOneMoney);
              newTwoMoney = extraNewMoney.multiply(twoAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicTwoMoney);
              newThreeMoney = extraNewMoney.multiply(threeAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicThreeMoney);
              newFourMoney = extraNewMoney.multiply(fourAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicFourMoney);
          }
          if (oneCount != 0) {
              List<Integer> redEnvelopeList1 = HongBaoUtils.process(newOneMoney.intValue(), oneCount, oneMaxMoney.multiply(oneHundredBigDecimal).intValue(), oneMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList1);
          }
          if (twoCount != 0) {
              List<Integer> redEnvelopeList2 = HongBaoUtils.process(newTwoMoney.intValue(), twoCount, twoMaxMoney.multiply(oneHundredBigDecimal).intValue(), twoMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList2);
          }
          if (threeCount != 0) {
              List<Integer> redEnvelopeList3 = HongBaoUtils.process(newThreeMoney.intValue(), threeCount, threeMaxMoney.multiply(oneHundredBigDecimal).intValue(), threeMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList3);
          }
          if (fourCount != 0) {
              List<Integer> redEnvelopeList4 = HongBaoUtils.process(newFourMoney.intValue(), fourCount, fourMaxMoney.multiply(oneHundredBigDecimal).intValue(), fourMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList4);
          }
      } else if (level == 3) {
          BigDecimal extraNewMoney = newMoney.subtract(minMoneyCount.multiply(oneHundredBigDecimal));
          BigDecimal newOneMoney = basicOneMoney;
          BigDecimal newTwoMoney = basicTwoMoney;
          BigDecimal newThreeMoney = basicThreeMoney;
          //如果分母不为0
          if (totalAvgMoney.compareTo(new BigDecimal(0)) != 0) {
              newOneMoney = extraNewMoney.multiply(oneAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicOneMoney);
              newTwoMoney = extraNewMoney.multiply(twoAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicTwoMoney);
              newThreeMoney = extraNewMoney.multiply(threeAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicThreeMoney);
          }
          if (oneCount != 0) {
              List<Integer> redEnvelopeList1 = HongBaoUtils.process(newOneMoney.intValue(), oneCount, oneMaxMoney.multiply(oneHundredBigDecimal).intValue(), oneMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList1);
          }
          if (twoCount != 0) {
              List<Integer> redEnvelopeList2 = HongBaoUtils.process(newTwoMoney.intValue(), twoCount, twoMaxMoney.multiply(oneHundredBigDecimal).intValue(), twoMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList2);
          }
          if (threeCount != 0) {
              List<Integer> redEnvelopeList3 = HongBaoUtils.process(newThreeMoney.intValue(), threeCount, threeMaxMoney.multiply(oneHundredBigDecimal).intValue(), threeMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList3);
          }
      } else if (level == 2) {
          BigDecimal extraNewMoney = newMoney.subtract(minMoneyCount.multiply(oneHundredBigDecimal));
          BigDecimal newOneMoney = basicOneMoney;
          BigDecimal newTwoMoney = basicTwoMoney;
          //如果分母不为0
          if (totalAvgMoney.compareTo(new BigDecimal(0)) != 0) {
              newOneMoney = extraNewMoney.multiply(oneAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicOneMoney);
              newTwoMoney = extraNewMoney.multiply(twoAvgMoney).divide(totalAvgMoney, BigDecimal.ROUND_HALF_UP).add(basicTwoMoney);
          }
          if (oneCount != 0) {
              List<Integer> redEnvelopeList1 = HongBaoUtils.process(newOneMoney.intValue(), oneCount, oneMaxMoney.multiply(oneHundredBigDecimal).intValue(), oneMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList1);
          }
          if (twoCount != 0) {
              List<Integer> redEnvelopeList2 = HongBaoUtils.process(newTwoMoney.intValue(), twoCount, twoMaxMoney.multiply(oneHundredBigDecimal).intValue(), twoMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList2);
          }
      } else {
          if (oneCount != 0) {
              List<Integer> redEnvelopeList1 = HongBaoUtils.process(newMoney.intValue(), oneCount, oneMaxMoney.multiply(oneHundredBigDecimal).intValue(), oneMinMoney.multiply(oneHundredBigDecimal).intValue());
              redEnvelopeList.addAll(redEnvelopeList1);
          }
      }
      
      try {
          //redEnvDao.createRedEnvelope(redEnv);

          try {
              return createLevelRedEnvelopeMoeny(rateCount, order.getProductCount(), redEnvelopeList, redEnv.getId(), redEnv.getOrderId(),redEnv.getVendorId(),rule.getId(),redEnv.getStartTimeStr(),redEnv.getEndTimeStr(),rule, redEnv,money);
          } catch (Exception e) {
              e.printStackTrace();
              //logger.error("往红包表中插入数据时出错：" + e.getMessage(), e);
          }
      } catch (Exception e) {
          e.printStackTrace();
          /*result.setErrorCode(-2);
          result.setMsg("异常错误！");*/

          return 0;
      }
        
		return 0;
	}

	/**
     * 层次红包——按中奖率生成红包奖池
     *
     * @param rateCount      中奖数量
     * @param count          红包二维码数量
     * @param envelopeMoney  中奖红包金额集合
     * @param sendEnvelopeId 红包金额创建ID
     * @param perfixOrderNo  订单ID流水号
     * @return
     */
	@Transactional
    public int createLevelRedEnvelopeMoeny(int rateCount, int count, List envelopeMoney, Integer sendEnvelopeId, Integer orderId, Integer vendorId,Integer ruleId,String startTime, String endTime, RedEnvRule rule, RedEnv redEnv,Double rechargeMoney) {
		int i = 0;
        List<Integer> listRateMoney = new ArrayList<Integer>();

        if (rateCount < count) {
            listRateMoney.addAll(envelopeMoney);
            for (int m = 0; m < count - rateCount; m++) {
                listRateMoney.add(0);
            }
            Collections.shuffle(listRateMoney);
        } else {
            listRateMoney.addAll(envelopeMoney);
            Collections.shuffle(listRateMoney);
        }
        //int n = listRateMoney.size() / 10000;
        List<Integer> listArray = null;
        
        StringBuffer sc = new StringBuffer();
        sc.append("CREATE TABLE `tb_envelope_vendor_createredenvelope_"+orderId+"` ( ")
        .append("`red_env_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '创建红包表ID', ")
        .append("`order_id` int(4) NOT NULL COMMENT '订单id', ")
        .append("`vendor_id` int(11) NOT NULL COMMENT '红包厂商的id', ")
        .append("`rule_id` int(11) NOT NULL COMMENT '红包规则id', ")
        .append("`securityCode_id` bigint(20) NOT NULL COMMENT '防伪码ID', ")
        .append("`envelope_money` double(64,2) NOT NULL COMMENT '红包金额', ")
        .append("`state` int(2) NOT NULL DEFAULT '0' COMMENT '打开状态 0未打开 1打开',")
        .append("`status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '红包状态 0,未领取 1,已领取', ")
        .append("`create_time` datetime DEFAULT NULL COMMENT '创建时间', ")
        .append("`start_time` datetime NOT NULL COMMENT ' 发放时间有效时间起点', ")
        .append("`end_time` datetime NOT NULL COMMENT '有效时间终点', ")
        .append("`sendEnvelope_id` bigint(32) NULL COMMENT '红包发放记录ID', ")
        .append("`openId` varchar(32) DEFAULT  NULL COMMENT '中奖者的openId', ")
        .append("`open_time` datetime DEFAULT NULL COMMENT '打开红包时间',")
        .append("`receive_time` datetime DEFAULT NULL COMMENT '领取红包时间',")
        .append("PRIMARY KEY (`red_env_id`) ")
        .append(") ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='红包金额详情表' ");
        
        //创建红包表
        redEnvDao.createRedEvnTable(sc.toString());
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("rule", rule);
        map.put("redEnv", redEnv);
        map.put("rechargeMoney", rechargeMoney);
        
        int temp1 = redEnvDao.addRedEnvInfo(map);
        if (temp1 < 0) {
        	return 0;
        }
        
        List<SecurityCode> list = redEnvDao.getSecurityCodeByOrderId(orderId);
        //List<SecurityCode> list = securityCodeDao.getSecurityCodeByOrderId(orderId);

        StringBuffer sb = new StringBuffer();
        
        
        //for (int j = 0; j <= n; j++) {
        	
            /*listArray = listRateMoney.subList(j * 10000, (j + 1) * 10000 > listRateMoney.size() ? listRateMoney.size() : (j + 1) * 10000);
            if (listArray.size() == 0) {
                return 1;
            }*/
            
            sb.append("INSERT INTO `tb_envelope_vendor_createredenvelope_" + orderId + "`(order_id,vendor_id,rule_id,securityCode_id,envelope_money,create_time,start_time,end_time) VALUES ");
            for (int a=0 ; a<listRateMoney.size() ; a++) {
            	
            	//判断是否为最后一个 不是则在最后加上 ,
            	if (a != listRateMoney.size()-1) {
            		double money = (double) listRateMoney.get(a) / 100;
                    sb.append("(" + orderId + "," + vendorId + "," + ruleId + "," + list.get(a).getSecurityCodeId() + "," + money + ",now(),'" + startTime + "','" + endTime + "'),");
            	} else {
            		double money = (double) listRateMoney.get(a) / 100;
                    sb.append("(" + orderId + "," + vendorId + "," + ruleId + "," + list.get(a).getSecurityCodeId() + "," + money + ",now(),'" + startTime + "','" + endTime + "')");
            	}
            	
            	
            }
            int temp = redEnvDao.batchInsert(sb.toString());
            if (temp < 0) {
            	return 0;
            }
            
            //i += redEnvDao.createRedEnvelopeMoney(sb.substring(0, sb.length() - 1));
            
        //}
        return 1;
    }

	@Override
	public PageBean getRedEnvRuleList(PageBean pageBean, Integer vendorId, String keyword, String startTime, String endTime) {
		JSONObject result = new JSONObject();
		JSONArray resultArray = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageBean", pageBean);
		map.put("vendorId", vendorId);
		map.put("keyword", keyword);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		//设置总记录数
		pageBean.setTotalRecords(redEnvDao.getRedEnvRuleCount(map));
		
		List<RedEnvRule> rule = redEnvDao.getRedEnvRuleList(map);
		if (rule != null && rule.size() > 0) {
			for (RedEnvRule redEnvRule : rule) {
				JSONObject object = new JSONObject();
				object.put("id", redEnvRule.getId());
				object.put("ruleName", redEnvRule.getRuleName());
				object.put("ruleLvel", redEnvRule.getRuleLvel());
				object.put("oneRate", redEnvRule.getOneRate());
				object.put("oneMinMoney", redEnvRule.getOneMinMoney());
				object.put("oneMaxMoney", redEnvRule.getOneMaxMoney());
				object.put("twoRate", redEnvRule.getTwoRate());
				object.put("twoMinMoney", redEnvRule.getTwoMinMoney());
				object.put("twoMaxMoney", redEnvRule.getTwoMaxMoney());
				object.put("threeRate", redEnvRule.getThreeRate());
				object.put("threeMinMoney", redEnvRule.getThreeMinMoney());
				object.put("threeMaxMoney", redEnvRule.getThreeMaxMoney());
				object.put("fourRate", redEnvRule.getFourRate());
				object.put("fourMinMoney", redEnvRule.getFourMinMoney());
				object.put("fourMaxMoney", redEnvRule.getFourMaxMoney());
				object.put("fiveRate", redEnvRule.getFiveRate());
				object.put("fiveMinMoney", redEnvRule.getFiveMinMoney());
				object.put("fiveMaxMoney", redEnvRule.getFiveMaxMoney());
				if (redEnvRule.getCreateTime() != null) {
					object.put("createTime", sdf.format(redEnvRule.getCreateTime()));
				}
				object.put("rule_type", redEnvRule.getRule_type());
				object.put("send_name", redEnvRule.getSend_name());
				object.put("wishing", redEnvRule.getWishing());
				resultArray.add(object);
			}
		}
		
		result.put("data", resultArray);
		//将数据放入pageBean中
		pageBean.setResult(result);
		
		return pageBean;
	}
	
	@Override
	public PageBean getgetRedEnvList(PageBean pageBean, Integer vendorId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageBean", pageBean);
		map.put("vendorId", vendorId);
		
		//设置总记录数
		pageBean.setTotalRecords(redEnvDao.getRedEnvInfoCount(map));
		
		List<RedEnvelope> list = redEnvDao.getRedEnvInfo(map);
		
		if (list != null && list.size() > 0) {
			for (RedEnvelope redEnvelope : list) {
				JSONObject object = new JSONObject();
				object.put("id", redEnvelope.getId());
				object.put("orderId", redEnvelope.getOrderId());
				object.put("ruleId", redEnvelope.getRuleId());
				object.put("ruleName", redEnvelope.getRuleName());
				object.put("rechargeMoney", redEnvelope.getRechargeMoney());
				object.put("consumeMoney", redEnvelope.getConsumeMoney());
					
				if (redEnvelope.getCreateTime() != null) 
					object.put("createTime", sdf.format(redEnvelope.getCreateTime()));
				if (redEnvelope.getStartTime() != null) 
					object.put("startTime", sdf.format(redEnvelope.getStartTime()));
				if (redEnvelope.getEndTime() != null) 
					object.put("endTime", sdf.format(redEnvelope.getEndTime()));
				
				array.add(object);
			}
			result.put("data", array);
		}
		
		//将查询的数据放入结果集中
		pageBean.setResult(result);
		
		return pageBean;
	}
	
	@Override
	public JSONObject getMoneyScope(Integer ruleId, Integer orderId,JSONObject result) {
		RedEnvRule rule = redEnvDao.getRedEnvRuleById(ruleId);
		ProductOrder  order = productOrderDao.findOrderByProductOrderId(orderId);
		
		BigDecimal orderNum = new BigDecimal(order.getProductCount());
		
		BigDecimal hundred = new BigDecimal(100);
		
		if (rule != null && order != null) {
			
			BigDecimal oneMin = new BigDecimal(Double.toString(rule.getOneMinMoney()));
			BigDecimal oneMax = new BigDecimal(Double.toString(rule.getOneMaxMoney()));
			BigDecimal oneRate = new BigDecimal(Double.toString(rule.getOneRate()));
			
			BigDecimal twoMin = new BigDecimal(Double.toString(rule.getTwoMinMoney()));
			BigDecimal twoMax = new BigDecimal(Double.toString(rule.getTwoMaxMoney()));
			BigDecimal twoRate = new BigDecimal(Double.toString(rule.getTwoRate()));
			
			BigDecimal threeMin = new BigDecimal(Double.toString(rule.getThreeMinMoney()));
			BigDecimal threeMax = new BigDecimal(Double.toString(rule.getThreeMaxMoney()));
			BigDecimal threeRate = new BigDecimal(Double.toString(rule.getThreeRate()));
			
			BigDecimal fourMin = new BigDecimal(Double.toString(rule.getFourMinMoney()));
			BigDecimal fourMax = new BigDecimal(Double.toString(rule.getFourMaxMoney()));
			BigDecimal fourRate = new BigDecimal(Double.toString(rule.getFourRate()));
			
			BigDecimal fiveMin = new BigDecimal(Double.toString(rule.getFiveMinMoney()));
			BigDecimal fiveMax = new BigDecimal(Double.toString(rule.getFiveMaxMoney()));
			BigDecimal fiveRate = new BigDecimal(Double.toString(rule.getFiveRate()));
			
			BigDecimal min = (oneMin.multiply(oneRate).multiply(orderNum)
			.add(twoMin.multiply(twoRate).multiply(orderNum))
			.add(threeMin.multiply(threeRate).multiply(orderNum))
			.add(fourMin.multiply(fourRate).multiply(orderNum))
			.add(fiveMin.multiply(fiveRate).multiply(orderNum))).divide(hundred);
			
			BigDecimal max = (oneMax.multiply(oneRate).multiply(orderNum)
					.add(twoMax.multiply(twoRate).multiply(orderNum))
					.add(threeMax.multiply(threeRate).multiply(orderNum))
					.add(fourMax.multiply(fourRate).multiply(orderNum))
					.add(fiveMax.multiply(fiveRate).multiply(orderNum))).divide(hundred);
			
			result.put("min", min);
			result.put("max", max);
		}
		
		return result;
	}
	
	@Override
	public RedEnv openEnv(Integer securityCodeId, Integer orderId) {
		RedEnv redEnv = new RedEnv();
		
		redEnv.setSecurityCodeId(securityCodeId);
		redEnv.setOrderId(orderId);
		
		return redEnvDao.getRedEnvById(redEnv);
	}
	
	@Override
	public int updateRedEnvById(RedEnv redEnv) {
		return redEnvDao.updateRedEnvById(redEnv);
	}

	@Override
	public String getSecurityCodeByIdRedEnvId(RedEnv redEnv) {
		return redEnvDao.getSecurityCodeByIdRedEnvId(redEnv);
	}
	
	@Override
	public int addDrawRecord(RedEnv redEnv) {
		return redEnvDao.addDrawRecord(redEnv);
	}
	
	@Override
	public RedEnvelope getEnvelopeByOrderId(Integer orderId) {
		return redEnvDao.getEnvelopeByOrderId(orderId);
	}
	
	@Override
	public int updateEnvelope(RedEnvelope redEnvelope) {
		return redEnvDao.updateEnvelope(redEnvelope);
	}

	@Override
	public int addRedEnvRuleArea(RedEnvRuleArea area) {
		return redEnvDao.addRedEnvRuleArea(area);
	}

	@Override
	public List<RedEnvRuleArea> getRedEnvRuleArea(Integer orderId) {
		return redEnvDao.getRedEnvRuleArea(orderId);
	}
	
}
