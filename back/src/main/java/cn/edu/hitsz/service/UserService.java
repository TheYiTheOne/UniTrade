package cn.edu.hitsz.service;

import cn.edu.hitsz.common.RegisterDTO;
import cn.edu.hitsz.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【users(用户表，记录系统中所有用户信息)】的数据库操作Service
* @createDate 2025-10-18 00:18:29
*/
public interface UserService extends IService<User> {
    /**
     * 用户登录
     * @param account 账号
     * @param password 明文密码
     * @return roleId
     */
    Integer login(String account, String password);

    /**
     * 用户注册
     * @param registerDTO 注册信息
     * @return 是否成功
     */
    boolean register(RegisterDTO registerDTO);

    /**
     * 根据账号查询用户
     * @param account 账号
     * @return 用户信息
     */
    User getUserByAccount(String account);

}
