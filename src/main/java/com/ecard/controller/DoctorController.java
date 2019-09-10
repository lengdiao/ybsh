package com.ecard.controller;

import com.ecard.dao.DoctorMapper;
import com.ecard.pojo.Response;
import com.ecard.service.DoctorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "医生相关接口" )
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @ApiOperation(value="添加医生接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "医生姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "age", value = "医生年龄", dataType = "int"),
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
    Response response = doctorService.insert(name,age,idCard,phone,disableFlag);
    return response;
    }

    @ApiOperation(value="修改医生信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "drNo", value = "医生编号", dataType = "Long"),
            @ApiImplicitParam(paramType="query", name = "name", value = "医生姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "age", value = "医生年龄", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "idCard", value = "身份证号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "disableFlag", value = "停用标志", dataType = "int")
    })
    @PostMapping("/update")
    public Response update(
            @RequestParam(value = "drNo") Long drNo,
            @RequestParam(value = "name", required=false) String name,
            @RequestParam(value = "age", required=false) int age,
            @RequestParam(value = "idCard", required=false) String idCard,
            @RequestParam(value = "phone", required=false) String phone,
            @RequestParam(value = "disableFlag", required=false) int disableFlag){
        Response response = doctorService.update(drNo,name,age,idCard,phone,disableFlag);
        return response;
    }

    @ApiOperation(value="查询医生列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "name", value = "医生姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "age", value = "医生年龄", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "idCard", value = "身份证号码", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "disableFlag", value = "停用标志", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "page", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "rows", value = "显示条数", dataType = "Integer")
    })
    @PostMapping("/select")
    public Response select(
            @RequestParam(value = "name", required=false) String name,
            @RequestParam(value = "age", required=false) Integer age,
            @RequestParam(value = "idCard", required=false) String idCard,
            @RequestParam(value = "phone", required=false) String phone,
            @RequestParam(value = "disableFlag", required=false) Integer disableFlag,
            @RequestParam(value="page", required=false, defaultValue="1") Integer page,
            @RequestParam(value="rows", required=false, defaultValue="10") Integer rows){
        Response response = doctorService.select(name,age,idCard,phone,disableFlag,page,rows);
        return response;
    }
}
