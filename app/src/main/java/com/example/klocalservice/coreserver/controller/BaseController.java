package com.example.klocalservice.coreserver.controller;


import com.example.klocalservice.model.ResponseModel;

/**
 * 控制基类
 */
public class BaseController {
    /**
     * 构造一个成功的空返回值
     *
     * @return
     */
    ResponseModel buildEmptySuccessResult() {
        return buildSuccessResult(null, "");
    }

    /**
     * 构造一个成功的返回值
     *
     * @param data
     * @param <T>
     * @return
     */
    <T> ResponseModel buildSuccessResult(T data) {
        return buildSuccessResult(data, "");
    }

    /**
     * 构造一个成功的返回值
     *
     * @param message
     * @return
     */
    ResponseModel buildSuccessResult(String message) {
        return buildSuccessResult(null, message);
    }

    /**
     * 构造一个成功的返回值
     *
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    <T> ResponseModel<T> buildSuccessResult(T data, String message) {
        return new ResponseModel<T>()
                .setData(data)
                .setMessage(message)
                .setSuccess();
    }

    /**
     * 构造一个失败的返回值
     *
     * @param code
     * @return
     */
    ResponseModel buildFailResult(int code) {
        return buildFailResult("", code);
    }

    /**
     * 构造一个失败的返回值
     *
     * @param message
     * @param code
     * @return
     */
    ResponseModel buildFailResult(String message, int code) {
        return new ResponseModel()
                .setMessage(message)
                .setCode(code);
    }
}