package com.skuld.user.rent_a.model.reverse_geocoder;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Address implements Serializable{

	@SerializedName("AdditionalData")
	private List<AdditionalDataItem> additionalData;

	@SerializedName("HouseNumber")
	private String houseNumber;

	@SerializedName("State")
	private String state;

	@SerializedName("Label")
	private String label;

	@SerializedName("Country")
	private String country;

	@SerializedName("Street")
	private String street;

	@SerializedName("PostalCode")
	private String postalCode;

	@SerializedName("City")
	private String city;

	@SerializedName("County")
	private String county;

	@SerializedName("District")
	private String district;

	public void setAdditionalData(List<AdditionalDataItem> additionalData){
		this.additionalData = additionalData;
	}

	public List<AdditionalDataItem> getAdditionalData(){
		return additionalData;
	}

	public void setHouseNumber(String houseNumber){
		this.houseNumber = houseNumber;
	}

	public String getHouseNumber(){
		return houseNumber;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setLabel(String label){
		this.label = label;
	}

	public String getLabel(){
		return label;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setCounty(String county){
		this.county = county;
	}

	public String getCounty(){
		return county;
	}

	public void setDistrict(String district){
		this.district = district;
	}

	public String getDistrict(){
		return district;
	}

	public String getAddress(){
		return houseNumber + street + city + district;
	}

	@Override
 	public String toString(){
		return 
			"Address{" + 
			"additionalData = '" + additionalData + '\'' + 
			",houseNumber = '" + houseNumber + '\'' + 
			",state = '" + state + '\'' + 
			",label = '" + label + '\'' + 
			",country = '" + country + '\'' + 
			",street = '" + street + '\'' + 
			",postalCode = '" + postalCode + '\'' + 
			",city = '" + city + '\'' + 
			",county = '" + county + '\'' + 
			",district = '" + district + '\'' + 
			"}";
		}
}