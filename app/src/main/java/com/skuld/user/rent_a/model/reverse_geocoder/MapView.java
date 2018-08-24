package com.skuld.user.rent_a.model.reverse_geocoder;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MapView implements Serializable {

	@SerializedName("BottomRight")
	private BottomRight bottomRight;

	@SerializedName("TopLeft")
	private TopLeft topLeft;

	public void setBottomRight(BottomRight bottomRight){
		this.bottomRight = bottomRight;
	}

	public BottomRight getBottomRight(){
		return bottomRight;
	}

	public void setTopLeft(TopLeft topLeft){
		this.topLeft = topLeft;
	}

	public TopLeft getTopLeft(){
		return topLeft;
	}

	@Override
 	public String toString(){
		return 
			"MapView{" + 
			"bottomRight = '" + bottomRight + '\'' + 
			",topLeft = '" + topLeft + '\'' + 
			"}";
		}
}