package com.ecard.service;

import com.ecard.pojo.Response;

public interface DrugStoreService {

    Response insert(String drugStoreName, String contact, String contactPhone, String address, int disableFlag);

    Response update(Long drugStoreNo, String drugStoreName, String contact, String contactPhone, String address, int disableFlag);

    Response select(String drugStoreName, String contact, String contactPhone, String address, Integer disableFlag, int page, int rows);
}
