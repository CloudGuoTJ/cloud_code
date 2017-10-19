package com.yunma.service.collectWord.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.CollectWordRandomUtils;
import com.yunma.dao.collectWord.CollectWordDao;
import com.yunma.dao.product.ProductDao;
import com.yunma.dao.product.ProductOrderDao;
import com.yunma.entity.collectWord.CollectWordGame;
import com.yunma.entity.collectWord.CollectWordRate;
import com.yunma.entity.collectWord.CollectWordRule;
import com.yunma.entity.collectWord.CollectWordRuleItem;
import com.yunma.entity.collectWord.CollectWordWinner;
import com.yunma.entity.collectWord.UserGetedWord;
import com.yunma.service.collectWord.CollectWordService;
import com.yunma.utils.PageBean;
import com.yunma.vo.collectWord.CollectWordForJson;
import com.yunma.vo.collectWord.CollectWordItemJson;
import com.yunma.vo.collectWord.CollectWordRateJson;
import com.yunma.vo.collectWord.CollectWordVo;
import com.yunma.vo.collectWord.CollectWordWinnerVo;
import com.yunma.vo.product.ProductOrderVO;
import com.yunma.vo.product.ProductVo;

@Service
public class CollectWordServiceImpl implements CollectWordService{
	
	@Resource
	private CollectWordDao dao;
	@Resource
	private ProductOrderDao orderDao;
	@Resource
	private ProductDao proDao;
	
	private Logger log = LoggerFactory.getLogger(CollectWordServiceImpl.class);
	
	@Override
	public JSONObject addCollectWordRule(CollectWordForJson json) {
		JSONObject result=new JSONObject();
		CollectWordRule rule=new CollectWordRule();
		
		rule.setName(json.getName());
		rule.setNumber(json.getNumber());
		rule.setStatus(1);
		rule.setVendorId(json.getVendorId());
		
		CollectWordRule gameRule=dao.isExistCurrentRule(json.getVendorId());
		
		if (gameRule != null) {
			result.put("status", -3);
			result.put("msg", "为了避免游戏规则混乱，当前只允许存在一套规则");
			return result;
		}
		
		String ruleName=dao.getRuleNameByName(json.getName());
		
		if (ruleName != null) {
			result.put("status", -1);
			result.put("msg", "当前规则名已存在！");
			return result;
		}
		
		int temp=dao.addCollectWordRule(rule);
		Integer ruleId;
		if (temp > 0) {
			ruleId=rule.getId();
		}else{
			return null;
		}
		List<CollectWordItemJson> items=json.getItems();
		
		int temp1 = 0;
		if (items.size() > 0) {
			for (CollectWordItemJson item1 : items) {
				CollectWordRuleItem item=new CollectWordRuleItem();
				item.setRule_id(ruleId);
				item.setPrize_name(item1.getPrize_name());
				item.setPrize_item(item1.getPrize_item());
				item.setPrize_is_sort(item1.getPrize_is_sort());
				item.setPrize_comment(item1.getPrize_comment());
				temp1=dao.addCollectWordRuleItem(item);
			}
		}
		
		int temp2 = 0;
		List<CollectWordRateJson> rates=json.getRates();
		if (rates.size() > 0) {
			for (CollectWordRateJson rate1 : rates) {
				CollectWordRate rate=new CollectWordRate();
				rate.setRule_id(ruleId);
				rate.setWord(rate1.getWord());
				rate.setWord_rate(rate1.getWord_rate());
				temp2=dao.addCollectWordRuleRate(rate);
			}
		}
		
		if (temp > 0 && temp1 > 0 && temp2 > 0) {
			result.put("status", 1);
			result.put("msg", "新增成功");
		}else{
			result.put("status", -2);
			result.put("msg", "新增失败");
		}
		
		
		return result;
	}

