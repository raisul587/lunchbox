package com.lunchbox.lunchboxdelivery.model;

import com.google.gson.annotations.SerializedName;

public class OrderSubcribeInfo {

	@SerializedName("ResponseCode")
	private String responseCode;

	@SerializedName("ResponseMsg")
	private String responseMsg;

	@SerializedName("SubscribeOrderInfo")
	private SubscribeOrderInfo subscribeOrderInfo;

	@SerializedName("Result")
	private String result;

	public String getResponseCode(){
		return responseCode;
	}

	public String getResponseMsg(){
		return responseMsg;
	}

	public SubscribeOrderInfo getSubscribeOrderInfo(){
		return subscribeOrderInfo;
	}

	public String getResult(){
		return result;
	}
}