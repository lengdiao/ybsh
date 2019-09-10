package com.ecard.service.impl;

import com.ecard.dao.PtInfoMapper;
import com.ecard.entity.Doctor;
import com.ecard.entity.PtInfo;
import com.ecard.pojo.Response;
import com.ecard.pojo.ResponseHasData;
import com.ecard.service.PtInfoService;
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
public class PtInfoServiceImpl implements PtInfoService {
    @Autowired
    private PtInfoMapper ptInfoMapper;

    public Response insert(String name, Integer age, String idCard, String phone, Integer disableFlag) {
        Response response = new ResponseHasData();
        try {
            PtInfo ptInfo = ptInfoMapper.selectByPhoneAndIdNo(phone,idCard);
            if(ptInfo!=null){
                response.setMsg("该患者已存在");
                response.setStatus(1);
                return response;
            }
            PtInfo ptInfo1 = new PtInfo();
            ptInfo1.setName(name);
            ptInfo1.setAge(age);
            ptInfo1.setDisableFlag(disableFlag);
            ptInfo1.setIdCard(idCard);
            ptInfo1.setPhone(phone);
            ptInfo1.setCreateTime(new Date());
            ptInfoMapper.insertSelective(ptInfo1);

            response.setMsg("添加患者成功");
            response.setStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMsg("添加患者失败");
            response.setStatus(1);
        }
        return response;
    }


    public Response update(Long ptNo, String name, Integer age, String idCard, String phone, Integer disableFlag) {
        Response response = new ResponseHasData();
        try {
            PtInfo ptInfo = ptInfoMapper.selectByPhoneAndPtNo(ptNo,phone);
            if(ptInfo!=null){
                response.setStatus(1);
                response.setMsg("已存在此手机号");
                return response;
            }
            PtInfo ptInfo1 = new PtInfo();
            ptInfo1.setPhone(phone);
            ptInfo1.setIdCard(idCard);
            ptInfo1.setDisableFlag(disableFlag);
            ptInfo1.setAge(age);
            ptInfo1.setPtNo(ptNo);
            ptInfo1.setName(name);
            ptInfoMapper.updateByPrimaryKeySelective(ptInfo1);

            response.setMsg("修改成功");
            response.setStatus(0);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(1);
            response.setMsg("修改患者信息失败");
        }
        return response;
    }


    public Response select(String name, Integer age, String idCard, String phone, Integer disableFlag, int page, int rows) {
        ResponseHasData response = new ResponseHasData();
        try {
            Page<?> pa =  PageHelper.startPage(page, rows);
            List<PtInfo> piq =
                    ptInfoMapper.select(name,age,idCard,phone,disableFlag);
            //查询结果总数
            Long total = pa.getTotal();
            //创建分页条件
            PageInfo<PtInfo> pageData = new PageInfo<PtInfo>(piq,total.intValue());

            response.setMsg("查询患者列表成功");
            response.setStatus(0);
            response.setData(pageData);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMsg("查询患者列表失败");
            response.setStatus(1);
        }
        return response;
    }
}
