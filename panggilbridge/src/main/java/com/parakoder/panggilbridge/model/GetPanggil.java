package com.parakoder.panggilbridge.model;

public class GetPanggil {
	private Integer status;
	private String message;
	private boolean panggil;
	private Object data;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isPanggil() {
		return panggil;
	}
	public void setPanggil(boolean panggil) {
		this.panggil = panggil;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
