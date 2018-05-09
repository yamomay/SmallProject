package com.model2.mvc.service.purchase;

import java.sql.SQLException;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {
	
	public void insertPurchase(Purchase pvo) throws SQLException;
	
	public Map<String, Object> getSaleList(Search search) throws SQLException;
	
	public Purchase getPurchase(int tranNo) throws NumberFormatException, Exception;

	public Purchase getPurchase2(int prodNo) throws NumberFormatException, Exception;
	
	public void updateTranCode(Purchase pvo) throws SQLException;
	
	public void updatePurchase(Purchase pvo) throws SQLException;

}
