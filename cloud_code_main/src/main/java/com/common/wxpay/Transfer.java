package com.common.wxpay;
//
//import com.securitycode.model.VendorSendEnvelope;
//import com.securitycode.model.WxPayAccount;
//import com.securitycode.model.WxPayResult;
//import com.securitycode.service.RedEnvelopeService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.UnsupportedEncodingException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Transfer {
//
//	@Autowired
//	private RedEnvelopeService redEnvelopeService;
//
//	final static  String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
//	//public static String mch_id = "1318442301";
//	//public static String appid = "wx465f6cda25c39598";
//	//private static String partnerkey = "ytxg123123ytxg123123ytxg12312388";// ΢���̻�����Կ
//	public WxPayResult wxTransfer(String openId,double money,WxPayAccount wxPayAccount,VendorSendEnvelope sendEnvelope){
//		String orderNNo =  MoneyUtils.getOrderNo() ;
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("mch_appid", wxPayAccount.getAppId());//�̻�appid
//		map.put("mchid", wxPayAccount.getMchId());//�̻���
//		map.put("nonce_str", MoneyUtils.buildRandom());//����ַ���
//		map.put("partner_trade_no", orderNNo);//�̻�����
//		map.put("openid", openId);//�û�openid
//		map.put("check_name", "NO_CHECK");//�û���
//		map.put("amount", (int)(money*100));//������
//		map.put("desc", "ɨ��ú��");//���ף����
//		map.put("spbill_create_ip", "127.0.0.1");//ip��ַ
//		map.put("sign", MoneyUtils.createSign(wxPayAccount.getWxKey(),map));//ǩ��
//
//		String result = "";
//		try {
//			result = MoneyUtils.doSendMoney(wxPayAccount.getCertName(),wxPayAccount.getMchId(),url, MoneyUtils.createXML(map));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		WxPayResult wxPayResult = new WxPayResult();
//
//		try {
//			if (!result.contains("<err_code>")){
//				wxPayResult = AnalysisXML.parseTransferXML(result);
//				wxPayResult.setVendorId(sendEnvelope.getVendorAccountId());
//				wxPayResult.setOpenId(openId);
//				wxPayResult.setAmount((int)(money*100));
//				wxPayResult.setPaymentNo("-1");
//				wxPayResult.setStatu(2);
//			}else{
//				wxPayResult = AnalysisXML.parseTransferXML(result);
//				wxPayResult.setVendorId(sendEnvelope.getVendorAccountId());
//				wxPayResult.setOpenId(openId);
//				wxPayResult.setAmount((int)(money*100));
//				wxPayResult.setStatu(1);
//				wxPayResult.setReturnMsg("���ųɹ�");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return  wxPayResult;
//	}
//}
