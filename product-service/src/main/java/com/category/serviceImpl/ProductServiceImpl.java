package com.category.serviceImpl;

import com.category.DTO.*;
import com.category.entity.CartEntity;
import com.category.entity.CategoryEntity;
import com.category.entity.ProductEntity;
import com.category.repository.ProductRepo;
import com.category.service.AuthenticationService;
import com.category.service.ProductService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public Map<String, Object> getProductById(Long pId, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            Optional<ProductEntity> existUser = productRepo.findById(pId);
            if (existUser.isPresent()) {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
                map.put(ResponseMessage.RESPONSE_DATA, existUser);
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_PRESENT);
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object>save(ProductDTO productDTO, String token) {
        Map<String, Object> map = new HashMap<>();
        ProductEntity saveProduct = new ProductEntity();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        System.out.println("authresponse-----" + authReponse);
        if (authReponse.getStatus()) {
            if (productDTO != null) {
                saveProduct.setCreatedAt(LocalDateTime.now());
                saveProduct.setIsActive(Boolean.TRUE);
                saveProduct.setName(productDTO.getName());
                saveProduct.setPrice(productDTO.getPrice());
                saveProduct.setDiscountPrice(productDTO.getDiscountPrice());
                saveProduct.setCategoryId(productDTO.getCategoryId());
                saveProduct.setQuantity(productDTO.getQuantity());
                saveProduct.setSpecification(productDTO.getSpecification());
                saveProduct.setUserId(productDTO.getUserId());
                productRepo.save(saveProduct);
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_SAVED_SUCCESSFULLY);
                map.put(ResponseMessage.RESPONSE_DATA, saveProduct);
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_SAVED);
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }


    @Override
    public Map<String, Object> updateProductById(Long pId, ProductDTO productDTO, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            if (productDTO != null) {
                ProductEntity existProduct = productRepo.findById(pId).get();
                if (existProduct.getPId() != null) {
                    existProduct.setName(productDTO.getName());
                    existProduct.setSpecification(productDTO.getSpecification());
                    existProduct.setQuantity(productDTO.getQuantity());
                    existProduct.setPrice(productDTO.getPrice());
                    existProduct.setDiscountPrice(productDTO.getDiscountPrice());
                    existProduct.setCategoryId(productDTO.getCategoryId());
                    productRepo.save(existProduct);
                    map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                    map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_SAVED_SUCCESSFULLY);
                    map.put(ResponseMessage.RESPONSE_DATA, existProduct);
                } else {
                    map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                    map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_UPDATED);
                    map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
                }
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_UPDATED);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());

        }
        return map;
    }

    @Override
    public Map<String, Object> deleteProductById(Long pId, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            Optional<ProductEntity> existUser = productRepo.findById(pId);
            if (existUser.isPresent()) {
                productRepo.deleteById(pId);
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_DELETE_SUCCESSFULLY);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_NOT_DELETED);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> uploadProductImage(Long pId, MultipartFile multipartFile, String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            ProductEntity productImg = productRepo.findById(pId).get();
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", ResponseMessage.CLOUD_NAME,
                    "api_key", ResponseMessage.API_KEY, "api_secret", ResponseMessage.API_SECRET));
            System.out.println("image sent successfully");
            try {
                Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                        ObjectUtils.asMap("public_id", "product" + pId));
                String url = uploadResult.get("url").toString();
                productImg.setImg(url);
                productImg.setUpdatedAt(LocalDateTime.now());
                productRepo.save(productImg);
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                map.put(ResponseMessage.RESPONSE_DATA, url);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.PRODUCT_UPLOADED_SUCESSFULLY);
            } catch (Exception e) {
                e.printStackTrace();
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.PRODUCT_NOT_UPLOADED);
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> search(String Text, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            if (!Text.isEmpty()) {
                List<ProductEntity> searchdata = productRepo.search(Text);
                if (!searchdata.isEmpty()) {
                    map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
                    map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.SUCCESS_SEARCH);
                    map.put(ResponseMessage.RESPONSE_DATA, searchdata);
                } else {
                    map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                    map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.FAIL_SEARCH);
                    map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
                }
            } else {
                map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_400);
                map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.FAIL_SEARCH_TEXT_NOT);
                map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
            }
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

    @Override
    public Map<String, Object> getproductList(ProductListDTO productListDTO, String token) {
        Map<String, Object> map = new HashMap<>();
        ResponseDTO authReponse = authenticationService.isAuthenticated(token);
        if (authReponse.getStatus()) {
            Boolean status = Boolean.valueOf(productListDTO.getWhere().get("isActive").toString());
            Long categoryId = Long.valueOf(productListDTO.getWhere().get("categoryId").toString());
            Integer page = Integer.valueOf(productListDTO.getPagination().get("page").toString());
            Integer rowPerPage = Integer.valueOf(productListDTO.getPagination().get("rowsPerPage").toString());
            Page<ProductEntity> productEntities = null;
            Pageable pageable = PageRequest.of(page, rowPerPage);
            List<ProductEntity> cartEntityList = new ArrayList<>();
            productEntities = productRepo.findStatusAndUserId(status, categoryId, pageable);
            for (ProductEntity cartEntites : productEntities) {
                cartEntityList.add(cartEntites);
            }
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_200);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.DATA_FIND_SUCCESSFULLY);
            map.put(ResponseMessage.RESPONSE_DATA, cartEntityList);
        } else {
            map.put(ResponseMessage.RESPONSE_STATUS, ResponseMessage.STATUS_401);
            map.put(ResponseMessage.RESPONSE_MESSAGE, ResponseMessage.AUTHENTICATION_FAIL);
            map.put(ResponseMessage.RESPONSE_DATA, new ArrayList<>());
        }
        return map;
    }

}
