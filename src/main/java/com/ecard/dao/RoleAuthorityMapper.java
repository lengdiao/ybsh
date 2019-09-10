package com.ecard.dao;

import com.ecard.entity.Authority;
import com.ecard.entity.RoleAuthority;

import java.util.List;

public interface RoleAuthorityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleAuthority record);

    int insertSelective(RoleAuthority record);

    RoleAuthority selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleAuthority record);

    int updateByPrimaryKey(RoleAuthority record);

    List<Authority> selectByRoleNo(Long roleNo);

    void deleteByRoleNo(Long roleNo);
}