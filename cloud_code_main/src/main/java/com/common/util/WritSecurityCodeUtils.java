package com.common.util;
//
//import com.securitycode.model.Product;
//import com.securitycode.model.SecurityCode;
//import org.apache.commons.lang.StringUtils;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by Atlantique on 2015/9/6.
// */
//public class WritSecurityCodeUtils {
//    public String saveSecurityCodeFile(List<SecurityCode> securityCodeList, int orderId, Product product, String vendorName, String wxQrCode, Config config) throws Exception {
//        String productStr = securityCodeList.get(0).getSecurityCode().substring(0, 6);
//        String vendorStr = productStr.substring(0, 4);
//        /*wirte date directory*/
//        Date now = new Date();
//        SimpleDateFormat formatter;
//        formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dataDirectory = "/data/securitycode/" + formatter.format(now);    //正式环境
//        File dataDirFile = new File(dataDirectory);
//        if (!dataDirFile.exists()) {
//            dataDirFile.mkdirs();
//        }
//
//        /*wirte internal code file*/
//        String content = "This is the content to write into file";
//        String fullInternalFileName = String.format("%s/%d" + vendorName + "nei", dataDirectory, orderId);
//        String fullExternalFileName = String.format("%s/%d" + vendorName + "wai", dataDirectory, orderId);
//        File file = new File(fullInternalFileName);
//        File file1 = new File(fullExternalFileName);
//
//        file.createNewFile();
//        file1.createNewFile();
//
//        FileWriter fw = new FileWriter(file.getAbsoluteFile());
//        FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
//        BufferedWriter bw = new BufferedWriter(fw);
//        BufferedWriter bw1 = new BufferedWriter(fw1);
//
//        String prefix = null;
//
//        if (product.getViewType() == 1) {
//            if (StringUtils.isNotEmpty(wxQrCode))
//                prefix = wxQrCode + "?code=";
//            else
//           prefix= config.getProductViewType1();
//        } else if (product.getViewType() == 2) {
//            prefix= config.getProductViewType2();
//        } else if (product.getViewType() == 3) {
//            prefix= config.getProductViewType3();
//        } else if (product.getViewType() == 4) {
//            if (vendorStr.equals("000W"))//黃河源厂商
//                prefix = "http://5cyc.cn?";
//            else
//                prefix= config.getProductViewType4();
//        } else {
//            prefix= config.getProductViewType1();
//        }
//
//        for (SecurityCode securityCode : securityCodeList) {
//            if (securityCode.getInternalCodeFlag() == 2) {
//                bw.write(prefix);
//                //当为微信web模式下的黄河源矿泉水那一款产品时，生成的二维码去掉前面6位
//                if (product.getViewType() == 4 && productStr.equals("000W01"))
//                    bw.write(securityCode.getSecurityCode().substring(6));
//                else
//                    bw.write(securityCode.getSecurityCode());
//                bw.write("\r\n");
//            } else {
//                bw1.write(prefix);
//                if (product.getViewType() == 4 && productStr.equals("000W01"))
//                    bw1.write(securityCode.getSecurityCode().substring(6));
//                else
//                    bw1.write(securityCode.getSecurityCode());
//                bw1.write("\r\n");
//            }
//        }
//        bw.close();
//        bw1.close();
//        return dataDirectory;
//    }
//
//}
