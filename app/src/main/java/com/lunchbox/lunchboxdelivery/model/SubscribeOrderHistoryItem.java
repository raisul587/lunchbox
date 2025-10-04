package com.lunchbox.lunchboxdelivery.model;

import com.google.gson.annotations.SerializedName;

public class SubscribeOrderHistoryItem {

	@SerializedName("rest_img")
	private String restImg;

	@SerializedName("o_status")
	private String oStatus;

	@SerializedName("order_complete_date")
	private String orderCompleteDate;

	@SerializedName("rest_sdesc")
	private String restSdesc;

	@SerializedName("order_total")
	private String orderTotal;

	@SerializedName("rest_name")
	private String restName;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("total_order_item")
	private int totalOrderItem;

	@SerializedName("rest_landmark")
	private String restLandmark;

	public String getRestImg(){
		return restImg;
	}

	public String getOStatus(){
		return oStatus;
	}

	public String getOrderCompleteDate(){
		return orderCompleteDate;
	}

	public String getRestSdesc(){
		return restSdesc;
	}

	public String getOrderTotal(){
		return orderTotal;
	}

	public String getRestName(){
		return restName;
	}

	public String getOrderId(){
		return orderId;
	}

	public int getTotalOrderItem(){
		return totalOrderItem;
	}

	public String getRestLandmark(){
		return restLandmark;
	}
}