package com.ecard.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="权限对象模型")
public class Authoritys {
	@ApiModelProperty(value="权限编号")
	private Long authorityNo;
	@ApiModelProperty(value="权限名称")
	private String authorityName;
	@ApiModelProperty(value="img")
	private String img;
	@ApiModelProperty(value="url")
	private String url;
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
	
	
}
