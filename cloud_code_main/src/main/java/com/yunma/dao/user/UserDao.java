package com.yunma.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.yunma.dao.BaseMapper;
import com.yunma.entity.register.RegisterChecking;
import com.yunma.model.User;
import com.yunma.vo.user.UserVO;
import com.yunma.vo.user.UserVendorVO;


public interface UserDao extends BaseMapper {
	/**
	 * 通过vendorId或取vendorName用于商家导出二维码
	 * 
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT vendorName FROM tb_user Where vendorId=#{vendorId}")
	String getVendorNameByVendorId(@Param("vendorId") Integer vendorId);

	/**
	 * 通过商家userId 匹配 vendorId验证是否进行下一步操作
	 */

	@Select("SELECT vendorId FROM tb_user Where userId=#{userId}")
	Integer getVendorIdByUserId(@Param("userId") Integer userId);

	/**
	 * 查询UserType
	 */
	@Select("SELECT userType FROM tb_user WHERE userId=#{userId}")
	int findUserType(@Param("userId") Integer userId);

	/**
	 * 创建用户vendorId递增值确定
	 */
	@Select("SELECT userId FROM tb_user WHERE userId=(SELECT max(userId) FROM tb_user)")
	int getMaxVendorId();

	/**
	 * 分页查询改造
	 * 
	 * @param map
	 * @return
	 */
	/* 统计user总数 */
	@Select(" SELECT count(*) FROM tb_user where deleteFlag=0")
	int getUserCount();

	@Select("SELECT userId,userName, status,userType,vendorId,vendorName, createTime FROM tb_user where deleteFlag=0 ORDER BY createTime DESC "
			+ "LIMIT #{pageBean.index},#{pageBean.pageSize}")
	List<UserVO> getUserInfo(Map<String, Object> map);

	/**
	 * 登录商家用户查询自身信息
	 * 
	 * @param userId
	 * @return
	 */
	/* 通过用户ID查询用户信息 */
	@Select("select userId,userName,vendorName,vendorId,createTime from tb_user where userId=#{userId}")
	UserVendorVO findUserVOByUserId(Integer userId);

	/**
	 * 根据用户ID查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select * from tb_user where userId=#{userId}")
	User findUserByUserId(Integer userId);

	/* 根据用户名查询用户信息 */
	@Select("select * from tb_user where userName=#{username}")
	User findUserByUserName(String username);

	/* 查询未删除的用户信息 */
	@Select("select * from tb_user where deleteFlag=0  limit #{0},#{1}")
	List<User> findUserList(int i, int num);

	/* 查询有关厂商的信息 1、2、3、4、5、6都是厂商相关用户 */
	@Select("select * from tb_user where deleteFlag=0 and userType in (1,3,4,5,6) and  vendorId=#{2}  limit #{0},#{1}")
	List<User> findUserByVendorList(int i, int num, int vendorId);

	/**/
	@Select("select count(*) from tb_user")
	int findUserListCount();

	@Select("select count(1) from tb_user where deleteFlag=0 and userType =1 and  vendorId=#{0}")
	int findUserByVendorListCount(int vendorId);

	/**
	 * 查找用户表中最后一名商家用户的vendorId
	 * 
	 * @param vendorId
	 * @return
	 */
	@Select("select vendorId from (SELECT * FROM tb_user order By createTime DESC LIMIT 1)mm")
	int findLastVendorId();

	/**
	 * 存入用户信息
	 * 
	 * @param user
	 * @return
	 */
	@Insert("insert into tb_user set userName=#{userName},passwd=#{passwd},status=1,createTime=now(),lastUpdateTime=now(),"
			+ "userType=#{userType},testSecurityCodeCount=#{testSecurityCodeCount},testTracingCodeCount=#{testTracingCodeCount},vendorId=#{vendorId},vendorName=#{vendorName}")
	int createUser(User user);

	@Insert("insert into tb_user set userId=#{userId},userName=#{userName},passwd=#{passwd},status=1,createTime=now(),"
			+ "userType=#{userType},vendorId=#{vendorId},vendorName=#{vendorName},mainHtmlComment=#{mainHtmlComment}"
			+ ",mainHtmlCommentEN=#{mainHtmlCommentEN},roleName=#{roleName}")
	int createSyncUser(User user);

