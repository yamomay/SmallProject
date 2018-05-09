package com.model2.mvc.service.domain;

public class Coupon {
	
	private String couponName;
	private String couponDesc;
	
	public Coupon() {
		
	}
	
	public String getCouponDesc() {
		return couponDesc;
	}
	
	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}
	
	public String getCouponName() {
		return couponName;
	}
	
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	public String toString() {
		return "CouponName : "+couponName+", CouponDesc : "+couponDesc;
	}

}
