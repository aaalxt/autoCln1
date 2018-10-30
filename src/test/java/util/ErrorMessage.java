package util;

/**
 * Created by wb-zyc239372 on 2016/11/16.
 */
public class ErrorMessage {
    /**
     * 国际化类型：错误码
     */
    public static final String I18N_TYPE_MSGCODE                     = "errorCode";

    /**
     * 默认的保存成功
     */
    public static final String SUCESS                                  = "message_0000";

    /**
     * 通用的保存成功
     */
    public static final String SAVE_SUCESS                             = "message_0001";

    /*************************************************************
     * 系统错误1000-4999
     *************************************************************/
    /** 初始化工作流时:传入参数校验报错 */
    public static final String ERROR_WORKFLOW_PARAM_CHECK              = "message_1001";

    /**
     * 初始化工作流时:调用工作流平台启动工作流异常
     */
    public static final String ERROR_WORKFLOW_STARTUP_ERROR            = "message_1002";

    /**
     * 调用工作流平台审批工作流异常
     */
    public static final String ERROR_WORKFLOW_APPROVE_ERROR            = "message_1003";

    /**
     * 工作流流程不在该用户名下不能操作
     */
    public static final String ERROR_WORKFLOW_NOPVG_ERROR            = "message_1004";
    /**
     * 工作流流程更新参数失败
     */
    public static final String ERROR_WORKFLOW_UPDATE_PARAM          = "message_1005";

    /** 系统错误，请联系管理员！ */
    public static final String ERROR_SYSTEM_INNER                      = "message_4999";

    /*************************************************************
     * 业务错误>5000
     *************************************************************/
    /** 入参有误 */
    public static final String ERROR_PARAM_ERR                         = "message_5000";
    /** 输入长度超过最大限制 */
    public static final String ERROR_DATA_TOOLONG                      = "message_5001";

    /** 根据查不到业务评估人员信息 */
    public static final String ERROR_EVALUATE_USER                     = "message_8003";
    /** 数据获取失败！ */
    public static final String ERROR_DATA_FAILED                       = "message_8004";
    /** 传入的主键ID为空！ */
    public static final String ERROR_PRIMKEY_NULL                      = "message_8005";
    /** 查不到单条解读详情 */
    public static final String ERROR_SINGLE_NULL                       = "message_8006";
    /** 单条制定反馈方案采纳保存错误 */
    public static final String ERROR_FEEDBACK_SAVE_ERR                 = "message_8007";
    /** 单条制定反馈方案采纳新增错误 */
    public static final String ERROR_FEEDBACK_NEW_ERR                  = "message_8008";
    /** 请完成每条法条的反馈方案制定，方可进入下一流程 */
    public static final String ERROR_FEEDBACK_EACH_ERR                 = "message_8009";
    /** 根据主键查不到整体解读信息 */
    public static final String ERROR_DATA_PRIMKEY_SINGLE               = "message_8010";

    /** 根据查不到业务评估详细信息 */
    public static final String ERROR_EVALUATEINFO_BYID                 = "message_8012";
    /** 选择的评估或评估支持人员已经存在 */
    public static final String ERROR_EVALUATEUSER_EXIST                = "message_8013";
    /** 只能选择一个其他接口人评估，当前库中已经存在 */
    public static final String ERROR_EVALUATEUSER_OTHERINTERFACE_EXIST = "message_8014";
    /** 插入工作流业务管理表错误！ */
    public static final String ERROR_INSERT_SUMMARY                    = "message_8020";
    /** 根据projectId查询重要性等级出错 */
    public static final String ERROR_GETBYPROJECTID_ERR                = "message_8021";

    /** 附件上传错误 */
    public static final String ERROR_UPLOADFILE_ERROR                  = "message_9001";
    /** 上传的附件为空 */
    public static final String ERROR_UPLOADFILE_NULL                   = "message_9002";
    /** 文件更新异常 */
    public static final String ERROR_UPLOADFILE_EX                     = "message_9003";
    /** 文件删除异常 */
    public static final String ERROR_UPLOADFILE_DELEX                  = "message_9004";
    /** 附件入参为空 */
    public static final String ERROR_UPLOADFILE_PARAMNULL              = "message_9005";
    /** 文件类型不符合要求，必须是：%s 中的一种 */
    public static final String ERROR_UPLOADFILE_TYPEERROR              = "message_9006";
    /** 解密resourceId出错 */
    public static final String ERROR_UPLOADFILE_DECODEERROR            = "message_9007";
    /** 存储类型错误，需要 %s 当前：%s */
    public static final String ERROR_UPLOADFILE_STORAGETYPEERR         = "message_9008";
    /** 附件超过限制大小：%d，当前附件大小：%d */
    public static final String ERROR_UPLOADFILE_SIZEERR                = "message_9009";
    /** 没有查询到附件 */
    public static final String ERROR_UPLOADFILE_NOFILE                 = "message_9010";
    /** 文件下载出错 */
    public static final String ERROR_UPLOADFILE_DOWNLOADERR            = "message_9012";

