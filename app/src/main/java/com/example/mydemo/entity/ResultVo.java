package com.example.mydemo.entity;

import java.io.Serializable;

/**
 * Created by jack on 2017/5/21.
 */

public class ResultVo {

    private String errcode;

    private String errmsg;

    private Object msg;

    private String result;

    public void setErrcode(String errcode){
        this.errcode = errcode;
    }
    public String getErrcode(){
        return this.errcode;
    }
    public void setErrmsg(String errmsg){
        this.errmsg = errmsg;
    }
    public String getErrmsg(){
        return this.errmsg;
    }
    public void setMsg(Object msg){
        this.msg = msg;
    }
    public Object getMsg(){
        return this.msg;
    }
    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return this.result;
    }
}
