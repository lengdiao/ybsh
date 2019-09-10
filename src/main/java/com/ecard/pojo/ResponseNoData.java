package com.ecard.pojo;

import java.io.Serializable;

public class ResponseNoData extends Response implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public ResponseNoData() {
		super();
	}

	public ResponseNoData(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "ResponseNoData [status=" + status + ", msg=" + msg + "]";
	}
	
	
}
