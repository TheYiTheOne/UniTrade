package cn.edu.hitsz.service.impl;

import cn.edu.hitsz.common.RegisterDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.User;
import cn.edu.hitsz.service.UserService;
import cn.edu.hitsz.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author Administrator
* @description 针对表【users(用户表，记录系统中所有用户信息)】的数据库操作Service实现
* @createDate 2025-10-18 00:18:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public Integer login(String account, String password) {
        // 1. 查询用户
        User user = userMapper.selectByAccount(account);
        if (user == null) {
            throw new IllegalArgumentException("账号或密码错误");
        }

        // 2. 密码校验
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("账号或密码错误");
        }

        // 3. 返回 roleId
        return user.getRoleId();
    }

    @Override
    public boolean register(RegisterDTO registerDTO) {
        // 1. 检查账号是否已存在
        User existingUser = userMapper.selectByAccount(registerDTO.getAccount());
        if (existingUser != null) {
            throw new IllegalArgumentException("账号已存在");
        }

        // 2. 创建新用户
        User user = new User();
        user.setAccount(registerDTO.getAccount());
        user.setPassword(registerDTO.getPassword()); // 这里应该加密，暂时明文存储
        user.setName(registerDTO.getName());
        user.setRoleId(registerDTO.getRoleId());
        
        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);

        // 3. 保存用户
        return save(user);
    }

    @Override
    public User getUserByAccount(String account) {
        return userMapper.selectByAccount(account);
    }
}




