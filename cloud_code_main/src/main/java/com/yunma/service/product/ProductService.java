package com.yunma.service.product;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.ResultObject;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductParams;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.entity.securityCode.WxAntiFakeScan;
import com.yunma.model.ColumnResult;
import com.yunma.model.User;
import com.yunma.utils.PageBean;
import com.yunma.vo.image.ImageVo;
import com.yunma.vo.product.GroupVo;
import com.yunma.vo.product.ProductVo;

/** 关于产品的service接口
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public interface ProductService
{
    List<Product> listProductByVendorId(int vendorId);

    List<Product> listProductByVendorId1(int vendorId);
    
    /**
     * 产品概述
     * @param productVo
     * @return
     */
    public JSONObject getSummary(ProductVo productVo);
    
    /**
     * 查询产品分类
     * @param vendorId 
     * @return 
     */
    public PageBean getGroup(Integer vendorId,PageBean pageBean);
    
    /**
     * 修改产品分类
     * @param vo
     * @return
     */
    public int updateGroup(GroupVo vo);
    
    /**
     * 删除产品分类
     * @param id
     * @return
     */
    public int deleteGroup(Integer id);
    
    /**
     * 添加产品分类
     * @param vo
     * @return
     */
    public int addGroup(GroupVo vo);
    
    /**
     * 查询 是否存在该产品名
     * @param vendorId
     * @param productName
     */
    public int hasProductName(Integer vendorId, String productName);
    
    /**
     * 上传图片
     * @param image
     * @return
     */
    public JSONObject uploadImage(MultipartFile image,JSONObject result);
    
    /**
     * 上传图片 不重调宽高
     * @param productImg
     * @param result
     * @return
     */
    public JSONObject uploadImageV1(MultipartFile productImg, JSONObject result);
    
    /**
     * 添加产品
     * @param product
     * @return
     */
    public int addProduct(Product product);
    
    /**
     * 修改产品
     * @param product
     * @return
     */
    public int updateProduct(Product product);
    
    /**
     * 删除产品
     * @param id
     * @return
     */
    public int deleteProduct(Integer[] id);
    
    /**
     * 分页查询 产品信息
     * @param page
     * @param productName
     */
    public PageBean getProduct(PageBean page, ProductVo product);
    
    /**
     * 查询礼品分类
     * @param vendorId
     * @return
     */
    public JSONArray getPresentGroup(Integer vendorId);
    
    /**
	 * 添加礼品分类
	 * @param vo
	 * @return
	 */
	public int addPresentGroup(GroupVo vo);
	
	/**
	 * 修改礼品分类
	 * @param vo
	 * @return
	 */
	public int updatePresentGroup(GroupVo vo);
	
	/**
	 * 删除礼品分类
	 * @param vendorId
	 * @return
	 */
	public int deletePresentGroup(Integer vendorId);
    
	/**
	 * 删除礼品库
	 * @param id
	 * @return
	 */
	public int deletePresent(Integer id);
	
	/**
	 * 判断礼品库中是否存在重名的礼品
	 * @param vo
	 * @return
	 */
	public int hasPresent(ProductVo vo);
    
	/**
	 * 添加礼品库
	 * @param vo
	 */
	public int addPresent(ProductVo vo);
	
	/**
	 * 修改产品库
	 * @param vo
	 * @return
	 */
	public int updatePresent(ProductVo vo);
	
	/**
	 * 分页查询礼品库
	 * @param vo
	 * @return
	 */
	public PageBean getPresent(PageBean pageBean,ProductVo vo);
	
	/**
	 * 判断是否有重名的分组名
	 * @param vendorId
	 * @param name
	 * @return
	 */
	public int hasGroup(Integer vendorId,String name);
    
	/**
	 * 判断是否有重名的礼品分组名
	 * @param vendorId
	 * @param name
	 * @return
	 */
	public int hasPresentGroup(Integer vendorId, String name);
	
	/**
	 * 添加图片信息
	 * @param vo
	 * @return
	 */
	public int addImageInfo(ImageVo vo);
	
	/**
	 * 修改图片信息
	 * @param vo
	 * @return
	 */
	public int updateImageInfo(ImageVo vo);
	
	/**
	 * 删除图片
	 * @param id
	 * @return
	 */
	public int deleteImage(Integer id);
	
	/**
	 * 得到该厂商id的所有图片信息
	 * @param pageBean
	 * @param keyword
	 * @return
	 */
	public PageBean getImageInfo(PageBean pageBean,ImageVo vo);
	
	/**
	 * 添加产品参数
	 * @param params
	 * @return
	 */
	public int addParams(ProductParams params);
	
	/**
	 * 根据产品id查询产品的信息
	 * @param productId
	 * @return
	 */
	public JSONObject getProductById(Integer productId);
	
	/**
	 * 修改产品参数
	 * @param string
	 * @param string2
	 * @return
	 */
	public int updateProductParams(ProductParams params);
	
	/**
	 * 根据礼品id查询礼品信息
	 * @param id
	 * @return
	 */
	public JSONObject getPresentById(Integer id);
	
	/**
	 * 删除产品参数
	 * @param paramId
	 * @return
	 */
	public int deleteProductParam(Integer paramId);
	
	
	
	
	
	
    
    

    /**
     * 创建产品信息
     * @param product
     * @return
     */
    int createProduct(Product product);
    /**
     * 创建自定义产品参数表
     * @param columnResult
     * @return
     */
    int createProductColumnCustom(ColumnResult columnResult);

    Product findProductByProductId(Integer productId);

    /**
     * 根据ID查询产品信息
     * @param id
     * @return
     */
    Product findById(long id);

    ColumnResult findProductByColumn(Integer productId);

    int setAdvert(int productId, String advert);

    int updateProductConfig(Product product);

    List<Product> findProductByProductName(String productName,User user);

    int  findProductAlready(String productName,User  user);

    String getAdvert(int productId);

    int findProductCountByVendorId(Integer vendorId);

    Product getProductByVendorIdAndSortNum(int vendorId, int sortNum);

    /**
     * 具有红包功能的二维码数量
     * @param product
     * @param productCount
     * @return
     */
    int redEnvelopeNum(Product product, int productCount);

    //清零当天产品的扫码次数
    int resetScanCountsInDay();

    //扫码次数加1
    int increaseScanCountsByProductId(int productId);

    /**
     * 微信获取产品信息
     * @param wxAntiFakeScan 微信扫码记录
     * @param codeBean 二维码信息
     * @param result
     */
    void getWecahtProduct(WxAntiFakeScan wxAntiFakeScan, SecurityCode codeBean, ResultObject result);

    /**
     * 产品中是否包含此功能类别
     * @param codeBean 二维码信息
     * @param product 产品
     * @param productType 产品功能类别
     * @return
     */
    boolean isExistType(SecurityCode codeBean, Product product, String productType);

    //查询未分类的产品的总数
    int getNotFenleiPro(int vendorId);
    
    //查询已上传礼品的数量
    int getUploadPresentCount(int vendorId);

	
	

	

	

	

	

	



	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	



	


}
