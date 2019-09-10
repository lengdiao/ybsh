package com.ecard.service;

import com.ecard.pojo.Response;

public interface CloudPassInfoService {

    Response login(String phone, String passWord);

    Response updateByPhoneAndPass(String phone, String oldPassword, String newPassword);

    Response sendCodeSms(String phone);

    Response updateByCode(String phone, String code, String newCode);
}
