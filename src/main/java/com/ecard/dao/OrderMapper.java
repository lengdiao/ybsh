package com.ecard.dao;

import com.ecard.entity.Order;
import com.ecard.pojo.OrderQr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderNo);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderNo);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<OrderQr> select(
            @Param("name") String name,
            @Param("age") Integer age,
            @Param("idCard") String idCard,
            @Param("phone") String phone,
            @Param("status") Integer status,
            @Param("orderNo") Long orderNo);
}