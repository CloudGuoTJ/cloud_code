//package com.common.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.*;
//import java.util.Properties;
//
///**
// * Created by Administrator on 2016/7/27.
// * 读取配置文件
// */
//public class ReadConfigPropertiesUtils {
//
//    private static Logger logger = LoggerFactory.getLogger(ReadConfigPropertiesUtils.class);
//
//    private static String dbhost = "";
//    private static String dbname = "";
//    private static String dbname1 = "";
//    private static String batch_code_db = "";
//    private static String dbuser = "";
//    private static String dbpwd = "";
//    private static String appid = "";
//    private static String appsecret = "";
//    private static String shortUrl = "";
//    private static String batchCodeUrl = "";
//    private static String batchLoginUrl = "";
//    private static String appidLog = "";
//    private static String appsecretLog = "";
//    private static String productViewType1 = "";
//    private static String productViewType2 = "";
//    private static String productViewType3 = "";
//    private static String productViewType4 = "";
//    private static String apiKey = "";
//    private static String appId = "";
//    private static String resultUrl = "";
//
//    public static String getBatchLoginUrl() {
//        return batchLoginUrl;
//    }
//
//    public static void setBatchLoginUrl(String batchLoginUrl) {
//        ReadConfigPropertiesUtils.batchLoginUrl = batchLoginUrl;
//    }
//
//    public static String getBatchCodeUrl() {
//        return batchCodeUrl;
//    }
//
//    public static void setBatchCodeUrl(String batchCodeUrl) {
//        ReadConfigPropertiesUtils.batchCodeUrl = batchCodeUrl;
//    }
//
//    public static String getBatch_code_db() {
//        return batch_code_db;
//    }
//
//    public static void setBatch_code_db(String batch_code_db) {
//        ReadConfigPropertiesUtils.batch_code_db = batch_code_db;
//    }
//
//    public static String getDbname1() {
//        return dbname1;
//    }
//
//    public static void setDbname1(String dbname1) {
//        ReadConfigPropertiesUtils.dbname1 = dbname1;
//    }
//
//    public static String getDbhost() {
//        return dbhost;
//    }
//
//    public static void setDbhost(String dbhost) {
//        ReadConfigPropertiesUtils.dbhost = dbhost;
//    }
//
//    public static String getDbname() {
//        return dbname;
//    }
//
//    public static void setDbname(String dbname) {
//        ReadConfigPropertiesUtils.dbname = dbname;
//    }
//
//    public static String getDbuser() {
//        return dbuser;
//    }
//
//    public static void setDbuser(String dbuser) {
//        ReadConfigPropertiesUtils.dbuser = dbuser;
//    }
//
//    public static String getDbpwd() {
//        return dbpwd;
//    }
//
//    public static void setDbpwd(String dbpwd) {
//        ReadConfigPropertiesUtils.dbpwd = dbpwd;
//    }
//
//    public static String getAppid() {
//        return appid;
//    }
//
//    public static void setAppid(String appid) {
//        ReadConfigPropertiesUtils.appid = appid;
//    }
//
//    public static String getAppsecret() {
//        return appsecret;
//    }
//
//    public static void setAppsecret(String appsecret) {
//        ReadConfigPropertiesUtils.appsecret = appsecret;
//    }
//
//    public static String getShortUrl() {
//        return shortUrl;
//    }
//
//    public static void setShortUrl(String shortUrl) {
//        ReadConfigPropertiesUtils.shortUrl = shortUrl;
//    }
//
//    public static String getAppidLog() {
//        return appidLog;
//    }
//
//    public static void setAppidLog(String appidLog) {
//        ReadConfigPropertiesUtils.appidLog = appidLog;
//    }
//
//    public static String getAppsecretLog() {
//        return appsecretLog;
//    }
//
//    public static void setAppsecretLog(String appsecretLog) {
//        ReadConfigPropertiesUtils.appsecretLog = appsecretLog;
//    }
//
//    public static String getProductViewType1() {
//        return productViewType1;
//    }
//
//    public static void setProductViewType1(String productViewType1) {
//        ReadConfigPropertiesUtils.productViewType1 = productViewType1;
//    }
//
//    public static String getProductViewType2() {
//        return productViewType2;
//    }
//
//    public static void setProductViewType2(String productViewType2) {
//        ReadConfigPropertiesUtils.productViewType2 = productViewType2;
//    }
//
//    public static String getProductViewType3() {
//        return productViewType3;
//    }
//
//    public static void setProductViewType3(String productViewType3) {
//        ReadConfigPropertiesUtils.productViewType3 = productViewType3;
//    }
//
//    public static String getProductViewType4() {
//        return productViewType4;
//    }
//
//    public static void setProductViewType4(String productViewType4) {
//        ReadConfigPropertiesUtils.productViewType4 = productViewType4;
//    }
//
//    public static String getApiKey() {
//        return apiKey;
//    }
//
//    public static void setApiKey(String apiKey) {
//        ReadConfigPropertiesUtils.apiKey = apiKey;
//    }
//
//    public static String getAppId() {
//        return appId;
//    }
//
//    public static void setAppId(String appId) {
//        ReadConfigPropertiesUtils.appId = appId;
//    }
//
//    public static String getResultUrl() {
//        return resultUrl;
//    }
//
//    public static void setResultUrl(String resultUrl) {
//        ReadConfigPropertiesUtils.resultUrl = resultUrl;
//    }
//
//    /**
//     * 读取配置文件的内容
//     * 放置于静态代码块里，当该类一旦被调用的时候，就会先执行静态代码块的内容，且只会执行一次，之后则不再执行
//     * 因为静态方法需要调用的时候才执行，所以不适合
//     */
//    static {
//        logger.info("开始读取配置文件config.properties");
//        String url = System.getProperty("catalina.home") + "/conf/config.properties";
//        File file = new File(url);
//        Properties properties = new Properties();
//        InputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(file);
//            properties.load(inputStream);
//        } catch (FileNotFoundException e) {
//            logger.error("Tomcat的conf目录不存在config.properties文件！", e);
//        } catch (IOException e) {
//            logger.error("加载config.properties时IO流转换出错！", e);
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    logger.error("加载config.properties时IO流关闭出错！", e);
//                }
//            }
//        }
//        setDbhost(properties.getProperty("dbhost"));
//        setDbname(properties.getProperty("dbname"));
//        setDbname1(properties.getProperty("dbname1"));
//        setBatch_code_db(properties.getProperty("batch_code_db"));
//        setDbuser(properties.getProperty("dbuser"));
//        setDbpwd(properties.getProperty("dbpwd"));
//        setAppid(properties.getProperty("appid"));
//        setAppsecret(properties.getProperty("appsecret"));
//        setShortUrl(properties.getProperty("shortUrl"));
//        setBatchCodeUrl(properties.getProperty("batchCodeUrl"));
//        setBatchLoginUrl(properties.getProperty("batchLoginUrl"));
//        setAppidLog(properties.getProperty("appidLog"));
//        setAppsecretLog(properties.getProperty("appsecretLog"));
//        setProductViewType1(properties.getProperty("productViewType1"));
//        setProductViewType2(properties.getProperty("productViewType2"));
//        setProductViewType3(properties.getProperty("productViewType3"));
//        setProductViewType4(properties.getProperty("productViewType4"));
//        setApiKey(properties.getProperty("apiKey"));
//        setAppId(properties.getProperty("appId"));
//        setResultUrl(properties.getProperty("resultUrl"));
//        logger.info("成功读取配置文件config.properties");
//    }
//}