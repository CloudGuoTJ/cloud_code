package com.common.util;
//
//import com.github.sd4324530.fastweixin.api.config.ApiConfig;
//import com.securitycode.interceptor.DBContextHolder;
//import com.securitycode.model.Activity;
//import com.securitycode.model.WxConfig;
//import com.securitycode.service.*;
//import com.securitycode.vo.PremiumMoneyVO;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by Administrator on 2016/1/11.
// */
//@Component("taskJob")
//public class TaskJob {
//    private static Logger log = LoggerFactory.getLogger(TaskJob.class);
//
//    @Autowired
//    private ProductVendorWalletService productVendorWalletService;
//    @Autowired
//    private RedEnvelopeService redEnvelopeService;
//    @Autowired
//    private ActivityService activityService;
//    @Autowired
//    private GameUserService gameUserService;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private  WxConfigService wxConfigService;
//
//    @Scheduled(cron ="0 59 23 * * ?")
//    public void job1() throws UnsupportedEncodingException {
//        DBContextHolder.setDbType("ds1");
//         List<PremiumMoneyVO> premiumMoneyVOList=redEnvelopeService.listOldRedEnvelopePremium();
//        if(premiumMoneyVOList!=null) {
//            for (PremiumMoneyVO vo : premiumMoneyVOList) {
//                PremiumMoneyVO premiumMoneyVO = new PremiumMoneyVO();
//                premiumMoneyVO.setSendEnvelopeId(vo.getId());
//                premiumMoneyVO.setRefundMoney(vo.getRefundMoney());
//                premiumMoneyVO.setVendorId(vo.getVendorId());
//                premiumMoneyVO.setVendorAccountId(vo.getVendorAccountId());
//                premiumMoneyVO.setStatus(0);
//                premiumMoneyVO.setRefundTime(new Date());
//                premiumMoneyVO.setRemark("2");
//                int i = redEnvelopeService.listRedEnvelopePremiumCount(vo.getId());
//                if (i > 0) {
//                    //重复数据不插入退费明细表
//                } else {//成功插入退费明细
//                    redEnvelopeService.createRedEnvelopePremium(premiumMoneyVO);
//                    //同时更新厂商钱包余额
//                    productVendorWalletService.updatePremiumMoney(premiumMoneyVO.getRefundMoney(), premiumMoneyVO.getVendorAccountId());
//                }
//            }
//        }
//    }
//
//    @Scheduled(cron ="0 59 23 * * ?")
//        public void job2() throws UnsupportedEncodingException {
//        List<Activity> activities = activityService.activityVOList();
//        if (activities!=null) {
//            for (Activity activity : activities) {
//                if (activity.getEndTime().before(new Date())) {//过期数据
//                    //同时更新厂商活动发放 状态 更改为失效状态
//                    activityService.updateActivityState(activity.getId());
//                }
//            }
//        }
//    }
//
//    /**
//     * 定时生成在有效时间内的定量红包
//     */
//    @Scheduled(cron ="0 40 23 * * ?")
//    public void createQuantitatePond(){
//        log.info("-----------每天23:40定时生成定量红包开始----------");
//        redEnvelopeService.quantitativePondCreateTiming();
//        log.info("-----------每天23:40定时生成定量红包结束----------");
//    }
//
//    /**
//     * 全民大挑战当天未挑战连胜的定时清零
//     */
//    @Scheduled(cron ="0 59 23 * * ?")
//    public void resetWinningNum(){
//        log.info("-----------每天23:59全民大挑战当天未挑战连胜的，定时清零开始----------");
//        gameUserService.resetWinningNum();
//        log.info("-----------每天23:59全民大挑战当天未挑战连胜的，定时清零结束----------");
//        log.info("-----------每天23:59产品的当天扫码次数清零开始----------");
//        productService.resetScanCountsInDay();
//        log.info("-----------每天23:59产品的当天扫码次数清零结束----------");
//    }
//
//    /**
//     *微信公众号接口唯一凭证定时刷新
//     */
//    @Scheduled(cron ="0 0 0/1 * * ?")
//    public void tokenRefreshing() throws Exception {
//        log.info("-----------获取微信公众号接口唯一凭证access_token,ApiConfig对象开始---------");
//        log.info("-----------刷新apiConfig对象，开始时间:{}----------",DateUtils.getNow(DateUtils.FORMAT_FULL));
//        List<WxConfig> wxConfigs=wxConfigService.findWxConfigByVendor();
//        ApiConfig apiConfig=null;
//        for(WxConfig wxConfig:wxConfigs){
//            log.info("appId:{};secret:{};vendorId:{};",wxConfig.getAppid(),wxConfig.getAppsecret(),wxConfig.getVendorId());
//            apiConfig=new ApiConfig(wxConfig.getAppid(),wxConfig.getAppsecret());
//
//            wxConfigService.updateWxConfigForAccessToken(wxConfig,apiConfig);
//        }
//        log.info("-----------刷新微信公众开发接口ApiConfig结束----------");
//        log.info("-----------结束时间:{}----------",DateUtils.getNow(DateUtils.FORMAT_FULL));
//    }
//
//}