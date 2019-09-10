package com.ecard.controller;

import com.ecard.pojo.Response;
import com.ecard.service.PtInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "患者相关接口" )
@RequestMapping("/ptInfo")
public class PtInfoController {
    @Autowired
    private PtInfoService ptInfoService;

    @ApiOperation(value="添加患者接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "患者姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "age", value = "患者年龄", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "idCard", value = "身份证号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "disableFlag", value = "停用标志", dataType = "int")
    })
    @PostMapping("/insert")
    public Response insert(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "age") int age,
            @RequestParam(value = "idCard") String idCard,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "disableFlag") int disableFlag){
        Response response = ptInfoService.insert(name,age,idCard,phone,disableFlag);
        return response;
    }

    @ApiOperation(value="修改患者信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "ptNo", value = "患者姓名", dataType = "Long"),
            @ApiImplicitParam(paramType="query", name = "name", value = "患者姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "age", value = "患者年龄", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "idCard", value = "身份证号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "disableFlag", value = "停用标志", dataType = "int")
    })
    @PostMapping("/update")
    public Response update(
            @RequestParam(value = "ptNo") Long ptNo,
            @RequestParam(value = "name", required=false) String name,
            @RequestParam(value = "age", required=false) int age,
            @RequestParam(value = "idCard", required=false) String idCard,
            @RequestParam(value = "phone", required=false) String phone,
            @RequestParam(value = "disableFlag", required=false) int disableFlag){
        Response response = ptInfoService.update(ptNo,name,age,idCard,phone,disableFlag);
        return response;
    }


    @ApiOperation(value="查询患者列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "患者姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "age", value = "患者年龄", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "idCard", value = "身份证号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "disableFlag", value = "停用标志", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "rows", value = "显示条数", dataType = "int")
    })
    @PostMapping("/select")
    public Response select(
            @RequestParam(value = "name", required=false) String name,
            @RequestParam(value = "age", required=false) Integer age,
            @RequestParam(value = "idCard", required=false) String idCard,
            @RequestParam(value = "phone", required=false) String phone,
            @RequestParam(value = "disableFlag", required=false) Integer disableFlag,
            @RequestParam(value="page", required=false, defaultValue="1") int page,
            @RequestParam(value="rows", required=false, defaultValue="10") int rows){
        Response response = ptInfoService.select(name,age,idCard,phone,disableFlag,page,rows);
        return response;
    }

}
