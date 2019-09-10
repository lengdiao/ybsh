package com.ecard.controller;

import com.ecard.pojo.Response;
import com.ecard.service.CloudPassInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@Api(value = "登录相关接口1" )
@RequestMapping("/cloudPassinfo")
public class CloudPassInfoController {
    @Autowired
    private CloudPassInfoService cloudPassInfoService;

    @ApiOperation(value="登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "passWord", value = "密码", dataType = "String")
    })
    @PostMapping("/login")
    public Response drugStoreLogin(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "passWord") String passWord) {
        Response response = cloudPassInfoService.login(phone,passWord);
        return response;
    }


    /**
     * 修改密码
     * @param telePhone
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @ApiOperation(value="修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "oldPassword", value = "旧密码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "newPassword", value = "新密码", dataType = "String")
    })
    @PostMapping("/updateByPhoneAndPass")
    public Response updateByPhoneAndPass(
            @RequestParam(value ="phone") String phone,
            @RequestParam(value ="oldPassword") String oldPassword,
            @RequestParam(value ="newPassword") String newPassword) {
        Response response = cloudPassInfoService.updateByPhoneAndPass(phone,oldPassword,newPassword);
        return response;
    }

    /**
     * 发送手机验证码
     *
     * @param phone
     * @return
     */
    @ApiOperation(value="发送手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号码", dataType = "String")
    })
    @PostMapping(value = "/sednSms")
    public Response sendCodeSms(@RequestParam("phone") String phone) {
        Response response = cloudPassInfoService.sendCodeSms(phone);
        return response;
    }

    /**
     * 用户未登录，忘记密码 ---填写新密码
     * @param code  手机验证码
     * @param newCode
     * @return
     */
    @ApiOperation(value="用户未登录，忘记密码 ---填写新密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "code", value = "验证码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "newCode", value = "新密码", dataType = "String")
    })
    @PostMapping("/updateByCode")
    public Response updateByCode(@RequestParam(value="phone",required=false) String phone,
                                 @RequestParam(value="code",required=false) String code,
                                 @RequestParam(value="newCode",required=false) String newCode) {
        Response response = cloudPassInfoService.updateByCode(phone,code,newCode);
        return response;
    }
}
