package com.ecard.dao;

import com.ecard.entity.Doctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DoctorMapper {
    int deleteByPrimaryKey(Long doctorNo);

    int insert(Doctor record);

    int insertSelective(Doctor record);

    Doctor selectByPrimaryKey(Long doctorNo);

    int updateByPrimaryKeySelective(Doctor record);

    int updateByPrimaryKey(Doctor record);

    Doctor selectByPhone(String phone);

    List<Doctor> select(
            @Param("name") String name,
            @Param("age") Integer age,
            @Param("idCard") String idCard,
            @Param("phone") String phone,
            @Param("disableFlag") Integer disableFlag);
}