package com.xqdd;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Msg {

    public Msg(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Msg(Integer code) {
        this.code = code;
    }

    public Msg() {
    }

    public Integer code;
    public Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
