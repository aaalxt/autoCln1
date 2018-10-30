package util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.test4j.module.ICore;
import org.testng.Assert;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Verify {
	//private final static Logger logger = LogManager.getLogger(Verify.class);
	public static void p(Object o) {
		//logger.info(o);
		System.out.print(o);
	}

	public static void main(String args[]) {
		String result = "{\"content\":[{\"typeDescEN\":\"Platform_service_agreement\",\"typeDescCH\":\"平台服务协议\",\"typeNo\":\"platform_service\"},{\"typeDescEN\":\"Product_agreement\",\"typeDescCH\":\"产品协议\",\"typeNo\":\"product\"}], \"hasError\":false}";
		String result2 = "{\"content\":\"[{\\\"typeDescEN\\\":\\\"Platform_service_agreement\\\",\\\"typeDescCH\\\":\\\"平台服务协议\\\",\\\"typeNo\\\":\\\"platform_service\\\"},{\\\"typeDescEN\\\":\\\"Product_agreement\\\",\\\"typeDescCH\\\":\\\"产品协议\\\",\\\"typeNo\\\":\\\"product\\\"}]\",\"hasError\":false}";

		System.out.println(result2);

		JSONObject jsonObject = JSON.parseObject(result2, Feature.AllowArbitraryCommas,
				Feature.AllowComment,
				Feature.AllowSingleQuotes,
				Feature.AllowUnQuotedFieldNames,
				Feature.IgnoreNotMatch,
				Feature.AutoCloseSource);
		String[] content = new String[]{"content"};
		JSONArray array = new JSONArray();

		Object object = jsonObject.get(content[0]);

		p("OBJECT=" + object);
		p(object.getClass());


	}


	public static JSONArray verifyData2(String result, String[] content) {
		System.out.println("Result===" + result);

		JSONObject jsonObject = JSON.parseObject(result);
		JSONArray actualArray = new JSONArray();

		for (int i = 0; i < content.length; i++) {
			Object contentObject = jsonObject.get(content[i]);
			p("contentObject.getClass()" + contentObject.getClass());

			if (contentObject instanceof List || contentObject instanceof JSONArray || contentObject instanceof String) {
				actualArray = JSONObject.parseArray(contentObject.toString());
			} else {
				p("数据内容不是数组形式或者字符串形式:" + contentObject);
			}
		}

		return actualArray;

	}

	public static JSONArray verifyData(String result, String[] content) {
		System.out.println("Result===" + result);
		JSONObject jsonObject = JSON.parseObject(result);
		JSONArray actualArray = new JSONArray();

		for (int i = 0; i < content.length; i++) {
			// 获取实际结果的默认数据
			if (jsonObject.parseObject(result).get(content[i]) instanceof List) {
				// 返回类型：数据列表
				actualArray = JSONObject.parseArray(jsonObject.get(content[i]).toString());
				jsonObject = (JSONObject) actualArray.get(0);
			} else if (jsonObject.parseObject(result).get(content[i]) instanceof Map) {
				// 返回类型：普通数据
				jsonObject = JSONObject.parseObject(jsonObject.get(content[i]).toString());
			} else {
				p("返回数据内容:" + jsonObject.parseObject(result).get(content[i]));
			}
			result = jsonObject.toString();
		}
		return actualArray;
	}


	/**
	 * 预期结果：普通数据
	 *
	 * @param expect  预期结果
	 * @param result  实际结果Json串
	 * @param content 返回值名称
	 */
	public static void verifyJson(Map<String, Object> expect, String result, String[] content) {
		// 获取实际结果List
		JSONObject contentJsonObject = verifyObject(result, content);
		System.out.println("contentJsonObject=============="+contentJsonObject);
		// 实际结果和预期结果对比
		for (Entry<String, Object> entry : expect.entrySet()) {
			boolean equals = contentJsonObject.get(entry.getKey()).toString().equals(entry.getValue().toString());
			p("期望值：" + entry.getKey() + ":" + entry.getValue().toString() + "="
					+ contentJsonObject.get(entry.getKey()).toString());
			if (!equals) {
				Assert.fail(entry.getKey() + "不相等\n" + "期望值：" + entry.getValue().toString() + "\n实际值："
						+ contentJsonObject.get(entry.getKey()).toString());
//				logger.error(entry.getKey() + "不相等\n" + "期望值：" + entry.getValue().toString() + "\n实际值："
//						+ contentJsonObject.get(entry.getKey()).toString());
				System.out.print(entry.getKey() + "不相等\n" + "期望值：" + entry.getValue().toString() + "\n实际值："
						+ contentJsonObject.get(entry.getKey()).toString());

			}
			Assert.assertTrue(equals);

		}
	}



	/**
	 * 预期结果：数据列表
	 *
	 * @param expect  预期结果
	 * @param result  实际结果Json串
	 * @param content 返回值名称
	 */
	public static void verifyJsonList(List<ICore.DataMap> expect, String result, String[] content) {
		// 获取实际结果List
		JSONArray JSONArray = verifyData2(result, content);

		p("actualArray=" + JSONArray);

		for (int i = 0; i < expect.size(); i++) {
			// 获取预期数据
			JSONObject expectData = (JSONObject) JSONObject.toJSON(expect.get(i));
			// 获取实际结果
			JSONObject contentJsonObject = JSONArray.getJSONObject(i);


			for (Entry<String, Object> entry : expectData.entrySet()) {
				boolean equals = contentJsonObject.get(entry.getKey()).toString().equals(entry.getValue().toString());
				p("期望值：" + entry.getKey() + ":" + entry.getValue().toString() + "="
						+ contentJsonObject.get(entry.getKey()).toString());
				if (!equals) {
                  /*  Assert.fail(entry.getKey() + "不相等\n" + "期望值：" + entry.getValue().toString() + "\n实际值："
                            + contentJsonObject.get(entry.getKey()).toString());*/
//					logger.error(entry.getKey() + "不相等\n" + "期望值：" + entry.getValue().toString() + "\n实际值："
//							+ contentJsonObject.get(entry.getKey()).toString());
					System.out.print(entry.getKey() + "不相等\n" + "期望值：" + entry.getValue().toString() + "\n实际值："
							+ contentJsonObject.get(entry.getKey()).toString());
				}
			}
		}
	}


	public static JSONObject verifyObject(String result, String[] content) {
		p("预期结果：普通数据===" + result);
		JSONObject jsonObject = JSON.parseObject(result);
//        JSONArray actualArray = new JSONArray();
//
//        for (int i = 0; i < content.length; i++) {
//            // 获取实际结果的默认数据
//            if (jsonObject.parseObject(result).get(content[i]) instanceof List) {
//                // 返回类型：数据列表
//                actualArray = JSONObject.parseArray(jsonObject.get(content[i]).toString());
//                jsonObject = (JSONObject) actualArray.get(0);
//            } else if (jsonObject.parseObject(result).get(content[i]) instanceof Map) {
//                // 返回类型：普通数据
//                jsonObject = JSONObject.parseObject(jsonObject.get(content[i]).toString());
//            } else {
//                p("返回数据内容:" + jsonObject.parseObject(result).get(content[i]));
//            }
//            result = jsonObject.toString();
//        }
		return jsonObject;
	}

	/**
	 * @param expect_size 预期结果
	 * @param result      实际数据列表
	 */
	public static void verifyDataSize(int expect_size, String result, String content) {
		JSONObject jsonObject = JSON.parseObject(result);
		String name = jsonObject.get(content).toString();
		int count = (Integer) JSONObject.parseObject(name).get("count");
		if(expect_size != count){
			//logger.error("预期数据条数：" + expect_size + ";实际数据条数：" + expect_size);
			System.out.print("预期数据条数：" + expect_size + ";实际数据条数：" + expect_size);
		}

		Assert.assertEquals(expect_size, count,"预期数据条数：" + expect_size + ";实际数据条数：" + expect_size);
	}

	/**
	 * 预期结果：普通数据
	 *
	 * @param expect  预期结果
	 * @param result  实际结果Json串
	 * @param content 返回值名称
	 */
	public static boolean verifyRetJson(Map<String, Object> expect, String result, String[] content) {
		String transfer = JSONObject.toJSONString(expect);
		Map<String, Object> expectedMap = JSONObject.parseObject(transfer, Map.class);


		// 获取实际结果List
		JSONObject contentJsonObject = verifyObject(result, content);
		boolean flag = true;
		// 实际结果和预期结果对比
		for (Entry<String, Object> entry : expectedMap.entrySet()) {
			String obj = null;
			if(entry.getValue().toString().contains("message_"))
				obj = ParseErrorMessage.getValue((String) entry.getValue());
			else
				obj = entry.getValue().toString();
			boolean equals = contentJsonObject.get(entry.getKey()).toString().equals(obj);
			p("期望值：" + entry.getKey() + ":" + entry.getValue().toString() + "="
					+ contentJsonObject.get(entry.getKey()).toString());
			if (!equals) {
				flag = equals;
/*                Assert.fail(entry.getKey() + "不相等\n" + "期望值：" + entry.getValue().toString() + "\n实际值："
                        + contentJsonObject.get(entry.getKey()).toString());*/
//				logger.error(entry.getKey() + "不相等\n" + "期望值：" + obj.toString() + "\n实际值："
//						+ contentJsonObject.get(entry.getKey()).toString());

				System.out.print(entry.getKey() + "不相等\n" + "期望值：" + obj.toString() + "\n实际值："
						+ contentJsonObject.get(entry.getKey()).toString());
			}
		}
		return flag;
	}

	public static boolean checkSaveStatus(Object expectObj, Map<String, Object> targetMap){
		return checkSaveStatus(expectObj, targetMap, null);
	}

	public static boolean checkSaveStatus(Object expectObj, Map<String, Object> targetMap, String[] excludeFields){
		Class<?> expectCls = expectObj.getClass();
		Field[] declaredFields = expectCls.getDeclaredFields();
		boolean flag = true;
		for(int i = 0; i < declaredFields.length; i++){
			String fieldName = declaredFields[i].getName();
			if(excludeFields != null && excludeFields.length > 0){
				List<String> strings = Arrays.asList(excludeFields);
				if (strings.contains(fieldName)) continue;
			}
			try {
				declaredFields[i].setAccessible(true);
				Object o = declaredFields[i].get(expectObj);
				if(o != null){
					Object targetObject = targetMap.get(StringUtil.humpToLine(fieldName));
					if(targetObject == null){
						//logger.error(String.format("<<<<<<<<<<<<<<<<<<<<<<<<<<无法在数据库中找到%s>>>>>>>>>>>>>>>>>>>>>>>", fieldName));
						System.out.print(String.format("<<<<<<<<<<<<<<<<<<<<<<<<<<无法在数据库中找到%s>>>>>>>>>>>>>>>>>>>>>>>", fieldName));
						flag = false;
					}
					if (targetObject instanceof java.sql.Timestamp){
						continue;
					}
					if(!targetObject.toString().equals(o.toString())){
						//logger.error(String.format("<<<<<<<<<<<<<<<<<<<<<<<<<<%s不相等>>>>>>>>>>>>>>>>>>>>>>>", fieldName));
						System.out.print(String.format("<<<<<<<<<<<<<<<<<<<<<<<<<<%s不相等>>>>>>>>>>>>>>>>>>>>>>>", fieldName));
						flag = false;
						break;
					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	public static void verifyRpcSuccess(String returnResult, Class<?> cls){
		Assert.assertNotNull(returnResult, String.format("%s接口返回为空", cls.getSimpleName()));
		JSONObject jsonObject = JSONObject.parseObject(returnResult);
		// JSONObject content = jsonObject.getJSONObject("content");
		if(!jsonObject.getBoolean("success")){
			p(String.format("%s接口返回错误:%s", cls.getSimpleName(), returnResult));
		}
		Assert.assertTrue(jsonObject.getBoolean("success"), String.format("%s接口返回错误:%s", cls.getSimpleName(), returnResult));
	}


}








//package com.alibaba.infointerface.utils.json;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import junit.framework.Assert;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.test4j.module.ICore;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class Verify {
//	private final static Logger logger = LogManager.getLogger(Verify.class);
//	public static void p(Object o) {
//		logger.info(o);
//	}
//
//	public static void verifyJson(List<ICore.DataMap> expect, String actual) {
//		for (int i = 0; i < expect.size(); i++) {
//			JSONObject jsonObject = (JSONObject) JSONObject.toJSON(expect.get(i));
//			System.out.println(jsonObject.toString());
//			JsonCompare.verifyResponseWithJson("[" + jsonObject.toString() + "]", actual);
//		}
//	}
//
//	public static void verify(Map<String, Object> expect, JSONObject contentJsonObject) {
//		System.out.println("接口返回 ---- >>>"+contentJsonObject);
//		// 实际结果和预期结果对比
//		for (Map.Entry<String, Object> entry : expect.entrySet()) {
//
//			boolean equals = contentJsonObject.get(entry.getKey()).toString().equals(entry.getValue().toString());
//			p("期望值：" + entry.getKey() + ":" + entry.getValue().toString() + "="
//					+ contentJsonObject.get(entry.getKey()).toString());
//			if (!equals) {
//				Assert.fail(entry.getKey() + "不相等\n" + "期望值：" + entry.getValue().toString() + "\n实际值："
//						+ contentJsonObject.get(entry.getKey()).toString());
//				logger.error(entry.getKey() + "不相等\n" + "期望值：" + entry.getValue().toString() + "\n实际值："
//						+ contentJsonObject.get(entry.getKey()).toString());
//
//			}
//			Assert.assertTrue(equals);
//		}
//	}
//
//
//	public static JSONObject verifyObject(String result, String[] content) {
//		p("返回参数===" + result);
//		JSONObject jsonObject = JSON.parseObject(result);
//		com.alibaba.fastjson.JSONArray actualArray = new com.alibaba.fastjson.JSONArray();
//
//		for (int i = 0; i < content.length; i++) {
//			// 获取实际结果的默认数据
//			if (jsonObject.parseObject(result).get(content[i]) instanceof List) {
//				// 返回类型：数据列表
//				actualArray = JSONObject.parseArray(jsonObject.get(content[i]).toString());
//				jsonObject = (JSONObject) actualArray.get(0);
//			} else if (jsonObject.parseObject(result).get(content[i]) instanceof Map) {
//				// 返回类型：普通数据
//				jsonObject = JSONObject.parseObject(jsonObject.get(content[i]).toString());
//			} else {
//				p("返回数据内容:" + jsonObject.parseObject(result).get(content[i]));
//			}
//			result = jsonObject.toString();
//		}
//		return jsonObject;
//	}
//
//	/**
//	 * Rely on format of actual, in meri, it should be likes
//	 * {"content":[{"productName":"到期投资test1","bankId":3,"subbranchId":20700}],
//	 * "hasError":false}
//	 *
//	 * @param actual
//	 * @param expect_size
//	 */
//	public static void verifySize(int expect_size, String actual) {
//		JSONObject jsonObject = JSONObject.parseObject(actual);
//		int actual_size = jsonObject.getJSONArray("content").size();
//		Assert.assertEquals(expect_size, actual_size);
//	}
//
//	/**
//	 * Rely on format of actual, in meri, it should be likes
//	 * {"content":{"data":[{"currencyCode":"CNY"},{"currencyCode":"USD"}],
//	 * "hasError":false}
//	 *
//	 * @param actual
//	 * @param expect_size
//	 */
//	public static void verifyDataSize(int expect_size, String actual) {
//		JSONObject jsonObject = JSONObject.parseObject(actual);
//		int actual_size = jsonObject.getJSONObject("content").getJSONArray("data").size();
//		Assert.assertEquals(expect_size, actual_size);
//	}
//
//	public static void main(String[] args) {
//		// String str =
//		// "{\"hasError\":false,\"content\":{\"data\":[{\"creationDate\":1423451088000,\"accountId\":14184,\"accountNature\":\"TMI_GENERAL_ACCOUNT\",\"accountStatus\":\"NORMAL\",\"accountName\":\"阿斯兰航空服务（上海）有限公司\",\"bankName\":\"招商银行股份有限公司\",\"subbranchName\":\"北京建国路支行12\",\"bankId\":1,\"createdBy\":51986,\"accountNumber\":\"310066629018170231483\",\"updatedMode\":\"DIRECT\",\"isActive\":\"Y\",\"accountType\":\"一般户\",\"ouCode\":\"BANK\",\"lastUpdateDate\":1463021646000,\"accountCategory\":\"MIX\",\"masterId\":100000530,\"accountAlias\":\"交通银行1483\",\"subbrandchId\":105,\"isOnline\":\"Y\",\"accountPurpose\":\"一般户\",\"isCollectionAccounts\":\"COLLECTION_ACCOUNTS\",\"categoryName\":\"混合类\",\"syncStatus\":\"NORMAL\",\"lastUpdatedBy\":76160,\"chopsAuthorize\":\"ssssss\",\"currencyCode\":\"CNY\"},{\"creationDate\":1454310922000,\"accountId\":205280,\"accountNature\":\"TMI_BASIC_ACCOUNT\",\"accountStatus\":\"NORMAL\",\"accountName\":\"阿里巴巴（中国）有限公司\",\"bankName\":\"招商银行股份有限公司\",\"subbranchName\":\"杭州高新支行\",\"bankId\":1,\"availableBalance\":2000,\"createdBy\":76160,\"accountNumber\":\"601382320048673888\",\"updatedMode\":\"DIRECT\",\"isActive\":\"Y\",\"accountType\":\"基本户\",\"ouCode\":\"A50\",\"lastUpdateDate\":1457076150000,\"finInstType\":\"BANK\",\"accountCategory\":\"TREASURY\",\"masterId\":100004700,\"accountAlias\":\"招商银行杭州高新支行3888\",\"subbrandchId\":102,\"isOnline\":\"Y\",\"parentAccountNumber\":\"10009\",\"accountPurpose\":\"testUC34\",\"categoryName\":\"财资类\",\"lastUpdatedBy\":2000019,\"chopsAuthorize\":\"1.
//		// 小于等于30万人民币的所有结算业务时，预留印鉴为公司财务专用章 加 法人章；\n2.
//		// 大于30万小于等于800万人民币的所有结算业务时，预留印鉴为公司财务专用章（2） 加 法人章；\n3.
//		// 大于800万人民币的所有结算业务时，预留印鉴为财务专用章（2） 加武卫印；\n4. 未涉及转账结算的所有业务的预留印鉴为公司财务专用章 加
//		// 法人章\",\"currencyCode\":\"CNY\",\"synchronDateStr\":\"2016-04-23\"},{\"creationDate\":1413445775000,\"accountId\":10430,\"accountNature\":\"TMI_GENERAL_ACCOUNT\",\"accountStatus\":\"NORMAL\",\"accountName\":\"11111\",\"bankName\":\"招商银行股份有限公司\",\"subbranchName\":\"北京建国路支行12\",\"bankId\":1,\"createdBy\":761600,\"accountNumber\":\"99988\",\"updatedMode\":\"DIRECT\",\"isActive\":\"Y\",\"accountType\":\"一般户\",\"ouCode\":\"BANK\",\"lastUpdateDate\":1413445775000,\"finInstType\":\"BANK\",\"accountCategory\":\"PAYMENT\",\"masterId\":100000000,\"accountAlias\":\"招商银行（杭州）9988\",\"subbrandchId\":105,\"isOnline\":\"Y\",\"contractSavingRate\":8,\"accountPurpose\":\"12\",\"isCollectionAccounts\":\"COLLECTION_ACCOUNTS\",\"categoryName\":\"付款类\",\"bussinessLine\":\"CORP_D_001\",\"lastUpdatedBy\":30001,\"chopsAuthorize\":\"1111\",\"currencyCode\":\"CNY\"}],\"currentpage\":1,\"size\":10,\"total\":3}}";
//		String str = "{\"hasError\":false}";
//		// JSONObject jsonObject = JSONObject.parseObject(str);
//		// int actual_size =
//		// jsonObject.getJSONObject("content").getJSONArray("data").size();
//		// System.out.println(actual_size);
//		// Verify verify=new Verify();
//		List<ICore.DataMap> data = new ArrayList<ICore.DataMap>();
//
//		ICore.DataMap map = new ICore.DataMap();
//		map.put("hasError", "false");
//		data.add(map);
//		Verify.verifyJson(data, str);
//
//	}
//}
