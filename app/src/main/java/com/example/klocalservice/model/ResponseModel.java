package com.example.klocalservice.model;

import com.example.klocalservice.model.base.BaseModel;
import com.google.gson.Gson;

public class ResponseModel<T> extends BaseModel {
    private int code = 0;
    private String msg = "";
    private T data;

    public int getCode() {
        return code;
    }

    public ResponseModel setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return msg;
    }

    public ResponseModel<T> setMessage(String message) {
        this.msg = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseModel setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 设置为请求成功
     *
     * @return 返回当前对象
     */
    public ResponseModel<T> setSuccess() {
        this.setCode(200);
        return this;
    }

    public ResponseModel<T> setFail(int code) {
        this.setCode(code);
        return this;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
