package demo;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.test4j.module.spring.annotations.SpringBeanByName;
import org.test4j.spec.annotations.Named;
import org.test4j.spec.annotations.Then;
import org.test4j.spec.annotations.When;
import org.test4j.spec.inner.IScenario;
import org.testng.Assert;
import org.testng.annotations.Test;
import postUtil.HttpRequestUtil;
import util.Verify;
import postUtil.DemoBaseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static postUtil.Constant.*;


/**
 * Created by wb-lxt292999 on 2017/11/9.
 */
public class searchTaskTest extends DemoBaseCase {

    /**
     * 标准rpc请求调用
     * 功能描述：提交页查询 <br>
     * 应用场景：<br>
     * @param paramMap
     * @throws Exception
     * @author
     */
    private String returnResult = null;
    List<String> list = new ArrayList<String>();
    private String  id;

    private String  propertyId;
    @When
    public void searchTask(final @Named("paramMap") Map<String,String> paramMap,
                            final @Named("isId") Boolean isId

    ) throws Exception {
       // Map<String,String> paramMap = new HashMap<String, String>();
       // paramMap.put("id",id);
       if(isId){
           Map<String, Object> dataMap = getMap("select * from admin_user  ORDER BY id desc limit 1");
           id = dataMap.get("id").toString();
           paramMap.put("id",id);
       }
            //db.table("admin_user").queryWhere(String.format("id = '2'")).sizeEq(1);
        returnResult = HttpRequestUtil.httpPostLogin(APP_HOST_TEST, TESTURL, USER_TEST,PWD_TEST,paramMap);//
        System.out.println(returnResult);

    }

    @Then
    public void checkResult(final @Named("expected") Map<String, Object> expected) {
//        db.table("regular_compliance_point").queryWhere(String.format("id = %s and is_deleted = 'n' ", id)).sizeEq(1);
        // 验证返回的数据的正确性
        String[] list = {"content"};
        Verify.verifyJson(expected, returnResult, list);
        System.out.println("返回数据验证完成！");
    }

    @Then
    public void checkItems() {
        JSONObject jsonObj = JSONObject.parseObject(returnResult);
        JSONObject content = jsonObj.getJSONObject("data");
        Assert.assertEquals("true",content.get("isLogin").toString());
        // JSONArray list = (JSONArray) content.getJSONArray("list").get(0);
        JSONObject s = JSON.parseObject(String.valueOf(content.getJSONArray("list").get(0)));
        String status = (String) s.get("status");
        Assert.assertEquals("1",status);
    }

    @SpringBeanByName
    @Test(dataProvider = "story",groups = "jspec")
    public void runScenario(IScenario iScenario) throws Throwable {
        this.run(iScenario);
    }
}

