package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 存储用户和权限之间的关联信息
 * @TableName user_permissions
 */
@TableName(value ="user_permissions")
@Data
public class UserPermissions implements Serializable {
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 用户ID，可以是用户表中的主键
     */
    private String user_id;

    /**
     * 权限ID，与permissions表关联
     */
    private String permission_id;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}