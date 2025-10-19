package cn.edu.hitsz.service;

import cn.edu.hitsz.pojo.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【role_permission(角色权限表，定义角色拥有哪些权限)】的数据库操作Service
* @createDate 2025-10-18 00:20:34
*/
public interface RolePermissionService extends IService<RolePermission> {
    /**
     * 获取角色的权限位掩码（可用于缓存、传输）
     */
    int getPermissionBitmask(int roleId);

    /**
     * 判断角色是否拥有指定权限（通过权限 ID）
     */
    boolean hasPermission(int roleId, int permissionId);

    /**
     * 判断角色是否拥有指定权限（通过权限码，如 "VIEW_DATA"）
     */
    boolean hasPermissionByCode(int roleId, String permissionCode);

    /**
     * 获取角色拥有的所有权限 ID 列表
     */
    List<Integer> getPermissionIds(int roleId);

    /**
     * 获取角色拥有的所有权限码列表（前端按钮控制用）
     */
    List<String> getPermissionCodes(int roleId);

    /**
     * 刷新权限缓存（权限变更后调用）
     */
    void refreshCache();
}
