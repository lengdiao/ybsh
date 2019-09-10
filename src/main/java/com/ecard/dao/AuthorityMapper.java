package com.ecard.dao;

import com.ecard.entity.Authority;
import com.ecard.pojo.Authoritys;

import java.util.List;

public interface AuthorityMapper {
    int deleteByPrimaryKey(Long authorityNo);

    int insert(Authority record);

    int insertSelective(Authority record);

    Authority selectByPrimaryKey(Long authorityNo);

    int updateByPrimaryKeySelective(Authority record);

    int updateByPrimaryKey(Authority record);

    List<Authority> selectByNo(String s);

    List<Authority> selectAllauths();

    List<Authoritys> selectByRoleno(int parseInt);
}