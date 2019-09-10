package com.ecard.service.impl;

import com.ecard.dao.CloudPassInfoMapper;
import com.ecard.dao.DrugStoreMapper;
import com.ecard.entity.CloudPassInfo;
import com.ecard.entity.Doctor;
import com.ecard.entity.DrugStore;
import com.ecard.pojo.Response;
import com.ecard.pojo.ResponseHasData;
import com.ecard.service.DrugStoreService;
import com.ecard.utils.RoleUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class DrugStoreServiceImpl implements DrugStoreService {
    @Autowired
    private DrugStoreMapper drugStoreMapper;
    @Autowired
    private CloudPassInfoMapper cloudPassInfoMapper;

    public Response insert(String drugStoreName, String contact, String contactPhone, String address, int disableFlag) {
        Response response = new ResponseHasData();
        try {
            CloudPassInfo cloudPassInfo = cloudPassInfoMapper.selectByPhone(contactPhone);
            if(cloudPassInfo!=null){
                response.setStatus(1);
                response.setMsg("此手机号码已存在");
                return response;
            }
            CloudPassInfo cloudPassInfo1 = new CloudPassInfo();
            cloudPassInfo1.setDisableFlag(disableFlag);
            cloudPassInfo1.setName(drugStoreName);
            cloudPassInfo1.setPhone(contactPhone);
            cloudPassInfo1.setPassword("123456");
            cloudPassInfo1.setCreateTime(new Date());
            cloudPassInfoMapper.insertSelective(cloudPassInfo1);

            DrugStore drugStore = new DrugStore();
            drugStore.setAddress(address);
            drugStore.setCloudPassNo(cloudPassInfo1.getCloudPassNo());
            drugStore.setContact(contact);
            drugStore.setContactPhone(contactPhone);
            drugStore.setCreateTime(new Date());
            drugStore.setDisableFlag(disableFlag);
            drugStore.setDrugStoreName(drugStoreName);
            drugStoreMapper.insertSelective(drugStore);

            RoleUtil.accredit(cloudPassInfo1.getCloudPassNo(),"药店");

            response.setStatus(0);
            response.setMsg("添加药店成功");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(1);
            response.setMsg("添加药店失败");
        }

        return response;
    }

    @Override
    public Response update(Long drugStoreNo, String drugStoreName,
                           String contact, String contactPhone, String address, int disableFlag) {
        Response response = new ResponseHasData();
        try {
            DrugStore ds = drugStoreMapper.selectByPrimaryKey(drugStoreNo);
            if(ds==null){
                response.setMsg("未找到此药店");
                response.setStatus(1);
                return response;
            }

            DrugStore drugStore = drugStoreMapper.selectByCloudPassNo(drugStoreNo);

            CloudPassInfo passInfo = cloudPassInfoMapper.selectByPhoneAndCloudPassNo(contactPhone,drugStore.getCloudPassNo());
            if(passInfo!=null){
                response.setMsg("此手机号已存在");
                response.setStatus(1);
                return response;
            }

            CloudPassInfo cloudPassInfo1 = new CloudPassInfo();
            cloudPassInfo1.setPhone(contactPhone);
            cloudPassInfo1.setName(drugStoreName);
            cloudPassInfo1.setUpdateTime(new Date());
            cloudPassInfo1.setDisableFlag(disableFlag);
            cloudPassInfo1.setCloudPassNo(drugStore.getCloudPassNo());
            cloudPassInfoMapper.updateByPrimaryKeySelective(cloudPassInfo1);

            drugStore.setDrugStoreName(drugStoreName);
            drugStore.setDisableFlag(disableFlag);
            drugStore.setContact(contact);
            drugStore.setContactPhone(contactPhone);
            drugStore.setAddress(address);
            drugStore.setUpdateTime(new Date());
            drugStoreMapper.updateByPrimaryKeySelective(drugStore);

            response.setStatus(0);
            response.setMsg("药店信息修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMsg("药店医生信息失败");
            response.setStatus(1);
        }
        return response;
    }

    public Response select(String drugStoreName, String contact,
                           String contactPhone, String address, Integer disableFlag, int page, int rows) {
        ResponseHasData response = new ResponseHasData();
        try {
            Page<?> pa =  PageHelper.startPage(page, rows);
            List<DrugStore> piq =
                    drugStoreMapper.select(drugStoreName,contact,contactPhone,address,disableFlag);
            //查询结果总数
            Long total = pa.getTotal();
            //创建分页条件
            PageInfo<DrugStore> pageData = new PageInfo<DrugStore>(piq,total.intValue());

            response.setMsg("查询药店列表成功");
            response.setStatus(0);
            response.setData(pageData);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMsg("查询药店列表失败");
            response.setStatus(1);
        }
        return response;
    }


}
