package com.model2.mvc.service.product.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;

@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public ProductDaoImpl() {
	}

	@Override
	public void insertProduct(Product product) throws SQLException {
		sqlSession.insert("ProductMapper.addProduct",product);
	}

	@Override
	public void updateProduct(Product product) throws SQLException {
		sqlSession.update("ProductMapper.updateProduct",product);

	}

	@Override
	public Product findProduct(int prodNo) throws SQLException {
		return sqlSession.selectOne("ProductMapper.findProduct",prodNo);
	}
	
	@Override
	public void likeProduct(String userId, String prodNo) throws SQLException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("prodNo", prodNo);
		sqlSession.selectOne("ProductMapper.likeProduct", map);
	}
	
	public boolean checkLike(String userId, String prodNo) throws SQLException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("prodNo", prodNo);
		int check = Integer.parseInt(sqlSession.selectOne("ProductMapper.checkLike", map));
		if (check > 0) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Map<String, Object> listProduct(Search search) throws SQLException {
		List<Product> list = sqlSession.selectList("ProductMapper.listProduct",search);
		Map<String , Object>  map = new HashMap<String, Object>();
		System.out.println("찾은 리스트의 정보 : "+list);
		map.put("list", list);
		int total = Integer.parseInt(sqlSession.selectOne("ProductMapper.count", search));
		map.put("totalCount",total);
		return map;
	}


}
