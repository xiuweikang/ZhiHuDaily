package com.sdust.zhihudaily.data.model;

import com.google.gson.annotations.Expose;


public class StartImage {
	
	@Expose
	private String text;
	
	@Expose
	private String img;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	@Override
	public boolean equals(Object o) {
		StartImage startImage = (StartImage)o;
		return this.text.equals(startImage.getText()) && this.img.equals(startImage.getImg());
	}
	
	

}
