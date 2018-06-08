
package com.brh.channel.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.brh.channel.web.annotation.TokenEnabled;
import com.brh.channel.web.annotation.TokenVerificationEnabled;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 功能描述:
 * <p>处理</p>
 * <p>版本: 1.0.0</p>
 */

public class TokenInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String URI_BEGIN = "/";
    private static final String DEFAULT_RESPONSE_TEXT = "{\"code\":501,\"msg\":\"\\u8bf7\\u52ff\\u91cd\\u590d\\u63d0\\u4ea4\"}";

    /**
     * This implementation always returns {@code true}.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  Object
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.trace("token interceptor-----------------------------------{}", handler.getClass().getName());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            TokenEnabled enableAnnotation = method.getAnnotation(TokenEnabled.class);
            TokenVerificationEnabled verifyAnnotation = method.getAnnotation(TokenVerificationEnabled.class);

            logger.debug("");
            logger.debug("=============TokenInterceptor=============");
            logger.debug("Handling uri:{}", request.getRequestURI());
            if (enableAnnotation == null && verifyAnnotation == null) {
                logger.debug("No applicable annotation, passed.");
                return super.preHandle(request, response, handler);
            }

            if (enableAnnotation != null) {
                // generate page token

                String sessionKey = enableAnnotation.sessionKey();
                if (StringUtils.hasText(sessionKey)) {
                    String pageToken = refreshSessionToken(request.getSession(), sessionKey,response);
                    logger.debug("Set page token:{} to key:{}", pageToken, sessionKey);
                } else {
                    logger.warn("Empty sessionKey in @TokenEnabled setting.");
                }


            } else {
                // verify page token

                String sessionKey = verifyAnnotation.sessionKey();
                String requestKey = verifyAnnotation.requestKey();
                if (StringUtils.isEmpty(sessionKey) || StringUtils.isEmpty(requestKey)) {
                    logger.warn("Empty sessionKey or requestKey in @TokenVerificationEnabled setting.");
                } else {
                    String requestToken = request.getHeader(requestKey);
                    HttpSession session = request.getSession();
                    String sessionToken = (String) session.getAttribute(sessionKey);
                    logger.debug("requestToken:{}", requestToken);
                    logger.debug("sessionToken:{}", sessionToken);

                    boolean tokenMatched = StringUtils.hasText(sessionToken)
                            && sessionToken.equals(requestToken);
                    logger.debug("Token verification result:{}", tokenMatched);

                    // reset token
                    String newToken = refreshSessionToken(session, sessionKey,response);
                    logger.debug("reset sessionToken:{}", newToken);

                    if (!tokenMatched) {
                        String url = verifyAnnotation.failureUrl();

                        if (StringUtils.isEmpty(url)) {
                            response.getWriter().write(DEFAULT_RESPONSE_TEXT);
                            logger.debug("No url specified, output default text.");

                        } else {
                            if (url.startsWith(URI_BEGIN)) {
                                String contextPath = request.getContextPath();
                                logger.debug("contextPath={}", contextPath);
                                url = contextPath + url;
                            }

                            logger.debug("Token verify failed, redirect to url={}", url);
                            response.sendRedirect(url);
                        }

                        // end after response
                        return false;
                    }
                }
            }
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * 获得新的token
     *
     * @return token
     */
    public static String refreshSessionToken(HttpSession session, String key,HttpServletResponse response) {
        String token = UUID.randomUUID().toString();
        session.setAttribute(key, token);
        response.setHeader(key,token);
        return token;
    }
}
