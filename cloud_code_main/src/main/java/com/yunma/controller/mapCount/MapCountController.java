//package com.yunma.controller.mapCount;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.common.controller.BaseController;
//import com.common.util.DateUtils;
//import com.common.util.ResultObject;
//import com.yunma.entity.product.ProductOrder;
//import com.yunma.entity.securityCode.SecurityCode;
//import com.yunma.model.MapDistrictHot;
//import com.yunma.model.User;
//import com.yunma.service.mapCount.MapCountService;
//import com.yunma.service.product.ProductOrderService;
//import com.yunma.service.secutrityCode.SecurityCodeService;
//import com.yunma.service.user.UserService;
//import com.yunma.vo.mapCount.MapCountVO;
//import com.yunma.vo.mapCount.MapDistrictHotVO;
//
//;
//
//@Controller
//public class MapCountController extends BaseController {
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private MapCountService mapCountService;
//	@Autowired
//	private SecurityCodeService  securityCodeService; 
//	@Autowired
//	private ProductOrderService orderService;
//
//
//	/**
//	 * 省地图数据
//	 * 
//	 * @param productName
//	 *            产品名称
//	 * @param startDate
//	 *            查询条件的开始时间
//	 * @param endDate
//	 *            查询条件的结束时间
//	 * @param orderId
//	 *            订单Id
//	 * @param userId
//	 * @return
//	 */
//	@RequestMapping("/GET/mapCount/mapAntiProCount.json")
//	@ResponseBody
//	public ResultObject mapAntiProCount(
//			@RequestParam(value = "productName", required = false) String productName,
//			@RequestParam(value = "startDate", required = false) String startDate,
//			@RequestParam(value = "endDate", required = false) String endDate,
//			@RequestParam(value = "orderId", required = false) Integer orderId,
//			@RequestParam("userId") Integer userId) {
//
//		logger.info("获取省地图数据开始时间 : {}", DateUtils.getTimeString());
//
//		// DBContextHolder.setDbType("ds1");
//		User user = userService.findUserByUserId(userId);
//		List<MapCountVO> countAntifakeAndLogisticsList;
//
//		if (productName != null && !("").equals(productName)) {
//			if (orderId != null && !("").equals(orderId)) { // 查询条件：时间、订单Id
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountProvinceByDateAndOrderId(startDate,
//								endDate, user, orderId);
//			} else { // 查询条件：时间、产品名称
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountProvinceByDateAndProductName(
//								startDate, endDate, user, productName);
//			}
//		} else {
//			if (orderId != null && !("").equals(orderId)) { // 查询条件:时间、订单Id
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountProvinceByDateAndOrderId(startDate,
//								endDate, user, orderId);
//			} else { // 查询条件:时间
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountProvinceByDate(startDate, endDate,
//								user);
//			}
//		}
//		List<MapCountVO> countMapVOList = mapCountService
//				.addCityAndDistrictDeaultMap(countAntifakeAndLogisticsList,
//						"云码科技", 0); // 给各省无数据的添加默认数据0（前端需要）
//
//		logger.info("获取省地图数据结束时间 : {}", DateUtils.getTimeString());
//
//		return ResultObject.createInstance(countMapVOList);
//	}
//
//	/**
//	 * 选择了省之后各市防伪扫数据
//	 * 
//	 * @param province
//	 *            省名称
//	 * @param productName
//	 *            产品名称
//	 * @param startDate
//	 *            查询开始日期
//	 * @param endDate
//	 *            查询结束日期
//	 * @param orderId
//	 *            订单Id
//	 * @param userId
//	 * @return
//	 */
//	@RequestMapping("/GET/mapCount/mapAntiCityCount.do")
//	@ResponseBody
//	public ResultObject mapAntiCityCount(
//			@RequestParam("province") String province,
//			@RequestParam(value = "productName", required = false) String productName,
//			@RequestParam(value = "startDate", required = false) String startDate,
//			@RequestParam(value = "endDate", required = false) String endDate,
//			@RequestParam(value = "orderId", required = false) Integer orderId,
//			@RequestParam("userId") Integer userId) {
//
//		logger.info("获取市地图数据开始时间 : {}", DateUtils.getTimeString());
//		/* 预备进行分库分表查询 */
//		// DBContextHolder.setDbType("ds1");
//		User user = userService.findUserByUserId(userId);
//
//		List<MapCountVO> countAntifakeAndLogisticsList;
//		if (productName != null && !("").equals(productName)) { // 查询条件中是否选择了产品名称
//			if (orderId != null && !("").equals(orderId)) { // 查询条件：时间、订单Id
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountCityByDateAndOrderId(startDate,
//								endDate, user, orderId, province);
//			} else { // 查询条件：时间、产品名称
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountCityByDateAndProductName(startDate,
//								endDate, user, productName, province);
//			}
//		} else {
//			if (orderId != null && !("").equals(orderId)) { // 查询条件:时间、订单Id
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountCityByDateAndOrderId(startDate,
//								endDate, user, orderId, province);
//			} else { // 查询条件:时间
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountCityByDate(startDate, endDate, user,
//								province);
//			}
//		}
//		List<MapCountVO> mapCountVOList = mapCountService
//				.addCityAndDistrictDeaultMap(countAntifakeAndLogisticsList,
//						province, 1); // 给各市的地图数据给默认值为0
//
//		logger.info("获取市地图数据结束时间 : {}", DateUtils.getTimeString());
//
//		return ResultObject.createInstance(mapCountVOList);
//	}
//
//	/**
//	 * 选择了城市之后各区县防伪扫数据
//	 * 
//	 * @param city
//	 *            城市名称
//	 * @param productName
//	 *            产品名称
//	 * @param startDate
//	 *            查询开始时间
//	 * @param endDate
//	 *            查询结束时间
//	 * @param orderId
//	 *            订单Id
//	 * @param userId
//	 * @return
//	 */
//	@RequestMapping("/GET/mapCount/mapAntiDistrictCount.do")
//	@ResponseBody
//	public ResultObject mapAntiDistrictCount(
//			@RequestParam("city") String city,
//			@RequestParam(value = "productName", required = false) String productName,
//			@RequestParam(value = "startDate", required = false) String startDate,
//			@RequestParam(value = "endDate", required = false) String endDate,
//			@RequestParam(value = "orderId", required = false) Integer orderId,
//			@RequestParam("userId") Integer userId) {
//
//		logger.info("获取县地图数据开始时间 : {}", DateUtils.getTimeString());
//
//		// DBContextHolder.setDbType("ds1");
//		User user = userService.findUserByUserId(userId);
//
//		List<MapCountVO> countAntifakeAndLogisticsList;
//		if (productName != null && !("").equals(productName)) { // 查询条件中是否选择了产品名称
//			if (orderId != null && !("").equals(orderId)) { // 查询条件：时间、订单Id
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountDistrictByDateAndOrderId(startDate,
//								endDate, user, orderId, city);
//			} else { // 查询条件：时间、产品名称
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountDistrictByDateAndProductName(
//								startDate, endDate, user, productName, city);
//			}
//		} else {
//			if (orderId != null && !("").equals(orderId)) { // 查询条件:时间、订单Id
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountDistrictByDateAndOrderId(startDate,
//								endDate, user, orderId, city);
//			} else { // 查询条件:时间
//				countAntifakeAndLogisticsList = mapCountService
//						.findAntiFakeCountDistrictByDate(startDate, endDate,
//								user, city);
//			}
//		}
//		List<MapCountVO> mapCountVOList = mapCountService
//				.addCityAndDistrictDeaultMap(countAntifakeAndLogisticsList,
//						city, 2); // 给各区的地图数据给默认值为0
//
//		logger.info("获取县地图数据结束时间 : {}", DateUtils.getTimeString());
//
//		return ResultObject.createInstance(mapCountVOList);
//	}
//	
//	   /**
//     * 将数据格式化成前端热力图所需要的数据格式
//     *
//     * @param mapDistrictHotList 需要格式化的热力图数据
//     * @return
//     */
///*    public List<MapDistrictHotVO> mapDistrictHotVOs(List<MapDistrictHot> mapDistrictHotList) {
//        Random random = new Random();
//        List<MapDistrictHotVO> mapDistrictHotVOList = new ArrayList<MapDistrictHotVO>();
//        for (MapDistrictHot mapDistrictHotVO : mapDistrictHotList) {
//            if (mapDistrictHotVO.getLatitude() == null) {
//                continue;
//            }
//            String securityCode = mapDistrictHotVO.getSecurityCode();
//            if (securityCode == null) {
//                MapDistrictHotVO mapDistrictHotVO1 = new MapDistrictHotVO();
//                mapDistrictHotVO1.setLng(Double.valueOf(mapDistrictHotVO.getLongitude()));
//                mapDistrictHotVO1.setLat(Double.valueOf(mapDistrictHotVO.getLatitude()));
//                int count = 10 + random.nextInt(40);
//                mapDistrictHotVO1.setCount(count);
//                mapDistrictHotVOList.add(mapDistrictHotVO1);
//            } else {
//                String perfixProductOrderNo = securityCode.substring(0, 8);
//              SecurityCode securityCode = securityCodeService.
//                DBContextHolder.setDbType("ds2");
//                SecurityCode securityCode1 = securityCodeService.findSecurityCodeBySecurityCode(perfixProductOrderNo, securityCode);
//                DBContextHolder.setDbType("ds1");
//               if (securityCode.getInternalCodeFlag() == 2) {   //判断是否为内码
//                    MapDistrictHotVO mapDistrictHotVO1 = new MapDistrictHotVO();
//                    mapDistrictHotVO1.setLng(Double.valueOf(mapDistrictHotVO.getLongitude()));
//                    mapDistrictHotVO1.setLat(Double.valueOf(mapDistrictHotVO.getLatitude()));
//                    int count = 10 + random.nextInt(40);
//                    mapDistrictHotVO1.setCount(count);
//                    mapDistrictHotVOList.add(mapDistrictHotVO1);
//                }
//            }
//        }
//        return mapDistrictHotVOList;
//    }
//*/
//    /**
//     * 当厂商刚点击的时候，将厂商最新的订单Id和产品名称返回给前端
//     *
//     * @param userId
//     * @return
//     */
//    @RequestMapping("/sanKeyInitOrderId.json")
//    @ResponseBody
//    public ResultObject sanKeyInitOrderId(@RequestParam("userId") int userId) {
//        logger.info("给桑基图一个默认数据开始时间 : {}", DateUtils.getTimeString());
//        User user = userService.findUserByUserId(userId);
//        int orderId = orderService.findLastOrder(user);
//        String productName = null;
//
//        if (orderId == 0) {
//            productName = "请新建订单";
//        } else {
//            ProductOrder order = orderService.findOrderByProductOrderId(orderId);
//            productName = order.getProductName();
//        }
//
//        ResultObject resultObject = new ResultObject();
//        resultObject.setMsg(productName);
//        resultObject.setData(orderId);
//        logger.info("给桑基图一个默认数据结束时间 : {}", DateUtils.getTimeString());
//        return resultObject;
//    }
//
//    /**
//     * 桑基图接口
//     *
//     * @param userId
//     * @param productName 产品名称
//     * @param orderId     订单ID
//     * @param startDate   查询开始时间
//     * @param endDate     查询结束时间
//     * @return
//     */
//    @RequestMapping("/GET/mapCount/sanKey.do")
//    @ResponseBody
//    public String sanKey(@RequestParam("userId") int userId,
//                         @RequestParam(value = "productName", required = false) String productName,
//                         @RequestParam(value = "orderId", required = false) Integer orderId,
//                         @RequestParam(value = "startDate", required = false) String startDate,
//                         @RequestParam(value = "endDate", required = false) String endDate) {
//        logger.info("获取桑基图数据开始时间 : {}", DateUtils.getTimeString());
//        User user = userService.findUserByUserId(userId);
//             String result = mapCountService.sanKey(user,orderId,productName,startDate,endDate);
//        logger.info("获取桑基图数据结束时间 : {}", DateUtils.getTimeString());
//       return result;
//    }
//
//    public String getJsonp(String callback, String json) {
//        return callback + "(" + json + ")";
//    }
//
//}
