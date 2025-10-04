package com.lunchbox.lunchboxdelivery.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderSubcribe {

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("SubscribeOrderHistory")
	private List<SubscribeOrderHistoryItem> subscribeOrderHistory;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public List<SubscribeOrderHistoryItem> getSubscribeOrderHistory(){
		return subscribeOrderHistory;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public String getResult(){
		return result;
	}
}