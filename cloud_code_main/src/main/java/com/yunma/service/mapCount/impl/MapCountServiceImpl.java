package com.yunma.service.mapCount.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.util.BaiduMapUtils;
import com.common.util.CommonConstants;
import com.yunma.dao.agent.AgentDao;
import com.yunma.dao.mapCount.MapCountDao;
import com.yunma.entity.agent.AgentEntity;
import com.yunma.entity.sankeydiagram.Link;
import com.yunma.entity.sankeydiagram.Node;
import com.yunma.entity.sankeydiagram.SankeyDiagram;
import com.yunma.entity.statAntiFakeCount.StatAntiFakeCount;
import com.yunma.model.User;
import com.yunma.service.mapCount.MapCountService;
import com.yunma.utils.DoubleUtil;
import com.yunma.vo.mapCount.MapCountVO;

@Service
public class MapCountServiceImpl implements MapCountService {
	@Autowired
	private MapCountDao mapCountDao;
	@Resource
	private AgentDao agentDao;

	/**
	 * 省 province
	 */
	public List<MapCountVO> findAntiFakeCountProvinceByDateAndOrderId(
			String startDate, String endDate, User user, Integer orderId) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return mapCountDao
					.findAntiFakeCountProvinceByDateAndOrderIdByAdmin(
							startDate, endDate, orderId);
		} else {
			return mapCountDao.findAntiFakeCountProvinceByDateAndOrderId(
					startDate, endDate, user.getVendorId(), orderId);
		}
	}

	@Override
	public List<MapCountVO> findAntiFakeCountProvinceByDateAndProductName(
			String startDate, String endDate, User user, String productName) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return mapCountDao
					.findAntiFakeCountProvinceByDateAndProductNameByAdmin(
							startDate, endDate, productName);
		} else {
			return mapCountDao.findAntiFakeCountProvinceByDateAndProductName(
					startDate, endDate, user.getVendorId(), productName);
		}
	}

	@Override
	public List<MapCountVO> findAntiFakeCountProvinceByDate(String startDate,
			String endDate, User user) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return mapCountDao.findAntiFakeCountProvinceByDateByAdmin(
					startDate, endDate);
		} else {
			return mapCountDao.findAntiFakeCountProvinceByDate(startDate,
					endDate, user.getVendorId());
		}
	}

	@Override
	/**
	 * 给地图中各省、市、区添加默认的数据
	 *
	 * @param list   查询出来的各省或者市或者区的数据
	 * @param name   指定的省或市的名称
	 * @param choice 它的值为0的时候，给全国各省插入默认数据；1的时候，给各市插入默认数据；2的时候给各地区插入默认数据
	 * @return
	 */
	public List<MapCountVO> addCityAndDistrictDeaultMap(List<MapCountVO> list,
			String name, int choice) {
		BaiduMapUtils baiduMapUtils = new BaiduMapUtils();
		int areaNo = 0;
		if (choice == 1) {
			areaNo = mapCountDao.findProAreaNo(name); // 查找省级编号
		} else if (choice == 2) {
			areaNo = mapCountDao.findCityAreaNo(name); // 查找市级编号
		}

		/**
		 * 表tb_map_area中parent_No相同的，则它们是同一个全国或省或市
		 */
		List<MapCountVO> list1 = mapCountDao
				.findCitynameOrDistrictnameByAreaNo(areaNo);
		List<MapCountVO> list2 = new ArrayList<MapCountVO>();

		Map<String, Integer> map = new HashMap<String, Integer>();
		for (MapCountVO countMapVO : list1) {
			if (choice == 0) {
				String proNickname = baiduMapUtils.getProvinceNick(countMapVO
						.getName());
				map.put(proNickname, countMapVO.getValue());
			} else {
				map.put(countMapVO.getName(), countMapVO.getValue());
			}
		}
		for (MapCountVO countMapVO : list) {
			map.put(countMapVO.getName(), countMapVO.getValue());
		}
		for (MapCountVO countMapVO : list1) {
			if (choice == 0) {
				String proNickname = baiduMapUtils.getProvinceNick(countMapVO
						.getName()); // 前端的全国各省是简称，所以由全称变简称
				countMapVO.setName(proNickname);
				countMapVO.setValue(map.get(proNickname));
			} else {
				countMapVO.setValue(map.get(countMapVO.getName()));
			}
			list2.add(countMapVO);
		}
		return list2;
	}

	/**
	 * 市 city
	 */

	@Override
	public List<MapCountVO> findAntiFakeCountCityByDateAndOrderId(
			String startDate, String endDate, User user, Integer orderId,
			String city) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SuperAdmin.getState()) {
			return mapCountDao
					.findAntiFakeCountDistrictByDateAndOrderIdByAdmin(
							startDate, endDate, orderId, city);
		} else {
			return mapCountDao.findAntiFakeCountDistrictByDateAndOrderId(
					startDate, endDate, user.getVendorId(), orderId, city);
		}

	}

	@Override
	public List<MapCountVO> findAntiFakeCountCityByDateAndProductName(
			String startDate, String endDate, User user, String productName,
			String province) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SuperAdmin.getState()) {
			return mapCountDao
					.findAntiFakeCountCityByDateAndProductNameByAdmin(
							startDate, endDate, productName, province);
		} else {
			return mapCountDao.findAntiFakeCountCityByDateAndProductName(
					startDate, endDate, user.getVendorId(), productName,
					province);
		}
	}

	@Override
	public List<MapCountVO> findAntiFakeCountCityByDate(String startDate,
			String endDate, User user, String province) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return mapCountDao.findAntiFakeCountCityByDateByAdmin(startDate,
					endDate, province);
		} else {
			return mapCountDao.findAntiFakeCountCityByDate(startDate, endDate,
					user.getVendorId(), province);
		}
	}

	/**
	 * 区县 district
	 */

	@Override
	public List<MapCountVO> findAntiFakeCountDistrictByDateAndOrderId(
			String startDate, String endDate, User user, Integer orderId,
			String city) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return mapCountDao
					.findAntiFakeCountDistrictByDateAndOrderIdByAdmin(
							startDate, endDate, orderId, city);
		} else {
			return mapCountDao.findAntiFakeCountDistrictByDateAndOrderId(
					startDate, endDate, user.getVendorId(), orderId, city);
		}
	}

	@Override
	public List<MapCountVO> findAntiFakeCountDistrictByDateAndProductName(
			String startDate, String endDate, User user, String productName,
			String city) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return mapCountDao
					.findAntiFakeCountDistrictByDateAndProductNameByAdmin(
							startDate, endDate, productName, city);
		} else {
			return mapCountDao.findAntiFakeCountDistrictByDateAndProductName(
					startDate, endDate, user.getVendorId(), productName, city);
		}
	}

	@Override
	public List<MapCountVO> findAntiFakeCountDistrictByDate(String startDate,
			String endDate, User user, String city) {
		int userType = user.getUserType();
		if (userType == CommonConstants.User_Type_SuperAdmin.getState()
				|| userType == CommonConstants.User_Type_SecondaryAdmin
						.getState()) {
			return mapCountDao.findAntiFakeCountDistrictByDateByAdmin(
					startDate, endDate, city);
		} else {
			return mapCountDao.findAntiFakeCountDistrictByDate(startDate,
					endDate, user.getVendorId(), city);
		}
	}
	/**
	 * 生成热力图(动态更新图数据)
	 */

	@Override
	public String sanKey(User user, Integer orderId, String productName,
			String startDate, String endDate) {
		
		return null;
	}

	@Override
	public List<StatAntiFakeCount> mapCountForCity(Integer vendorId,
			String province) {
		return mapCountDao.mapCountForCity(vendorId, province);
	}

	@Override
	public List<StatAntiFakeCount> heatMapForDistr(Integer vendorId,
			String district) {
		
		return mapCountDao.heatMapForDistr(vendorId, district);
	}

	@Override
	public List<StatAntiFakeCount> getDistrCount(Integer vendorId,
			String city) {
		
		return mapCountDao.getDistrCount(vendorId, city);
	}

	@Override
	public SankeyDiagram getSankeyDiagramData(Integer vendorId) {
		
		SankeyDiagram sankey = null;
		
		String tableName="tb_product_tracing_agent_scan_"+vendorId;
		
		Integer rootAgentId=null;
		
		if (mapCountDao.isExistTable(tableName) == null) {
			
			return null;
		}else{
			
			sankey=new SankeyDiagram();
			// 获取所有 的代理商名称
			List<AgentEntity> agentNames = agentDao.getAllAgentName(vendorId);

			List<Node> names = new ArrayList<Node>();
			List<Link> links = new ArrayList<Link>();
			
			if (agentNames ==null && agentNames.size()==0) {
				
				return null;
			}else{
				for (AgentEntity agentName : agentNames) {

					Node node = new Node();

					node.setName(agentName.getAgentName());
					
					//判断是否为根节点
					if (agentName.getAgentFid() != null) {

						Link link = new Link();
						link.setTarget(agentName.getAgentName());
						link.setAgentId(agentName.getId());
						
						//遍历找到父节点
						for (int i = 0; i < agentNames.size(); i++) {
							
							if (agentName.getAgentFid().equals((agentNames.get(i).getId()))) {

								link.setSource(agentNames.get(i).getAgentName());
								link.setAgentFid(agentNames.get(i).getId());
							}

						}
						links.add(link);

					}else{
						rootAgentId=agentName.getId();
					}
					names.add(node);

				}

				sankey.setNodes(names);
				
//				StringBuilder sql=new StringBuilder();
//				sql.append("select count from ");
//				sql.append(tableName);
//				sql.append("where agentId=");
//				sql.append(links.get(i).getAgentId());
				
				//从厂商发出的总的产品数
				Integer countPro=mapCountDao.getProCount(vendorId, rootAgentId);
				
				sankey.setRootProCount(countPro);
				
				if (links !=null && links.size()>0) {
					
					for (int i = 0; i < links.size(); i++) {
						//获取目标代理商的产品数
						Integer agentCount=mapCountDao.getProCount(vendorId,links.get(i).getAgentId());
						
						Integer sourceCount=mapCountDao.getProCount(vendorId, links.get(i).getAgentFid());
						
						if (sourceCount != null) {
							links.get(i).setSourceProCount(sourceCount);
						}
						
						if (agentCount !=null) {
							double value;
							if (countPro == 0) {
								value = DoubleUtil.divide((double)agentCount, 1.0, 15);
							}else{
								value = DoubleUtil.divide((double)agentCount, (double)countPro, 15);
							}
							links.get(i).setCurrentProCount(agentCount);
							links.get(i).setValue(value);
							
						}
						
						
					}
					
				}
				sankey.setLinks(links);
				
				
			}

			
			
		}
		
		return sankey;
	}


