package com.model2.mvc.service.product.impl;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao pda;
	
	public void setProductDao(ProductDao pda) {
		this.pda = pda;
	}

	public ProductServiceImpl() {
		pda = this.pda;
	}

	@Override
	public void addProduct(Product product) throws Exception {
		pda.insertProduct(product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		Product prodvo = pda.findProduct(prodNo);
		return prodvo;
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		Map<String, Object> map = null;
		map = pda.listProduct(search);
		return map;
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		pda.updateProduct(product);
	}

	@Override
	public void likeProduct(String userId,String prodNo) throws Exception {
		pda.likeProduct(userId,prodNo);
	}

	@Override
	public boolean chekLike(String userId, String prodNo) throws SQLException {
		return pda.checkLike(userId, prodNo);
	}


}
