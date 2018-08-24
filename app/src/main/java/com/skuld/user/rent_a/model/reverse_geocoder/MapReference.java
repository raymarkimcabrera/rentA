package com.skuld.user.rent_a.model.reverse_geocoder;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MapReference implements Serializable {

	@SerializedName("CountryId")
	private String countryId;

	@SerializedName("ReferenceId")
	private String referenceId;

	@SerializedName("CityId")
	private String cityId;

	@SerializedName("Spot")
	private double spot;

	@SerializedName("StateId")
	private String stateId;

	@SerializedName("SideOfStreet")
	private String sideOfStreet;

	@SerializedName("CountyId")
	private String countyId;

	@SerializedName("BuildingId")
	private String buildingId;

	@SerializedName("AddressId")
	private String addressId;

	public void setCountryId(String countryId){
		this.countryId = countryId;
	}

	public String getCountryId(){
		return countryId;
	}

	public void setReferenceId(String referenceId){
		this.referenceId = referenceId;
	}

	public String getReferenceId(){
		return referenceId;
	}

	public void setCityId(String cityId){
		this.cityId = cityId;
	}

	public String getCityId(){
		return cityId;
	}

	public void setSpot(double spot){
		this.spot = spot;
	}

	public double getSpot(){
		return spot;
	}

	public void setStateId(String stateId){
		this.stateId = stateId;
	}

	public String getStateId(){
		return stateId;
	}

	public void setSideOfStreet(String sideOfStreet){
		this.sideOfStreet = sideOfStreet;
	}

	public String getSideOfStreet(){
		return sideOfStreet;
	}

	public void setCountyId(String countyId){
		this.countyId = countyId;
	}

	public String getCountyId(){
		return countyId;
	}

	public void setBuildingId(String buildingId){
		this.buildingId = buildingId;
	}

	public String getBuildingId(){
		return buildingId;
	}

	public void setAddressId(String addressId){
		this.addressId = addressId;
	}

	public String getAddressId(){
		return addressId;
	}

	@Override
 	public String toString(){
		return 
			"MapReference{" + 
			"countryId = '" + countryId + '\'' + 
			",referenceId = '" + referenceId + '\'' + 
			",cityId = '" + cityId + '\'' + 
			",spot = '" + spot + '\'' + 
			",stateId = '" + stateId + '\'' + 
			",sideOfStreet = '" + sideOfStreet + '\'' + 
			",countyId = '" + countyId + '\'' + 
			",buildingId = '" + buildingId + '\'' + 
			",addressId = '" + addressId + '\'' + 
			"}";
		}
}