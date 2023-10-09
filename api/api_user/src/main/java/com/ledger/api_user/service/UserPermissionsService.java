package com.ledger.api_user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ledger.api_user.model.domain.Permissions;
import com.ledger.api_user.model.domain.UserPermissions;

import java.util.List;

/**
* @author 22866
* @description 针对表【user_permissions(存储用户和权限之间的关联信息)】的数据库操作Service
* @createDate 2023-10-01 18:04:42
*/
public interface UserPermissionsService extends IService<UserPermissions> {
    List<String> getAuthByUserId(String userId);

    List<Permissions> getPermissionsByUserId(String userId);

}