	@Override
	public JSONObject getRuleList(Integer vendorId) {
		JSONObject result=new JSONObject();
		JSONArray array=new JSONArray();
		List<CollectWordRule> rules=dao.getRuleList(vendorId);
		
		if (rules == null && rules.size()==0) {
			return null;
		}
		
		for (CollectWordRule rule : rules) {
			JSONObject obj=new JSONObject();
			
			obj.put("id",rule.getId());
			obj.put("name",rule.getName());
			obj.put("vendorId",rule.getVendorId());
			obj.put("createTime",rule.getCreateTime());
			
			List<String> words=dao.getWordsFromRate(rule.getId());
			if (words != null && words.size()>0) {
				StringBuilder sb=new StringBuilder();
				for (int i = 0; i < words.size(); i++) {
					if (i == 0) {
						sb.append(words.get(i));
					}else{
						sb.append("、"+words.get(i));
					}
				}
				String word=sb.toString();
				obj.put("prize_item",word);
			}
			array.add(obj);

		}
		result.put("data", array);
		
		return result;
	}

	@Override
	public int addCollectWordGame(CollectWordGame game) {
		return dao.addCollectWordGame(game);
	}

	@Override
	public JSONObject getCollectWordGameList(Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array=new JSONArray();
		
		List<CollectWordVo> vos=dao.getCollectWordGameList(vendorId);
		
		if (vos != null && vos.size() > 0) {
			
			for (CollectWordVo vo : vos) {
				JSONObject obj = new JSONObject();
				obj.put("id", vo.getId());
				obj.put("order_id", vo.getOrder_id());
				obj.put("product_id", vo.getProduct_id());
				obj.put("rule_id", vo.getRule_id());
				obj.put("invalid_time", vo.getInvalid_time());
				obj.put("effect_time", vo.getEffect_time());
				obj.put("name", vo.getName());
				obj.put("vendor_id", vo.getVendor_id());
				obj.put("create_time", vo.getCreate_time());
				
				//获取规则里面中奖字
				List<String> words=dao.getWordsFromRate(vo.getRule_id());
				if (words != null && words.size()>0) {
					StringBuilder sb=new StringBuilder();
					for (int i = 0; i < words.size(); i++) {
						if (i == 0) {
							sb.append(words.get(i));
						}else{
							sb.append("、"+words.get(i));
						}
					}
					String word=sb.toString();
					obj.put("prize_item",word);
				}
				array.add(obj);
				
			}
			result.put("data", array);
		}
		
		
		return result;
	}

	@Override
	public CollectWordForJson getRuleInfoById(Integer id) {
		
		CollectWordForJson json=new CollectWordForJson();
		
		CollectWordRule rule=dao.getRuleInfoById(id);
		
		if (rule != null) {
			json.setId(rule.getId());
			json.setName(rule.getName());
			json.setNumber(rule.getNumber());
			json.setStatus(rule.getStatus());
			json.setVendorId(rule.getVendorId());
			
			//规则中的字及概率
			List<CollectWordRate> rates=dao.getRateInfoByRuleId(rule.getId());
			
			if (rates != null && rates.size()>0) {
				List<CollectWordRateJson> rateJsons=new ArrayList<CollectWordRateJson>();
				for (CollectWordRate rate : rates) {
					CollectWordRateJson rateJson=new CollectWordRateJson();
					rateJson.setRatgeId(rate.getId());
					rateJson.setRuleId(rate.getRule_id());
					rateJson.setWord(rate.getWord());
					rateJson.setWord_rate(rate.getWord_rate());
					rateJsons.add(rateJson);
				}
				json.setRates(rateJsons);
			}
			//规则中的奖项
			List<CollectWordRuleItem> items=dao.getItemInfoByRuleId(rule.getId());
			
			if (items != null && items.size() > 0) {
				List<CollectWordItemJson> itemJsons=new ArrayList<CollectWordItemJson>();
				for (CollectWordRuleItem item : items) {
					CollectWordItemJson itemJson=new CollectWordItemJson();
					itemJson.setItemId(item.getId());
					itemJson.setPrize_item(item.getPrize_item());
					itemJson.setPrize_name(item.getPrize_name());
					itemJson.setPrize_is_sort(item.getPrize_is_sort());
					itemJson.setPrize_comment(item.getPrize_comment());
					
					itemJsons.add(itemJson);
				}
				json.setItems(itemJsons);
			}
		}
		
		return json;
	}

