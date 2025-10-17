package cn.edu.hitsz.exception;

import cn.edu.hitsz.common.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleException(Exception e) {
        Result<Void> result = Result.fail(500, "系统内部错误：" + e.getMessage());
        return ResponseEntity.status(500).body(result);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<Void>> handleIllegalArgument(IllegalArgumentException e) {
        Result<Void> result = Result.fail(400, e.getMessage());
        return ResponseEntity.status(400).body(result);
    }
}
