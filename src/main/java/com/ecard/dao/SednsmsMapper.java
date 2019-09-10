package com.ecard.dao;

import com.ecard.entity.Sednsms;

public interface SednsmsMapper {
    int deleteByPrimaryKey(Long sedneSmsNo);

    int insert(Sednsms record);

    int insertSelective(Sednsms record);

    Sednsms selectByPrimaryKey(Long sedneSmsNo);

    int updateByPrimaryKeySelective(Sednsms record);

    int updateByPrimaryKey(Sednsms record);

    void save(Sednsms sednsms);

    Sednsms selectByPhone(String phone);

    void update(Sednsms sednsms);
}