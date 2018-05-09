package com.model2.mvc.service.purchase.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;


@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("sqlSession for PurchaseDaoImpl!");
		this.sqlSession = sqlSession;
	}
	
	public PurchaseDaoImpl() {
		System.out.println("PurchaseDaoImpl 생성!");
	}
	
	public void insertPurchase(Purchase pvo) throws SQLException {		
		sqlSession.selectOne("PurchaseMapper.insertPurchase", pvo);					
	}

	public Map<String, Object> getSaleList(Search search) throws SQLException {
		
		System.out.println("구매 리스트 dao진입");
					
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<Purchase> list = null;
		
		list = sqlSession.selectList("PurchaseMapper.getSaleList", search);
		System.out.println("PurchaseMapper에서 바로 받은 리스트의 내용 "+list.size()+"개 : "+list);
		int count = sqlSession.selectOne("PurchaseMapper.count",search.getSearchKeyword());
		System.out.println("total count : "+count);
		map.put("list", list);
		map.put("count", count);
			
		return map;


		
	}

	public Purchase getPurchase(int tranNo) throws NumberFormatException, Exception {
		
		Purchase pvo = null;
		
		pvo = sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);	
		
		System.out.println("찾은 내용 : "+pvo);
		
		return pvo;
	}
	
	public Purchase getPurchase2(int prodNo) throws NumberFormatException, Exception {
		
		Purchase pvo = null;
		
		pvo = sqlSession.selectOne("PurchaseMapper.getPurchase2", prodNo);	
		
		System.out.println("찾은 내용 : "+pvo);
		
		return pvo;
	}
	
	public void updateTranCode(Purchase pvo) throws SQLException {
		
		System.out.println("updateTranCode 진입");
		
		sqlSession.selectOne("PurchaseMapper.updateTranCode", pvo);
		
		System.out.println("updateTranCode 나옴");
	}
	
	public void updatePurchase(Purchase pvo) throws SQLException {
		
		System.out.println("updatePurchase 진입");
						
		sqlSession.selectOne("PurchaseMapper.updatePurchase", pvo);
		
		System.out.println("updatePurchase 나옴");
		
	}
	
}
