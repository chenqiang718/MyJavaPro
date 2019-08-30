package cq.common;

/**
 * @Author: Chen Qiang
 * @Date: 2019-08-30 14:25
 * @description
 */
public class FateException extends Exception {

    private int code;
    private String msg;

    public FateException(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    public FateException(CommonError error){
        this.code=error.getCode();
        this.msg=error.getMsg();
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
