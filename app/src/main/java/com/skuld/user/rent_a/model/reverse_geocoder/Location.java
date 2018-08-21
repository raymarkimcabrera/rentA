package com.skuld.user.rent_a.model.reverse_geocoder;

import com.google.gson.annotations.SerializedName;

public class Location{

	@SerializedName("DisplayPosition")
	private DisplayPosition displayPosition;

	@SerializedName("Address")
	private Address address;

	@SerializedName("LocationId")
	private String locationId;

	@SerializedName("MapView")
	private MapView mapView;

	@SerializedName("MapReference")
	private MapReference mapReference;

	@SerializedName("LocationType")
	private String locationType;

	public void setDisplayPosition(DisplayPosition displayPosition){
		this.displayPosition = displayPosition;
	}

	public DisplayPosition getDisplayPosition(){
		return displayPosition;
	}

	public void setAddress(Address address){
		this.address = address;
	}

	public Address getAddress(){
		return address;
	}

	public void setLocationId(String locationId){
		this.locationId = locationId;
	}

	public String getLocationId(){
		return locationId;
	}

	public void setMapView(MapView mapView){
		this.mapView = mapView;
	}

	public MapView getMapView(){
		return mapView;
	}

	public void setMapReference(MapReference mapReference){
		this.mapReference = mapReference;
	}

	public MapReference getMapReference(){
		return mapReference;
	}

	public void setLocationType(String locationType){
		this.locationType = locationType;
	}

	public String getLocationType(){
		return locationType;
	}

	@Override
 	public String toString(){
		return 
			"Location{" + 
			"displayPosition = '" + displayPosition + '\'' + 
			",address = '" + address + '\'' + 
			",locationId = '" + locationId + '\'' + 
			",mapView = '" + mapView + '\'' + 
			",mapReference = '" + mapReference + '\'' + 
			",locationType = '" + locationType + '\'' + 
			"}";
		}
}