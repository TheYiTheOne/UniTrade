package cn.edu.hitsz.mapper;

import cn.edu.hitsz.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author Administrator
* @description 针对表【users(用户表，记录系统中所有用户信息)】的数据库操作Mapper
* @createDate 2025-10-18 00:18:29
* @Entity cn.edu.hitsz.pojo.User
*/
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM users WHERE account = #{account}")
    User selectByAccount(String account);
}




