package com.yunma.dao.product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.yunma.dao.BaseMapper;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductParams;
import com.yunma.model.ColumnResult;
import com.yunma.vo.image.ImageVo;
import com.yunma.vo.product.GroupVo;
import com.yunma.vo.product.ProductVo;

/**
 * Created by ${CloudGoo} on 2017/5/8 0008.
 */
public interface ProductDao extends BaseMapper {
	
	/**
	 * 查询该商户下的一级目录
	 * @param map
	 * @return
	 */
	public List<GroupVo> getGroupFirst(Map<String, Object> map);
	
	/**
	 * 查询该商户下的一级目录的总记录数
	 * @param vendorId
	 * @return
	 */
	public Integer getGroupFirstCount(Integer vendorId);
	
	/**
	 * 查询一级目录下 对应的二级目录
	 * @param id
	 * @return
	 */
	public List<GroupVo> getGroupSecond(Integer id);
	
	/**
	 * 修改分组信息
	 * @param vo
	 */
	public int updateGroup(GroupVo vo);
	
	/**
	 * 删除指定id的数据
	 * @param id
	 * @return
	 */
	public int deleteGroupById(Integer id);
	
	/**
	 * 添加分类
	 * @param vo
	 */
	public int addGroup(GroupVo vo);
	
	/**
	 * 查询 产品名是否存在
	 * @param vendorId
	 * @param productName
	 * @return
	 */
	public int hasProductName(Integer vendorId, String productName);
	
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
	 * @param ids
	 */
	public int deleteProduct(Integer id);
	
	/**
	 * 分页查询产品信息
	 * @param page
	 * @param productName
	 */
	public List<ProductVo> getProduct(Map<String,Object> map);
	
	/**
	 * 分页查询产品信息的总条数
	 * @param page
	 * @param productName
	 * @return
	 */
	public Integer getProductCount(ProductVo product);
	
	/**
	 * 根据id查询产品信息
	 * @param id
	 * @return
	 */
	public Product getProductInfoById(Integer id);
	
	/**
	 * 查询礼品分类
	 * @param vendorId
	 * @return
	 */
	public List<GroupVo> getPresentGroup(Integer vendorId);
	
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
	public int deletePresentGroup(Integer id);
	
	/**
	 * 判断礼品库中是否存在该礼品
	 * @param vo
	 * @return
	 */
	public int hasPresent(ProductVo vo);
	
	/**
	 * 删除礼品库
	 * @param id
	 * @return
	 */
	public int deletePresent(Integer id);
	
	/**
	 * 添加礼品库
	 * @param vo
	 * @return
	 */
	public int addPresent(ProductVo vo);
	
	/**
	 * 修改产品库
	 * @param vo
	 * @return
	 */
	public int updatePresent(ProductVo vo);
	
	/**
	 * 统计 礼品库总记录数
	 * @param vo
	 * @return
	 */
	public Integer getPresentCount(ProductVo vo);
	
	/**
	 * 分页查询 礼品库
	 * @param map
	 * @return
	 */
	public List<ProductVo> getPresent(Map<String, Object> map);
	
	/**
	 * 判断是否有重名的分组名
	 * @param vendorId
	 * @param name
	 * @return
	 */
	public int hasGroup(Integer vendorId, String name);
	
	/**
	 * 判断是否有重名的礼品分组名
	 * @param vendorId
	 * @param name
	 * @return
	 */
	public int hasPresentGroup(Integer vendorId, String name);
	
	/**
	 * 未分类的产品
	 * @param productVo
	 * @return
	 */
	public int getNotGroupProductCount(ProductVo productVo);
	
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
	 * 查询图片的总记录数
	 * @param vendorId
	 * @param keyword
	 * @return
	 */
	public int getImageInfoCount(ImageVo vo);
	
	/**
	 * 分页查询图片信息
	 * @param map
	 */
	public List<ImageVo> getImageInfo(Map<String, Object> map);
	
	/**
	 * 添加产品参数
	 * @param params
	 * @return
	 */
	public int addParams(ProductParams params);
	
	/**
	 * 根据产品id查询产品信息
	 * @param id
	 * @return
	 */
	public ProductVo getProductById(Integer id);
	
	/**
	 * 根据产品id查询产品参数信息
	 * @param productId
	 * @return
	 */
	public List<ProductParams> getProductParamByProductId(Integer productId);
	
	/**
	 * 修改产品参数信息
	 * @param params
	 * @return
	 */
	public int updateProductParams(ProductParams params);
	
	/**
	 * 根据礼品id查询礼品信息
	 * @param id
	 * @return
	 */
	public ProductVo getPresentById(Integer id);
	
	/**
	 * 删除产品参数
	 * @param paramId
	 * @return
	 */
	public int deleteProductParam(Integer paramId);
	
	
	
	
    @Select("select * from tb_product where vendorId=#{vendorId} and  deleteFlag=0 order by productId desc")
    List<Product> listProductByVendorId(int vendorId);

    @Select("select * from tb_product where deleteFlag=0 order by productId desc")
    List<Product> listProductByVendorId1(int vendorId);

    @Select("select * from tb_product where deleteFlag=0")
    List<Product> findListAll();

