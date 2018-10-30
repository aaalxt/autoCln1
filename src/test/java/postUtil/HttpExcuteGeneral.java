package postUtil;

/**
 * @Author：wb-hjp289563
 * @Description:
 * @Date:Created in 15:32 2017/12/5.
 * @Modified By:
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;


public class HttpExcuteGeneral {

    public HttpExcuteGeneral() {
    }

    public static CloseableHttpClient createRedirectDisableSSLClient(String host, Integer port) {
        return createSSLClientDefault(host, port, true);
    }

    public static CloseableHttpClient createRedirectDisableSSLClient(String host) {
        return createSSLClientDefault(host, (Integer)null, true);
    }

    public static CloseableHttpClient createRedirectDisableSSLClient() {
        return createSSLClientDefault("", (Integer)null, true);
    }

    public static CloseableHttpClient createSSLClientDefault() {
        return createSSLClientDefault("", (Integer)null, false);
    }

    public static CloseableHttpClient createSSLClientDefault(String host) {
        return createSSLClientDefault(host, (Integer)null, false);
    }

    public static CloseableHttpClient createSSLClientDefault(String host, Integer port) {
        return createSSLClientDefault(host, port, false);
    }

    public String getHttpPostStringResult(String url, String host, Integer port, List<NameValuePair> nvps) {
        String responseBody = null;
        CloseableHttpClient httpclient = null;
        if(StringUtils.isNotBlank(host)) {
            if(port == null) {
                httpclient = createRedirectDisableSSLClient(host);
            } else {
                httpclient = createRedirectDisableSSLClient(host, port);
            }
        } else {
            httpclient = createRedirectDisableSSLClient();
        }

        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf8"));
            BasicResponseHandler e = new BasicResponseHandler();
            responseBody = (String)httpclient.execute(httpPost, e);
        } catch (Exception var17) {
            var17.printStackTrace();
        } finally {
            try {
                httpPost.completed();
                httpclient.close();
            } catch (IOException var16) {
                var16.printStackTrace();
            }

        }

        return responseBody;
    }

    public String getHttpPostStringResult(String url, List<NameValuePair> nvps) {
        return this.getHttpPostStringResult(url, "", (Integer)null, nvps);
    }

    public String getHttpPostStringResult(String url, String host, List<NameValuePair> nvps) {
        return this.getHttpPostStringResult(url, host, (Integer)null, nvps);
    }

    public Map<String, String> getHttpPostResult(String url, List<NameValuePair> nvps) {
        Map dataMap = null;
        CloseableHttpClient httpclient = createSSLClientDefault();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf8"));
            BasicResponseHandler e = new BasicResponseHandler();
            String responseBody = (String)httpclient.execute(httpPost, e);
            dataMap = (Map) JSON.parseObject(responseBody, new TypeReference() {
            }, new Feature[0]);
        } catch (Exception var16) {
            var16.printStackTrace();
        } finally {
            try {
                httpPost.completed();
                httpclient.close();
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return dataMap;
    }

    public String getHttpPostStringResultByFormNoClose(CloseableHttpClient client, String url, List<NameValuePair> nvps) {
        String responseBody = null;
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf8"));
            BasicResponseHandler e = new BasicResponseHandler();
            responseBody = (String)client.execute(httpPost, e);
        } catch (Exception var10) {
            var10.printStackTrace();
        } finally {
            httpPost.completed();
        }

        return responseBody;
    }

    public String getHttpPostStringResultByForm(CloseableHttpClient client, String url, List<NameValuePair> nvps) {
        String responseBody = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf8"));
            List<Cookie> cookies = Constant.cookieStore.getCookies();
            for (Cookie c : cookies){
                if (c.getName().equals("c_csrf")){
                    httpPost.addHeader("h_csrf",c.getValue());
                    break;
                }
            }
            BasicResponseHandler e = new BasicResponseHandler();
            responseBody = (String)client.execute(httpPost, e);

        } catch (Exception var15) {
            var15.printStackTrace();
        } finally {
            try {
                httpPost.completed();
                client.close();
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        }

        return responseBody;
    }

    public String getHttpGetRedirectUrl(String host, CloseableHttpClient client, HttpResponse httpResponse) {
        try {
            return this.getHttpGetRedirectUrl(0, host, client, httpResponse);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public String getHttpPostRedirectUrl(String host, CloseableHttpClient client, HttpResponse httpResponse) {
        try {
            return this.getHttpPostRedirectUrl(0, host, client, httpResponse);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    private String getHttpGetRedirectUrl(int i, String host, CloseableHttpClient client, HttpResponse httpResponse) throws Exception {
        String locationUrl = null;
        if(httpResponse.getStatusLine().getStatusCode() != 302 && httpResponse.getStatusLine().getStatusCode() != 301) {
            return i > 8?locationUrl:locationUrl;
        } else {
            ++i;
            locationUrl = httpResponse.getLastHeader("Location").getValue();
            String newHost = this.getHostSplit(locationUrl);
            String oldHost = this.getHostSplit(host);
            if(!newHost.equals(oldHost)) {
                return locationUrl;
            } else {
                HttpGet httpGet = new HttpGet(locationUrl);
                CloseableHttpResponse var9 = client.execute(httpGet);
                locationUrl = var9.getLastHeader("Location").getValue();
                return this.getHttpGetRedirectUrl(i, host, client, var9);
            }
        }
    }

    private String getHttpPostRedirectUrl(int i, String host, CloseableHttpClient client, HttpResponse httpResponse) throws Exception {
        String locationUrl = null;
        if(httpResponse.getStatusLine().getStatusCode() != 302 && httpResponse.getStatusLine().getStatusCode() != 301) {
            return i > 8?locationUrl:locationUrl;
        } else {
            ++i;
            locationUrl = httpResponse.getLastHeader("Location").getValue();
            String newHost = this.getHostSplit(locationUrl);
            if(!newHost.equals(host)) {
                return locationUrl;
            } else {
                HttpPost httpPost = new HttpPost(locationUrl);
                CloseableHttpResponse var8 = client.execute(httpPost);
                locationUrl = var8.getLastHeader("Location").getValue();
                return this.getHttpPostRedirectUrl(i, host, client, var8);
            }
        }
    }

    private static CloseableHttpClient createSSLClientDefault(String host, Integer port, boolean disableRedirect) {
        SSLConnectionSocketFactory sslsf = null;
        RequestConfig.Builder builder = RequestConfig.custom().setCookieSpec("best-match");
        RequestConfig globalConfig = null;

        SSLContext proxy;
        try {
            proxy = (new SSLContextBuilder()).loadTrustMaterial((KeyStore)null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(proxy);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        if(disableRedirect) {
            globalConfig = builder.setRedirectsEnabled(false).build();
        } else {
            globalConfig = builder.build();
        }

        if(StringUtils.isBlank(host) && port == null) {
            return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(globalConfig).build();
        } else {
            proxy = null;
            HttpHost proxy1;
            if(port == null) {
                proxy1 = new HttpHost(host);
            } else {
                proxy1 = new HttpHost(host, port.intValue());
            }

            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy1);
            return HttpClients.custom().setRoutePlanner(routePlanner).setSSLSocketFactory(sslsf).setDefaultRequestConfig(globalConfig).build();
        }
    }

    private String getHostSplit(String url) {
        try {
            URL e = new URL(url);
            return e.getHost();
        } catch (MalformedURLException var3) {
            var3.printStackTrace();
            return null;
        }
    }
}

