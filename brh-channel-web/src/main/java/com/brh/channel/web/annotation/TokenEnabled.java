
package com.brh.channel.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功能描述:
 * <p>开启页面令牌，创建令牌</p>
 * <p>版本: 1.0.0</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenEnabled {
    /**
     * 设置token在session中的名字
     *
     * @return token session key
     */
    String sessionKey() default "__token__";
}
