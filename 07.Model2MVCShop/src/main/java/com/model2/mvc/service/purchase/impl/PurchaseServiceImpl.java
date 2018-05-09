package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDaoImpl dao;
	
	public void setPurchaseDao(PurchaseDaoImpl dao) {
		this.dao = dao;
	}
	
	public PurchaseServiceImpl() {
		dao = this.dao;
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		dao.insertPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {

		return dao.getPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int ProdNo) throws Exception {

		return dao.getPurchase2(ProdNo);
	}

	@Override
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		return null;
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		return dao.getSaleList(search);
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		dao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Purchase purchase) throws Exception {
		dao.updateTranCode(purchase);
	}



}