	@Update("update tb_user set passwd=#{0} where userId=#{1}")
	int setUserPasswd(String passwd, int userId);

	@Update("update tb_user set findpwd=#{0} where userId=#{1}")
	int setFindPasswd(String passwd, int userId);

	@Update("update tb_user set status=0 where userId=#{0}")
	int setLockUser(int userId);

	@Update("update tb_user set status=1 where userId=#{0}")
	int cencalLockUser(int userId);

	/* */
	@Select("select * from tb_user where vendorId = #{0} and userType=#{1}")
	User findTypeUserByVendorId(int vendorId, int userType);

	@Select("select * from tb_user where vendorId = #{vendorId} and userType=1")
	User findUserByVendorId(@Param("vendorId") Integer vendorId);

	@Update("update tb_user set mainHtmlComment=#{mainHtmlComment} where  userId=#{userId}")
	Integer UpdateMainHtmlConfig(User user);

	@Update("update tb_user set mainHtmlCommentEN=#{mainHtmlCommentEN} where  userId=#{userId}")
	Integer mainHtmlENConfig(User user);

	@Update("UPDATE tb_user set passwd=#{passwd} where username=#{username}")
	int setPasswd(String passwd, String username);

	/**
	 * 注册审核表
	 * 
	 * usage:用于拓展注册功能 追加管理员审核信息,
	 * 
	 */
	@Update("INSERT INTO tb_vendor_user_register_checking "
			+ "SET "
			+ "vendorName=#{vendorName},industryName=#{industryName},tradeMarkImgUrl=#{tradeMarkImgUrl},"
			+ "tradeMarkLicense=#{tradeMarkLicense},contactName=#{contactName},customPhone=#{customPhone},"
			+ "vendorAddress=#{vendorAddress},vendorEmaill=#{vendorEmaill},checkComment=#{checkComment},"
			+ "createTime=now(),checkStatus=#{checkStatus}")
	int addRegisterInfo(RegisterChecking registerChecking);

	/**
	 * 审核通过
	 * 
	 * @param checkId
	 * @return
	 */
	@Update("UPDATE tb_vendor_user_register_checking SET checkStatus=2 ;")
	int permitCheckingregister(Integer checkId);

	/**
	 * 分页显示须审查的注册者 获取总的条数
	 */
	@Select("SELECT COUNT(*) FROM tb_vendor_user_register_checking WHERE checkStatus=1;")
	int getRegisterCheckingCount();

	/**
	 * 分页显示需要审核的内容
	 * 
	 * @param map
	 * @return
	 */
	@Select("SELECT * FROM tb_vendor_user_register_checking where checkStatus=1 ORDER BY createTime DESC;")
	List<RegisterChecking> getRegisterCheckingInfoList(Map<String, Object> map);

	/**
	 * 根据vendorName查询User信息
	 * 
	 * @param vendorName
	 * @return
	 */
	@Select("SELECT * FROM tb_user WHERE vendorName=#{vendorName}")
	User findUSerByVendorName(@Param("vendorName") String vendorName);

