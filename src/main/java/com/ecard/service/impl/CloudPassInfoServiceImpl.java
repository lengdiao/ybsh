package com.ecard.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.ecard.dao.*;
import com.ecard.entity.*;
import com.ecard.pojo.AuthorityQr;
import com.ecard.pojo.Response;
import com.ecard.pojo.ResponseHasData;
import com.ecard.service.CloudPassInfoService;
import com.ecard.utils.AppUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@Transactional
public class CloudPassInfoServiceImpl implements CloudPassInfoService {

    @Autowired
    private CloudPassInfoMapper cloudPassInfoMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private SednsmsMapper sednsmsMapper;

    public Response login(String phone, String passWord) {
        ResponseHasData response = new ResponseHasData();
        try {
            CloudPassInfo cloudPassInfo = cloudPassInfoMapper.selectByPhoneFlag(phone);

            UsernamePasswordToken token = new UsernamePasswordToken(phone, passWord);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);

            UserRole urRole = userRoleMapper.selectByCloudPassNo(cloudPassInfo.getCloudPassNo());
            Map<String, Object> data = new HashMap<String, Object>();
            Long roleNo = urRole.getRoleNo();

            List<Authority> authorities = roleAuthorityMapper.selectByRoleNo(roleNo);
            List<AuthorityQr> authorityQrs = new ArrayList<AuthorityQr>();
            for(Authority authority : authorities) {
                AuthorityQr authorityQr = new AuthorityQr();
                authorityQr.setAuthorityName(authority.getAuthorityName());
                authorityQr.setAuthorityNo(authority.getAuthorityNo());
                authorityQr.setImg(authority.getImg());
                authorityQr.setUrl(authority.getUrl());
                List<Authority> list = authorityMapper.selectByNo(authority.getAuthorityNo()+"");
                authorityQr.setChildList(list);
                authorityQrs.add(authorityQr);
            }

            data.put("authorities", authorityQrs);
            data.put("roleNo", roleNo);
            data.put("name",cloudPassInfo.getName());
            data.put("cloudPassNo",cloudPassInfo.getCloudPassNo());

            response.setData(data);
            response.setMsg("登陆成功");
            response.setStatus(0);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMsg("登陆失败");
            response.setStatus(1);
        }

        return response;
    }


    public Response updateByPhoneAndPass(String phone, String oldPassword, String newPassword) {
        ResponseHasData response = new ResponseHasData();
        try {
            if(phone==null||"".equals(phone)) {
                response.setStatus(1);
                response.setMsg("电话号码为空");
            }else if(oldPassword==null||"".equals(oldPassword)) {
                response.setStatus(1);
                response.setMsg("原密码为空");
            }else if(newPassword==null||"".equals(newPassword)) {
                response.setStatus(1);
                response.setMsg("新密码为空");
            }else {
                CloudPassInfo cloudPassInfo = cloudPassInfoMapper.selectByPhone(phone);
                if(oldPassword.endsWith(cloudPassInfo.getPassword())) {
                    cloudPassInfo.setPassword(newPassword);
                    cloudPassInfoMapper.updateByPrimaryKeySelective(cloudPassInfo);
                    response.setStatus(0);
                    response.setMsg("修改成功");
                }else {
                    response.setStatus(0);
                    response.setMsg("原密码错误");
                }
            }
        } catch (Exception e) {
            response.setStatus(1);
            response.setMsg("修改失败");
        }
        return response;
    }

    public Response sendCodeSms(String phone) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Response response = new ResponseHasData();
        Sednsms sednsms = null;
        if (phone!=null && !phone.equals("") && phone.length() == 11) {
            try {
                String code = String.valueOf(Math.round(((Math.random() * 9 + 1) * 100000)));
                String content = "您的注册验证码为：" + code;

                // 发送验证码
                boolean sms = AppUtil.sendSms(phone, content);
                if (sms) {
                    HttpSession session = request.getSession();
                    session.setAttribute(phone, code);
                    sednsms = sednsmsMapper.selectByPhone(phone);
                    Sednsms sednsmsObject = new Sednsms();
                    sednsmsObject.setCode(code);
                    sednsmsObject.setExpiryDate(new Date(new Date().getTime() + 600000));
                    if (sednsms == null) {
                        sednsmsObject.setPhone(phone);
                        sednsmsObject.setCreateTime(new Date());
                        sednsmsMapper.save(sednsmsObject);
                        response.setMsg(phone + "发送注册验证码成功-----");
                    } else {
                        sednsms.setCode(code);
                        sednsms.setPhone(phone);
                        sednsms.setExpiryDate(new Date(new Date().getTime() + 600000));
                        sednsmsMapper.update(sednsms);
                        response.setMsg(phone + "更新验证码成功-----");
                    }
                    return response;
                } else {
                    response.setMsg(phone + "发送注册验证码失败-----");
                    response.setStatus(1);
                    return response;
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.setMsg(phone + "发送注册验证码失败-----");
                response.setStatus(1);
                return response;
            }
        } else {
            response.setMsg("手机号码有误");
            response.setStatus(1);
            return response;
        }
    }

    public Response updateByCode(String phone, String code, String newCode) {
        Response response = new ResponseHasData();
        CloudPassInfo cloudPassInfo = new CloudPassInfo();
        Sednsms sednsms = new Sednsms();
        try {
            if (StringUtils.isEmpty(phone)) {
                response.setStatus(1);
                response.setMsg("手机号不能为空");
                return response;
            }
            cloudPassInfo = cloudPassInfoMapper.selectByPhone(phone);
            if(cloudPassInfo==null) {
                response.setStatus(1);
                response.setMsg("没有此用户");
                return response;
            }
            sednsms = sednsmsMapper.selectByPhone(phone);
            if (sednsms == null) {
                response.setStatus(1);
                response.setMsg("请点击获取验证码");
                return response;
            }
            if (!sednsms.getCode().equals(code)) {
                response.setStatus(1);
                response.setMsg("验证码错误！");
                return response;
            }
            System.out.println("截止时间查看 ： " + sednsms.getExpiryDate());
            if (sednsms.getExpiryDate().getTime() < new Date().getTime()) {
                response.setStatus(1);
                response.setMsg("验证码失效！");
                return response;
            }
            if (newCode!=null&&!"".equals(newCode)) {
                cloudPassInfo.setPassword(newCode);
                cloudPassInfoMapper.updateByPrimaryKeySelective(cloudPassInfo);
                response.setStatus(0);
                response.setMsg("修改成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(1);
            response.setMsg("填写新密码失败");
            return response;
        }
        response.setStatus(0);
        response.setMsg("填写新密码成功");
        return response;

    }
}
