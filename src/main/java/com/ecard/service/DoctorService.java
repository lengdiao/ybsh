package com.ecard.service;

import com.ecard.pojo.Response;

public interface DoctorService {
    Response insert(String name, int age, String idCard, String phone ,int disableFlag);

    Response update(Long drNo, String name, int age, String idCard, String phone, int disableFlag);

    Response select(String name, Integer age, String idCard, String phone, Integer disableFlag, Integer page, Integer rows);
}
