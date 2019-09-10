package com.ecard.dao;

import com.ecard.entity.PtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PtInfoMapper {

    int deleteByPrimaryKey(Long ptNo);

    int insert(PtInfo record);

    int insertSelective(PtInfo record);

    PtInfo selectByPrimaryKey(Long ptNo);

    int updateByPrimaryKeySelective(PtInfo record);

    int updateByPrimaryKey(PtInfo record);

    PtInfo selectByPhone(String phone);

    PtInfo selectByPhoneAndIdNo(
            @Param("phone") String phone,
            @Param("idCard") String idCard);

    PtInfo selectByPhoneAndPtNo(
            @Param("ptNo") Long ptNo,
            @Param("phone") String phone);

    List<PtInfo> select(
            @Param("name") String name,
            @Param("age") Integer age,
            @Param("idCard") String idCard,
            @Param("phone") String phone,
            @Param("disableFlag") Integer disableFlag);

    PtInfo selectByIdCard(String idCard);
}