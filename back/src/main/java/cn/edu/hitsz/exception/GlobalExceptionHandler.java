package cn.edu.hitsz.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.DisableServiceException;
import cn.edu.hitsz.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器（专用于处理 Sa-Token 和其他常见异常）
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 Sa-Token 未登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public Result<String> handleNotLoginException(NotLoginException e) {
        return Result.deny("NOT_LOGIN");
    }

    /**
     * 处理角色校验失败异常
     */
    @ExceptionHandler(NotRoleException.class)
    public Result<String> handleNotRoleException(NotRoleException e) {
        return Result.deny("缺少角色：" + e.getRole());
    }

    /**
     * 处理权限码校验失败异常
     */
    @ExceptionHandler(NotPermissionException.class)
    public Result<String> handleNotPermissionException(NotPermissionException e) {
        return Result.deny("缺少权限：" + e.getCode());
    }

    /**
     * 处理被封禁异常
     */
    @ExceptionHandler(DisableServiceException.class)
    public Result<String> handleDisableServiceException(DisableServiceException e) {
        return Result.deny("账号已被封禁：" + e.getDisableTime() + " 秒内无法操作");
    }

    /**
     * 处理参数校验异常（可选）
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return Result.fail("参数错误：" + e.getMessage());
    }

    /**
     * 处理所有未被捕获的异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        // 生产环境建议只返回通用提示，不暴露细节
        return Result.fail("服务器内部错误:"  + e.getMessage());
    }
}