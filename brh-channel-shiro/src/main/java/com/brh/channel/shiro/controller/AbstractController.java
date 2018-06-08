package com.brh.channel.shiro.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.brh.channel.shiro.entity.SysUserEntity;
import com.brh.channel.shiro.utils.ShiroUtils;

/**
 * Controller公共组件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return ShiroUtils.getUserEntity();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
	protected String getUserIdString() {
		return String.valueOf(getUser().getUserId());
	}
}
