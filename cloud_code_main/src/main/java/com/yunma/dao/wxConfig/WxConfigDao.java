package com.yunma.dao.wxConfig;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yunma.model.WxConfig;
import com.yunma.vo.wxConfig.WxConfigGzhVo;
import com.yunma.vo.wxConfig.WxConfigVo;

public interface WxConfigDao {
	/**
	 * 新增公众号信息
	 * @param vo
	 * @return
	 */
	@Insert("insert into tb_wx_config set vendorId=#{vendorId}, appid=#{appid}, appsecret=#{appsecret}, wxQrCode=#{img_wxQrCode}, redirect_uri=#{redirect_uri}, " +
            "suCaiUrl=#{suCaiUrl}, mchId=#{mchId}, wxKey=#{wxKey}, certName=#{img_certName}, sendName=#{sendName}, wishing=#{wishing}, createTime=now(), cssLink=#{cssLink}")
    int createWxConfig(WxConfigVo vo);
	/**
	 * 查询公众号配置表总记录数
	 * @return
	 */
	@Select("select count(*) from tb_wx_config")
	int countWxConfig();
	
	/**
	 * 根据vendorId查询公众号信息
	 * @param vendorId
	 * @return
	 */
	@Select("select appid,appsecret,wxQrCode=#{img_wxQrCode},redirect_uri redirectUrl,suCaiUrl,mchId,certName=#{img_certName},wxKey,sendName,wishing from tb_wx_config where vendorId=#{0} limit 1")
    WxConfig getWxConfiginfoByVendorId(Integer vendorId);
	
	/**
	 * 修改企业微信公众号信息
	 * @param wx
	 * @return
	 */
	@Update("update tb_wx_config set appid=#{appid}, appsecret=#{appsecret}, wxQrCode=#{img_wxQrCode}, redirect_uri=#{redirect_uri}, suCaiUrl=#{suCaiUrl}, " +
			"mchId=#{mchId}, wxKey=#{wxKey},certName=#{img_certName}, sendName=#{sendName}, wishing=#{wishing}, updateTime=now() where id=#{id} ")
    int updateWxConfig(WxConfigVo vo);
	/**
	 * 分页查询微信公众号信息
	 * @param map
	 * @return	
	 */
	@Select("select vendorId vendorId,appid appid,appsecret appsecret,redirect_uri redirect_uri,suCaiUrl suCaiUrl,mchId mchId,wxQrCode,sendName sendName ,wishing wishing,createTime createTime" +
			" from tb_wx_config where deleteFlag=0 LIMIT #{pageBean.index},#{pageBean.pageSize}")
	List<WxConfig> getWxConfigInfo(Map<String, Object> map);
	
	/**
	 * 新增从微擎上提交的公众号信息
	 * @param vo
	 * @return
	 */
	int addWxGzh(WxConfigGzhVo vo);
	
	/**
	 * 点击下一步时获取token,url,appKey的修改操作
	 * @param vo
	 * @return
	 */
	int updateWxGzhInfo(WxConfigGzhVo vo);
	
	/**
	 * 添加公众号时候的修改操作
	 * @param vo
	 * @return
	 */
	int addWxGzhInfo(WxConfigGzhVo vo);
	
	/**
	 * 获取公众号信息
	 * @param vendorId
	 * @return
	 */
	WxConfigGzhVo getWxGzhInfo(Integer vendorId);
	
	/**
	 * 分页获取公众号信息
	 * @param map
	 * @return
	 */
	List<WxConfigGzhVo> getAllWxGzhInfo(Map<String, Object> map);

}
