package cn.edu.hitsz.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.edu.hitsz.common.Permission;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author Jayden
 * @Datetime Created in 14:09 2025/10/18
 * @Description 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合 
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 1. 从当前会话的 Session 中获取 permissionBitmask
        Integer bitmaskObj = (Integer) StpUtil.getSession().get("permissionBitmask");

        if (bitmaskObj == null || bitmaskObj == 0) {
            return Collections.emptyList();
        }

        int bitmask = bitmaskObj;

        // 2. 根据掩码解析出权限码列表
        List<String> permissions = new ArrayList<>();
        for (Permission p : Permission.values()) {
            if ((bitmask & p.getBitMask()) != 0) {
                permissions.add(p.getCode());
            }
        }
        return permissions;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {

        return Collections.emptyList();
    }

}
