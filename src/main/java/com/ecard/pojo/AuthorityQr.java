package com.ecard.pojo;

import java.util.List;

import com.ecard.entity.Authority;

public class AuthorityQr {
	private Long authorityNo;
	private String authorityName;
	private String img;
	private String url;
	private List<Authority> childList;
	public Long getAuthorityNo() {
		return authorityNo;
	}
	public void setAuthorityNo(Long authorityNo) {
		this.authorityNo = authorityNo;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Authority> getChildList() {
		return childList;
	}
	public void setChildList(List<Authority> childList) {
		this.childList = childList;
	}
	
}
