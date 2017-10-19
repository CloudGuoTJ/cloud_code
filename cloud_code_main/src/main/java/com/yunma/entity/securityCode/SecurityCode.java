package com.yunma.entity.securityCode;

import java.io.Serializable;
import java.sql.Date;

/**  二维码表
 * Created by ${CloudGoo} on 2017/5/8 0008.
 */
public class SecurityCode implements Serializable
/*
 * 
 * 	securityCode.setCreateRowNum(rowNum);
				securityCode.setOrderId(orderId);
				securityCode.setProductId(productId);
				securityCode.setSecurityCode(code);
				securityCode.setProductName(order.getProductName());
				securityCode.setCodePrefix(codePrefix);
				securityCode.setCodeTailTag(codeTailTag);
				
  `securityCodeId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '防伪码ID',
  `orderId` bigint(20) NOT NULL COMMENT '订单ID',
  `productId` bigint(20) NOT NULL COMMENT '产品ID',
  `productName` varchar(64) NOT NULL COMMENT '产品名',
  `codeFlag` tinyint(4) NOT NULL COMMENT 'codeFlag:本版本中代表：1.防伪 2.溯源',
  `codePrefix` varchar(64) NOT NULL COMMENT '二维码的前缀，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成',
  `securitycode` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '二维码全码',
  `codeTailTag` varchar(32) NOT NULL COMMENT '二维码尾号：使用UUID生成变长码，码长和产品数量有关',
  `createRowNum` varchar(32) NOT NULL  COMMENT '分组生成的组号，单次生成未超过3844个，组号为1',
  `createDate` datetime NOT NULL COMMENT '生成日期',
 * 
 * */




    {
        private Integer securityCodeId;//二维码id
        private Integer orderId;//订单ID
        private Integer productId;//产品ID
        private String productName;//产品名
        private Integer codeFlag;//codeFlag:本版本中代表：1.未扫码 2.已扫描
		private String codePrefix;//二维码的前缀，前4位：根据orderId转换，中2位：venderId转换 ，第7-8位：根据productId转换，最后两位根据bitset生成
        private String securityCode;//生成二维码全码
        private Date createDate;//生成日期
        private String codeTailTag;//二维码尾号：使用UUID生成变长码，码长和产品数量有关
        private int createRowNum;//分组生成的组号，单次生成未超过3844个，组号为1
        
        public Integer getCodeFlag() {
 			return codeFlag;
 		}

 		public void setCodeFlag(Integer codeFlag) {
 			this.codeFlag = codeFlag;
 		}


        public int getCreateRowNum() {
			return createRowNum;
		}

		public void setCreateRowNum(int createRowNum) {
			this.createRowNum = createRowNum;
		}

		public String getSecurityCode()
        {
            return securityCode;
        }

        public void setSecurityCode(String securityCode)
        {
            this.securityCode = securityCode;
        }

        public Integer getSecurityCodeId()
        {
            return securityCodeId;
        }

        public void setSecurityCodeId(Integer securityCodeId)
        {
            this.securityCodeId = securityCodeId;
        }

        public Integer getOrderId()
        {
            return orderId;
        }

        public void setOrderId(Integer orderId)
        {
            this.orderId = orderId;
        }

        public Integer getProductId()
        {
            return productId;
        }

        public void setProductId(Integer productId)
        {
            this.productId = productId;
        }

        public String getProductName()
        {
            return productName;
        }

        public void setProductName(String productName)
        {
            this.productName = productName;
        }



        public Date getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Date createDate)
        {
            this.createDate = createDate;
        }

 

		public String getCodePrefix() {
			return codePrefix;
		}

		public void setCodePrefix(String codePrefix) {
			this.codePrefix = codePrefix;
		}

		public String getCodeTailTag() {
			return codeTailTag;
		}

		public void setCodeTailTag(String codeTailTag) {
			this.codeTailTag = codeTailTag;
		}

		
    }
