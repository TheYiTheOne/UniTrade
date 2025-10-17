package cn.edu.hitsz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.Role;
import cn.edu.hitsz.service.RoleService;
import cn.edu.hitsz.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【roles(角色表，定义系统中不同角色（如经理、店长、店员等）)】的数据库操作Service实现
* @createDate 2025-10-18 00:20:26
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




