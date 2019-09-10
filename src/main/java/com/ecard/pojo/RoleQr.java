package com.ecard.pojo;

import java.util.List;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(value="角色对象模型")
public class RoleQr {
	@ApiModelProperty(value="角色编号")
	private Long roleNo;
	@ApiModelProperty(value="角色名称")
	private String roleName;
	@ApiModelProperty(value="权限List")
	private List<Authoritys> authoritys;
	@ApiModelProperty(value="全部权限List")
	private List<Authoritys> allAuthoritys;
	public Long getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(Long roleNo) {
		this.roleNo = roleNo;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<Authoritys> getAuthoritys() {
		return authoritys;
	}
	public void setAuthoritys(List<Authoritys> authoritys) {
		this.authoritys = authoritys;
	}
	public List<Authoritys> getAllAuthoritys() {
		return allAuthoritys;
	}
	public void setAllAuthoritys(List<Authoritys> allAuthoritys) {
		this.allAuthoritys = allAuthoritys;
	}
	
	
}
