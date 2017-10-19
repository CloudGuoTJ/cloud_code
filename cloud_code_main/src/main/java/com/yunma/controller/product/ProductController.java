package com.yunma.controller.product;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.sd4324530.fastweixin.api.enums.OauthScope;
import com.github.sd4324530.fastweixin.util.BeanUtil;
import com.github.sd4324530.fastweixin.util.StrUtil;
import com.yunma.controller.couponRule.CouponRuleController;
import com.yunma.entity.product.Product;
import com.yunma.entity.product.ProductParams;
import com.yunma.service.dictionary.DictionaryService;
import com.yunma.service.product.ProductService;
import com.yunma.utils.PageBean;
import com.yunma.vo.dictionary.DictionaryVo;
import com.yunma.vo.image.ImageVo;
import com.yunma.vo.product.GroupVo;
import com.yunma.vo.product.ProductVo;

@Controller
public class ProductController {

	@Resource
	private ProductService productService;
	
	@Resource
	private CouponRuleController couponRuleController;
	
	@Resource
	private DictionaryService dictionaryService;
	
	private Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@RequestMapping("/test.do")
	public ModelAndView test(@RequestParam(value="image", required=false) CommonsMultipartFile[] image) {
		return new ModelAndView("redirect:https://localhost:8080/wx/index.jsp");
	}
	
	/**
	 * 查询 产品概述
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/product/home.do")
	@ResponseBody
	public JSONObject getSummary(Integer vendorId) {
		ProductVo productVo = new ProductVo(); 
		productVo.setVendorId(vendorId);
		productVo.setDeleteFlag(0);
		return productService.getSummary(productVo);
	}
	
	/**
	 * 查询 产品分类 信息
	 * @param vendorId 厂商id
	 * @return
	 */
	@RequestMapping("/GET/product/group.do")
	@ResponseBody
	public PageBean getGroup(PageBean pageBean,Integer vendorId) {
		return productService.getGroup(vendorId,pageBean);
	}
	
	/**
	 * 修改产品分类信息
	 * @param id 分类 id
	 * @param vendorId 厂商id
	 * @param name 分类名
	 * @param num 产品数
	 * @return
	 */
	@RequestMapping("/UPDATE/product/group.do")
	@ResponseBody
	public JSONObject updateGroup(Integer id,Integer vendorId,String name,Integer num) {
		JSONObject result = new JSONObject();

		if (name != null) {
			int hasName = productService.hasGroup(vendorId, name);
			if (hasName > 0) {
				result.put("statuscode", -3);
				result.put("msg", "添加失败，不能添加相同的分类名");
				return result;
			}
		}
		
		GroupVo vo = new GroupVo();
		vo.setRowId(id);
		vo.setVendorId(vendorId);
		vo.setRowName(name);
		vo.setProductNum(num);
		
		int resultVal = productService.updateGroup(vo);
		if (resultVal == 1) {
			result.put("statuscode", 1);
			result.put("msg", "成功");
			return result;
		} else if (resultVal == 0) {
			result.put("statuscode", -1);
			result.put("msg", "该id不存在无法修改");
			return result;
		} else {
			result.put("statuscode", -2);
			result.put("msg", "修改失败");
			return result;
		}
		
	}
	
