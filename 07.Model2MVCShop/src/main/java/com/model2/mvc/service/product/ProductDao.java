package com.model2.mvc.service.product;

import java.sql.SQLException;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

public interface ProductDao {
	
	public void insertProduct(Product product) throws SQLException;
	
	public void updateProduct(Product product) throws SQLException;
	
	public Product findProduct(int prodNo) throws SQLException;
	
	public void likeProduct(String userId,String prodNo) throws SQLException;
	
	boolean checkLike(String userId, String prodNo) throws SQLException;
	
	public Map<String, Object> listProduct(Search search) throws SQLException;

	

}
