package com.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.sd4324530.fastweixin.util.JSONUtil;
import com.yunma.model.Location;
import com.yunma.service.mapCount.MapCountService;

/**
 * Created by Atlantique on 2015/7/28.
 */
public class BaiduMapUtils {

    @Autowired
    private MapCountService mapCountService;

    private final static String ak = "1d6e2e83ea918b5f613bcf5a82257a3c";
    
    private static Logger logger = LoggerFactory.getLogger(BaiduMapUtils.class);

    //"http://api.map.baidu.com/geocoder/v2/?ak=1d6e2e83ea918b5f613bcf5a82257a3c&location=39.983424,116.322987&output=json"
    public static String getAddress(String latitude, String longitude) {
        String url = "http://api.map.baidu.com/geocoder/v2/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("ak", ak);
        params.put("location", latitude + "," + longitude);
        params.put("output", "json");
        String result = HttpClientUtils.doGet(url, params);
        Object obj = JSONUtil.getStringFromJSONObject(result, "result");
        return JSONUtil.getStringFromJSONObject(obj.toString(), "formatted_address").toString();
    }

    public static Location getLocation(String latitude,String longitude){
        String url = "http://api.map.baidu.com/geocoder/v2/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("ak", ak);
        params.put("location", latitude + "," + longitude);
        params.put("output", "json");
        String result = HttpClientUtils.doGet(url, params);
        logger.debug("经度："+longitude+"纬度："+latitude);
        logger.debug("百度地图获得的数据："+result);
        Object obj = JSONUtil.getStringFromJSONObject(result, "result");
        logger.debug("百度地图获得的数据："+obj.toString());
        Object addressComponent = JSONUtil.getStringFromJSONObject(obj.toString(), "addressComponent");
        String formatted_address = JSONUtil.getStringFromJSONObject(obj.toString(), "formatted_address").toString();
        String province = JSONUtil.getStringFromJSONObject(addressComponent.toString(), "province").toString();
        String city = JSONUtil.getStringFromJSONObject(addressComponent.toString(), "city").toString();
        String district = JSONUtil.getStringFromJSONObject(addressComponent.toString(), "district").toString();

        Location location = new Location();
        location.setAddress(formatted_address);
        location.setProvince(province);
        location.setCity(city);
        location.setDistrict(district);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;
    }

    public static List<Location> getLocation1(){

        List<Location> locationList = new ArrayList<Location>();

        String latitude = null;
        String longitude = null;

        Random random = new Random();

        int i=0;
        int j=0;

        while (i<50){
            latitude = random.nextInt(123-18)+18+"."+random.nextInt(999999);
            longitude = random.nextInt(135-73)+73+"."+random.nextInt(999999);

            String url = "http://api.map.baidu.com/geocoder/v2/";
            Map<String, String> params = new HashMap<String, String>();
            params.put("ak", ak);
            params.put("location", latitude + "," + longitude);
            params.put("output", "json");
            String result = HttpClientUtils.doGet(url, params);
            Object obj = JSONUtil.getStringFromJSONObject(result, "result");

            Object addressComponent = JSONUtil.getStringFromJSONObject(obj.toString(), "addressComponent");
            String formatted_address = JSONUtil.getStringFromJSONObject(obj.toString(), "formatted_address").toString();
            String province = JSONUtil.getStringFromJSONObject(addressComponent.toString(), "province").toString();
            String city = JSONUtil.getStringFromJSONObject(addressComponent.toString(), "city").toString();
            String district = JSONUtil.getStringFromJSONObject(addressComponent.toString(), "district").toString();
            String country = JSONUtil.getStringFromJSONObject(addressComponent.toString(), "country").toString();

            if (country.equals("中国")){  //中国
                Location location = new Location();
                location.setAddress(formatted_address);
                location.setProvince(province);
                location.setCity(city);
                location.setDistrict(district);
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                locationList.add(location);
                i++;
            }

            j++;
            //int a=random.nextInt(999999);
        }
        return locationList;
    }

    public String getProvinceNick(String pro){
        String province = null;
        if (pro.contains("北京")){
            province = "北京";
        }else if (pro.contains("天津")){
            province = "天津";
        }else if (pro.contains("河北")){
            province = "河北";
        }else if (pro.contains("山西")){
            province = "山西";
        }else if (pro.contains("内蒙古")){
            province = "内蒙古";
        }else if (pro.contains("辽宁")){
            province = "辽宁";
        }else if (pro.contains("吉林")){
            province = "吉林";
        }else if (pro.contains("黑龙江")){
            province = "黑龙江";
        }else if (pro.contains("上海")){
            province = "上海";
        }else if (pro.contains("江苏")){
            province = "江苏";
        }else if (pro.contains("浙江")){
            province = "浙江";
        }else if (pro.contains("安徽")){
            province = "安徽";
        }else if (pro.contains("福建")){
            province = "福建";
        }else if (pro.contains("江西")){
            province = "江西";
        }else if (pro.contains("山东")){
            province = "山东";
        }else if (pro.contains("河南")){
            province = "河南";
        }else if (pro.contains("湖北")){
            province = "湖北";
        }else if (pro.contains("湖南")){
            province = "湖南";
        }else if (pro.contains("广东")){
            province = "广东";
        }
        else if (pro.contains("广西")){
            province = "广西";
        }else if (pro.contains("海南")){
            province = "海南";
        }else if (pro.contains("重庆")){
            province = "重庆";
        }else if (pro.contains("四川")){
            province = "四川";
        }else if (pro.contains("贵州")){
            province = "贵州";
        }else if (pro.contains("云南")){
            province = "云南";
        }else if (pro.contains("西藏")){
            province = "西藏";
        }else if (pro.contains("陕西")){
            province = "陕西";
        }else if (pro.contains("甘肃")){
            province = "甘肃";
        }else if (pro.contains("青海")){
            province = "青海";
        }else if (pro.contains("宁夏")){
            province = "宁夏";
        }else if (pro.contains("新疆")){
            province = "新疆";
        }else if (pro.contains("台湾")){
            province = "台湾";
        }else if (pro.contains("香港")){
            province = "香港";
        }else if (pro.contains("澳门")){
            province = "澳门";
        }
        return province;
    }

    public int isProvinceCity(String province){
        String pro = province;
        int i = 0;
        if (pro.contains("北京")){
            i = 1;
        }else if (pro.contains("天津")){
            i = 1;
        }else if (pro.contains("上海")){
            i = 1;
        }else if (pro.contains("重庆")){
            i = 1;
        }
        return i;
    }

    public static void main(String[] args) {
        //System.out.println(getAddress("39.922682", "116.417204"));
    }
}
