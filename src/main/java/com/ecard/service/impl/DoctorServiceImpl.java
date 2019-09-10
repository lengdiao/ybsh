package com.ecard.service.impl;

import com.ecard.dao.CloudPassInfoMapper;
import com.ecard.dao.DoctorMapper;
import com.ecard.entity.CloudPassInfo;
import com.ecard.entity.Doctor;
import com.ecard.pojo.Response;
import com.ecard.pojo.ResponseHasData;
import com.ecard.service.DoctorService;
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
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private CloudPassInfoMapper cloudPassInfoMapper;

    public Response insert(String name, int age, String idCard, String phone, int disableFlag) {
        Response response = new ResponseHasData();
        try {
            //查询是否有重复手机号
            Doctor doctor = doctorMapper.selectByPhone(phone);
            if(doctor!=null){
                response.setStatus(1);
                response.setMsg("注册手机号重复");
                return response;
            }

            CloudPassInfo cloudPassInfo = new CloudPassInfo();
            cloudPassInfo.setCreateTime(new Date());
            cloudPassInfo.setDisableFlag(disableFlag);
            cloudPassInfo.setIdNo(idCard);
            cloudPassInfo.setName(name);
            cloudPassInfo.setPassword("123456");
            cloudPassInfo.setPhone(phone);
            cloudPassInfoMapper.insertSelective(cloudPassInfo);

            Doctor doctor1 = new Doctor();
            doctor1.setAge(age);
            doctor1.setIdCard(idCard);
            doctor1.setName(name);
            doctor1.setPhone(phone);
            doctor1.setCloudPassNo(cloudPassInfo.getCloudPassNo());
            doctorMapper.insertSelective(doctor1);

            RoleUtil.accredit(cloudPassInfo.getCloudPassNo(),"医生");

            response.setStatus(0);
            response.setMsg("添加医生成功");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMsg("添加医生失败");
            response.setStatus(1);
        }


        return response;
    }


    public Response update(Long drNo, String name, int age, String idCard, String phone, int disableFlag) {
        Response response = new ResponseHasData();
        try {
            Doctor dr = doctorMapper.selectByPrimaryKey(drNo);
            if(dr==null){
                response.setMsg("未找到此医生");
                response.setStatus(1);
                return response;
            }

            Doctor doctor = doctorMapper.selectByPrimaryKey(drNo);

            CloudPassInfo passInfo = cloudPassInfoMapper.selectByPhoneAndCloudPassNo(phone,doctor.getCloudPassNo());
            if(passInfo!=null){
                response.setMsg("此手机号已存在");
                response.setStatus(1);
                return response;
            }

            CloudPassInfo cloudPassInfo1 = new CloudPassInfo();
            cloudPassInfo1.setPhone(phone);
            cloudPassInfo1.setName(name);
            cloudPassInfo1.setIdNo(idCard);
            cloudPassInfo1.setUpdateTime(new Date());
            cloudPassInfo1.setDisableFlag(disableFlag);
            cloudPassInfo1.setCloudPassNo(doctor.getCloudPassNo());
            cloudPassInfoMapper.updateByPrimaryKeySelective(cloudPassInfo1);

            doctor.setPhone(phone);
            doctor.setName(name);
            doctor.setIdCard(idCard);
            doctor.setAge(age);
            doctorMapper.updateByPrimaryKeySelective(doctor);

            response.setStatus(0);
            response.setMsg("医生信息修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMsg("修改医生信息失败");
            response.setStatus(1);
        }
        return response;
    }

    public Response select(String name, Integer age, String idCard, String phone, Integer disableFlag, Integer page, Integer rows) {
        ResponseHasData response = new ResponseHasData();
        try {
            Page<?> pa =  PageHelper.startPage(page, rows);
            List<Doctor> piq =
                    doctorMapper.select(name,age,idCard,phone,disableFlag);
            //查询结果总数
            Long total = pa.getTotal();
            //创建分页条件
            PageInfo<Doctor> pageData = new PageInfo<Doctor>(piq,total.intValue());

            response.setMsg("查询医生列表成功");
            response.setStatus(0);
            response.setData(pageData);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMsg("查询医生列表失败");
            response.setStatus(1);
        }
        return response;
    }
}
