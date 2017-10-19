package com.yunma.controller.collectWord;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.yunma.entity.collectWord.CollectWordGame;
import com.yunma.entity.collectWord.CollectWordWinner;
import com.yunma.entity.collectWord.UserGetedWord;
import com.yunma.service.collectWord.CollectWordService;
import com.yunma.utils.PageBean;
import com.yunma.vo.collectWord.CollectWordForJson;
import com.yunma.vo.product.ProductVo;

@Controller
public class CollectWordController {

	@Autowired
	private CollectWordService service;
	
	/**
	 * 新增集字游戏规则
	 * @param json
	 * @return
	 */
	@RequestMapping("/ADD/CollectWord/addCollectWordRule.do")
	@ResponseBody
	public JSONObject addCollectWordRule(String json){
		JSONObject result=new JSONObject();
		Gson gson=new Gson();
		System.out.println("接收到的JSON："+json);
		
		CollectWordForJson obj = new CollectWordForJson();
		obj=(CollectWordForJson)gson.fromJson(json, CollectWordForJson.class);
		
		if (obj != null) {
			System.out.println("接收到的JSON："+obj.toString());
			result=service.addCollectWordRule(obj);
		}else{
			return null;
		}
		
		
		return result;
	}
	
	/**
	 * 获取规则列表
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/CollectWord/getRuleList.do")
	@ResponseBody
	public JSONObject getRuleList(Integer vendorId){
		
		return service.getRuleList(vendorId);
	}
	
	/**
	 * 新增游戏
	 * @param game
	 * @return
	 */
	@RequestMapping("/ADD/CollectWord/addCollectWordGame.do")
	@ResponseBody
	public JSONObject addCollectWordGame(CollectWordGame game){
		JSONObject result=new JSONObject();
		
		int temp=service.addCollectWordGame(game);
		
		if (game.getOrder_id() != null) {
			int i=service.updateOrderInfoForCollectWord(game.getOrder_id());
			if (i > 0) {
				result.put("update", "修改订单状态成功！");
			}else{
				result.put("update", "修改订单状态失败！");
			}
		}
		
		if (game.getProduct_id() != null) {
			int i=service.updateProInfoForCollectWord(game.getProduct_id());
			if (i > 0) {
				result.put("update", "修改订单状态成功！");
			}else{
				result.put("update", "修改订单状态失败！");
			}
		}
		
		if (temp > 0) {
			result.put("status", 1);
			result.put("msg", "新增游戏成功");
		}else{
			result.put("status", -1);
			result.put("msg", "新增游戏失败");
		}
		
		return result;
	}
	
	/**
	 * 集字游戏订单列表
	 * @param pageBean
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/CollectWord/getCollectWordOrder.do")
	@ResponseBody
	public PageBean getCollectWordOrder(PageBean pageBean,Integer vendorId){
		
		return service.getOrderInfoForCollectWord(pageBean, vendorId);
	}
	
	/**
	 * 分页查询集字游戏产品信息
	 * @param page
	 * @param vendorId 厂商id
	 * @param keyword 查询关键字
	 * @return
	 */
	@RequestMapping("/GET/CollectWord/getProductForCollectWord.do")
	@ResponseBody
	public PageBean getProductForCollectWord(PageBean page,Integer vendorId) {
		ProductVo vo = new ProductVo();
		vo.setVendorId(vendorId);
		return service.getProductForCollectWord(page,vo);
	}
	
