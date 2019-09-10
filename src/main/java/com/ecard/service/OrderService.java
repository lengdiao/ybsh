package com.ecard.service;

import com.ecard.pojo.Response;

public interface OrderService {
    Response addImg(String img, String orderNo);

    Response insert(String name, Integer age, String idCard, String phone, Integer status, String orderNo);

    Response update(String name, Integer age, String idCard, String phone, Integer status, String orderNo, String remark);

    Response select(String name, Integer age, String idCard, String phone, Integer status, String orderNo, int page, int rows);
}
