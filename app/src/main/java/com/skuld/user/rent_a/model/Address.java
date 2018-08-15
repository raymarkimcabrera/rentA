package com.skuld.user.rent_a.model;

import com.google.gson.annotations.SerializedName;

public class Address{
	@SerializedName("country")
	private String country;

	@SerializedName("city")
	private String city;

	@SerializedName("street")
	private String street;

	@SerializedName("district")
	private String district;

	@SerializedName("postalCode")
	private String postalCode;

	@SerializedName("county")
	private String county;

	@SerializedName("state")
	private String state;

	@SerializedName("houseNumber")
	private String houseNumber;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return street;
	}

	public void setDistrict(String district){
		this.district = district;
	}

	public String getDistrict(){
		return district;
	}

	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public void setCounty(String county){
		this.county = county;
	}

	public String getCounty(){
		return county;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setHouseNumber(String houseNumber){
		this.houseNumber = houseNumber;
	}

	public String getHouseNumber(){
		return houseNumber;
	}

	@Override
 	public String toString(){
		return 
			"Address{" + 
			"country = '" + country + '\'' + 
			",city = '" + city + '\'' + 
			",street = '" + street + '\'' + 
			",district = '" + district + '\'' + 
			",postalCode = '" + postalCode + '\'' + 
			",county = '" + county + '\'' + 
			",state = '" + state + '\'' + 
			",houseNumber = '" + houseNumber + '\'' + 
			"}";
		}
}