	@Override
	public JSONObject updateRuleInfoById(CollectWordForJson obj) {
		JSONObject result=new JSONObject();
		
		CollectWordRule rule=new CollectWordRule();
		rule.setId(obj.getId());
		rule.setName(obj.getName());
		rule.setNumber(obj.getNumber());
		rule.setVendorId(obj.getVendorId());
		
		int temp=dao.updateRuleInfoById(rule);
		
		int delete=dao.deleteRuleRateByRuleId(obj.getId());
		int delete1=dao.deleteRuleItemByRuleId(obj.getId());
		
		if (delete > 0 && delete1 > 0) {
			log.debug("删除规则奖项及字成功！！！！！！！！");
		}else{
			log.debug("删除规则奖项及字失败。。。。。。。。");
		}
		
		
		Integer ruleId;
		if (temp > 0) {
			ruleId=rule.getId();
		}else{
			return null;
		}
		List<CollectWordItemJson> items=obj.getItems();
		
		int temp1 = 0;
		if (items.size() > 0) {
			for (CollectWordItemJson item1 : items) {
				CollectWordRuleItem item=new CollectWordRuleItem();
				item.setRule_id(ruleId);
				item.setPrize_name(item1.getPrize_name());
				item.setPrize_item(item1.getPrize_item());
				item.setPrize_is_sort(item1.getPrize_is_sort());
				item.setPrize_comment(item1.getPrize_comment());
				temp1=dao.addCollectWordRuleItem(item);
			}
		}
		
		int temp2 = 0;
		List<CollectWordRateJson> rates=obj.getRates();
		if (rates.size() > 0) {
			for (CollectWordRateJson rate1 : rates) {
				CollectWordRate rate=new CollectWordRate();
				rate.setRule_id(ruleId);
				rate.setWord(rate1.getWord());
				rate.setWord_rate(rate1.getWord_rate());
				temp2=dao.addCollectWordRuleRate(rate);
			}
		}
		
		if (temp > 0 && temp1 > 0 && temp2 > 0) {
			result.put("status", 1);
			result.put("msg", "修改成功");
		}else{
			result.put("status", -1);
			result.put("msg", "修改失败");
		}
		
		
		return result;
	}

	@Override
	public int deleteRuleById(Integer id) {
		return dao.deleteRuleById(id);
	}

	@Override
	public int updateProInfoForCollectWord(Integer proId) {
		return proDao.updateProInfoForCollectWord(proId);
	}

	@Override
	public int updateOrderInfoForCollectWord(Integer orderId) {
		return orderDao.updateOrderInfoForCollectWord(orderId);
	}
	
