package com.ecard.dao;

import com.ecard.entity.UserRole;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);

    UserRole selectByCloudPassNo(Long cloudPassNo);

    UserRole selectByRoleNo(Long roleNo);
}