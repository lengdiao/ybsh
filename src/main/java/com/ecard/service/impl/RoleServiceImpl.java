package com.ecard.service.impl;

import com.ecard.dao.AuthorityMapper;
import com.ecard.dao.RoleAuthorityMapper;
import com.ecard.dao.RoleMapper;
import com.ecard.dao.UserRoleMapper;
import com.ecard.entity.Authority;
import com.ecard.entity.Role;
import com.ecard.entity.RoleAuthority;
import com.ecard.entity.UserRole;
import com.ecard.pojo.*;
import com.ecard.service.RoleService;
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
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    public Response list(String roleName, Integer page, Integer rows) {
        ResponseHasData response = new ResponseHasData();
        try {
            Page<?> pa =  PageHelper.startPage(page, rows);
            List<RoleListQr> piq =
                    roleMapper.selectRoleList(roleName);
            //查询结果总数
            Long total = pa.getTotal();
            //创建分页条件
            PageInfo<RoleListQr> pageData = new PageInfo<RoleListQr>(piq,total.intValue());

            response.setStatus(0);
            response.setMsg("查询成功");
            response.setData(pageData);
        } catch (Exception e) {
            response.setStatus(1);
            response.setMsg("查询失败");
            e.printStackTrace();
            return response;
        }
        return response;
    }


    public Response selectByNo(String roleNo) {
        ResponseHasData response = new ResponseHasData();
        try {
            RoleQr roleQr = roleMapper.selectByNo(Integer.parseInt(roleNo));
            List<Authoritys> authoritys = authorityMapper.selectByRoleno(Integer.parseInt(roleNo));
            //List<Authoritys> allAuthoritys = authorityMapper.selectAllauths();
            roleQr.setAuthoritys(authoritys);
            //roleQr.setAllAuthoritys(allAuthoritys);

            response.setStatus(0);
            response.setMsg("查询成功");
            response.setData(roleQr);
        } catch (Exception e) {
            response.setStatus(1);
            response.setMsg("查询失败");
            return response;
        }
        return response;
    }

    public Response save(RoleQr roleQr) {
        ResponseHasData response = new ResponseHasData();
        try {
            //新增role
            Role role = new Role();
            role.setRoleName(roleQr.getRoleName());
            role.setCreateTime(new Date());
            roleMapper.insert(role);
            Long roleNo = role.getRoleNo();
            //如果authoritys不为空，怎么循环新增roleAuthority
            List<Authoritys> authoritys = roleQr.getAuthoritys();
            if (authoritys!=null) {
                for (Authoritys authority:authoritys) {
                    RoleAuthority roleAuthority = new RoleAuthority();
                    roleAuthority.setRoleNo(roleNo);
                    roleAuthority.setAuthorityNo(authority.getAuthorityNo());
                    roleAuthorityMapper.insert(roleAuthority);
                }
            }
            response.setStatus(0);
            response.setMsg("新增角色成功");
        } catch (Exception e) {
            response.setStatus(1);
            response.setMsg("新增角色失败");
            return response;
        }
        return response;
    }

    public Response update(RoleQr roleQr) {
        ResponseHasData response = new ResponseHasData();
        try {
            //修改role
            Role role = roleMapper.selectByPrimaryKey(roleQr.getRoleNo());
            role.setRoleName(roleQr.getRoleName());
            role.setUpdateTime(new Date());
            roleMapper.updateByPrimaryKey(role);
            //先删除原来的角色权限关系
            roleAuthorityMapper.deleteByRoleNo(roleQr.getRoleNo());
            //如果authoritys不为空，怎么循环新增roleAuthority
            List<Authoritys> authoritys = roleQr.getAuthoritys();
            if (authoritys!=null) {
                for (Authoritys authority:authoritys) {
                    RoleAuthority roleAuthority = new RoleAuthority();
                    roleAuthority.setRoleNo(roleQr.getRoleNo());
                    roleAuthority.setAuthorityNo(authority.getAuthorityNo());
                    roleAuthorityMapper.insert(roleAuthority);
                }
            }
            response.setStatus(0);
            response.setMsg("修改成功");
        } catch (Exception e) {
            response.setStatus(1);
            response.setMsg("修改失败");
            e.printStackTrace();
            return response;
        }
        return response;
    }

    public Response delete(Long roleNo) {
        ResponseHasData response = new ResponseHasData();
        try {
            UserRole userRole = userRoleMapper.selectByRoleNo(roleNo);
            if (userRole!=null) {
                response.setStatus(1);
                response.setMsg("该角色有关联用户，不能进行删除操作");
                return response;
            }
            roleMapper.deleteByPrimaryKey(roleNo);
            roleAuthorityMapper.deleteByRoleNo(roleNo);

            response.setStatus(0);
            response.setMsg("删除成功");
        } catch (Exception e) {
            response.setStatus(1);
            response.setMsg("删除失败");
            return response;
        }
        return response;
    }

    public Response findAllAuthority() {
        ResponseHasData response = new ResponseHasData();
        try {
            List<Authority> authorities = authorityMapper.selectAllauths();
            response.setData(authorities);
            response.setMsg("查询成功");
            response.setStatus(0);
        } catch (Exception e) {
            response.setMsg("查询失败");
            response.setStatus(1);
            e.printStackTrace();
        }


        return response;
    }
}