	@Override
	public PageBean getOrderInfoForCollectWord(PageBean pageBean,
			Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		/**
		 * 设定总数
		 */
		int temp = orderDao.getOrderCountForActiv(vendorId);
		ProductOrderVO vo = new ProductOrderVO();
		pageBean.setTotalRecords(temp);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageBean", pageBean);
		map.put("ProductOrderVO", vo);
		map.put("vendorId", vendorId);
		List<ProductOrderVO> list = orderDao.getOrderInfoForCollectWord(map);
		if (list != null && list.size() > 0) {
			for (ProductOrderVO orderVO : list) {
				/*
				 * orderId, vendorId,vendorName,productId,
				 * productName,productCount,createDate,
				 * status,deleteFlag,lastUpdateTime, FROM tb_product_order where
				 * deleteFlag=0
				 */
				JSONObject object = new JSONObject();
				object.put("orderId", orderVO.getOrderId());
				object.put("vendorId", orderVO.getVendorId());
				object.put("vendorName", orderVO.getVendorName());
				object.put("productId", orderVO.getProductId());
				object.put("productName", orderVO.getProductName());
				object.put("productCount", orderVO.getProductCount());
				object.put("createDate", orderVO.getCreateDate());
				object.put("status", orderVO.getStatus());
				object.put("deleteFlag", orderVO.getDeleteFlog());
				object.put("lastUpdateTime", orderVO.getLastUpdateTime());
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);

		return pageBean;
	}

	@Override
	public PageBean getProductForCollectWord(PageBean pageBean,
			ProductVo product) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//设置总记录数
		pageBean.setTotalRecords(proDao.getProductCountForCollectWord(product));
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageBean", pageBean);
		map.put("product", product);
		
		//result.put("keyword", product.getKeyword());
		
		List<ProductVo> list = proDao.getProductForCollectWord(map);
		
		for (ProductVo pro : list) {
			JSONObject object = new JSONObject();
			object.put("id", pro.getId());
			object.put("productImg", pro.getProductImg());
			object.put("productName", pro.getProductName());
			object.put("rowId", pro.getRowId());
			if (pro.getRowName() != null) {
				object.put("rowName", pro.getRowName());
			} else {
				object.put("rowName", "未分类");
			}
			object.put("productSpe", pro.getProductSpe());
			object.put("productPrice", pro.getProductPrice());
			object.put("sortNum", pro.getSortNum());
			if (pro.getLastUpdateTime() != null) {
				object.put("lastUpdateTime", sdf.format(pro.getLastUpdateTime()));
			}
			array.add(object);
		}
		
		result.put("data", array);
		
		
		//将数据放入pageBean中
		pageBean.setResult(result);
		
		return pageBean;
	}

	@Override
	public JSONObject getWordForMobile(String code, Integer orderOrProId,
			String openId, Integer orderId, Integer vendorId) {
		JSONObject result = new JSONObject();
		UserGetedWord user = dao.selectUserGetWordByShortCode(code);

		if (user != null) {
			result.put("status", -4);
			result.put("msg", "当前二维码已获取过字！");
			
		} else {
			List<CollectWordRate> rates = dao.getWordAndRate(orderOrProId);

			Map<String, Double> map = new HashMap<String, Double>();

			if (rates != null && rates.size() > 0) {
				for (CollectWordRate rate : rates) {
					map.put(rate.getWord(), (rate.getWord_rate() / 100));
					System.out.println("概率：" + (rate.getWord_rate() / 100));
				}
				String word = CollectWordRandomUtils.chanceSelect(map);
				if (word != null) {

					UserGetedWord userWord = new UserGetedWord();
					userWord.setCollectedWord(word);
					userWord.setOpenId(openId);
					userWord.setOrderId(orderId);
					userWord.setVendorId(vendorId);
					userWord.setShortCode(code);
					int temp = dao.addUserGetWord(userWord);

					if (temp > 0) {
						result.put("status", 1);
						result.put("msg", word);
					} else {
						result.put("status", -3);
						result.put("msg", "收藏字失败！");
					}

				} else {
					UserGetedWord userWord = new UserGetedWord();
					userWord.setOpenId(openId);
					userWord.setOrderId(orderId);
					userWord.setVendorId(vendorId);
					userWord.setShortCode(code);
					int temp = dao.addUserGetWord(userWord);
					if (temp > 0) {
						result.put("status", -1);
						result.put("msg", "很遗憾，没有获得字！");
					}
					
				}
			} else {
				result.put("status", -2);
				result.put("msg", "当前订单没有参加集字游戏！");
			}
		}
		return result;
	}

	@Override
	public int addUserGetWord(UserGetedWord userWord) {
		return dao.addUserGetWord(userWord);
	}

	@Override
	public JSONObject getUserCollectWords(String openId,Integer vendorId) {
		JSONObject result=new JSONObject();
		JSONArray array=new JSONArray();
		
		List<UserGetedWord> words=dao.getUserCollectWords(openId,vendorId);
		
		if (words != null && words.size()>0) {
			
			for (UserGetedWord word : words) {
				JSONObject obj=new JSONObject();
				obj.put("openId", word.getOpenId());
				obj.put("collectedWord", word.getCollectedWord());
				obj.put("orderId", word.getOrderId());
				obj.put("productId", word.getProductId());
				
				array.add(obj);
			}
			
			result.put("data", array);
		}
		
		return result;
	}

