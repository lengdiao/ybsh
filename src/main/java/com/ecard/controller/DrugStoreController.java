package com.ecard.controller;

import com.ecard.pojo.Response;
import com.ecard.service.DrugStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "药店相关接口")
@RequestMapping("/drugStore")
public class DrugStoreController {
    @Autowired
    private DrugStoreService drugStoreService;

    @ApiOperation(value="添加药店接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "drugStoreName", value = "药店名称", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "contact", value = "联系人姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "contactPhone", value = "联系人手机号", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "address", value = "药店地址", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "disableFlag", value = "停用标志", dataType = "int")
    })
    @PostMapping("/insert")
    public Response insert(
            @RequestParam(value = "drugStoreName") String drugStoreName,
            @RequestParam(value = "contact") String contact,
            @RequestParam(value = "contactPhone") String contactPhone,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "disableFlag") int disableFlag){
        Response response = drugStoreService.insert(drugStoreName,contact,contactPhone,address,disableFlag);
        return response;
    }


    @ApiOperation(value="修改药店信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "drugStoreNo", value = "药店编号", dataType = "Long"),
            @ApiImplicitParam(paramType="query", name = "drugStoreName", value = "药店名称", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "contact", value = "联系人姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "contactPhone", value = "联系人手机号", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "address", value = "药店地址", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "disableFlag", value = "停用标志", dataType = "int")
    })
    @PostMapping("/update")
    public Response update(
            @RequestParam(value = "drugStoreNo") Long drugStoreNo,
            @RequestParam(value = "drugStoreName", required=false) String drugStoreName,
            @RequestParam(value = "contact", required=false) String contact,
            @RequestParam(value = "contactPhone", required=false) String contactPhone,
            @RequestParam(value = "address", required=false) String address,
            @RequestParam(value = "disableFlag", required=false) int disableFlag){
        Response response = drugStoreService.update(drugStoreNo,drugStoreName,contact,contactPhone,address,disableFlag);
        return response;
    }

    @ApiOperation(value="查询药店列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "drugStoreName", value = "药店名称", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "contact", value = "联系人姓名", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "contactPhone", value = "联系人手机号", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "address", value = "药店地址", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "disableFlag", value = "停用标志", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "page", value = "当前页", dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "rows", value = "显示条数", dataType = "int")
    })
    @PostMapping("/select")
    public Response select(
            @RequestParam(value = "drugStoreName", required=false) String drugStoreName,
            @RequestParam(value = "contact", required=false) String contact,
            @RequestParam(value = "contactPhone", required=false) String contactPhone,
            @RequestParam(value = "address", required=false) String address,
            @RequestParam(value = "disableFlag", required=false) Integer disableFlag,
            @RequestParam(value="page", required=false, defaultValue="1") int page,
            @RequestParam(value="rows", required=false, defaultValue="10") int rows){
        Response response = drugStoreService.select(drugStoreName,contact,contactPhone,address,disableFlag,page,rows);
        return response;
    }


}
