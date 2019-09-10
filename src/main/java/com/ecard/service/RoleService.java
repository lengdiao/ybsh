package com.ecard.service;

import com.ecard.pojo.Response;
import com.ecard.pojo.RoleQr;

public interface RoleService {
    Response list(String roleName, Integer page, Integer rows);

    Response selectByNo(String roleNo);

    Response save(RoleQr roleQr);

    Response update(RoleQr roleQr);

    Response delete(Long roleNo);

    Response findAllAuthority();
}
