package org.janastu.heritageapp.web.rest;

public class MediaResponse 
{
	
	  String status;
	  int code;
	  String message;
	  long mediaCreatedId;
	public MediaResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getMediaCreatedId() {
		return mediaCreatedId;
	}
	public void setMediaCreatedId(long mediaCreatedId) {
		this.mediaCreatedId = mediaCreatedId;
	}
	  
	  

}
