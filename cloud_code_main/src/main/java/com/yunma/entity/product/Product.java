package com.yunma.entity.product;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Product implements Serializable{

	private Integer id;				//
	private Integer rowId;			//产品组id
	private Integer vendorId;		//商家id
	private Integer securityCodeId;	//防伪码ID
	private String productName;		//产品名字
	private Integer productImgId;	//产品图片id
	private String productImg;		//产品图片
	private Integer productType;		//商品类目
	private String productSpe;		//商品系列类型  1实体 2虚拟
	private Float productPrice;		//商品价格
	private Float productMarketPrice;//市场价
	private Integer productTotal;	//商品库存
	private String productCode;		//商品编码
	private String productUnit;		//产品单位（个、瓶、盒）
	private String productBarCode;	//产品条码
	private String productBewrite; 	//产品描述
	private Integer sortNum;		//排序
	private String oppenId;			//微信id
	private Integer deleteFlag;		//删除标识  0未删除 1已删除
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;	//最后更新时间
	private String productDetail;	//图文详情
	
	// ----------------微商城--------------
	private String mallTitle;		//商城商品标题
	private Integer mallNum;		//全网销量
	private Float mallWeight;		//商品重量
	private Integer mallPoint;		//赠送积分
	private Integer mallSort;		//排序
	private Integer isMall;			//是否上架 0未上架 1已上架
	
	//-------------优惠券规则---------------
	private String isCouponRule;	//是否创建了优惠券规则  0未创建 1已创建
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getSecurityCodeId() {
		return securityCodeId;
	}
	public void setSecurityCodeId(Integer securityCodeId) {
		this.securityCodeId = securityCodeId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductImgId() {
		return productImgId;
	}
	public void setProductImgId(Integer productImgId) {
		this.productImgId = productImgId;
	}
	public String getProductImg() {
		return productImg;
	}
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getProductSpe() {
		return productSpe;
	}
	public void setProductSpe(String productSpe) {
		this.productSpe = productSpe;
	}
	public Float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}
	public Float getProductMarketPrice() {
		return productMarketPrice;
	}
	public void setProductMarketPrice(Float productMarketPrice) {
		this.productMarketPrice = productMarketPrice;
	}
	public Integer getProductTotal() {
		return productTotal;
	}
	public void setProductTotal(Integer productTotal) {
		this.productTotal = productTotal;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public String getProductBarCode() {
		return productBarCode;
	}
	public void setProductBarCode(String productBarCode) {
		this.productBarCode = productBarCode;
	}
	public String getProductBewrite() {
		return productBewrite;
	}
	public void setProductBewrite(String productBewrite) {
		this.productBewrite = productBewrite;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public String getOppenId() {
		return oppenId;
	}
	public void setOppenId(String oppenId) {
		this.oppenId = oppenId;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}
	public String getMallTitle() {
		return mallTitle;
	}
	public void setMallTitle(String mallTitle) {
		this.mallTitle = mallTitle;
	}
	public Integer getMallNum() {
		return mallNum;
	}
	public void setMallNum(Integer mallNum) {
		this.mallNum = mallNum;
	}
	public Float getMallWeight() {
		return mallWeight;
	}
	public void setMallWeight(Float mallWeight) {
		this.mallWeight = mallWeight;
	}
	public Integer getMallPoint() {
		return mallPoint;
	}
	public void setMallPoint(Integer mallPoint) {
		this.mallPoint = mallPoint;
	}
	public Integer getMallSort() {
		return mallSort;
	}
	public void setMallSort(Integer mallSort) {
		this.mallSort = mallSort;
	}
	public Integer getIsMall() {
		return isMall;
	}
	public void setIsMall(Integer isMall) {
		this.isMall = isMall;
	}
	public String getIsCouponRule() {
		return isCouponRule;
	}
	public void setIsCouponRule(String isCouponRule) {
		this.isCouponRule = isCouponRule;
	}
	
	
	
}
