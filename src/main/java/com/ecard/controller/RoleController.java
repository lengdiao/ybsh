package com.ecard.controller;

import com.ecard.pojo.Response;
import com.ecard.pojo.RoleQr;
import com.ecard.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Api(value = "权限相关接口" )
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询角色列表
     * @return
     */
    @ApiOperation(value="查询角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "roleName", value = "角色名称", dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "page", value = "当前页", dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "rows", value = "显示个数", dataType = "Integer")
    })
    @GetMapping("/list")
    public Response roleList(
            @RequestParam(value="roleName" ,required=false) String roleName,
            @RequestParam(value="page", required=false, defaultValue="1") Integer page,
            @RequestParam(value="rows", required=false, defaultValue="10") Integer rows) {
        Response response = roleService.list(roleName,page,rows);
        return response;
    }


    /**
     * 查询角色详情
     * @param roleNo
     * @return
     */
    @ApiOperation(value="查询角色详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "roleNo", value = "角色编号", dataType = "String")
    })
    @GetMapping("/selectByNo")
    public Response selectByNo(@RequestParam("roleNo") String roleNo) {
        Response response = roleService.selectByNo(roleNo);
        return response;
    }

    /**
     * 新增角色
     * @param roleQr
     * @return
     */
    @ApiOperation(value=" 新增角色")
    @PostMapping("/save")
    public Response save(@RequestBody RoleQr roleQr) {
        Response response = roleService.save(roleQr);
        return response;
    }


    /**
     * 修改角色
     * @param roleQr
     * @return
     */
    @ApiOperation(value="修改角色")
    @PostMapping("/update")
    public Response update(@RequestBody RoleQr roleQr) {
        Response response = roleService.update(roleQr);
        return response;
    }


    /**
     * 删除角色
     * @param roleNo
     * @return
     */
    @ApiOperation(value="删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "roleNo", value = "角色编号", dataType = "Long")
    })
    @PostMapping("/delete")
    public Response delete(@RequestParam("roleNo") Long roleNo) {
        Response response = roleService.delete(roleNo);
        return response;
    }


    /**
     * 查询所有权限
     * @param roleNo
     * @return
     */
    @ApiOperation(value="查询所有权限")
    @PostMapping("/findAllAuthority")
    public Response findAllAuthority() {
        Response response = roleService.findAllAuthority();
        return response;
    }
}
