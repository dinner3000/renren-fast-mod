package com.brh.channel.shiro.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brh.channel.common.entity.SysLogEntity;
import com.brh.channel.common.service.SysLogService;
import com.brh.channel.common.utils.HttpContextUtils;
import com.brh.channel.common.utils.IPUtils;
import com.brh.channel.common.utils.R;
import com.brh.channel.shiro.utils.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 登录相关
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
public class SysLoginController {
	@Autowired
	private Producer producer;
	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public R login(String username, String password, String captcha)throws IOException {
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!captcha.equalsIgnoreCase(kaptcha)){
			return R.error("验证码不正确");
		}
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		SysLogEntity sysLog = new SysLogEntity();
		sysLog.setMethod("com.brh.channel.shiro.controller.SysLoginController" + "." + "login" + "()");
		sysLog.setIp(IPUtils.getIpAddr(request));
		sysLog.setOperation("登录系统");
		sysLog.setParams(username);
		//用户名
		sysLog.setUsername(username);
		java.sql.Timestamp dateTime = new java.sql.Timestamp(new Date().getTime());
		sysLog.setCreateDate(dateTime);
		try{
			Subject subject = ShiroUtils.getSubject();
			//sha256加密
			password = new Sha256Hash(password).toHex();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
		}catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error(e.getMessage());
		}catch (LockedAccountException e) {
			return R.error(e.getMessage());
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
		sysLogService.save(sysLog);
		ShiroUtils.setSessionAttribute("username", username);
		return R.ok();
	}
	
	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		SysLogEntity sysLog = new SysLogEntity();
		sysLog.setMethod("com.brh.channel.shiro.controller.SysLoginController" + "." + "logout" + "()");
		sysLog.setIp(IPUtils.getIpAddr(request));
		sysLog.setOperation("退出登录");
		sysLog.setParams("");
		//用户名
		String username = ShiroUtils.getUserEntity().getUsername();
		sysLog.setUsername(username);
		java.sql.Timestamp dateTime = new java.sql.Timestamp(new Date().getTime());
		sysLog.setCreateDate(dateTime);
		sysLogService.save(sysLog);
		ShiroUtils.logout();
		return "redirect:login.html";
	}
	
}