    @Insert("insert into tb_product set vendorName=#{vendorName},vendorId=#{vendorId},productName=#{productName},"+
            "levelOneNum=#{levelOneNum},levelOneType=#{levelOneType},levelTwoNum=#{levelTwoNum},levelTwoType=#{levelTwoType}," +
            "levelThreeNum=#{levelThreeNum},levelThreeType=#{levelThreeType},levelFourNum=#{levelFourNum},levelFourType=#{levelFourType}," +
            "internalFlag=#{internalFlag},internalOneType=#{internalOneType},internalTwoType=#{internalTwoType},internalThreeType=#{internalThreeType}," +
            "advert=#{advert},comment=#{comment},sortNum=#{sortNum},renderCodeType=#{renderCodeType},innerMappingRelation=#{innerMappingRelation}," +
            "outerMappingRelation=#{outerMappingRelation},viewType=#{viewType},imgUrl=#{imgUrl},logisticsLogCode=#{logisticsLogCode},website=#{website}")
    int createProduct(Product product);

    /**
     * 创建产品自定义参数名和内宿
     * @param columnResult
     * @return
     */
    @Insert("insert into tb_product_column set productId=#{productId}," +
            "column1=#{column1},value1=#{value1},"+
            "column2=#{column2},value2=#{value2}," +
            "column3=#{column3},value3=#{value3}," +
            "column4=#{column4},value4=#{value4}," +
            "column5=#{column5},value5=#{value5}," +
            "column6=#{column6},value6=#{value6}," +
            "createTime=now()")
    int createProductColumn(ColumnResult columnResult);

    /**
     * 修改产品自定义参数名和内宿
     * @param columnResult
     * @return
     */
    @Insert("update  tb_product_column set " +
            "column1=#{column1},value1=#{value1},"+
            "column2=#{column2},value2=#{value2}," +
            "column3=#{column3},value3=#{value3}," +
            "column4=#{column4},value4=#{value4}," +
            "column5=#{column5},value5=#{value5}," +
            "column6=#{column6},value6=#{value6}," +
            "createTime=now() where productId=#{productId}")
    int updateProductColumn(ColumnResult columnResult);


    @Select("select * from tb_product where id=#{0} ")
    Product findProductByProductId(Integer productId);

    @Select("select * from tb_product_column where id=#{0}")
    ColumnResult findProductByColumn(int productId);

    @Select("select count(1) from tb_product where vendor_id=#{vendorId} and delete_flag=0")
    int findProductCountByVendorId(Integer vendorId);

    @Update("update tb_product set advert=#{1} where id=#{0}")
    int setAdvert(int productId, String advert);


    @Update("update tb_product set productNum=#{0} where productId=#{1}")
    int updateProductNum(int productNum,int productId);

    @Update("update tb_product set renderCodeType=#{renderCodeType}," +
            "innerMappingRelation=#{innerMappingRelation}," +
            "outerMappingRelation=#{outerMappingRelation}," +
            "comment=#{comment},imgUrl=#{imgUrl},advert=#{advert}," +
            "viewType=#{viewType},website=#{website} where productId=#{productId}")
    int updateProductConfig(Product product);

    //新增订单的时候给产品的orderCount势
    @Update("update tb_product set orderCount = orderCount + 1 where productId = #{0}")
    int updateProductOrderCount(int productId);

    @Select("select * from tb_product where productName=#{0} and vendorId=#{1} and deleteFlag=0")
    List<Product> findProductByProductName(String productName,int vendorId);

    @Select("select count(1) from tb_product where productName=#{0} and vendorId=#{1} and deleteFlag=0")
    int  findProductAlready(String productName,int vendorId);

    @Select("select advert from tb_product where productId=#{0} and deleteFlag=0")
    String getAdvert(int productId);

    @Select("select productId, productName from tb_product where vendorId=#{0} and sortNum=#{1}")
    Product getProductByVendorIdAndSortNum(int vendorId, int sortNum);

    //清零产品的当天扫码次敿
    @Update("update tb_product set scanCountsInDay = 0")
    int resetScanCountsInDay();

    //产品的当天扫码次数加1
    @Update("update tb_product set scanCountsInDay = scanCountsInDay + 1 where productId = #{0}")
    int increaseScanCountsByProductId(long productId);

    //查询未分类的产品的数量
	@Select("select count(*) from tb_product where vendor_id=#{0} And delete_flag=0 and row_id is null")
    int getNotFenleiPro(int vendorId);
	
	//查询已上传礼品的数量
	@Select("select count(*) from tb_present where vendor_id=#{0} And delete_flag=0")
	int getUploadPresentCount(int vendorId);
	
	/**
	 * 修改参加集字游戏的产品状态
	 * @param proId
	 * @return
	 */
	@Update("update tb_product set isCollectWord=1 where id=#{proId}")
	int updateProInfoForCollectWord(Integer proId);
	
	/**
	 * 分页查询集字游戏产品信息
	 * @param page
	 * @param productName
	 */
	public List<ProductVo> getProductForCollectWord(Map<String,Object> map);
	
	/**
	 * 分页查询集字游戏产品信息的总条数
	 * @param page
	 * @param productName
	 * @return
	 */
	public Integer getProductCountForCollectWord(ProductVo product);

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	

	
}


