package com.brh.channel.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brh.channel.api.annotation.IgnoreAuth;
import com.brh.channel.api.annotation.LoginUser;
import com.brh.channel.api.entity.UserEntity;
import com.brh.channel.common.utils.R;

/**
 * API测试接口
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:47
 */
@RestController
@RequestMapping("/api")
public class ApiTestController {

    /**
     * 获取用户信息
     */
    @GetMapping("userInfo")
    public R userInfo(@LoginUser UserEntity user){

        return R.ok().put("user", user);
    }

    /**
     * 忽略Token验证测试
     */
    @IgnoreAuth
    @GetMapping("notToken")
    public R notToken(){

        return R.ok().put("message", "无需token也能访问。。。");
    }
}
