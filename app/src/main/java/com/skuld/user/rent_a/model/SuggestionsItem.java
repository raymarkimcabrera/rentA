package com.skuld.user.rent_a.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SuggestionsItem implements Serializable{

	@SerializedName("address")
	@Expose
	private Address address;

	@SerializedName("matchLevel")
	@Expose
	private String matchLevel;

	@SerializedName("countryCode")
	@Expose
	private String countryCode;

	@SerializedName("locationId")
	@Expose
	private String locationId;

	@SerializedName("language")
	@Expose
	private String language;

	@SerializedName("label")
	@Expose
	private String label;

	public void setAddress(Address address){
		this.address = address;
	}

	public Address getAddress(){
		return address;
	}

	public void setMatchLevel(String matchLevel){
		this.matchLevel = matchLevel;
	}

	public String getMatchLevel(){
		return matchLevel;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setLocationId(String locationId){
		this.locationId = locationId;
	}

	public String getLocationId(){
		return locationId;
	}

	public void setLanguage(String language){
		this.language = language;
	}

	public String getLanguage(){
		return language;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}

	@Override
 	public String toString(){
		return 
			"SuggestionsItem{" + 
			"address = '" + address + '\'' + 
			",matchLevel = '" + matchLevel + '\'' + 
			",countryCode = '" + countryCode + '\'' + 
			",locationId = '" + locationId + '\'' + 
			",language = '" + language + '\'' + 
			",label = '" + label + '\'' + 
			"}";
		}
}
