package com.ecard.pojo;



public abstract class Response {
	int status = 0;
	
	String msg;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", msg=" + msg + "]";
	}
	
	
}