	@Override
	public JSONObject getCollectWordItemList(Integer orderOrProId) {
		JSONObject result=new JSONObject();
		JSONArray array=new JSONArray();
		
		Integer ruleId=dao.getRuleIdByOrderId(orderOrProId);
				
		if (ruleId == null) {
			result.put("status", -1);
			result.put("msg", "当前订单未参与集字游戏！");
		}else{
			List<CollectWordRuleItem> items=dao.getItemInfoByRuleId(ruleId);
			
			for (CollectWordRuleItem item : items) {
				JSONObject obj=new JSONObject();
				obj.put("id", item.getId());
				obj.put("rule_id", item.getRule_id());
				obj.put("prize_name", item.getPrize_name());
				obj.put("prize_item", item.getPrize_item());
				obj.put("prize_is_sort", item.getPrize_is_sort());
				obj.put("prize_comment", item.getPrize_comment());
				
				array.add(obj);
			}
			
			result.put("data", array);
		}
		
		return result;
	}

	@Override
	public JSONObject cashPrizeForUser(String openId,Integer vendorId,Integer itemId) {
		JSONObject result=new JSONObject();
		String userGetWords="";
		List<UserGetedWord> userWords = dao.getUserCollectWords(openId,vendorId);
		
		if (userWords != null && userWords.size() > 0) {
			StringBuilder sb=new StringBuilder();
			for (UserGetedWord word : userWords) {
				sb.append(word.getCollectedWord());
			}
			userGetWords=sb.toString();
		}else{
			result.put("status", -2);
			result.put("msg", "您暂未获得任何字，再接再厉！");
			return result;
		}
		
		CollectWordRule rule=dao.isExistCurrentRule(vendorId);
		if (rule == null) {
			result.put("status", -1);
			result.put("msg", "当前商家未参加集字游戏或游戏时间已过期！");
		}
		CollectWordRuleItem item=dao.getRuleItemById(itemId);
		//用户已经收集的字是否包含当前奖项中的字
		if (userGetWords.contains(item.getPrize_item())) {
			result.put("status", 1);
			result.put("prize_name", item.getPrize_name());
			result.put("msg", item.getPrize_comment());
			return result;
		}else{
			result.put("status", -3);
			result.put("msg", "您暂不能兑换当前奖项！");
			
		}
		
		return result;
	}

	@Override
	public int addCollectWordWinner(CollectWordWinner winner) {
		
		if (winner.getArea() !=null && winner.getStreet() !=null && winner.getDetailAddress() != null) {
			winner.setAddress(winner.getArea()+winner.getStreet()+winner.getDetailAddress());
		}
		
		return dao.addCollectWordWinner(winner);
	}

	@Override
	public JSONObject getWinnerList(Integer vendorId) {
		JSONObject result=new JSONObject();
		JSONArray array=new JSONArray();
		List<CollectWordWinnerVo> vos=dao.getWinnerList(vendorId);
		
		if (vos != null && vos.size() > 0) {
			for (CollectWordWinnerVo vo : vos) {
				JSONObject obj=new JSONObject();
				obj.put("orderId", vo.getOrderId());
				obj.put("productId", vo.getProductId());
				obj.put("winner", vo.getWinner());
				obj.put("winnerTel", vo.getWinnerTel());
				obj.put("address", vo.getAddress());
				obj.put("createTime", vo.getCreateTime());
				obj.put("isDeliver", vo.getIsDeliver());
				obj.put("nickName", vo.getNickName());
				obj.put("prize_name", vo.getPrize_name());
				obj.put("prize_comment", vo.getPrize_comment());
				
				array.add(obj);
			}
			result.put("data", array);
		}
		
		
		return result;
	}
	
	
	

	
	
	
}
