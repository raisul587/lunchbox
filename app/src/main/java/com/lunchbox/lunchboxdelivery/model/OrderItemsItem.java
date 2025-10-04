package com.lunchbox.lunchboxdelivery.model;

import com.google.gson.annotations.SerializedName;

public class OrderItemsItem {

	@SerializedName("item_id")
	private String itemId;

	@SerializedName("item_date")
	private String itemDate;

	@SerializedName("item_day")
	private String itemDay;

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("status")
	private String status;

	public String getItemDate(){
		return itemDate;
	}

	public String getItemDay(){
		return itemDay;
	}

	public String getCatName(){
		return catName;
	}

	public String getStatus(){
		return status;
	}

	public String getItemId() {
		return itemId;
	}
}