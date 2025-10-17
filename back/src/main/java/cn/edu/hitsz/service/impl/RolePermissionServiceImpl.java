package cn.edu.hitsz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.RolePermission;
import cn.edu.hitsz.service.RolePermissionService;
import cn.edu.hitsz.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【role_permission(角色权限表，定义角色拥有哪些权限)】的数据库操作Service实现
* @createDate 2025-10-18 00:20:34
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

}




