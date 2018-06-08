package com.brh.channel.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.SocketTimeoutException;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * Created by AB045982 on 2015/6/1.
 */
public class HttpUtilsNew {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtilsNew.class);
    private static final int CONNECTION_TIMEOUT = 15000;
    private static final int SOCKET_TIMEOUT = 60000;
    private static final int CONNECTION_TIMEOUT_DOWNLOAD = 15000;
    private static final int SOCKET_TIMEOUT_DOWNLOAD = 1200000;

    public static File getFileAndCreateDir(String fileFullPath) {
        File file = new File(fileFullPath);
        if (file.exists()) {
            logger.error("文件已经存在，文件名{}", fileFullPath);
            return null;
        }
        if (fileFullPath.endsWith(File.separator)) {
            logger.error("文件名错误，{}", fileFullPath);
            return null;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录

            if (!file.getParentFile().mkdirs()) {
                logger.error("创建文件目录失败，文件名{}", fileFullPath);
                return null;
            }
        }
        return file;
    }

    public static boolean downloadFile(String url, String filePath) {
        boolean ret = false;
        File file = getFileAndCreateDir(filePath);
        if (file == null) {
            //创建路径错误或文件已经存在
            logger.error("下载文件时，文件名错误或路径错误，{}", filePath);
            return ret;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT_DOWNLOAD)
                    .setConnectTimeout(CONNECTION_TIMEOUT_DOWNLOAD).build();
            httpGet.setConfig(requestConfig);
            HttpResponse httpResponse = httpclient.execute(httpGet);

            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == 200) {

                FileOutputStream outputStream = new FileOutputStream(file);
                InputStream inputStream = httpResponse.getEntity()
                                                      .getContent();
                byte b[] = new byte[1024 * 1024];
                int j = 0;
                while ((j = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, j);
                }
                outputStream.flush();
                outputStream.close();
                ret = true;
            }
        } catch (ClientProtocolException e) {
            logger.error("HttpClient ClientProtocolException异常", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("HttpClient IO异常", e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("HttpClient 异常", e);
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("关闭httpclient连接异常", e);
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 根据指定URL外发POST请求
     *
     * @param obj      外发对象
     * @param url      外发地址
     * @param encoding 编码
     * @param isBlank  是否允许为空值
     * @return 返回字符串
     */
    public static String sendPostMessage(Object obj, String url, String encoding, boolean isBlank)
            throws SocketTimeoutException {
        List<NameValuePair> formParams = StaticFunctions.getListNamValPar(obj, isBlank);

        UrlEncodedFormEntity uefEntity = null;
        try {
            uefEntity = new UrlEncodedFormEntity(formParams, encoding);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }

        return sendPostRequest(uefEntity, url, encoding);
    }

    /**
     * 根据指定URL外发POST请求，入参为字符串
     *
     * @param keyValStr 外发字符串
     * @param url       外发地址
     * @param encoding  编码
     * @return 返回字符串
     * @throws SocketTimeoutException
     */
    public static String sendPostMessage(String keyValStr, String url, String encoding)
            throws SocketTimeoutException {
        StringEntity strEntity = new StringEntity(keyValStr, encoding);
        return sendPostRequest(strEntity, url, encoding);
    }

    /**
     * 外发POST请求
     *
     * @param httpEntity        外发对象
     * @param url               外发地址
     * @param encoding          编码
     * @return 返回字符串
     * @throws SocketTimeoutException
     */
    public static String sendPostRequest(HttpEntity httpEntity, String url, String encoding)
            throws SocketTimeoutException {
        return sendPostRequest(httpEntity, url, encoding, SOCKET_TIMEOUT, CONNECTION_TIMEOUT);
    }

    /**
     * 外发POST请求
     *
     * @param httpEntity        外发对象
     * @param url               外发地址
     * @param encoding          编码
     * @param socketTimeOut     socket超时时间
     * @param connectionTimeOut 连接超时时间
     * @return 返回字符串
     * @throws SocketTimeoutException
     */
    public static String sendPostRequest(HttpEntity httpEntity, String url, String encoding,
                                          int socketTimeOut, int connectionTimeOut)
            throws SocketTimeoutException {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        logger.trace("HttpClient方法创建！");
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        logger.trace("Post方法创建！");

        String resMes = null;
        try {
            logger.info("HTTP请求数据：{}", EntityUtils.toString(httpEntity, encoding));

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut)
                    .setConnectTimeout(connectionTimeOut).build();
            httppost.setConfig(requestConfig);
            httppost.setEntity(httpEntity);
            logger.info("HTTP请求URL:{}", httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);

            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resMes = EntityUtils.toString(entity, encoding);
                    
                    logger.info("HTTP返回数据:{}", resMes);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        } catch (SocketTimeoutException e) {
            //捕获服务器响应超时
            logger.error(e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            // 关闭连接,释放资源
            try {

                if(httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        return resMes;
    }

    /**
     * 根据指定URL外发HTTPS POST请求，入参为字符串
     *
     * @param keyValStr 外发字符串
     * @param url       外发地址
     * @param encoding  编码
     * @return 返回字符串
     * @throws SocketTimeoutException
     */
    public static String sendPostSSLMessage(String keyValStr, String url, String encoding)
            throws SocketTimeoutException {
        StringEntity strEntity = new StringEntity(keyValStr, encoding);
        return sendPostSSLRequest(strEntity, url, encoding);
    }

    /**
     * 外发HTTPS POST请求，默认超时设置
     *
     * @param httpEntity        外发对象
     * @param url               外发地址
     * @param encoding          编码
     * @return 返回字符串
     * @throws SocketTimeoutException
     */
    public static String sendPostSSLRequest(HttpEntity httpEntity, String url, String encoding)
            throws SocketTimeoutException {
        return sendPostSSLRequest(httpEntity, url, encoding, SOCKET_TIMEOUT, CONNECTION_TIMEOUT);
    }

    /**
     * 外发HTTPS POST请求 单向验证
     *
     * @param httpEntity
     * @param url
     * @param encoding
     * @param socketTimeOut
     * @param connectionTimeOut
     * @return
     * @throws SocketTimeoutException
     */
    public static String sendPostSSLRequest(HttpEntity httpEntity, String url, String encoding,
                                            int socketTimeOut, int connectionTimeOut)
            throws SocketTimeoutException {
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).build();
        logger.trace("HttpClient方法创建！");
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        logger.trace("Post方法创建！");

        String resMes = null;
        try {
            logger.info("HTTPS请求数据:{}", EntityUtils.toString(httpEntity, encoding));

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeOut)
                    .setConnectTimeout(connectionTimeOut).build();
            httppost.setConfig(requestConfig);
            httppost.setEntity(httpEntity);
            logger.info("HTTPS请求URL:{}", httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);

            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    resMes = EntityUtils.toString(entity, encoding);
                    logger.info("HTTPS返回数据:{}", resMes);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        } catch (SocketTimeoutException e) {
            //捕获服务器响应超时
            logger.error(e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }

        return resMes;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(java.security.cert.X509Certificate[] certs,
                                               String authType) {
                }

                public void checkServerTrusted(java.security.cert.X509Certificate[] certs,
                                               String authType) {
                }
            } };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    public static void main(String[] args) {
        String req = "a=2&b=3";
        String url = "https://220.250.30.210:37031/acquire/easypay.do";
        String ecoding = "UTF-8";
        try {
            System.out.println(HttpUtilsNew.sendPostSSLMessage(req, url, ecoding));
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
    }
}