//	@Override
//	public String sanKey(User user, Integer orderId, String productName,
//			String startDate, String endDate)
//	{
//		List<SecurityCode> securityCodeList = null;
//        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//
//        List<SanKeyVO1> sanKeyVO1List = new ArrayList<SanKeyVO1>();
//        List<SankeyVO> sankeyVOList = new ArrayList<SankeyVO>();
//
//        //将厂商这一个节点放到node中
//        SanKeyVO1 sanKeyVO1 = new SanKeyVO1();
//        sanKeyVO1.setName(sysUser.getVendorName());
//        sanKeyVO1List.add(sanKeyVO1);
//
//        //初始化，给map中的每一个代理商的id初始化并赋0
//        int productVendorId = sysUser.getVendorId();
//        List<LogisticsVendor> logisticsVendorList = logisticsDao.findLogisticsVendorByProductVendorId(productVendorId);
//        for (LogisticsVendor logisticsVendor : logisticsVendorList) {
//            map.put(logisticsVendor.getLogisticsVendorId(), 0);
//        }
//
//        if (orderId == null) { //通过产品名称和时间来查询一段时间内的桑基图
//            List<LogisticsLog> logisticsLogList = logisticsDao.findLogisticLogByDateAndProductName(productName, startDate, endDate);
//            for (LogisticsLog logisticsLog : logisticsLogList) {
//                if (logisticsLog.getSecurityCode().length() == 14) {  //一级外码是14位，只有是一级外码的时候才把它放到桑基图中
//                    int count = map.get(logisticsLog.getLogisticsVendorId()) + 1;
//                    map.put(logisticsLog.getLogisticsVendorId(), count);
//                }
//            }
//        } else { //查询单个订单的桑基图
//            Order order = orderDao.findOrderByOrderId(orderId);
//            if (order.getPerfixOrderNo() == null) { //老表,通过订单Id找到对应的防伪码级别为1级的外码
//                securityCodeList = securityCodeDao.findOldProductSecurityCodeListByOrderId(orderId);
//                getSankeyMap(securityCodeList, map);
//            } else { //新表,通过订单Id找到对应的防伪码级别为1级的外码
//                String perfixOrderNo = order.getPerfixOrderNo();
//                //通过订单前缀来找到相对应的t_logstics_log表中的logisticLog对象
//                List<LogisticsLog> logisticsLogList = logisticsDao.findNewLogisticLogByOrderPerfix(perfixOrderNo);
//                for (LogisticsLog logisticsLog : logisticsLogList) {
//                    if (logisticsLog.getSecurityCode().length() == 14) {  //一级外码是14位，只有是一级外码的时候才把它放到桑基图中
//                        int count = map.get(logisticsLog.getLogisticsVendorId()) + 1;
//                        map.put(logisticsLog.getLogisticsVendorId(), count);
//                    }
//                }
//            }
//        }
//
//        for (LogisticsVendor logisticsVendor : logisticsVendorList) {
//            //桑基图的来源和目标
//            String source = null;
//            String target = null;
//
//            int vendorId = logisticsVendor.getpId();    //查找上级代理
//            int value = map.get(logisticsVendor.getLogisticsVendorId());
//            target = logisticsVendor.getLogisticsVendorName();
//
//            if (vendorId == -1) {   //一级代理
//                source = logisticsVendor.getProductVendorName();
//            } else {
//                source = logisticsDao.findLogisticsVendorByVendorId(vendorId).getLogisticsVendorName();
//            }
//
//            if (value != 0) {
//                SanKeyVO1 sanKeyVO2 = new SanKeyVO1();
//                sanKeyVO2.setName(logisticsVendor.getLogisticsVendorName());
//                sanKeyVO1List.add(sanKeyVO2);
//
//                SankeyVO sankeyVO = new SankeyVO();
//                sankeyVO.setSource(source);
//                sankeyVO.setTarget(target);
//                sankeyVO.setValue(value);
//                sankeyVOList.add(sankeyVO);
//            }
//        }
//
//        String result1 = JSONObject.toJSONString(sankeyVOList);
//        String result2 = JSONObject.toJSONString(sanKeyVO1List);
//
//        String result = "{\"nodes\":" + result2 + ",\n" +
//                "\"links\":" + result1 + "}";
//
//        return result;
//    }
//
//    public Map<Integer, Integer> getSankeyMap(List<SecurityCode> securityCodeList, Map<Integer, Integer> map) {
//        for (SecurityCode securityCode : securityCodeList) {
//            List<LogisticsLog> logisticsLogList = logisticsDao.findLogisticLogBySecurityCode(securityCode.getSecurityCode());
//            for (LogisticsLog logisticsLog : logisticsLogList) {
//                int count = map.get(logisticsLog.getLogisticsVendorId()) + 1;
//                map.put(logisticsLog.getLogisticsVendorId(), count);
//            }
//        }
//        return map;
//    }
//	}
	
	
	
	

}
