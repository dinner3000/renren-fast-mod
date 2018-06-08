package com.brh.channel.common.xss;

import org.apache.commons.lang.StringUtils;

import com.brh.channel.common.utils.HttpHelper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.*;

/**
 * XSS过滤处理
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-04-01 11:29
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    //没被包装过的HttpServletRequest（特殊场景，需求自己过滤）
    HttpServletRequest orgRequest;
    //html过滤
    private final static HTMLFilter htmlFilter = new HTMLFilter();

    private final byte[] body;

    private final Map<String, String[]> paramsMap;

    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        orgRequest = request;

        body = cleanXSS(HttpHelper.getBodyString(request)).getBytes(Charset.forName("UTF-8"));

        if ("POST".equals(request.getMethod().toUpperCase())) {
            paramsMap = getParamMapFromPost(this);
        } else {
            paramsMap = getParamMapFromGet(this);
        }

    }

    public HashMap<String, String[]> parseQueryString(String s) {
        String valArray[] = null;
        HashMap<String, String[]> ht = new HashMap<String, String[]>();
        if (s == null) {
            return ht;
        }
    	//s=URLDecoder.decode(s);
        StringTokenizer st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens()) {
            String pair = (String) st.nextToken();
            int pos = pair.indexOf('=');
            if (pos == -1) {
                continue;
            }
            String key = pair.substring(0, pos);
            String val = pair.substring(pos + 1, pair.length());
            if (ht.containsKey(key)) {
                String oldVals[] = (String[]) ht.get(key);
                valArray = new String[oldVals.length + 1];
                for (int i = 0; i < oldVals.length; i++) {
                    valArray[i] = oldVals[i];
                }
                valArray[oldVals.length] = decodeValue(xssEncode(val));
            } else {
                valArray = new String[1];
                valArray[0] = decodeValue(xssEncode(val));
            }
            ht.put(key, valArray);
        }
        return ht;
    }

    private HashMap<String, String[]> getParamMapFromPost(HttpServletRequest request) {

        String s = null;
        try {
            s = new String(body,"UTF-8");
            if(StringUtils.isBlank(s)){
                s = request.getQueryString();
            }else {
                s += "&"+request.getQueryString();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HashMap<String, String[]> result = new HashMap<String, String[]>();

        if (null == s || 0 == s.length()) {
            return result;
        }

        return parseQueryString(s);
    }

    private Map<String, String[]> getParamMapFromGet(HttpServletRequest request) {
        return parseQueryString(request.getQueryString());
    }

    private String cleanXSS(String value) {
        if(value != null && value.length() != 0) {
            value = value.replaceAll("[\"\'][\\s]*javascript:(.*)[\"\']", "\"\"");
            value = value.replaceAll("eval\\((.*)\\)", "");
            value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
            value = value.replaceAll("\'", "&#39;");
            //value = value.replaceAll("\"", "&#34;");
            return value;
        } else {
            return value;
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isReady() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void setReadListener(ReadListener arg0) {
                // TODO Auto-generated method stub

            }
        };
    }

    /*@Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = xssEncode(parameters[i]);
        }
        return parameters;
    }

    @Override
    public Map<String,String[]> getParameterMap() {
        Map<String,String[]> map = new LinkedHashMap<>();
        Map<String,String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = xssEncode(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }*/
    @Override
    public Map getParameterMap() {
        return paramsMap;
    }

    @Override
    public String getParameter(String name) {// 重写getParameter，代表参数从当前类中的map获取
        String[] values = paramsMap.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {// 同上
        return paramsMap.get(name);
    }

    @Override
    public Enumeration getParameterNames() {
        return Collections.enumeration(paramsMap.keySet());
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(xssEncode(name));
        if (StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
        }
        return value;
    }

    private String xssEncode(String input) {
        return htmlFilter.filter(input);
    }

    /**
     * 获取最原始的request
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
        if (request instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) request).getOrgRequest();
        }

        return request;
    }

 // 自定义解码函数
    private String decodeValue(String value) {
        if (value.contains("%u")) {
            return decodeUnicode(value);
        } else {
            try {
                return URLDecoder.decode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return "";// 非UTF-8编码
            }
        }
    }
    
    public static String decodeUnicode(String unicode) {       
        StringBuffer sb = new StringBuffer();  
            
        String[] hex = unicode.split("\\\\u");  
        
        for (int i = 1; i < hex.length; i++) {  
            int data = Integer.parseInt(hex[i], 16);  
            sb.append((char) data);  
        }  
        return sb.toString();    
     }   
}


