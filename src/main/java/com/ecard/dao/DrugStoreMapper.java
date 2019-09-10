package com.ecard.dao;

import com.ecard.entity.DrugStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DrugStoreMapper {
    int deleteByPrimaryKey(Long drugStoreNo);

    int insert(DrugStore record);

    int insertSelective(DrugStore record);

    DrugStore selectByPrimaryKey(Long drugStoreNo);

    int updateByPrimaryKeySelective(DrugStore record);

    int updateByPrimaryKey(DrugStore record);

    DrugStore selectByCloudPassNo(Long drugStoreNo);

    List<DrugStore> select(
            @Param("drugStoreName") String drugStoreName,
            @Param("contact") String contact,
            @Param("contactPhone") String contactPhone,
            @Param("address") String address,
            @Param("disableFlag") Integer disableFlag);
}