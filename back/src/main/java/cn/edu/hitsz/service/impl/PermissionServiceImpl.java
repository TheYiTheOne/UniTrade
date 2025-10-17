package cn.edu.hitsz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.Permission;
import cn.edu.hitsz.service.PermissionService;
import cn.edu.hitsz.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【permissions(权限表，定义系统权限项)】的数据库操作Service实现
* @createDate 2025-10-18 00:20:42
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