	/**
	 * 从tb_vendor_user_detail获取vendorId
	 * 
	 * @param vendorName
	 * @return
	 */
	@Select("SELECT vendor_id as vendorId FROM yunma.tb_vendor_user_detail WHERE brand_name=#{vendorName};")
	Integer getVendorIdFromVendorDetailByVendorName(String vendorName);
	/**
	 * 变更UserType
	 * @param userId
	 * @return
	 */
	@Update("UPDATE tb_user SET userType=1 WHERE userId=#{userId}")
	int updateUserType(@Param("userId")Integer userId);
	/**
	 * 分页显示测试用户
	 */
	@Select(" SELECT count(*) FROM tb_user where deleteFlag=0 AND userType=2")
	int getTestUserCount();
	/**
	 * 分页显示测试用户
	 * @param map
	 * 
	 * SELECT u.userId,u.userName, u.status,u.userType,u.vendorId,u.vendorName, u.createTime,c.vendorEmaill,c.customPhone,c.tradeMarkImgUrl,c.tradeMarkLicense FROM tb_user u , tb_vendor_user_register_checking c where u.vendorName=c.vendorName And deleteFlag=0 AND userType=2 ORDER BY u.createTime DESC limit 1,2
	 * @return
	 */
	@Select("SELECT userId,userName, status,userType,vendorId,vendorName, createTime FROM tb_user where deleteFlag=0 AND userType=2 ORDER BY createTime DESC "
			+ "LIMIT #{pageBean.index},#{pageBean.pageSize}")
	List<UserVO> getTestUserInfo(Map<String, Object> map);
	/**
	 * 连表分页显示测试用户数据
	 * @param map
	 * @return
	 */
	@Select("SELECT u.userId,u.userName,u.userType,u.vendorId,u.vendorName, u.createTime,c.vendorEmaill,c.customPhone,c.tradeMarkImgUrl,c.tradeMarkLicense,c.bankAccountOpeningLicense,c.foodProductionLicence,c.industrialProductionLicense,c.checkStatus "
			+ "FROM "
			+ "tb_user u , tb_vendor_user_register_checking c "
			+ " WHERE "
			+ " u.vendorName=c.vendorName And deleteFlag=0 AND userType=2 ORDER BY u.createTime DESC "
			+ " LIMIT #{pageBean.index},#{pageBean.pageSize} ;")
	List<UserVO> getTestUserAllInfo(Map<String, Object> map);

	
	/**
	 * 更新测试用户生成二维码数量
	 * 
	 * @param testSecurityCodeCountPri
	 * @param userId
	 * @return
	 */
	@Update("UPDATE tb_user SET testSecurityCodeCount=#{testSecurityCodeCountPri} WHERE userId=#{userId}")
	int  updateUserTestSecurityCount(@Param("testSecurityCodeCountPri")Integer testSecurityCodeCountPri,@Param("userId")Integer userId);
	/**
	 * 更新测试用户溯源码生成数量
	 * @param testTracingCodeCount
	 * @param userId
	 * @return
	 */
	
	@Update("UPDATE tb_user SET testTracingCodeCount=#{testTracingCodeCount} WHERE userId=#{userId}")
	int updateUserTestTracingCodeCount(@Param("testTracingCodeCountPri")Integer testTracingCodeCountPri,
			@Param("userId")Integer userId);
	

	/***
	 * 通过vendorName查询待审核单个用户信息
	 * @param vendorName
	 * @return
	 */
	@Select("SELECT * FROM tb_vendor_user_register_checking WHERE vendorName=#{vendorName};")
	RegisterChecking findRegisterCheckingByVendorName(@Param("vendorName")String vendorName);
	/**
	 * 查询单个测试用户
	 * @param vendorId
	 * @return
	 */
	@Select("SELECT * FROM tb_user WHERE vendorId=#{vendorId} AND userType=2")
	User findTestUserByVendorId(@Param("vendorId")int vendorId);
	/**
	 * 试用户通过审核状态变更
	 * @param vendorName
	 * @return
	 */
	@Update("UPDATE tb_vendor_user_register_checking SET checkStatus=2 WHERE vendorName=#{vendorName}")
	int updateUserTestUserCheckStatus(@Param("vendorName")String vendorName);
	/**
	 * 添加未通过审核通知
	 * @param vendorName
	 * @param checkComment
	 * @return
	 */
	@Update("UPDATE tb_vendor_user_register_checking SET checkComment=#{checkComment} WHERE vendorName = #{vendorName} ;")
	int updateCheckingInfo(@Param("vendorName")String vendorName, @Param("checkComment")String checkComment);
	/**
	 * 更新user表中试用用户创建溯源码数量
	 * @param vendorId
	 * @param count
	 * @return
	 */
	@Update("UPDATE tb_user SET testTracingCodeCount=#{count} WHERE vendorId = #{vendorId} ;")
	int updateTestUserCreatingTracingCodeCount(@Param("vendorId")Integer vendorId,@Param("count") int count);
	
	@Select("SELECT u.userId,u.userName,u.userType,u.vendorId,u.vendorName, u.createTime,c.vendorEmaill,c.customPhone,c.tradeMarkImgUrl,c.tradeMarkLicense,c.bankAccountOpeningLicense,c.foodProductionLicence,c.industrialProductionLicense,c.checkStatus "
			+ "FROM "
			+ "tb_user u , tb_vendor_user_register_checking c "
			+ " WHERE "
			+ " u.userId=#{userId} and u.vendorName=c.vendorName ;")
	UserVO findSingleUserInfo(@Param("userId")Integer userId);

	
	



	

	
	


}
