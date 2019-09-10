package com.ecard.service;

import com.ecard.pojo.Response;

public interface PtInfoService {
    Response insert(String name, Integer age, String idCard, String phone, Integer disableFlag);

    Response update(Long ptNo, String name, Integer age, String idCard, String phone, Integer disableFlag);

    Response select(String name, Integer age, String idCard, String phone, Integer disableFlag, int page, int rows);
}
