package com.ledger.api_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ledger.api_user.model.domain.Permissions;
import com.ledger.api_user.model.domain.UserPermissions;

import java.util.List;

/**
* @author 22866
* @description 针对表【user_permissions(存储用户和权限之间的关联信息)】的数据库操作Mapper
* @createDate 2023-10-01 18:04:42
* @Entity generator.domain.UserPermissions
*/
public interface UserPermissionsMapper extends BaseMapper<UserPermissions> {

    List<Permissions> getPermissionsByUserId(String userId);
}




