package org.janastu.heritageapp.domain.util;

public class MResponseToken {
	 
	//REGISTER_SUCCESS
	//USER_ID_EXISTS
	///Once registered the 
	  String status;
	  int code;
	  String message;
	  long userId;
	public MResponseToken() {
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	    
 
}
