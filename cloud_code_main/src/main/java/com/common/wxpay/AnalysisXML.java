package com.common.wxpay;

/**
 * Created by Administrator on 2016/3/16.
 */
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.yunma.entity.coupon.wechat.WxCoupon;

public class AnalysisXML {
    /*public static WxPayResult parseRedEnvelopeXML(String protocolXML) {

        WxPayResult wxPayResult = new WxPayResult();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(protocolXML)));

            Element root = doc.getDocumentElement();
            NodeList redEnvelopes = root.getChildNodes();
            if (redEnvelopes != null) {
                for (int i = 0; i < redEnvelopes.getLength(); i++) {
                    Node redEnvelope = redEnvelopes.item(i);
                    if (redEnvelope.getNodeName().equals("return_msg")){
                        wxPayResult.setReturnMsg(redEnvelope.getFirstChild().getNodeValue());
                    }else if (redEnvelope.getNodeName().equals("mch_billno")){
                        wxPayResult.setPaymentNo(redEnvelope.getFirstChild().getNodeValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wxPayResult;
    }

    public static WxPayResult parseWxErrorXML(String protocolXML) {

        WxPayResult wxPayResult = new WxPayResult();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(protocolXML)));

            Element root = doc.getDocumentElement();
            NodeList wxErrors = root.getChildNodes();
            if (wxErrors != null) {
                for (int i = 0; i < wxErrors.getLength(); i++) {
                    Node wxError = wxErrors.item(i);
                    if (wxError.getNodeName().equals("return_msg")){
                        wxPayResult.setReturnMsg(wxError.getFirstChild().getNodeValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxPayResult;
    }

    public static WxPayResult parseTransferXML(String protocolXML) {

        WxPayResult wxPayResult = new WxPayResult();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(protocolXML)));

            Element root = doc.getDocumentElement();
            NodeList transfers = root.getChildNodes();
            if (transfers != null) {
                for (int i = 0; i < transfers.getLength(); i++) {
                    Node transfer = transfers.item(i);
                    if (transfer.getNodeName().equals("partner_trade_no")){
                        wxPayResult.setPaymentNo(transfer.getFirstChild().getNodeValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxPayResult;
    }*/
    public static WxCoupon parseCoupnXML(String protocolXML) {

        WxCoupon wxCoupn=new WxCoupon();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(protocolXML)));

            Element root = doc.getDocumentElement();
            NodeList redEnvelopes = root.getChildNodes();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            if (redEnvelopes != null) {
                for (int i = 0; i < redEnvelopes.getLength(); i++) {
                    Node redEnvelope = redEnvelopes.item(i);
                    if (redEnvelope.getNodeName().equals("appid")){
                        wxCoupn.setAppid(redEnvelope.getFirstChild().getNodeValue());
                    }else if (redEnvelope.getNodeName().equals("mch_id")){
                        wxCoupn.setMch_id(redEnvelope.getFirstChild().getNodeValue());
                    }else if (redEnvelope.getNodeName().equals("coupon_stock_id")){
                        wxCoupn.setCoupon_stock_id(redEnvelope.getFirstChild().getNodeValue());
                    }else if (redEnvelope.getNodeName().equals("coupon_name")){
                        wxCoupn.setCoupon_name(redEnvelope.getFirstChild().getNodeValue());
                    }else if (redEnvelope.getNodeName().equals("coupon_value")){
                        wxCoupn.setCoupon_value(Double.parseDouble(redEnvelope.getFirstChild().getNodeValue())/100);
                    }else if (redEnvelope.getNodeName().equals("coupon_mininumn")){
                        wxCoupn.setCoupon_mininumn(Double.parseDouble(redEnvelope.getFirstChild().getNodeValue())/100);
                    }else if (redEnvelope.getNodeName().equals("coupon_type")){
                        wxCoupn.setCoupon_type(Integer.parseInt(redEnvelope.getFirstChild().getNodeValue()));
                    }else if (redEnvelope.getNodeName().equals("coupon_stock_status")){
                        wxCoupn.setCoupon_stock_status(Integer.parseInt(redEnvelope.getFirstChild().getNodeValue()));
                    }else if (redEnvelope.getNodeName().equals("coupon_total")){
                        wxCoupn.setCoupon_total(Integer.parseInt(redEnvelope.getFirstChild().getNodeValue()));
                    }else if (redEnvelope.getNodeName().equals("max_quota")){
                        wxCoupn.setMax_quota(Integer.parseInt(redEnvelope.getFirstChild().getNodeValue()));
                    }else if (redEnvelope.getNodeName().equals("used_num")){
                        wxCoupn.setUsed_num(Integer.parseInt(redEnvelope.getFirstChild().getNodeValue()));
                    }else if (redEnvelope.getNodeName().equals("is_send_num")){
                        wxCoupn.setIs_send_num(Integer.parseInt(redEnvelope.getFirstChild().getNodeValue()));
                    }else if (redEnvelope.getNodeName().equals("begin_time_t")){
                    	Date date = sdf.parse(redEnvelope.getFirstChild().getNodeValue());
                        wxCoupn.setBegin_time(date);
                    }else if (redEnvelope.getNodeName().equals("end_time_t")){
                        Date date= sdf.parse(redEnvelope.getFirstChild().getNodeValue());
                        wxCoupn.setEnd_time(date);
                    }else if (redEnvelope.getNodeName().equals("create_time_t")){
                        Date date= sdf.parse(redEnvelope.getFirstChild().getNodeValue());
                        wxCoupn.setCreateTime(date);
                    }else if (redEnvelope.getNodeName().equals("coupon_id")) {
                    	wxCoupn.setCoupon_id(redEnvelope.getFirstChild().getNodeValue());
                    }else if (redEnvelope.getNodeName().equals("coupon_state")) {
                    	wxCoupn.setCoupon_state(redEnvelope.getFirstChild().getNodeValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wxCoupn;
    }
	
	public static WxCoupon parseCoupnXMLerr(String protocolXML) {

		WxCoupon wxCoupn=new WxCoupon();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder
                    .parse(new InputSource(new StringReader(protocolXML)));

            Element root = doc.getDocumentElement();
            NodeList redEnvelopes = root.getChildNodes();
            if (redEnvelopes != null) {
                for (int i = 0; i < redEnvelopes.getLength(); i++) {
                    Node redEnvelope = redEnvelopes.item(i);
                    if (redEnvelope.getNodeName().equals("err_code_des")){
                        wxCoupn.setErr(redEnvelope.getFirstChild().getNodeValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxCoupn;
    }
    
    public static String parseCoupnXMLsignke(String protocolXML) {

        String sandbox_signkey="";

         try {
             DocumentBuilderFactory factory = DocumentBuilderFactory
                     .newInstance();
             DocumentBuilder builder = factory.newDocumentBuilder();
             Document doc = builder
                     .parse(new InputSource(new StringReader(protocolXML)));

             Element root = doc.getDocumentElement();
             NodeList redEnvelopes = root.getChildNodes();
             if (redEnvelopes != null) {
                 for (int i = 0; i < redEnvelopes.getLength(); i++) {
                     Node redEnvelope = redEnvelopes.item(i);
                     if (redEnvelope.getNodeName().equals("sandbox_signkey")){
                         sandbox_signkey=redEnvelope.getFirstChild().getNodeValue();
                     }
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return sandbox_signkey;
     }

}
