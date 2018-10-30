package postUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class LoginWithHttpclient {
    static CookieStore cookieStore = null;

    /**
     * 组装登录参数
     * @return
     */
    public static List<NameValuePair> getLoginNameValuePairList() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("sso_callback_uri","/xxx/forward?locale=zh_CN"));
//        params.add(new BasicNameValuePair("appName", "xxx"));
        params.add(new BasicNameValuePair("name", "admin"));
        params.add(new BasicNameValuePair("passwd", "admin"));

        return params;
    }
    /**
     * 组装操作参数
     * @return
     */
    public static List<NameValuePair> getQueryNameValuePairList() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("regionNo", "xxx"));
//        params.add(new BasicNameValuePair("pageNo", "xxx"));
//        params.add(new BasicNameValuePair("pageSize", "xxx"));
        return params;
    }
    /**
     * 将cookie保存到静态变量中供后续调用
     * @param httpResponse
     */
    public static void setCookieStore(HttpResponse httpResponse) {
        System.out.println("----setCookieStore");
        cookieStore = new BasicCookieStore();
        // JSESSIONID
        String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
        String PHPSESSID = setCookie.substring("PHPSESSID=".length(),
                setCookie.indexOf(";"));
        System.out.println("PHPSESSID:" + PHPSESSID);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie("PHPSESSID",PHPSESSID);
        cookie.setVersion(0);
        cookie.setDomain("47.94.147.93");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
    }

    public static String doPost(String postUrl,List<NameValuePair> parameterList) {
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = null;
        if(cookieStore!=null){
            closeableHttpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
        }else{
            closeableHttpClient = httpClientBuilder.build();
        }
        HttpPost httpPost = new HttpPost(postUrl);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000).setConnectTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameterList, "UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);

            if(cookieStore==null){
                setCookieStore(response);
            }


            HttpEntity httpEntity = response.getEntity();
            retStr = EntityUtils.toString(httpEntity, "UTF-8");
           // System.out.println(retStr);
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return retStr;
    }

//    public static void main(String[] args) {
//        String loginUrl = "http://47.94.147.93/?c=admin/login_post";
//        String queryReginUrl = "http://47.94.147.93/?c=admin/test";
//        //第一次登录会保存cookie
//        doPost(loginUrl, getLoginNameValuePairList());
//        //第二次操作会调用已经存在的cookie
//        doPost(queryReginUrl, getQueryNameValuePairList());
//    }
}