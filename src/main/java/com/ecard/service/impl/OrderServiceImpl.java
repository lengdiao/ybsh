package com.ecard.service.impl;

import com.ecard.dao.OrderMapper;
import com.ecard.dao.PtInfoMapper;
import com.ecard.entity.Order;
import com.ecard.entity.PtInfo;
import com.ecard.pojo.OrderQr;
import com.ecard.pojo.Response;
import com.ecard.pojo.ResponseHasData;
import com.ecard.service.OrderService;
import com.ecard.utils.UploadUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private PtInfoMapper ptInfoMapper;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    public Response addImg(String img, String orderNo) {
        ResponseHasData response = new ResponseHasData();
        try {
            if(orderNo!=null&&!"".equals(orderNo)){
                //orderNo不為空
                String name = dateFormat.format(new Date()); //可以把日期转换转指定格式的字符串
                UploadUtil.generateImage(img,"webapps/glzy/WEB-INF/classes/static/images/"+orderNo+"/"+name);
            }else{
                //orderNo為空
                Order order = new Order();
                order.setOrderTime(new Date());
                orderMapper.insertSelective(order);
                orderNo = order.getOrderNo()+"";
                String name = dateFormat.format(new Date()); //可以把日期转换转指定格式的字符串
                UploadUtil.generateImage(img,"webapps/glzy/WEB-INF/classes/static/images/"+order.getOrderNo()+"/"+name);
            }
            Map<String,String> map = new HashMap<>();
            map.put("orderNo",orderNo);
            response.setData(map);
            response.setMsg("上传成功");
            response.setStatus(0);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMsg("上传失败");
            response.setStatus(1);
        }
        return response;
    }


    public Response insert(String name, Integer age, String idCard, String phone, Integer status, String orderNo) {
        ResponseHasData response = new ResponseHasData();
        try {
            PtInfo ptInfo = ptInfoMapper.selectByIdCard(idCard);
            if(ptInfo!=null){
                //已存在患者
                Order order = new Order();
                order.setOrderTime(new Date());
                order.setStatus(2);
                order.setPtNo(ptInfo.getPtNo());
                if(orderNo==null||"".equals(orderNo)){
                    orderMapper.insertSelective(order);
                }else{
                    order.setOrderNo(Long.valueOf(orderNo));
                    orderMapper.updateByPrimaryKeySelective(order);
                }
            }else{
                //新建患者
                PtInfo ptInfo1 = new PtInfo();
                ptInfo1.setName(name);
                ptInfo1.setAge(age);
                ptInfo1.setDisableFlag(0);
                ptInfo1.setIdCard(idCard);
                ptInfo1.setPhone(phone);
                ptInfo1.setCreateTime(new Date());
                ptInfoMapper.insertSelective(ptInfo1);

                Order order = new Order();
                order.setOrderTime(new Date());
                order.setStatus(2);
                order.setPtNo(ptInfo1.getPtNo());

                if(orderNo==null||"".equals(orderNo)){
                    orderMapper.insertSelective(order);
                }else{
                    order.setOrderNo(Long.valueOf(orderNo));
                    orderMapper.updateByPrimaryKeySelective(order);
                }
            }
            response.setStatus(0);
            response.setMsg("订单录入成功");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(1);
            response.setMsg("订单录入失败");
        }
        return response;
    }


    public Response update(String name, Integer age, String idCard, String phone, Integer status, String orderNo, String remark) {
        ResponseHasData response = new ResponseHasData();
        try {
            PtInfo ptInfo = ptInfoMapper.selectByIdCard(idCard);
            Order order = new Order();
            order.setOrderNo(Long.valueOf(orderNo));
            order.setPtNo(ptInfo.getPtNo());
            order.setStatus(status);
            order.setRemark(remark);
            orderMapper.updateByPrimaryKeySelective(order);

            ptInfo.setUpdateTime(new Date());
            ptInfo.setPhone(phone);
            ptInfo.setIdCard(idCard);
            ptInfo.setAge(age);
            ptInfo.setName(name);
            ptInfoMapper.updateByPrimaryKeySelective(ptInfo);

            response.setMsg("订单修改成功");
            response.setStatus(0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setMsg("订单修改失败");
            response.setStatus(1);
        }
        return response;
    }


    public Response select(String name, Integer age, String idCard, String phone, Integer status, String orderNo, int page, int rows) {
        ResponseHasData response = new ResponseHasData();
        try {
            Page<?> pa =  PageHelper.startPage(page, rows);
            List<OrderQr> piq =
                    orderMapper.select(name,age,idCard,phone,status,Long.valueOf(orderNo));
            //查询结果总数
            Long total = pa.getTotal();
            //创建分页条件
            PageInfo<OrderQr> pageData = new PageInfo<OrderQr>(piq,total.intValue());

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
