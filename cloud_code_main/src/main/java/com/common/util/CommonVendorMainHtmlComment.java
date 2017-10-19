package com.common.util;


import com.yunma.service.user.UserService;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/30.
 */
public class CommonVendorMainHtmlComment {
    @Autowired
    private UserService userService;

    private  static  Map<Integer,String> htmlMap=new HashMap<Integer, String>();

    private  static  Map<Integer,String> htmlEnMap=new HashMap<Integer, String>();

    private  static  Map<Integer,String> roleNameMap=new HashMap<Integer, String>();

    static {
        htmlMap.put(98, "<div class=\"item\">厂商管理<i class=\"cjgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"ad-product\">企业信息</a></li><li><a id=\"sys_user1\">账号信息</a></li><li><a id=\"wx-infot\">微信公众号信息</a></li></ul><div class=\"item\">防伪<i class=\"ycts\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"exception_message\">防伪地图</a></li></ul><div class=\"item\">大数据<i class=\"ddgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"data-fx\">消费者扫码信息分析</a></li></ul><div class=\"item\">用户互动<i class=\"uegl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"user_list\">用户列表</a></li><li><a id=\"suggestion\">意见反馈</a></li><li><a id=\"newQuestionnaire\">调查问卷</a></li></ul><div class=\"item\">查询管理<i class=\"ddg2\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"findManager\">对账查询</a></li><li><a id=\"findlogistics\">物流查询</a></li></ul><div class=\"item\">全民大挑战<i class=\"ddg3\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"challenge\">挑战模式</a></li><li><a id=\"relaxation\">休闲模式</a></li></ul><div class=\"item\">游戏互动<i class=\"wei\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"Gamemanager\" class=\"hover\">游戏管理</a></li></ul>");
        htmlMap.put(99, "<div class=\"item\">厂商管理<i class=\"cjgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"ad-product\">企业信息</a></li><li><a id=\"sys_user1\">账号信息</a></li><li><a id=\"wx-infot\">微信公众号信息</a></li></ul><div class=\"item\">防伪<i class=\"ycts\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"exception_message\">防伪地图</a></li></ul><div class=\"item\">大数据<i class=\"ddgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"data-fx\">消费者扫码信息分析</a></li></ul><div class=\"item\">用户互动<i class=\"uegl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"user_list\">用户列表</a></li><li><a id=\"suggestion\">意见反馈</a></li><li><a id=\"newQuestionnaire\">调查问卷</a></li></ul><div class=\"item\">查询管理<i class=\"ddg2\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"findManager\">对账查询</a></li><li><a id=\"findlogistics\">物流查询</a></li></ul><div class=\"item\">全民大挑战<i class=\"ddg3\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"challenge\">挑战模式</a></li><li><a id=\"relaxation\">休闲模式</a></li></ul><div class=\"item\">游戏互动<i class=\"wei\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"Gamemanager\" class=\"hover\">游戏管理</a></li></ul>");
        htmlMap.put(1, "<div class=\"item\">企业管理<i class=\"cjgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"product\"class=\"hover\">产品管理</a></li><li><a id=\"order\"class=\"hover\">订单管理</a></li><li><a id=\"slap\">批码管理</a></li><li><a id=\"vendor_wallet\"class=\"hover\">厂商钱包</a></li><li><a id=\"award_list\"class=\"hover\">奖品管理</a></li><li><a id=\"sys_user\"class=\"hover\">账号信息</a></li><li><a id=\"wx-info\"class=\"hover\">微信公众号信息</a></li></ul><div class=\"item\">防伪<i class=\"ycts\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"Security\"class=\"hover\">信息设置</a></li><li><a id=\"exception_message\"class=\"hover\">防伪地图</a></li></ul><div class=\"item\">溯源<i class=\"qdgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"logistics\"class=\"hover\">渠道管理</a></li><li><a id=\"sankey\"class=\"hover\">渠道信息分析</a></li></ul><div class=\"item\">营销<i class=\"hbgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"red_list\"class=\"hover\">红包管理</a></li><li><a id=\"award_grant\"class=\"hover\">活动管理</a></li><li><a id=\"integral_list\"class=\"hover\">积分管理</a></li><li><a id=\"voucher_list\"class=\"hover\">优惠券管理</a></li></ul><div class=\"item\">大数据<i class=\"ddgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"data-fx\"class=\"hover\">消费者扫码信息分析</a></li></ul><div class=\"item\">用户互动<i class=\"uegl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"user_list\"class=\"hover\">用户列表</a></li><li><a id=\"suggestion\"class=\"hover\">意见反馈</a></li><li><a id=\"newQuestionnaire\"class=\"hover\">调查问卷</a></li></ul><div class=\"item\">查询管理<i class=\"ddg2\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"findManager\"class=\"hover\">对账查询</a></li><li><a id=\"findlogistics\"class=\"hover\">物流查询</a></li></ul><div class=\"item\">全民大挑战<i class=\"ddg3\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"challenge\"class=\"hover\">挑战模式</a></li><li><a id=\"relaxation\"class=\"hover\">休闲模式</a></li></ul><div class=\"item\">游戏互动<i class=\"wei\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"playlist\"class=\"hover\">游戏列表</a></li><li><a id=\"arrangements\"class=\"hover\">游戏安排</a></li><li><a id=\"Winning_details\"class=\"hover\">中奖信息</a></li></ul><div class=\"item\" onClick=\"wxin()\">微信商城<i class=\"wxshop\"></i><b></b></div>");
        htmlMap.put(2, "<div class=\"item\">溯源<i class=\"qdgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"logistics-dlc\">代理信息</a></li><li><a id=\"logistics-dlcj\">次级代理管理</a></li></ul><div class=\"item\">营销<i class=\"hbgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"vendor_wallet\">厂商钱包</a></li><li><a id=\"red_list\">红包管理</a></li><li><a id=\"award_grant\">奖品管理</a></li><li><a id=\"integral_list\">积分管理</a></li><li><a id=\"voucher_list\">代金券管理</a></li></ul>");


        htmlEnMap.put(98, "<div class=\"item\">Manufacturer Management <i class=\"cjgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"ad-product\">Company Profile</a></li><li><a id=\"sys_user\">Accounts</a></li><li><a id=\"wx-infot\">WeChat Offical Accounts</a></li></ul><div class=\"item\">Anti-fake <i class=\"ycts\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"exception_message\">Anti-fake</a></li></ul><div class=\"item\">Big Data <i class=\"ddgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"data-fx\">Consumer code-scanning info analysis</a></li></ul><div class=\"item\">User Interaction <i class=\"uegl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"user_list\">User List</a></li><li><a id=\"suggestion\">Suggestions</a></li><li><a id=\"newQuestionnaire\">Questionnaire</a></li></ul><div class=\"item\">Query Management <i class=\"ddg2\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"findManager\">Statement Search</a></li><li><a id=\"findlogistics\">Logistics Search</a></li></ul><div class=\"item\">Big Challenge <i class=\"ddg3\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"challenge\">Challenging Mode</a></li><li><a id=\"relaxation\">Leisure mode</a></li></ul><div class=\"item\">Interactive Game <i class=\"wei\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"Gamemanager\" class=\"hover\">Game Management</a></li></ul>");
        htmlEnMap.put(99, "<div class=\"item\">Manufacturer Management <i class=\"cjgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"ad-product\">Company Profile</a></li><li><a id=\"sys_user\">Accounts</a></li><li><a id=\"wx-infot\">WeChat Offical Accounts</a></li></ul><div class=\"item\">Anti-fake <i class=\"ycts\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"exception_message\">Anti-fake</a></li></ul><div class=\"item\">Big Data <i class=\"ddgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"data-fx\">Consumer code-scanning info analysis</a></li></ul><div class=\"item\">User Interaction <i class=\"uegl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"user_list\">User List</a></li><li><a id=\"suggestion\">Suggestions</a></li><li><a id=\"newQuestionnaire\">Questionnaire</a></li></ul><div class=\"item\">Query Management <i class=\"ddg2\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"findManager\">Statement Search</a></li><li><a id=\"findlogistics\">Logistics Search</a></li></ul><div class=\"item\">Big Challenge <i class=\"ddg3\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"challenge\">Challenging Mode</a></li><li><a id=\"relaxation\">Leisure mode</a></li></ul><div class=\"item\">Interactive Game <i class=\"wei\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"Gamemanager\" class=\"hover\">Game Management</a></li></ul>");
        htmlEnMap.put(1, "<div class=\"item\">Enterprise Management<i class=\"cjgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"product\">ProductService Management</a></li><li><a id=\"order\">Order Management</a></li><li><a id=\"slap\">Batch of QR Code Management</a></li><li><a id=\"vendor_wallet\">Manufacturer’s wallet</a></li><li><a id=\"award_list\">Prize Management</a></li><li><a id=\"sys_user\">Accounts</a></li><li><a id=\"wx-info\">WeChat Offical Accounts</a></li></ul><div class=\"item\">Anti-fake<i class=\"ycts\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"Security\">Message Settings</a></li><li><a id=\"exception_message\">Anti-fake Map</a></li></ul><div class=\"item\">Traceability<i class=\"qdgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"logistics\">Channel Management</a></li><li><a id=\"sankey\">Channel Analysis</a></li></ul><div class=\"item\">Marketing<i class=\"hbgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"red_list\">Red Packet Management</a></li><li><a id=\"award_grant\">Activity management</a></li><li><a id=\"integral_list\">Points Management</a></li><li><a id=\"voucher_list\">Coupons Management</a></li></ul><div class=\"item\">Big Data<i class=\"ddgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"data-fx\">Consumer code-scanning info analysis</a></li></ul><div class=\"item\">User Interaction<i class=\"uegl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"user_list\">User List</a></li><li><a id=\"suggestion\">Suggestions</a></li><li><a id=\"newQuestionnaire\">Questionnaire</a></li></ul><div class=\"item\">Search Management<i class=\"ddg2\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"findManager\">Statement Search</a></li><li><a id=\"findlogistics\">Logistics Search</a></li></ul><div class=\"item\">Big challenge<i class=\"ddg3\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"challenge\">Challenging Mode</a></li><li><a id=\"relaxation\">Leisure Mode</a></li></ul><div class=\"item\">Interactive game<i class=\"wei\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"playlist\">Game list</a></li><li><a id=\"arrangements\">Game arrangement</a></li><li><a id=\"Winning_details\">Winning information</a></li></ul>");
        htmlEnMap.put(2, "<div class=\"item\">Channel Tracing<i class=\"qdgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"logistics-dlc\">Agent</a></li><li><a id=\"logistics-dlcj\">Agent Management</a></li></ul><div class=\"item\">Marketing<i class=\"hbgl\"></i><b></b></div><ul class=\"item-child\"><li><a id=\"vendor_wallet\">Enterprise's Capital Flow</a></li><li><a id=\"red_list\">Red Envelope Management</a></li><li><a id=\"award_grant\">Prize Management</a></li><li><a id=\"integral_list\">Integral Management</a></li><li><a id=\"voucher_list\">Voucher Management</a></li></ul>");

        roleNameMap.put(98,"次级管理员");
        roleNameMap.put(99,"超级管理员");
        roleNameMap.put(1,"厂商管理员");
        roleNameMap.put(2,"代理厂商");
    }


    public  static String getMainHtmlComment(Integer key) {
        if(StringUtils.isNotEmpty(htmlMap.get(key))){
            return htmlMap.get(key);
        }
        return null;
    }

    public  static String getMainHtmlEnComment(Integer key) {
        if(StringUtils.isNotEmpty(htmlEnMap.get(key))){
            return htmlEnMap.get(key);
        }
        return null;
    }

    public  static String getUserRoleName(Integer key) {
        if(StringUtils.isNotEmpty(roleNameMap.get(key))){
            return roleNameMap.get(key);
        }
        return null;
    }
}


