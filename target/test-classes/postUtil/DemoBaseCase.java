package postUtil;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.test4j.module.database.utility.SqlRunner;
import org.test4j.testng.JSpec;

import java.util.List;
import java.util.Map;

public abstract class DemoBaseCase extends JSpec {


    //protected static final String APP_HOST     = Constants.APP_HOST;
    /**
     * 登陆
     */
//    protected static final String APP_NAME     = Constants.APP_NAME;
//    // appname
//    protected static final String USER         = Constants.USER;
    // user

    /**
     * 定义返回结果
     */
    protected String              returnResult = null;


    protected Logger logger = LogManager.getLogger(this.getClass());

    protected String getField(String sql, String field){
        Map<String, Object> object = SqlRunner.instance.queryMap(sql);
        return object.get(field).toString();
    }

    protected Map<String, Object> getMap(String sql){
        Map<String, Object> object = SqlRunner.instance.queryMap(sql);
        return object;
    }

    protected <T> List<T> queryMapList(String sql) {
        List<Object> objects = SqlRunner.instance.queryMapList(sql);
        return (List<T>) objects;
    }



    protected String getInstanceIdByProjectId(String projectId){
        String instanceId = getField(String.format("select instanceid from workflow_bill a left join legis_workflow_summary b on a.business_id = b.id where b.bill_id = %s", projectId), "instanceid");
        logger.info(String.format("===================== 获取的instance_id为%s =======================", instanceId));
        return instanceId;
    }

}