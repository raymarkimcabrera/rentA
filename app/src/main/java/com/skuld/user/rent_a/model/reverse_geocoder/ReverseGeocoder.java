package com.skuld.user.rent_a.model.reverse_geocoder;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReverseGeocoder implements Serializable {


	@SerializedName("MetaInfo")
	private MetaInfo metaInfo;

	@SerializedName("View")
	private List<ViewItem> view;


	public void setMetaInfo(MetaInfo metaInfo){
		this.metaInfo = metaInfo;
	}

	public MetaInfo getMetaInfo(){
		return metaInfo;
	}

	public void setView(List<ViewItem> view){
		this.view = view;
	}

	public List<ViewItem> getView(){
		return view;
	}

	@Override
 	public String toString(){
		return 
			"ReverseGeocoder{" +
			",metaInfo = '" + metaInfo + '\'' +
			",view = '" + view + '\'' + 
			"}";
		}
}