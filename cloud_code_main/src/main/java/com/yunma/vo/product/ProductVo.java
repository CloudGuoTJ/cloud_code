package com.yunma.vo.product;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ProductVo implements Serializable{

	private Integer id;				//
	private Integer rowId;			//产品组id
	private String rowName;			//产品组名
	private Integer securityCodeId;	//防伪码ID
	private String productName;		//产品名字
	private Integer imgId;			//图片id
	private String productImg;		//产品图片
	private String productType;		//商品类目id
	private String productTypeName;	//商品类目名
	private String productSpe;		//商品系列类型  1实体 2虚拟
	private Float productPrice;		//商品价格
	private Float productMarketPrice;//市场价
	private Integer productNum;		//商品数量
	private Integer productTotal;	//商品总数
	private String productCode;		//商品编码
	private String productUnit;		//产品单位（个、瓶、盒）
	private String productBarCode;	//产品条码
	private String productBewrite; 	//产品描述
	private String oppenId;			//微信id
	private Integer vendorId;		//商家id
	private Integer sortNum;		//排序
	private Integer deleteFlag;		//删除标识  0未删除 1已删除
	private String  keyword;		//查询关键字
	private String productDetail;	//图文详情
	private Float weight;			//重量
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;	//最后更新时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;		//创建时间
	
	// ----------------微商城--------------
	private String mallTitle;		//商城商品标题
	private Integer mallNum;		//全网销量
	private Float mallWeight;		//商品重量
	private Integer mallPoint;		//赠送积分
	private Integer mallSort;		//排序
	private Integer isMall;			//是否上架 0未上架 1已上架
	
	
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
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
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
	public String getProductImg() {
		return productImg;
	}
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
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
	public Integer getProductNum() {
		return productNum;
	}
	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
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
	public String getOppenId() {
		return oppenId;
	}
	public void setOppenId(String oppenId) {
		this.oppenId = oppenId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getProductDetail() {
		return productDetail;
	}
	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public Integer getImgId() {
		return imgId;
	}
	public void setImgId(Integer imgId) {
		this.imgId = imgId;
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
	
	
	
}
