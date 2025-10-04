package com.lunchbox.lunchboxdelivery.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubscribeOrderInfo {

	@SerializedName("p_method_name")
	private String pMethodName;

	@SerializedName("o_status")
	private String oStatus;

	@SerializedName("order_complete_date")
	private String orderCompleteDate;

	@SerializedName("address_type")
	private String addressType;

	@SerializedName("cust_address")
	private String custAddress;

	@SerializedName("pack_description")
	private String packDescription;

	@SerializedName("rest_name")
	private String restName;

	@SerializedName("rest_address")
	private String restAddress;

	@SerializedName("rider_name")
	private String riderName;

	@SerializedName("order_total")
	private String orderTotal;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("pack_img")
	private String packImg;

	@SerializedName("pack_title")
	private String packTitle;

	@SerializedName("order_items")
	private List<OrderItemsItem> orderItems;

	public String getPMethodName(){
		return pMethodName;
	}

	public String getOStatus(){
		return oStatus;
	}

	public String getOrderCompleteDate(){
		return orderCompleteDate;
	}

	public String getAddressType(){
		return addressType;
	}

	public String getCustAddress(){
		return custAddress;
	}

	public String getPackDescription(){
		return packDescription;
	}

	public String getRestName(){
		return restName;
	}

	public String getRestAddress(){
		return restAddress;
	}

	public String getRiderName(){
		return riderName;
	}

	public String getOrderTotal(){
		return orderTotal;
	}

	public String getOrderId(){
		return orderId;
	}

	public String getPackImg(){
		return packImg;
	}

	public String getPackTitle(){
		return packTitle;
	}

	public List<OrderItemsItem> getOrderItems(){
		return orderItems;
	}
}