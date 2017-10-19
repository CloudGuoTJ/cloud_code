package com.yunma.service.weChatUser.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunma.controller.wechat.WechatController;
import com.yunma.dao.weChart.WeChatDao;
import com.yunma.entity.weChat.WeChatUser;
import com.yunma.entity.weChat.WeChatUserWallet;
import com.yunma.service.weChatUser.WeChatService;
import com.yunma.utils.WeChatConfig;
import com.yunma.utils.weChat.Global;
import com.yunma.utils.weChat.PayCommonUtil;
import com.yunma.utils.weChat.SendRedPacketsUtil;

@Service
public class WeChatServiceImpl implements WeChatService{
	
	private Logger log = LoggerFactory.getLogger(WechatController.class);
	
	@Resource
	private WeChatDao weChatDao;

	@Override
	@Transactional
	public int addWeChatUserInfo(WeChatUser user) {
		weChatDao.addWeChatUserInfo(user);
		return weChatDao.addWeChatUserWallet(user.getOpenId());
	}

	@Override
	public int updateWeChatUserInfo(WeChatUser user) {
		return weChatDao.updateWeChatUserInfo(user);
	}

	@Override
	public int hasWeChatUserInfo(String openId) {
		return weChatDao.hasWeChatUserInfo(openId);
	}
	
	@Override
	public int addWeChatUserWallet(String openId) {
		return weChatDao.addWeChatUserWallet(openId);
	}
	
	@Override
	public Map<Object, Object> sendRedEnv(String appId,String mchId,String apiKey,String openId, Integer amount,
			Integer nums,String fileLocation) {
		Map<Object, Object> resultMap=null;
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("act_name","红包抽奖");							//活动名称
		parameters.put("client_ip",WeChatConfig.CLIENT_IP);				//Ip地址
		parameters.put("mch_billno",getMchBillno(mchId));				//订单号
		parameters.put("mch_id", mchId);								//商户号
		parameters.put("nonce_str",PayCommonUtil.CreateNoncestr());		//随机字符串
		parameters.put("re_openid",openId);								//用户openid
		parameters.put("remark","红包抽奖");								//备注
		parameters.put("send_name","云码互联");							//商户名称
		parameters.put("total_amount",Global.parseString(amount));		//付款金额 (分)
		parameters.put("total_num",Global.parseString(nums));			//红包发放总人数
		parameters.put("wishing","恭喜中奖");							//红包祝福语
		parameters.put("wxappid",appId);								//公众账号appid
		String sign = PayCommonUtil.createSignV4("UTF-8",apiKey, parameters);	
		parameters.put("sign",sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		log.info(requestXML);
		resultMap=SendRedPacketsUtil.postCertificateV2(WeChatConfig.SEND_RED_PACKETS_URL,mchId, requestXML,fileLocation);
		//System.out.println(list.get(5));
		
		return resultMap;
	}
	
	private String getMchBillno (String mchId){
		String mch_billno="";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyymmdd");
		sdf.format(date);
		String timeStamp=Global.parseString(date.getTime());
		mch_billno=mchId+sdf.format(date)+timeStamp.substring(0, 10);
		return  mch_billno;
	}
	
	@Override
	public WeChatUserWallet getWalletByOpenId(String openId) {
		WeChatUserWallet wallet = weChatDao.getWalletByOpenId(openId);
		if (wallet == null) {
			int temp = weChatDao.addWeChatUserWallet(openId);
			WeChatUserWallet wallet1 = new WeChatUserWallet();
			if (temp > 0) {
				wallet1.setOpenId(openId);
				wallet1.setMoney(0.00);
				return wallet1;
			} else {
				return null;
			}
		}
		return wallet;
	}
	
	@Override
	public int updateWalletById(WeChatUserWallet wallet) {
		return weChatDao.updateWalletById(wallet);
	}
	
	@Override
	public int addWeChatUserRecord(WeChatUserWallet wallet) {
		return weChatDao.addWeChatUserRecord(wallet);
	}

	@Override
	public String getNickNameBy(String openId) {

		return weChatDao.findNickNameByOpenId(openId);
	}

	@Override
	public WeChatUser getUserInfoByOpenId(String openId) {

		return weChatDao.getUserInfoByOpenId(openId);
	}

}