    /** buc异常 根据工号找不到人 **/
    public static final String ERROR_BUC_NO_EMPLOYEE_FOUND             = "message_6001";
    /** 根据角色找人buc异常 */
    public static final String ERROR_BUC_FINDNOPERSONBYROLE            = "message_6002";
    /** 不符合业务逻辑的错误，如初判状态的立法监测信息basicId为空，立法基本信息projectId为空 **/

    public static final String ERROR_BUSINESS_ERROR                    = "message_6010";
    /** 非用户提交的立法监测信息不能删除 **/
    public static final String ERROR_BUSINESS_NODEL_PVG                = "message_6011";
    /** 根据工号查不到appUser **/
    public static final String ERROR_GETNOAPPUSERBYWORKNO                = "message_6012";

    /** 根据Id找不到立法监测信息 **/
    public static final String ERROR_MONITOR_GETMONITORBYID_FAIL       = "message_6101";
    /** 保存立法监测信息校验失败 **/
    public static final String ERROR_MONITOR_SAVE_PARAMERROR           = "message_6102";
    /** 提交立法监测信息校验失败 **/
    public static final String ERROR_MONITOR_SUBMIT_PARAMERROR         = "message_6103";

    /** 初判 保存立法基本信息参数校验失败 **/
    public static final String ERROR_BASIC_SAVE_PARAMERROR             = "message_6104";
    /** 初判 提交立法基本信息参数校验失败 **/
    public static final String ERROR_BASIC_SUBMIT_PARAMERROR           = "message_6105";
    /** 调用更新基本信息同步更新项目信息方法 入参错误 **/
    public static final String ERROR_BASICANDPROJECT_UPDATE_PARAMERROR = "message_6106";
    /** 立项审批通过事件 参数校验失败 **/
    public static final String ERROR_APPROVAL_SUBMIT_PARAMERROR        = "message_6107";
    /** 立项监测事件提交事件多次重复提交 **/
    public static final String ERROR_MONITOR_SUBMIT_MULTIPLE           = "message_6108";
    /** 初判事件提交事件多次重复提交 **/
    public static final String ERROR_FIRSTJUDGE_SUBMIT_MULTIPLE        = "message_6109";
    /** 初判 立法依据转换错误 **/
    public static final String ERROR_FIRSTJUDGE_LEGISBASE_TRANSFORM    = "message_6110";

    /** 邮件发送异常 */
    public static final String ERROR_SENDMAIL_ERROR                    = "message_9010";

    /**传入的参数不能为空*/
    public static final String ERROR_INPUTPARAM_NULL                   ="message_7000";

    /**文件格式错误，只能是xls或xlsx**/
    public static final String ERROR_EXCLERESOLUTION_ERROR             ="message_7010";

    /**导入的excle没有数据*/
    public static final String ERROR_EXCLERESOLUTION_NODATA            ="message_7020";

    /**传入的参数有误*/
    public static final String ERROR_INPUTPARAM_ERROR                  ="message_7030";

    /**传入的主键有误*/
    public static final String ERROR_PRIMKEY_ERROR                     ="message_7040";
    /**不能同时传入上下移动参数,或者同时不传*/
    public static final String ERROR_UPANDDOWN_ERROR                   ="message_7050";
    /**excle导入IO流错误*/
    public static final String ERROR_EXCLEIO_ERROR                     ="message_7060";
    /**excle记录解析成对象错误*/
    public static final String ERROR_EXCLETOCLASS_ERROR                ="message_7070";

    /**法规名称校验失败*/
    public static final String ERROR_STATUTENAME_ERROR                ="message_7080";


    /**法规被引用或者关联状态，不能被删除*/
    public static final String ERROR_STATUTEDELETE_ERROR                ="message_7090";

    /**法条被引用或者关联状态，不能被删除*/
    public static final String ERROR_PROVISIONDELETE_ERROR               ="message_7100";

    /**法规章节条编辑时不能更改类型*/
    public static final String ERROR_PROVISIONTYPECHANGE_ERROR          ="message_7101";

    /**入参字符超出限制长度*/
    public static final String ERROR_INPUTPARAMLENGTH_ERROR             ="message_7102";


    /**查询汇报线：工号不能为空*/
    public static final String ERR_REPORTLINE_WORKNO_NULL                   ="message_7501";
    /**查询汇报线：审批层数不能为空*/
    public static final String ERR_REPORTLINE_LEVEL_NULL                   ="message_7502";
    /**查询汇报线：汇报线为空*/
    public static final String ERR_REPORTLINE_REPORTLINE_NULL              ="message_7503";
    /**查询汇报线：审批层数超过汇报线顶层*/
    public static final String ERR_REPORTLINE_LEVEL_UPTO                   ="message_7504";

    /**查询主数据：BucException异常*/
    public static final String ERR_BUC_EXCEPTION                   ="message_7505";
    /**工作流：WorkFlowException异常*/
    public static final String ERR_WORKFLOW_EXCEPTION                   ="message_7506";
}
