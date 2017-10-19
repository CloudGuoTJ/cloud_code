package com.yunma.entity.product;
import java.io.Serializable;
import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;
/** 产品订单表
 * Created by ${CloudGoo} on 2017/5/5 0005.
 */
public class ProductOrder implements Serializable
{
    private Integer orderId;//订单ID
    
    private Integer vendorId;//厂商ID
    
    private String vendorName;//厂商名称
    
    private Integer productId;//产品ID
    
    private String productName;//产品名称
    
    private Integer productCount;//产品数量
    
    private String codeLocation;//二维码下载地址
    
    private Date createDate;//'创建日期
    
    private Integer status;//订单已生成二维码状态 1、未生成，2、已生成
    
    private int deleteFlag;//删除标识
    
    private Date lastUpdateTime;//最后更新时间
    
	private int downloadCount;//二维码下载次数
	
    private int isClear;//扫描记录清零状态 0、未清零，1、已清零
    
    private int isException;//订单异常状态 0、正常，1、非正常
    
    private String codePrefix;  //生成二维码前缀
    
    //private int dayOfCounts;//每天下载量
    
    private Double referencePrice;//参考单价
    
    private Date expiryDate;//有效期
    
    private Integer exprotTracingCodeCount;//导出溯源码次数
    
    private Integer tracingHigherDegreeControll;//溯源码 等级1:普通等级(代理之间可以直接送货),2:严格 等级(代理之间遵从上下级同一直线物流溯源,防止区域代理之间窜货)
    
    private Integer tracingFlag;//是否生成溯源码1,未生成 2,已生成
    
    private String is_coupon_rule;//是否创建优惠券规则 0未创建 1已创建
    
    private Integer isJoinActiv;//是否参加扫码活动 0未参加 1参加了防伪活动
    
    private Integer isJoinTracActiv;//是否参加溯源活动
    
    private Integer connecTracingAndSecurty;//区分防伪和溯源的标志:1:代表防伪溯源通用两边都会显示,2.表示只在防伪表中显示,3,只在溯源表中显示
    
    public Integer getConnecTracingAndSecurty() {
		return connecTracingAndSecurty;
	}

	public void setConnecTracingAndSecurty(Integer connecTracingAndSecurty) {
		this.connecTracingAndSecurty = connecTracingAndSecurty;
	}

	public Integer getIsJoinTracActiv() {
		return isJoinTracActiv;
	}

	public void setIsJoinTracActiv(Integer isJoinTracActiv) {
		this.isJoinTracActiv = isJoinTracActiv;
	}

	public Integer getTracingHigherDegreeControll() {
		return tracingHigherDegreeControll;
	}

	public Integer getExprotTracingCodeCount() {
		return exprotTracingCodeCount;
	}

	public void setExprotTracingCodeCount(Integer exprotTracingCodeCount) {
		this.exprotTracingCodeCount = exprotTracingCodeCount;
	}

	public Integer getTracingFlag() {
		return tracingFlag;
	}

	public void setTracingFlag(Integer tracingFlag) {
		this.tracingFlag = tracingFlag;
	}

	public String getIs_coupon_rule() {
		return is_coupon_rule;
	}

	public void setIs_coupon_rule(String is_coupon_rule) {
		this.is_coupon_rule = is_coupon_rule;
	}

	public Integer getIsJoinActiv() {
		return isJoinActiv;
	}

	public void setIsJoinActiv(Integer isJoinActiv) {
		this.isJoinActiv = isJoinActiv;
	}

	public void setTracingHigherDegreeControll(Integer tracingHigherDegreeControll) {
		this.tracingHigherDegreeControll = tracingHigherDegreeControll;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCodePrefix() {
		return codePrefix;
	}

	public void setCodePrefix(String codePrefix) {
		this.codePrefix = codePrefix;
	}

	public void setReferencePrice(Double referencePrice) {
		this.referencePrice = referencePrice;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
    public java.util.Date getExpiryDate() {
        return expiryDate;
    }

    public double getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(double referencePrice) {
        this.referencePrice = referencePrice;
    }

    public int getIsException() {
        return isException;
    }

    public void setIsException(int isException) {
        this.isException = isException;
    }

    public int getIsClear() {
        return isClear;
    }

    public void setIsClear(int isClear) {
        this.isClear = isClear;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCodeLocation() {
        return codeLocation;
    }

    public void setCodeLocation(String codeLocation) {
        this.codeLocation = codeLocation;
    }



}




