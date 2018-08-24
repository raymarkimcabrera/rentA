package com.skuld.user.rent_a.model.reverse_geocoder;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MatchQuality implements Serializable {

	@SerializedName("HouseNumber")
	private double houseNumber;

	@SerializedName("State")
	private double state;

	@SerializedName("Country")
	private double country;

	@SerializedName("Street")
	private List<Double> street;

	@SerializedName("PostalCode")
	private double postalCode;

	@SerializedName("City")
	private double city;

	@SerializedName("County")
	private double county;

	@SerializedName("District")
	private double district;

	public void setHouseNumber(double houseNumber){
		this.houseNumber = houseNumber;
	}

	public double getHouseNumber(){
		return houseNumber;
	}

	public void setState(double state){
		this.state = state;
	}

	public double getState(){
		return state;
	}

	public void setCountry(double country){
		this.country = country;
	}

	public double getCountry(){
		return country;
	}

	public void setStreet(List<Double> street){
		this.street = street;
	}

	public List<Double> getStreet(){
		return street;
	}

	public void setPostalCode(double postalCode){
		this.postalCode = postalCode;
	}

	public double getPostalCode(){
		return postalCode;
	}

	public void setCity(double city){
		this.city = city;
	}

	public double getCity(){
		return city;
	}

	public void setCounty(double county){
		this.county = county;
	}

	public double getCounty(){
		return county;
	}

	public void setDistrict(double district){
		this.district = district;
	}

	public double getDistrict(){
		return district;
	}

	@Override
 	public String toString(){
		return 
			"MatchQuality{" + 
			"houseNumber = '" + houseNumber + '\'' + 
			",state = '" + state + '\'' + 
			",country = '" + country + '\'' + 
			",street = '" + street + '\'' + 
			",postalCode = '" + postalCode + '\'' + 
			",city = '" + city + '\'' + 
			",county = '" + county + '\'' + 
			",district = '" + district + '\'' + 
			"}";
		}
}