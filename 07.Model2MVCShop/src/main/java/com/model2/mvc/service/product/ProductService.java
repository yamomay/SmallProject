package com.model2.mvc.service.product;

import java.sql.SQLException;
import java.util.*;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;


public interface ProductService {
	
	public void addProduct(Product productVO) throws Exception;

	public Product getProduct(int prodNo) throws Exception;

	public Map<String,Object> getProductList(Search searchVO) throws Exception;

	public void updateProduct(Product productVO) throws Exception;
	
	public void likeProduct(String userId,String prodNo) throws Exception;

	public boolean chekLike(String userId, String prodNo) throws SQLException;
	
}