	/**
	 * 获取游戏列表
	 * @return
	 */
	@RequestMapping("/GET/CollectWord/getCollectWordGameList.do")
	@ResponseBody
	public JSONObject getCollectWordGameList(Integer vendorId){
		
		return service.getCollectWordGameList(vendorId);
	}
	
	
	/**
	 * 获取规则详情
	 * @return
	 */
	@RequestMapping("/GET/CollectWord/getRuleInfoById.do")
	@ResponseBody
	public void getRuleInfoById(HttpServletResponse response,Integer id){
		
		String strJson = "";
		CollectWordForJson json = service.getRuleInfoById(id);
		if (json != null) {
			Gson gson = new Gson();
			strJson = gson.toJson(json);
		}

		// 输出JSON数据
		// 这里直接通过response输出JSON字符串
		// Spring MVC也提供了输出JSON数据的方法

		// 设置编码格式
		response.setContentType("text/plain;charset=utf-8");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(strJson);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 修改规则
	 * @return
	 */
	@RequestMapping("/UPDAE/CollectWord/updateRuleInfoById.do")
	@ResponseBody
	public JSONObject updateRuleInfoById(String json){
		JSONObject result=new JSONObject();
		Gson gson=new Gson();
		System.out.println("接收到的JSON："+json);
		
		CollectWordForJson obj = new CollectWordForJson();
		obj=(CollectWordForJson)gson.fromJson(json, CollectWordForJson.class);
		result=service.updateRuleInfoById(obj);
		
		return result;
	}
	
	/**
	 * 删除规则
	 * @return
	 */
	@RequestMapping("/DELETE/CollectWord/deleteRuleById.do")
	@ResponseBody
	public JSONObject deleteRuleById(Integer id){
		JSONObject result=new JSONObject();
		
		int temp=service.deleteRuleById(id);
		
		if (temp > 0) {
			result.put("msg", "删除成功");
			result.put("status", 1);
		}else {
			result.put("msg", "删除失败");
			result.put("status", -1);
		}
		
		return result;
	}
	
	/**
	 * 字
	 * @param orderOrProId
	 * @return
	 */
	@RequestMapping("/GET/CollectWord/getWordForMobile.do")
	@ResponseBody
	public JSONObject getWordForMobile(String code,Integer orderOrProId,String openId,Integer orderId,Integer vendorId){
		
		return service.getWordForMobile(code,orderOrProId,openId,orderId,vendorId);
		
	}
	
	/**
	 * 新增用户获得的字
	 * @param userWord
	 * @return
	 */
	@RequestMapping("/ADD/CollectWord/addUserGetWord.do")
	@ResponseBody
	public JSONObject addUserGetWord(UserGetedWord userWord){
		JSONObject result=new JSONObject();
		
		int temp=service.addUserGetWord(userWord);
		
		if (temp > 0) {
			result.put("status", 1);
			result.put("msg", "收藏字成功！");
		}else{
			result.put("status", -1);
			result.put("msg", "收藏字失败！");
		}
		
		return result;
	}
	
	/**
	 * 根据openId查询用户已经获得的字
	 * @param openId
	 * @return
	 */
	@RequestMapping("/GET/CollectWord/getUserCollectWords.do")
	@ResponseBody
	public JSONObject getUserCollectWords(String openId,Integer vendorId) {
		
		return service.getUserCollectWords(openId,vendorId);
	}
	
	/**
	 * 获取奖项列表
	 * @param orderOrProId
	 * @return
	 */
	@RequestMapping("/GET/CollectWord/getCollectWordItemList.do")
	@ResponseBody
	public JSONObject getCollectWordItemList(Integer orderOrProId){
		
		return service.getCollectWordItemList(orderOrProId);
	}
	
	/**
	 * 根据openId查询是否获奖
	 * @param openId
	 * @return
	 */
	@RequestMapping("/GET/CollectWord/cashPrizeForUser.do")
	@ResponseBody
	public JSONObject cashPrizeForUser(String openId,Integer vendorId,Integer itemId){
		
		return service.cashPrizeForUser(openId, vendorId, itemId);
	}
	
	/**
	 * 领奖
	 * @param winner
	 * @return
	 */
	@RequestMapping("/ADD/CollectWord/addCollectWordWinner.do")
	public JSONObject addCollectWordWinner(CollectWordWinner winner){
		JSONObject result=new JSONObject();
		
		int temp = service.addCollectWordWinner(winner);
		
		if (temp > 0) {
			result.put("status", 1);
			result.put("msg", "兑换成功！");
		}else{
			result.put("status", -1);
			result.put("msg", "兑换失败！");
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/collectWord/getWinnerList.do")
	@ResponseBody
	public JSONObject getWinnerList(Integer vendorId){
		
		return service.getWinnerList(vendorId);
	}
	
	
	
	@RequestMapping("/test/collectWord/test.do")
	@ResponseBody
	public void test(){
		
	}
}
