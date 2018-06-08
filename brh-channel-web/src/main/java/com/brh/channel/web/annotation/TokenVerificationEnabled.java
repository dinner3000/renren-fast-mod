
package com.brh.channel.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:
 * <p>指示需要验证页面令牌</p>
 * <p>版本: 1.0.0</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenVerificationEnabled {
    /**
     * 设置token在session中的名字
     *
     * @return token session key
     */
    String sessionKey() default "__token__";
    /**
     * 设置token在http请求中的参数名字
     *
     * @return token request key
     */
    String requestKey() default "__token__";

    /**
     * 设置token验证失败后重定向的地址
     *
     * @return url
     */
    String failureUrl() default "";
}
