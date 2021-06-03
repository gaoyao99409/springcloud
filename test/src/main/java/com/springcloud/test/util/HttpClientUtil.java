package com.springcloud.test.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 带连接池的httpclient 适合调用车机方的http接口
 *
 * @author Martin
 */
@Slf4j
public class HttpClientUtil {

    private static final String CHAR_SET = "UTF-8";

    /**
     * 最大连接数400
     */
    private static int MAX_CONNECTION_NUM = 400;

    /**
     * 单路由最大连接数100
     */
    private static int MAX_PER_ROUTE = 100;

    /**
     * 向服务端请求超时时间设置(单位:毫秒)
     */
    private static int SERVER_REQUEST_TIME_OUT = 20000;

    /**
     * 服务端响应超时时间设置(单位:毫秒)
     */
    private static int SERVER_RESPONSE_TIME_OUT = 20000;

    /**
     * 构造函数
     */
    private HttpClientUtil() {
    }

    private static Object LOCAL_LOCK = new Object();

    /**
     * 连接池管理对象
     */
    private static PoolingHttpClientConnectionManager cm = null;
    /**
     * 无效连接清理线程
     */
    private static IdleConnectionMonitorThread scanThread = null;

    /**
     * 功能描述: <br>
     * 初始化连接池管理对象
     */
    private static PoolingHttpClientConnectionManager getPoolManager() {
        if (null == cm) {
            synchronized (LOCAL_LOCK) {
                if (null == cm) {
                    SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
                    try {
                        // ssl连接配置
                        // sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
                        // SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                        // sslContextBuilder.build());
                        // Registry<ConnectionSocketFactory> socketFactoryRegistry =
                        // RegistryBuilder.<ConnectionSocketFactory> create()
                        // .register("https", socketFactory)
                        // .register("http", new PlainConnectionSocketFactory())
                        // .build();
                        // cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                        cm = new PoolingHttpClientConnectionManager();
                        cm.setMaxTotal(MAX_CONNECTION_NUM);
                        cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);


                    } catch (Exception e) {
                    }

                }

                if (null == scanThread) {
                    scanThread = new IdleConnectionMonitorThread(cm);
                    scanThread.start();
                }
            }
        }


        return cm;
    }

    /**
     * 创建线程安全的HttpClient
     *
     * @param config 客户端超时设置
     * @return
     */
    public static CloseableHttpClient getHttpsClient(RequestConfig config) {

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config)
                .setConnectionManager(HttpClientUtil.getPoolManager()).build();

        return httpClient;
    }

    /**
     * 处理Http请求
     *
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SERVER_REQUEST_TIME_OUT)
                .setConnectTimeout(SERVER_RESPONSE_TIME_OUT).build();
        CloseableHttpClient httpClient = getHttpsClient(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            int status = response.getStatusLine().getStatusCode();
            String result = null;
            if (status == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                    EntityUtils.consume(entity);
                    response.close();

                    return result;
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            request.releaseConnection();
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                }
            }
        }

        return "";
    }

    public static String httpGetRequest(String url) {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        return getResult(httpGet);
    }

    public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)
            throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return getResult(httpGet);
    }

    public static String httpPostRequest(String url) {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHAR_SET));
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)
            throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, Object> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }

        ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, CHAR_SET));

        return getResult(httpPost);
    }

    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }

        return pairs;
    }

    /**
     * post请求
     *
     * @param url       url地址
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPostJson(String url, JSONObject jsonParam) {

        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonResult = new JSONObject();
        if (null != jsonParam) {
            // 解决中文乱码问题
            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
        }
        jsonResult = JSONObject.parseObject(getResult(httpPost));

        return jsonResult;
    }

    /**
     * 发送get请求
     *
     * @param url 路径
     * @return
     */
    public static JSONObject httpGetJson(String url) {
        // get请求返回结果
        JSONObject jsonResult = null;
        HttpGet httpGet = new HttpGet(url);
        jsonResult = JSONObject.parseObject(getResult(httpGet));
        return jsonResult;

    }
}

/**
 * inner class 关闭无效连接
 *
 * @author Martin
 */
class IdleConnectionMonitorThread extends Thread {
    private final HttpClientConnectionManager connMgr;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);


                    PoolStats stat = ((PoolingHttpClientConnectionManager) connMgr).getTotalStats();

                    // 关闭无效连接
                    connMgr.closeExpiredConnections();

                }
            }
        } catch (InterruptedException ex) {

        }
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
