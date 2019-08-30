package cq.common;

/**
 * @Author: Chen Qiang
 * @Date: 2019-08-30 14:07
 * @description
 */
public enum CommonError implements Error{
    WEB_STATUS_ERROR(20000,"返回状态码非200")
    ;
    private int code;
    private String msg;

    CommonError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
