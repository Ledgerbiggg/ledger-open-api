package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UserPermissions;
import generator.service.UserPermissionsService;
import generator.mapper.UserPermissionsMapper;
import org.springframework.stereotype.Service;

/**
* @author 22866
* @description 针对表【user_permissions(存储用户和权限之间的关联信息)】的数据库操作Service实现
* @createDate 2023-10-01 18:04:24
*/
@Service
public class UserPermissionsServiceImpl extends ServiceImpl<UserPermissionsMapper, UserPermissions>
    implements UserPermissionsService{

}




