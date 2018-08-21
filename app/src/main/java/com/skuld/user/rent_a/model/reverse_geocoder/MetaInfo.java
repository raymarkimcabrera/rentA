package com.skuld.user.rent_a.model.reverse_geocoder;

import com.google.gson.annotations.SerializedName;

public class MetaInfo{

	@SerializedName("NextPageInformation")
	private String nextPageInformation;

	@SerializedName("Timestamp")
	private String timestamp;

	public void setNextPageInformation(String nextPageInformation){
		this.nextPageInformation = nextPageInformation;
	}

	public String getNextPageInformation(){
		return nextPageInformation;
	}

	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
	}

	public String getTimestamp(){
		return timestamp;
	}

	@Override
 	public String toString(){
		return 
			"MetaInfo{" + 
			"nextPageInformation = '" + nextPageInformation + '\'' + 
			",timestamp = '" + timestamp + '\'' + 
			"}";
		}
}