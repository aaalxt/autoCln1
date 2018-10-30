package postUtil;

import org.apache.http.impl.client.BasicCookieStore;

/**
 * @author caolina
 * @create
 **/
public class Constant {

    public static final String APP_HOST_TEST = "http://47.94.147.93";

    public static final String LOGINURL = "/?c=admin/login_post";
    public static final String TESTURL = "/?c=admin/test";
    public static final String USER_TEST    = "admin";
    public static final String PWD_TEST    = "admin";
    public static final String USER_WORKNO  = "104423";

    public static final org.apache.http.client.CookieStore cookieStore = new BasicCookieStore();;


}
