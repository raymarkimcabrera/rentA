package com.skuld.user.rent_a.model.reverse_geocoder;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ViewItem implements Serializable {

	@SerializedName("_type")
	private String type;

	@SerializedName("ViewId")
	private int viewId;

	@SerializedName("Result")
	private List<ResultItem> result;

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setViewId(int viewId){
		this.viewId = viewId;
	}

	public int getViewId(){
		return viewId;
	}

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"ViewItem{" + 
			"_type = '" + type + '\'' + 
			",viewId = '" + viewId + '\'' + 
			",result = '" + result + '\'' + 
			"}";
		}
}