package com.power.exception;

public class BizException extends Exception {

    //需要和ResultEntity中的code一致
    private int code=-1;

    public BizException(){

    }

    public BizException(String message){
        super(message);
    }

    public BizException(int code, String message){
        super(message);
        this.code=code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
