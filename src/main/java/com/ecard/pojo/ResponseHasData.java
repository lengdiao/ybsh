package com.ecard.pojo;

import java.io.Serializable;


public class ResponseHasData extends Response implements Serializable{

	private static final long serialVersionUID = 1L;

	private Object data;

	public ResponseHasData() {
		super();
	}

	public ResponseHasData(int status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseHasData [data=" + data + "]";
	}

	
}
