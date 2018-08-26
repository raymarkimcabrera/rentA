package com.skuld.user.rent_a.model.reverse_geocoder;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResultItem implements Serializable {

	@SerializedName("Relevance")
	private double relevance;

	@SerializedName("MatchLevel")
	private String matchLevel;

	@SerializedName("MatchQuality")
	private MatchQuality matchQuality;

	@SerializedName("MatchType")
	private String matchType;

	@SerializedName("Distance")
	private double distance;

	@SerializedName("Location")
	private Locations locations;

	public void setRelevance(double relevance){
		this.relevance = relevance;
	}

	public double getRelevance(){
		return relevance;
	}

	public void setMatchLevel(String matchLevel){
		this.matchLevel = matchLevel;
	}

	public String getMatchLevel(){
		return matchLevel;
	}

	public void setMatchQuality(MatchQuality matchQuality){
		this.matchQuality = matchQuality;
	}

	public MatchQuality getMatchQuality(){
		return matchQuality;
	}

	public void setMatchType(String matchType){
		this.matchType = matchType;
	}

	public String getMatchType(){
		return matchType;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setLocations(Locations locations){
		this.locations = locations;
	}

	public Locations getLocations(){
		return locations;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"relevance = '" + relevance + '\'' + 
			",matchLevel = '" + matchLevel + '\'' + 
			",matchQuality = '" + matchQuality + '\'' + 
			",matchType = '" + matchType + '\'' + 
			",distance = '" + distance + '\'' + 
			",locations = '" + locations + '\'' +
			"}";
		}
}