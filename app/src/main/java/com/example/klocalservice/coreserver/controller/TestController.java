package com.example.klocalservice.coreserver.controller;

import com.example.klocalservice.model.ResponseModel;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController extends BaseController {

    @PostMapping("/login")
    String login(@RequestParam(name = "account") String account, @RequestParam(name = "password") String password) {
        ResponseModel<String> result = new ResponseModel<>();
        result.setSuccess();
        result.setMessage("登陆成功");
        return result.toJson();
    }
}
