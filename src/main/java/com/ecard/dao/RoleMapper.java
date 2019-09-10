package com.ecard.dao;

import com.ecard.entity.Role;
import com.ecard.pojo.RoleListQr;
import com.ecard.pojo.RoleQr;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long roleNo);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleNo);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    Long selectbyRoleName(String roleName);

    List<RoleListQr> selectRoleList(String roleName);

    RoleQr selectByNo(int parseInt);
}