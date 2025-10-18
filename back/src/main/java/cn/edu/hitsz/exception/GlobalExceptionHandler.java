package cn.edu.hitsz.exception;

import cn.edu.hitsz.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

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
