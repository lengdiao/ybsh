package com.ecard.dao;

import com.ecard.entity.CloudPassInfo;
import com.ecard.entity.Doctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CloudPassInfoMapper {
    int deleteByPrimaryKey(Long cloudPassNo);

    int insert(CloudPassInfo record);

    int insertSelective(CloudPassInfo record);

    CloudPassInfo selectByPrimaryKey(Long cloudPassNo);

    int updateByPrimaryKeySelective(CloudPassInfo record);

    int updateByPrimaryKey(CloudPassInfo record);

    CloudPassInfo selectByPhoneFlag(String phone);

    CloudPassInfo selectByPhoneAndCloudPassNo(@Param("phone") String phone, @Param("cloudPassNo") Long cloudPassNo);

    CloudPassInfo selectByPhone(String contactPhone);
}