	/**
	 * 删除分组
	 * @param id 
	 * @return
	 */
	@RequestMapping("/DELETE/product/group.do")
	@ResponseBody
	public JSONObject deleteGroup(Integer id) {
		JSONObject result = new JSONObject();
		
		int temp = productService.deleteGroup(id);
		
		if (temp == 1) {
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else {
			result.put("statuscode", -1);
			result.put("msg", "删除失败");
		}
		return result;
	}
	
	/**
	 * 添加分组
	 * @param pid 父节点id
	 * @param name 组名
	 * @param num 产品数
	 * @param type 1 代表一级 2代表二级 分类
	 * @return
	 */
	@RequestMapping("/ADD/product/group.do")
	@ResponseBody
	public JSONObject addGroup(Integer vendorId,Integer pid,String name,Integer num,String type) {
		JSONObject result = new JSONObject();
		
		int hasName = productService.hasGroup(vendorId, name);
		if (hasName > 0) {
			result.put("statuscode", -2);
			result.put("msg", "添加失败，不能添加相同的分类名");
			return result;
		}
		
		GroupVo vo = new GroupVo();
		vo.setVendorId(vendorId);
		vo.setRowName(name);
		vo.setProductNum(num);
		vo.setRowPid(pid);
		vo.setType(type);
		
		int temp = productService.addGroup(vo);
		
		if (temp == 1) {
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else {
			result.put("statuscode", -1);
			result.put("msg", "添加失败");
		}
		
		return result;
	}
	
	/**
	 * 上传图片
	 * @param vendorId
	 * @param rowId
	 * @param productImg
	 * @return
	 */
	@RequestMapping("/ADD/product/uploadImage.do")
	@ResponseBody
	public JSONObject uploadImage(Integer vendorId,Integer rowId,MultipartFile productImg,Integer imgType) {
		JSONObject result = new JSONObject();
		
		ImageVo vo = new ImageVo();
		
		//判断是否有图片
		if (productImg != null && productImg.getSize() > 0) {
			JSONObject imageResult = productService.uploadImageV1(productImg, result);
 			if ((Integer)imageResult.get("uploadState") > 0) {
 				vo.setImgType(imgType.toString());
 				vo.setVendorId(vendorId);
 				vo.setImgName(imageResult.getString("fileName").toString());
 				vo.setImgUrl(imageResult.getString("imgUrl").toString());
 				vo.setImgHeight(imageResult.getInteger("height"));
 				vo.setImgWidth(imageResult.getInteger("width"));

 				int temp = productService.addImageInfo(vo);
 				if (temp > 0) {
 					result.put("id", vo.getId());
 		        	result.put("statuscode", 1);
 					result.put("msg", "成功");
 		        } else {
 		        	result.put("statuscode", -2);
 					result.put("msg", "上传失败");
 		        }
 				
			} else {
				result.put("statuscode", -2);
				result.put("msg", "上传失败");
				return imageResult;
			}
		}
		
		return result;
	}
	
	/**
	 * 查询该vendorId下 上传的所有图片
	 * @param vendorId
	 * @param keyword
	 * @return
	 */
	@RequestMapping("/GET/product/Image.do")
	@ResponseBody
	public PageBean getImageByVendorId(PageBean pageBean,Integer vendorId,String keyword,String imgType) {
		ImageVo vo = new ImageVo();
		vo.setVendorId(vendorId);
		vo.setKeyword(keyword);
		vo.setImgType(imgType);
		return productService.getImageInfo(pageBean, vo);
	}
	
	/**
	 * 删除图片
	 * @param vendorId
	 * @param imageId
	 * @param productImg
	 * @return
	 */
	@RequestMapping("/DELETE/product/Image.do")
	@ResponseBody
	public JSONObject deleteImage(Integer id) {
		JSONObject result = new JSONObject();
		int temp = productService.deleteImage(id);
		if (temp > 0) {
        	result.put("statuscode", 1);
			result.put("msg", "成功");
        } else {
        	result.put("statuscode", -2);
			result.put("msg", "失败");
        }
		return result;
	}
	
	/**
	 * 添加产品
	 * @param vendorId
	 * @param rowId
	 * @param productName
	 * @param productImg
	 * @param productPrice
	 * @param productMarketPrice
	 * @param productTotal
	 * @param productCode
	 * @param productUnit
	 * @param productBarCode
	 * @param paramKey []
	 * @param paramValue []
	 * @param productBewrite
	 * @param productDetail
	 * @param productType
	 * @param productSpe
	 * @return
	 */
	@RequestMapping("/ADD/product/info.do")
	@ResponseBody
	public JSONObject addProduct(
			Integer vendorId,
			Integer rowId,
			String productName,
			Integer productImg,
			Float productPrice,
			Float productMarketPrice,
			Integer productTotal,
			String productCode,
			String productUnit,
			String productBarCode,
			@RequestParam(value = "paramKey[]", required = false)String [] paramKey,
			@RequestParam(value = "paramValue[]", required = false)String [] paramValue,
			String productBewrite,
			String productDetail,
			String productType,
			String productSpe
			/*,String mallTitle,		//商城商品标题
			@RequestParam(value = "itemId[]", required = false) Integer [] itemId,//字典项id
			@RequestParam(value = "itemId[]", required = false) Integer [] itemId,//字典项id
			@RequestParam(value = "itemId[]", required = false) Integer [] itemId,//字典项id
*/			
			/*Integer mallRule,		//商品规格 
			Integer mallNum,		//全网销量
			Float mallWeight,		//商品重量
			Integer mallPoint,		//赠送积分
			Integer mallSort,		//排序
			Integer isMall			//是否上架 0未上架 1已上架
*/			){
		
		JSONObject result = new JSONObject();
		
		int productCount = productService.hasProductName(vendorId,productName);
		if (productCount > 0) {
			result.put("statuscode", -1);
			result.put("msg", "产品名称已经存在，不能重复新增同样的产品名称");
			return result;
		}
		
		DictionaryVo dict = dictionaryService.getDictionaryByItemName(productType);
		
		
		Product product = new Product();
		
		product.setVendorId(vendorId);
		product.setRowId(rowId);
		product.setProductName(productName);
		product.setProductImgId(productImg);
		product.setProductPrice(productPrice);
		product.setProductMarketPrice(productMarketPrice);
		product.setProductTotal(productTotal);
		product.setProductCode(productCode);
		product.setProductUnit(productUnit);
		product.setProductBarCode(productBarCode);
		product.setProductBewrite(productBewrite);
		product.setProductDetail(productDetail);
		product.setProductType(dict.getItemId());
		product.setProductSpe(productSpe);
		
		/*product.setMallTitle(mallTitle);
		product.setMallNum(mallNum);
		product.setMallWeight(mallWeight);
		product.setMallPoint(mallPoint);
		product.setMallSort(mallSort);
		product.setIsMall(isMall);*/
		
		int addResult = productService.addProduct(product);
		if (addResult > 0) {
			
			if (paramKey != null && paramValue != null) {
				for (int i=0 ; i<paramKey.length ; i++) {
					ProductParams params = new ProductParams();
					Integer id = product.getId();
					params.setProductId(id);
					params.setParamKey(paramKey[i]);
					params.setParamValue(paramValue[i]);
					
					int temp = productService.addParams(params);
					if (temp == 0) {
						result.put("statuscode", -2);
						result.put("msg", "新增失败");
					}
				}
			}
			
        	result.put("statuscode", 1);
			result.put("msg", "成功");
        } else {
        	result.put("statuscode", -2);
			result.put("msg", "新增失败");
        }
		
		return result;
	}
	
	/**
	 * 根据产品id查询产品信息
	 * @param productId
	 * @return
	 */
	@RequestMapping("/GET/product/infoById.do")
	@ResponseBody
	public JSONObject getProductById(Integer productId) {
		return productService.getProductById(productId);
	}
	
	/**
	 * 修改产品
	 * @param id
	 * @param vendorId
	 * @param rowId
	 * @param productName
	 * @param productImg
	 * @param productPrice
	 * @param productMarketPrice
	 * @param productTotal
	 * @param productCode
	 * @param productUnit
	 * @param productBarCode
	 * @param paramId
	 * @param paramKey
	 * @param paramValue
	 * @param productBewrite
	 * @param productDetail
	 * @param productType
	 * @param productSpe
	 * @param deleteFlag
	 * @return
	 */
	@RequestMapping("/UPDATE/product/info.do")
	@ResponseBody
	public JSONObject updateProduct(
			Integer id,
			Integer vendorId,
			Integer rowId,
			String productName,
			String productImg,
			Float productPrice,
			Float productMarketPrice,
			Integer productTotal,
			String productCode,
			String productUnit,
			String productBarCode,
			/*@RequestParam(value = "paramId[]", required = false)Integer [] paramId,
			@RequestParam(value = "paramKey[]", required = false)String [] paramKey,
			@RequestParam(value = "paramValue[]", required = false)String [] paramValue,*/
			String productBewrite,
			String productDetail,
			String productType,
			String productSpe,
			Integer deleteFlag
			/*,String mallTitle,
			Integer mallRule,		//商品规格 
			Integer mallNum,		//全网销量
			Float mallWeight,		//商品重量
			Integer mallPoint,		//赠送积分
			Integer mallSort,		//排序
			Integer isMall			//是否上架 0未上架 1已上架
*/			) {
		
		JSONObject result = new JSONObject();
		
		if (productName != null) {
			int productCount = productService.hasProductName(vendorId,productName);
			if (productCount > 0) {
				result.put("statuscode", -1);
				result.put("msg", "产品名称已经存在，不能重复新增同样的产品名称");
				return result;
			}
		}
		
		Product product = new Product();

		if (productType != null) {
			DictionaryVo dict = dictionaryService.getDictionaryByItemName(productType);
			product.setProductType(dict.getItemId());
		}
		
		product.setId(id);
		product.setVendorId(vendorId);
		product.setRowId(rowId);
		product.setProductName(productName);
		product.setProductImg(productImg);
		product.setProductPrice(productPrice);
		product.setProductMarketPrice(productMarketPrice);
		product.setProductTotal(productTotal);
		product.setProductCode(productCode);
		product.setProductUnit(productUnit);
		product.setProductBarCode(productBarCode);
		product.setProductBewrite(productBewrite);
		product.setProductDetail(productDetail);
		product.setProductSpe(productSpe);
		product.setDeleteFlag(deleteFlag);
		product.setLastUpdateTime(new Date());
		
		/*product.setMallTitle(mallTitle);
		product.setMallNum(mallNum);
		product.setMallWeight(mallWeight);
		product.setMallPoint(mallPoint);
		product.setMallSort(mallSort);
		product.setIsMall(isMall);*/
		
		int addResult = productService.updateProduct(product);
		if (addResult > 0) {
			/*if (paramId != null && paramKey != null && paramValue != null) {

				for (int i=0 ; i<paramId.length ; i++) {
				
					ProductParams params = new ProductParams();
					params.setId(paramId[i]);
					params.setParamKey(paramKey[i]);
					params.setParamValue(paramValue[i]);
					
					int temp = productService.updateProductParams(params);
					if (temp < 0) {
						result.put("statuscode", -2);
						result.put("msg", "修改失败");
						return result;
					}
				}
				
				
			}*/
        	result.put("statuscode", 1);
			result.put("msg", "成功");
        } else {
        	result.put("statuscode", -2);
			result.put("msg", "修改失败");
        }
		return result;
	}
	
	/**
	 * 批量删除到回收站
	 * @return
	 */
	@RequestMapping("/DELETE/product/batch.do")
	@ResponseBody
	public JSONObject deleteBatchProduct(
			@RequestParam(value = "id[]", required = false)Integer [] id,Integer deleteFlag) {
		JSONObject result = new JSONObject();
		
		if (id != null) {
			for (Integer ids : id) {
				Product product = new Product();
				product.setId(ids);
				product.setDeleteFlag(deleteFlag);
				product.setLastUpdateTime(new Date());
				int temp = productService.updateProduct(product);
				if (temp < 0) {
					result.put("statuscode", -2);
					result.put("msg", "失败");
					return result;
				}
				
			}
			result.put("statuscode", 1);
			result.put("msg", "成功");
			return result;
		}
		return result;
	}
	
	/**
	 * 删除产品
	 * @param id
	 * @return
	 */
	@RequestMapping("/DELETE/product/info.do")
	@ResponseBody
	public JSONObject deleteProduct(@RequestParam(value = "id[]") Integer[] id) {
		JSONObject result = new JSONObject();
		int delResult = productService.deleteProduct(id);
		if (delResult == 1) {
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else {
			result.put("statuscode", -1);
			result.put("msg", "删除失败");
		}
		return result;
	}
	
	/**
	 * 分页查询产品信息
	 * @param page
	 * @param vendorId 厂商id
	 * @param keyword 查询关键字
	 * @return
	 */
	@RequestMapping("/GET/product/info.do")
	@ResponseBody
	public PageBean getProduct(PageBean page,Integer vendorId,String keyword,Integer deleteFlag) {
		ProductVo vo = new ProductVo();
		vo.setVendorId(vendorId);
		vo.setKeyword(keyword);
		vo.setDeleteFlag(deleteFlag);
		
		return productService.getProduct(page,vo);
	}
	
	/**
	 * 查询礼品分组
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/product/presentGroup.do")
	@ResponseBody
	public JSONObject getpresentGroup(Integer vendorId) {
		JSONObject result = new JSONObject();
		JSONArray array =  productService.getPresentGroup(vendorId);
		result.put("data", array);
		return result;
	}
	
	/**
	 * 添加礼品分类
	 * @param name
	 * @return
	 */
	@RequestMapping("/ADD/product/presentGroup.do")
	@ResponseBody
	public JSONObject addPresentGroup(Integer vendorId,String name) {
		JSONObject result = new JSONObject();
		
		int hasName = productService.hasPresentGroup(vendorId,name);
		if (hasName > 0) {
			result.put("statuscode", -2);
			result.put("msg", "添加失败，不能添加重复的分组名");
			return result;
		}
		
		GroupVo vo = new GroupVo();
		vo.setVendorId(vendorId);
		vo.setRowName(name);
		int temp = productService.addPresentGroup(vo);
		if (temp == 1) {
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else {
			result.put("statuscode", -1);
			result.put("msg", "失败");
		}
		return result;
	}

	/**
	 * 修改礼品分类
	 * @param vendorId
	 * @param name
	 * @return
	 */
	@RequestMapping("/UPDATE/product/presentGroup.do")
	@ResponseBody
	public JSONObject updatePresentGroup(Integer id,Integer vendorId,String name) {
		JSONObject result = new JSONObject();
		
		int hasName = productService.hasPresentGroup(vendorId,name);
		if (hasName > 0) {
			result.put("statuscode", -2);
			result.put("msg", "添加失败，不能添加重复的分组名");
			return result;
		}
		
		GroupVo vo = new GroupVo();
		vo.setRowId(id);
		vo.setRowName(name);
		int temp = productService.updatePresentGroup(vo);
		if (temp == 1) {
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else {
			result.put("statuscode", -1);
			result.put("msg", "失败");
		}
		return result;
	}

	/**
	 * 删除礼品分类
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/DELETE/product/presentGroup.do")
	@ResponseBody
	public JSONObject deletePresentGroup(Integer id) {
		JSONObject result = new JSONObject();
		int temp = productService.deletePresentGroup(id);
		if (temp == 1) {
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else {
			result.put("statuscode", -1);
			result.put("msg", "失败");
		}
		return result;
	}
	
	/**
	 * 分页查询礼品库
	 * @param pageBean 分页bean
	 * @param vendorId 厂商id
	 * @param keyword 搜索关键字
	 * @param deleteFlag 删除标识
	 * @return
	 */
	@RequestMapping("/GET/product/present.do")
	@ResponseBody
	public PageBean getPresent(PageBean pageBean,Integer vendorId,String keyword,Integer deleteFlag) {
		ProductVo vo = new ProductVo();
		vo.setVendorId(vendorId);
		vo.setKeyword(keyword);
		vo.setDeleteFlag(deleteFlag);
		return productService.getPresent(pageBean,vo);
	}
	
	/**
	 * 根据礼品id查询礼品信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/GET/product/presentById.do")
	@ResponseBody
	public JSONObject getPresentById(Integer id) {
		return productService.getPresentById(id);
	}
	
	/**
	 * 修改礼品库
	 * @param id
	 * @param name
	 * @param rowId
	 * @param price
	 * @param img
	 * @param weight
	 * @param total
	 * @param code
	 * @param detail
	 * @return
	 */
	@RequestMapping("/UPDATE/product/present.do")
	@ResponseBody
	public JSONObject updatePresent(Integer id,String name,Integer presentSpe,
			Integer rowId,Float price,String img,
			Float weight,Integer total,String code,String detail) {
		JSONObject result = new JSONObject();
		
		ProductVo vo = new ProductVo();
		
		Date date=new Date();
		vo.setId(id);
		vo.setProductImg(img);
		vo.setProductName(name);
		vo.setRowId(rowId);
		vo.setProductPrice(price);
		vo.setLastUpdateTime(date);
		vo.setWeight(weight);
		vo.setProductTotal(total);
		vo.setProductCode(code);
		vo.setProductDetail(detail);
		vo.setProductSpe(presentSpe.toString());
		
		int temp = productService.updatePresent(vo);
		if (temp > 0) {
        	result.put("statuscode", 1);
			result.put("msg", "成功");
        } else {
        	result.put("statuscode", -2);
			result.put("msg", "新增失败");
        }
		return result;
	}
	
	/**
	 * 添加礼品库
	 * @param vendorId
	 * @param name
	 * @param rowId
	 * @param price
	 * @param img
	 * @param weight
	 * @param total
	 * @param code
	 * @param detail
	 * @return
	 */
	@RequestMapping("/ADD/product/present.do")
	@ResponseBody
	public JSONObject addPresent(Integer vendorId,String name,Integer presentSpe,
			Integer rowId,Float price,String img,
			Float weight,Integer total,String code,String detail) {
		JSONObject result = new JSONObject();
		ProductVo vo = new ProductVo();
		
		vo.setProductImg(img);
		vo.setVendorId(vendorId);
		vo.setProductName(name);
		vo.setRowId(rowId);
		vo.setProductPrice(price);
		
		vo.setWeight(weight);
		vo.setProductTotal(total);
		vo.setProductCode(code);
		vo.setProductDetail(detail);
		vo.setProductSpe(presentSpe.toString());
		
		int result1 = productService.hasPresent(vo);
		if (result1 > 0) {
			result.put("statuscode", -1);
			result.put("msg", "添加失败，不能重复添加相同的礼品名");
			return result;
		}
		
		int temp = productService.addPresent(vo);
		if (temp > 0) {
        	result.put("statuscode", 1);
			result.put("msg", "成功");
        } else {
        	result.put("statuscode", -2);
			result.put("msg", "新增失败");
        }
		return result;
	}
	
	/**
	 * 批量删除到回收站
	 * @param id
	 * @return
	 */
	@RequestMapping("/DELETE/product/batchPresent.do")
	@ResponseBody
	public JSONObject batchDeletePresent(@RequestParam(value = "id[]") Integer [] id,Integer deleteFlag) {
		JSONObject result = new JSONObject();
		for (Integer ids : id) {
			ProductVo vo = new ProductVo();
			vo.setId(ids);
			vo.setDeleteFlag(deleteFlag);
			int temp = productService.updatePresent(vo);
			if (temp == 0) {
				result.put("statuscode", -1);
				result.put("msg", "失败");
				return result;
			}
		}
		
		result.put("statuscode", 1);
		result.put("msg", "成功");
		
		return result;
	}
	
	/**
	 * 删除礼品库
	 * @param id
	 * @return
	 */
	@RequestMapping("/DELETE/product/present.do")
	@ResponseBody
	public JSONObject deletePresent(@RequestParam(value = "id[]") Integer [] id) {
		JSONObject result = new JSONObject();
		for (Integer ids : id) {
			int temp = productService.deletePresent(ids);
			if (temp != 1) {
				result.put("statuscode", -1);
				result.put("msg", "失败");
				return result;
			}
		}
		
		result.put("statuscode", 1);
		result.put("msg", "成功");

		return result;
	}
	
	/**
	 * 删除产品参数
	 * @param paramId
	 * @return
	 */
	@RequestMapping("/DELETE/product/param.do")
	@ResponseBody
	public JSONObject deleteProductParam(Integer paramId) {
		JSONObject result = new JSONObject();
		int temp = productService.deleteProductParam(paramId);
		if (temp > 0) {
			result.put("statuscode", 1);
			result.put("msg", "成功");
		} else {
			result.put("statuscode", -1);
			result.put("msg", "失败");
		}
		return result;
	}
	
	/**
	 * 添加产品参数
	 * @param paramKey
	 * @param paramValue
	 * @return
	 */
	@RequestMapping("/ADD/product/param.do")
	@ResponseBody
	public JSONObject addProductParam(
			Integer productId,
			String paramKey,
			String paramValue) {
		
		JSONObject result = new JSONObject();
		
			ProductParams params = new ProductParams();
			params.setProductId(productId);
			params.setParamKey(paramKey);
			params.setParamValue(paramValue);
			
			int temp = productService.addParams(params);
			if (temp > 0) {
				result.put("id", params.getId());
				result.put("statuscode", 1);
				result.put("msg", "成功");
			} else {
				result.put("statuscode", -2);
				result.put("msg", "新增失败");
			}
		
		return result;
	}
	
	
	public static void main(String[] args){
		List<Integer> list = new ArrayList<Integer>();
		list.add(6);
		list.add(5);
		list.add(7);
		list.add(3);
		list.add(8);
		
		Collections.shuffle(list);
		
		Set<Integer> set = new HashSet<Integer>(list);
		list.clear();
		list.addAll(set);
		
		for (Integer integer : list) {
			System.out.print(integer + " ");
		}
	}
	
	public String test1(String redirectUrl, OauthScope scope, String state) {
		 BeanUtil.requireNonNull(redirectUrl, "redirectUrl is null");
	        BeanUtil.requireNonNull(scope, "scope is null");
	        String userState = StrUtil.isBlank(state) ? "STATE" : state;
	        String url = null;
	        try {
	            url = URLEncoder.encode(redirectUrl, "UTF-8");
	        } catch (UnsupportedEncodingException e) {
	            //LOG.error("异常", e);
	        }
	        StringBuilder stringBuilder = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?");
	        stringBuilder.append("appid=").append("wx4e8cf1716ea239ef")
	                .append("&redirect_uri=").append(url)
	                .append("&response_type=code&scope=").append(scope.toString())
	                .append("&state=")
	                .append(userState)
	                .append("#wechat_redirect");
	        return stringBuilder.toString();
	}
	
	
	/**
	 * 获取已上传产品的数量
	 * @param vendorId
	 * @return
	 */
	@RequestMapping("/GET/product/getProCountByVendorId.do")
	@ResponseBody
	public JSONObject getProCountByVendorId(Integer vendorId){
		JSONObject result=new JSONObject();
		//获取已上传产品的数量
		int countPro=productService.findProductCountByVendorId(vendorId);
		// 查询未分类的产品数
		int count = productService.getNotFenleiPro(vendorId);
		// 查询已上传礼品的数量
		int countPresent = productService.getUploadPresentCount(vendorId);
		if (countPro==0) {
			result.put("countPro", countPro);
			result.put("countPromsg", "当前无上传的商品");
		}else if(countPro>0){
			result.put("countPro", countPro);
			result.put("countPromsg", "成功");
		}
		if (count==0) {
			result.put("count", count);
			result.put("countmsg", "当前无未分类的产品");
		}else if(count>0){
			result.put("count", count);
			result.put("countmsg", "成功");
		}
		if (countPresent==0) {
			result.put("countPresent", countPresent);
			result.put("countPresentmsg", "当前无未上传的礼品");
		}else if(countPresent>0){
			result.put("countPresent", countPresent);
			result.put("countPresentmsg", "成功");
		}
		return result;
	}
	
	
	
	
	
}
