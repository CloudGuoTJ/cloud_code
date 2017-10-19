package com.common.util;
//
//import com.securitycode.interceptor.DBContextHolder;
//import com.securitycode.order.data.entity.Order;
//import com.securitycode.order.service.OrderService;
//import com.securitycode.service.TableHandleService;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2016/9/28.
// */
//@Controller
//public class TableUtils {
//    @Autowired
//    private Config config;
//    @Autowired
//    private OrderService orderService;
//    @Autowired
//    private TableHandleService tableHandleService;
//
//    //增加分库分表的表_t_securityCode_message/
//    @RequestMapping("/addTable.json")
//    @ResponseBody
//    public ResultObject addTable(){
//        List<Order> allOrder = orderService.findAllOrder();
//        for (Order order : allOrder) {
//            String perfixOrderNo = order.getPerfixOrderNo();
//            if (StringUtils.isBlank(perfixOrderNo)) {
//                continue;
//            }
//            String dbName = config.getDbname();
//            String tableExist = tableHandleService.tableExistByDB(dbName + "1", perfixOrderNo + "_t_securityCode_message");
//            if (StringUtils.isBlank(tableExist)) {
//                int table = tableHandleService.createTable(perfixOrderNo, dbName + "1");
//            }
//
//            String tableExceptionExist = tableHandleService.tableExistByDB(dbName + "1", perfixOrderNo + "_t_anti_fake_exception");
//            if (StringUtils.isBlank(tableExceptionExist)) {
//                int table = tableHandleService.createExceptionTable(perfixOrderNo, dbName + "1");
//            }
//
//            String tableExceptionCountExist = tableHandleService.tableExistByDB(dbName + "1", perfixOrderNo + "_t_anti_fake_exception_count");
//            if (StringUtils.isBlank(tableExceptionCountExist)) {
//                int table = tableHandleService.createExceptionCountTable(perfixOrderNo, dbName + "1");
//            }
//
//            String tableCollectExist = tableHandleService.tableExistByDB(dbName + "1", perfixOrderNo + "_t_collect_securityCode");
//            if (StringUtils.isBlank(tableCollectExist)) {
//                int table = tableHandleService.createCollectTable(perfixOrderNo, dbName + "1");
//            }
//
//        }
//        return new ResultObject(0);
//    }
//
//
//
//    //删除Order有记录但是分库分表没有表的Order记录
//    @RequestMapping("/testCalculate.json")
//    @ResponseBody
//    public ResultObject testCalculate() {
//        ResultObject resultObject = new ResultObject();
//        List<Order> allOrder = orderService.findAllOrder();
//        for (Order order : allOrder) {
//            String perfixOrderNo = order.getPerfixOrderNo();
//            if (StringUtils.isBlank(perfixOrderNo))//非分库分表的不算
//                continue;
//            if (order.getStatus() == 1) //未生成二维码的不算
//                continue;
//            String dbName = config.getDbname();
//            String tableExist = tableHandleService.tableExistByDB(dbName + "1", perfixOrderNo + "_t_anti_fake");
//            if (StringUtils.isBlank(tableExist)) {
//                System.out.println(perfixOrderNo);
//                orderService.deleteOrder(order.getOrderId());
//            }
//        }
//        return resultObject;
//    }
//
//    //删除Order表中没有记录但是分库分表却存在表的表
//    @RequestMapping("/deleteTables.json")
//    @ResponseBody
//    public ResultObject deleteTables() {
//        StringBuffer sb = new StringBuffer();
//        List<Order> allOrder = orderService.findAllOrder();
//        for (Order order : allOrder) {
//            String perfixOrderNo = order.getPerfixOrderNo();
//            if (StringUtils.isBlank(perfixOrderNo))//非分库分表的不算
//                continue;
//            sb.append(perfixOrderNo + "_");
//        }
//
//        String dbName = config.getDbname();
//        List<String> tableNames = tableHandleService.getTableNames(dbName + "1");
//        for (String s : tableNames) {
//            String substring = s.substring(0, s.indexOf("_"));
//            int i = sb.indexOf(substring);
//            if (i == -1) { //该表在Order中没有记录
//                System.out.println(s);
//                tableHandleService.dropTable(s, dbName + "1");
//            }
//        }
//        return new ResultObject();
//    }
//
//    //给t_anti_fake表增加moreMoney字段
//    @RequestMapping("/addMoreMoney.json")
//    @ResponseBody
//    public ResultObject addMoreMoney() {
//        ResultObject resultObject = new ResultObject();
//
//        List<Order> allOrder = orderService.findAllOrder();
//        DBContextHolder.setDbType("ds2");
//        for (Order order : allOrder) {
//            String perfixOrderNo = order.getPerfixOrderNo();
//            if (StringUtils.isBlank(perfixOrderNo))//非分库分表的不算
//                continue;
//            if (order.getStatus() == 1) //未生成二维码的不算
//                continue;
//
//            Integer count = tableHandleService.columnExistOfTable(perfixOrderNo + "_t_anti_fake", "moreMoney");
//
//            if (count != 0 ) {
//                System.out.println(perfixOrderNo + "----");
//                tableHandleService.dropColumnOfTable(perfixOrderNo + "_t_anti_fake", "`moreMoney`");
//            }
//
//            tableHandleService.addColumnOfTable(perfixOrderNo + "_t_anti_fake", "`moreMoney` decimal(10,2) NOT NULL DEFAULT '-1' COMMENT '二维码红包已领取金额（默认-1，未领取状态）'");
//
//            Integer integer = tableHandleService.columnExistOfTable(perfixOrderNo + "_t_anti_fake", "productScanCount");
//            if (integer == 0) {
//                System.out.println(perfixOrderNo);
//                tableHandleService.addColumnOfTable(perfixOrderNo + "_t_anti_fake", "productScanCount int(11) DEFAULT '0' COMMENT '当天产品扫码次数'");
//            }
//        }
//        DBContextHolder.setDbType("ds1");
//        return resultObject;
//    }
//
//    /**
//     * t_anti_fake_exception_count表的times字段添加唯一索引
//     * ALTER TABLE `004701jn_t_anti_fake_exception_count` ADD UNIQUE(`times`);
//     * t_anti_fake_exception表userId字段修改为openId字段
//     * @return
//     */
//    @RequestMapping("/handleExceptionAndExceptionCount.json")
//    @ResponseBody
//    public ResultObject handleExceptionAndExceptionCount() {
//        ResultObject resultObject = new ResultObject();
//        List<Order> allOrder = orderService.findAllOrder();
//        for (Order order : allOrder) {
//            String perfixOrderNo = order.getPerfixOrderNo();
//            if (StringUtils.isBlank(perfixOrderNo))//非分库分表的不算
//                continue;
//            if (order.getStatus() == 1) //未生成二维码的不算
//                continue;
//            DBContextHolder.setDbType("ds2");
//            //判断列userId是否存在
//            Integer integer = tableHandleService.columnExistOfTable(perfixOrderNo + "_t_anti_fake_exception", "userId");
//            if (integer == 0) {
//                //更换字段openId的位置
//                tableHandleService.update("ALTER TABLE `" + perfixOrderNo + "_t_anti_fake_exception` CHANGE `openId` `openId` varchar(64) NOT NULL COMMENT 'openID' AFTER `scanAddress`;");
//                DBContextHolder.setDbType("ds1");
//                continue;
//            }
//            //删除userId字段
//            tableHandleService.dropColumnOfTable(perfixOrderNo + "_t_anti_fake_exception", "`userId`");
//            //增加openId字段
//            tableHandleService.addColumnOfTable(perfixOrderNo + "_t_anti_fake_exception", "`openId` varchar(64) NOT NULL COMMENT 'openID' AFTER `scanAddress`");
//            //t_anti_fake_exception_count表的times字段添加唯一索引
//            tableHandleService.update("ALTER TABLE `" + perfixOrderNo + "_t_anti_fake_exception_count` ADD UNIQUE(`times`);");
//            System.out.println(perfixOrderNo);
//            DBContextHolder.setDbType("ds1");
//        }
//
//        return resultObject;
//    }
//
//    /**
//     *
//     * @return
//     */
//    @RequestMapping("/handleCollect.json")
//    @ResponseBody
//    public ResultObject handleCollect() {
//        ResultObject resultObject = new ResultObject();
//        List<Order> allOrder = orderService.findAllOrder();
//        for (Order order : allOrder) {
//            String perfixOrderNo = order.getPerfixOrderNo();
//            if (StringUtils.isBlank(perfixOrderNo))//非分库分表的不算
//                continue;
//            if (order.getStatus() == 1) //未生成二维码的不算
//                continue;
//            System.out.println(perfixOrderNo);
//            tableHandleService.dropTable(perfixOrderNo + "_t_logstics_log", "securitycode1");
//            tableHandleService.dropTable(perfixOrderNo + "_t_securityCode_message", "securitycode1");
//            tableHandleService.dropTable(perfixOrderNo + "_t_vendor_mapping_inner", "securitycode1");
//            tableHandleService.dropTable(perfixOrderNo + "_t_vendor_mapping_outer", "securitycode1");
//        }
//
//        return resultObject;
//    }
//
//    //给t_anti_fake表增加isSendRed字段
//    @RequestMapping("/addIsSendRed.json")
//    @ResponseBody
//    public ResultObject addIsSendRed() {
//        ResultObject resultObject = new ResultObject();
//
//        List<Order> allOrder = orderService.findAllOrder();
//        DBContextHolder.setDbType("ds2");
//        for (Order order : allOrder) {
//            String perfixOrderNo = order.getPerfixOrderNo();
//            if (StringUtils.isBlank(perfixOrderNo))//非分库分表的不算
//                continue;
//            if (order.getStatus() == 1) //未生成二维码的不算
//                continue;
//
//            Integer count = tableHandleService.columnExistOfTable(perfixOrderNo + "_t_anti_fake", "isSendRed");
//
//            if (count != 0 ) {
//                System.out.println(perfixOrderNo + "----");
//                tableHandleService.dropColumnOfTable(perfixOrderNo + "_t_anti_fake", "`isSendRed`");
//            }
//
//            System.out.println("订单前缀：" + perfixOrderNo);
//
//            tableHandleService.addColumnOfTable(perfixOrderNo + "_t_anti_fake", "`isSendRed` bit(1) NOT NULL DEFAULT b'0' COMMENT '二维码红包是否取现：0、未取现，1、已取现'");
//        }
//        DBContextHolder.setDbType("ds1");
//        return resultObject;
//    }
//}
