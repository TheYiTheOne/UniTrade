package cn.edu.hitsz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {

    // 状态码常量
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_FAIL = 500;

    private int code;
    private String message;
    private T data;

    public Result(int code, String message, T data) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(CODE_SUCCESS, "success", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CODE_SUCCESS, "success", data);
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(CODE_SUCCESS, message, data);
    }

    // ========== 失败响应 ==========
    public static <T> Result<T> fail(String message) {
        return new Result<>(CODE_FAIL, message, null);
    }
}
