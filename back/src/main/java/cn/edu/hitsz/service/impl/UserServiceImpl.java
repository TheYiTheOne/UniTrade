package cn.edu.hitsz.service.impl;

import cn.edu.hitsz.utils.JwtUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.hitsz.pojo.User;
import cn.edu.hitsz.service.UserService;
import cn.edu.hitsz.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

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
    public String login(String account, String password) {
        // 1. 查询用户
        User user = userMapper.selectByAccount(account);
        if (user == null) {
            throw new IllegalArgumentException("账号或密码错误");
        }

        // 2. 密码校验（MD5 示例）
        String encodedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!user.getPassword().equals(encodedPassword)) {
            throw new IllegalArgumentException("账号或密码错误");
        }

        // 3. 生成 JWT（包含 id, account, name, roleId）
        return JwtUtils.generateToken(user.getId(), user.getAccount(), user.getName(), user.getRoleId());
    }
}




