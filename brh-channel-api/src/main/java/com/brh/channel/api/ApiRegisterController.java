package com.brh.channel.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brh.channel.api.annotation.IgnoreAuth;
import com.brh.channel.api.entity.UserEntity;
import com.brh.channel.api.service.UserService;
import com.brh.channel.common.utils.R;
import com.brh.channel.common.validator.Assert;

/**
 * 注册
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/api")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     */
    @IgnoreAuth
    @PostMapping("register")
    public R register(@RequestBody UserEntity user){
        Assert.isBlank(user.getMobile(), "手机号不能为空");
        Assert.isBlank(user.getPassword(), "密码不能为空");

        userService.save(user);

        return R.ok();
    }
}
