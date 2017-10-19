package com.yunma.service.product.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.ResultObject;
import com.yunma.dao.product.ProductDao;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductParams;
import com.yunma.entity.securityCode.SecurityCode;
import com.yunma.entity.securityCode.WxAntiFakeScan;
import com.yunma.model.ColumnResult;
import com.yunma.model.User;
import com.yunma.service.product.ProductService;
import com.yunma.utils.ImageUploladUtil;
import com.yunma.utils.PageBean;
import com.yunma.vo.image.ImageVo;
import com.yunma.vo.product.GroupVo;
import com.yunma.vo.product.ProductVo;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Resource
	private ProductDao productDao;
	
	public JSONObject getSummary(ProductVo productVo) {
		
		int productCount = productDao.getProductCount(productVo);//已上传的产品
		int notGroupCount = productDao.getNotGroupProductCount(productVo);//未分类的产品
		int presentCount = productDao.getPresentCount(productVo);
		
		JSONObject result = new JSONObject();
		
		result.put("productCount", productCount);
		result.put("notGroupCount", notGroupCount);
		result.put("presentCount", presentCount);
		
		return result;
	}

	@Override
	@Transactional
	public PageBean getGroup(Integer vendorId,PageBean pageBean) {
		JSONObject result = new JSONObject();
		JSONArray resultArray = new JSONArray();
		
		//设置总记录数
		pageBean.setTotalRecords(productDao.getGroupFirstCount(vendorId));
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageBean", pageBean);
		map.put("vendorId", vendorId);
		
		List<GroupVo> list = productDao.getGroupFirst(map);
		
		if (list != null && list.size() > 0) {
			for (GroupVo vo : list) {
				JSONObject jsonObject = new JSONObject();
				int firstNum = 0;
				jsonObject.put("id", vo.getRowId());
				jsonObject.put("name", vo.getRowName());
				jsonObject.put("productNum", vo.getProductNum());
				firstNum += vo.getProductNum();
				JSONArray array = new JSONArray();
				
				List<GroupVo> voList = productDao.getGroupSecond(vo.getRowId());
				
				if (voList != null && voList.size() > 0) {
					int secondNum = 0;
					for (GroupVo vo1 : voList) {
						JSONObject object = new JSONObject();
						object.put("id", vo1.getRowId());
						object.put("name", vo1.getRowName());
						object.put("pid", vo1.getRowPid());
						object.put("productNum", vo1.getProductNum());
						secondNum += vo1.getProductNum();
						array.add(object);
					}
					jsonObject.put("children", array);
					firstNum += secondNum;
					jsonObject.put("productNum", firstNum);
				}
				
				resultArray.add(jsonObject);
			}
			
		} 
		
		result.put("data", resultArray);
		//将数据放入pageBean中
		pageBean.setResult(result);
		
		return pageBean;
	}
	
	@Override
	public PageBean getProduct(PageBean pageBean, ProductVo product) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//设置总记录数
		pageBean.setTotalRecords(productDao.getProductCount(product));
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageBean", pageBean);
		map.put("product", product);
		
		result.put("keyword", product.getKeyword());
		
		List<ProductVo> list = productDao.getProduct(map);
		
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

	@Transactional
	public int updateGroup(GroupVo vo) {
		return productDao.updateGroup(vo);
	}
	
	@Transactional
	public int deleteGroup(Integer id) {
		List<GroupVo> list = productDao.getGroupSecond(id);
		
		if (list != null && list.size() > 0) {
			for (GroupVo groupVo : list) {
				int temp = productDao.deleteGroupById(groupVo.getRowId());
				if (temp != 1) {
					return 0;
				}
			}
		}
		
		int temp = productDao.deleteGroupById(id);
		if (temp == 1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Transactional
	public int addGroup(GroupVo vo) {
		return productDao.addGroup(vo);
	}
	
	@Override
	public int hasGroup(Integer vendorId,String name) {
		return productDao.hasGroup(vendorId,name);
	}
	
	@Override
	public int hasProductName(Integer vendorId, String productName) {
		return productDao.hasProductName(vendorId,productName);
	}
	
	@Override
	public int hasPresentGroup(Integer vendorId, String name) {
		return productDao.hasPresentGroup(vendorId,name);
	}
	
	@Override
	public JSONObject uploadImage(MultipartFile image,JSONObject result) {
		//判断是否有图片
		if (image != null && image.getSize() > 0) {
			
			ImageUploladUtil util = new ImageUploladUtil();
			return result = util.uploadBatchImage(image,result);
			
		} else {
			return null;
		}
	}
	
	@Override
	public JSONObject uploadImageV1(MultipartFile image,JSONObject result) {
		//判断是否有图片
		if (image != null && image.getSize() > 0) {
			
			ImageUploladUtil util = new ImageUploladUtil();
			return result = util.uploadImage(image,result);
			
		} else {
			return null;
		}
	}
	
	@Override
	public int addProduct(Product product) {
		int sortNum = productDao.findProductCountByVendorId(product.getVendorId());
        product.setSortNum(sortNum+1);
		return productDao.addProduct(product);
	}
	
	@Override
	public int updateProduct(Product product) {
		return productDao.updateProduct(product);
	}
	
	@Override
	public int deleteProduct(Integer[] id) {
		for (Integer ids : id) {
			//删除产品
			int temp = productDao.deleteProduct(ids);
			if (temp == 0) {
				return 0;
			}
		}
		return 1;
		
	}
	
	@Override
	public JSONArray getPresentGroup(Integer vendorId) {
		JSONArray array = new JSONArray();
		List<GroupVo> list = productDao.getPresentGroup(vendorId);
		if (list != null && list.size() > 0) {
			for (GroupVo groupVo : list) {
				JSONObject object = new JSONObject();
				object.put("id", groupVo.getRowId());
				object.put("name", groupVo.getRowName());
				object.put("productNum", groupVo.getProductNum());
				array.add(object);
			}
		}
		return array;
	}
	
	@Override
	public JSONObject getPresentById(Integer id) {
		JSONObject result = new JSONObject();
		ProductVo vo = productDao.getPresentById(id);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (vo != null) {
			result.put("id", vo.getId());
			result.put("name", vo.getProductName());
			result.put("price", vo.getProductPrice());
			result.put("weight", vo.getWeight());
			result.put("total", vo.getProductTotal());
			result.put("code", vo.getProductCode());
			result.put("rowId", vo.getRowId());
			result.put("rowName", vo.getRowName());
			result.put("imgId", vo.getImgId());
			result.put("img", vo.getProductImg());
			result.put("presentSpe", vo.getProductSpe());
			result.put("detail", vo.getProductDetail());
			if (vo.getLastUpdateTime()!=null) {
				result.put("lastUpdateTime", sdf.format(vo.getLastUpdateTime()));
			}
		}
		return result;
	}
	
	@Override
	public int updateProductParams(ProductParams params) {
		return productDao.updateProductParams(params);
	}
	
	@Override
	public int addPresentGroup(GroupVo vo) {
		return productDao.addPresentGroup(vo);
	}

	@Override
	public int updatePresentGroup(GroupVo vo) {
		return productDao.updatePresentGroup(vo);
	}

	@Override
	public int deletePresentGroup(Integer id) {
		return productDao.deletePresentGroup(id);
	}

	@Override
	public int deletePresent(Integer id) {
		return productDao.deletePresent(id);
	}
	
	@Override
	public int hasPresent(ProductVo vo) {
		return productDao.hasPresent(vo);
	}
	
	@Override
	public int addPresent(ProductVo vo) {
		return productDao.addPresent(vo);
	}

	@Override
	public int updatePresent(ProductVo vo) {
		return productDao.updatePresent(vo);
	}
	
	@Override
	public PageBean getPresent(PageBean pageBean,ProductVo vo) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("productVo", vo);
		map.put("pageBean", pageBean);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		
		//设置总记录数
		pageBean.setTotalRecords(productDao.getPresentCount(vo));
		
		List<ProductVo> list = productDao.getPresent(map);
		if (list != null && list.size() > 0) {
			for (ProductVo productVo : list) {
				JSONObject object = new JSONObject();
				object.put("id", productVo.getId());
				object.put("presentName", productVo.getProductName());
				object.put("rowId", productVo.getRowId());
				object.put("rowName", productVo.getRowName());
				object.put("presentPrice", productVo.getProductPrice());
				object.put("presentImg", productVo.getProductImg());
				if (productVo.getLastUpdateTime() != null) {
					object.put("lastUpdateTime", sdf.format(productVo.getLastUpdateTime()));
				}
				array.add(object);
			}
		}
		result.put("data", array);
		pageBean.setResult(result);
		return pageBean;
	}
	
	@Override
	public int addImageInfo(ImageVo vo) {
		return productDao.addImageInfo(vo);
	}
	
	@Override
	public int updateImageInfo(ImageVo vo) {
		return productDao.updateImageInfo(vo);
	}
	
	@Override
	public int deleteImage(Integer id) {
		return productDao.deleteImage(id);
	}
	
	@Override
	public PageBean getImageInfo(PageBean pageBean,ImageVo vo) {
		JSONObject result = new JSONObject();
		JSONArray array = new JSONArray();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageBean", pageBean);
		map.put("imageVo", vo);
		
		pageBean.setTotalRecords(productDao.getImageInfoCount(vo));
		
		List<ImageVo> list = productDao.getImageInfo(map);
		if (list != null && list.size() > 0) {
			for (ImageVo imageVo : list) {
				JSONObject object = new JSONObject();
				object.put("id", imageVo.getId());
				object.put("vendorId", imageVo.getVendorId());
				object.put("imgName",imageVo.getImgName());
				object.put("imgType", imageVo.getImgType());
				object.put("imgUrl", imageVo.getImgUrl());
				object.put("imgWidth", imageVo.getImgWidth());
				object.put("imgHeight", imageVo.getImgHeight());
				array.add(object);
			}
		}
		
		result.put("data", array);
		result.put("keyword", vo.getKeyword());		
		pageBean.setResult(result);
		
		return pageBean;
	}
	
	@Override
	public int addParams(ProductParams params) {
		return productDao.addParams(params);
	}
	
	@Override
	public JSONObject getProductById(Integer productId){
		JSONObject result = new JSONObject();
		
		ProductVo product = productDao.getProductById(productId);
		
		List<ProductParams> params = null;
		
		if (product != null) {
			params = productDao.getProductParamByProductId(product.getId());
			
			result.put("id", product.getId());
			result.put("rowId", product.getRowId());
			result.put("vendorId", product.getVendorId());
			result.put("productName", product.getProductName());
			result.put("productSpe", product.getProductSpe());
			result.put("productPrice", product.getProductPrice());
			result.put("productMarketPrice", product.getProductMarketPrice());
			result.put("productTotal", product.getProductTotal());
			result.put("productCode", product.getProductCode());
			result.put("productUnit", product.getProductUnit());
			result.put("productBarCode", product.getProductBarCode());
			result.put("productBewrite", product.getProductBewrite());
			result.put("sortNum", product.getSortNum());
			result.put("productDetail", product.getProductDetail());
			result.put("rowName", product.getRowName());
			result.put("productTypeName", product.getProductTypeName());
			result.put("imgId",product.getImgId());
			result.put("productImg", product.getProductImg());
			
			JSONArray array = new JSONArray();
			List<Integer> ids = new ArrayList<Integer>();
			List<String> keys = new ArrayList<String>();
			List<String> values = new ArrayList<String>();
			for (ProductParams param : params) {
				ids.add(param.getId());
				keys.add(param.getParamKey());
				values.add(param.getParamValue());
				/*JSONObject object = new JSONObject();
				object.put("paramId", param.getId());
				object.put("paramKey", param.getParamKey());
				object.put("paramValue", param.getParamValue());*/
				//array.add(object);
			}
			result.put("paramsIds", ids);
			result.put("paramsKeys", keys);
			result.put("paramsValues", values);
		}
		
		return result;
	}
	
	@Override
	public int deleteProductParam(Integer paramId) {
		return productDao.deleteProductParam(paramId);
	}
	
	
	
	
	@Override
	public List<Product> listProductByVendorId(int vendorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> listProductByVendorId1(int vendorId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int createProduct(Product product) {
		int sortNum = productDao.findProductCountByVendorId(product.getVendorId());
        product.setSortNum(sortNum+1);
        return productDao.createProduct(product);
	}

	@Override
	public int createProductColumnCustom(ColumnResult columnResult) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Product findProductByProductId(Integer productId) {
		// TODO Auto-generated method stub
		return productDao.findProductByProductId(productId);
	}

	@Override
	public Product findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColumnResult findProductByColumn(Integer productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int setAdvert(int productId, String advert) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateProductConfig(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Product> findProductByProductName(String productName, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findProductAlready(String productName, User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getAdvert(int productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findProductCountByVendorId(Integer vendorId) {
		productDao.findProductCountByVendorId(vendorId);
		return productDao.findProductCountByVendorId(vendorId);
	}

	@Override
	public Product getProductByVendorIdAndSortNum(int vendorId, int sortNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int redEnvelopeNum(Product product, int productCount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int resetScanCountsInDay() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int increaseScanCountsByProductId(int productId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getWecahtProduct(WxAntiFakeScan wxAntiFakeScan,
			SecurityCode codeBean, ResultObject result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isExistType(SecurityCode codeBean, Product product,
			String productType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getNotFenleiPro(int vendorId) {
		
		return productDao.getNotFenleiPro(vendorId);
	}

	@Override
	public int getUploadPresentCount(int vendorId) {
		return productDao.getUploadPresentCount(vendorId);
	}





	

	

	

	













	


	
	

}
