package util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParse {
	
	private  Log log = LogFactory.getLog(JsonParse.class);

	public  List<Map<String,Object>> sublist = new ArrayList<Map<String,Object>>();
	public  List<Map<String,Object>> totallist = new ArrayList<Map<String,Object>>();
	public  Map<String,Object> itemmap = new HashMap<String,Object>();
	/**
	 */
	public  List<Map<String,Object>> parseJsonObjectToListMap(String jstr){
		try {
			getJSON(jstr);
			
			log.info("[json object is parsed to listmap like]:");
			for(Map<String,Object> jmap : totallist){
				log.info(jmap);
			}
			
			return totallist;
			
		} catch (JSONException e) {
			log.error("[parse json error]:",e);
			return null;
		} catch (IOException e) {
			log.error("[parse json io error]:",e);
			return null;
		}
	}
	
	
	 /**
	   * try to get JSON object and walk through it.
	   *
	   * @return JSON string. null if the @s is not a nest JSON file
	   * @throws IOException
	   *             if any resource does not exist
	   * @throws JSONException
	   *             if any resource is not JSON object and array
	   */
	  private  Object getJSON(String jstr) throws IOException,
	      JSONException {
	    Object obj = null;
	      try {
	        obj = JSONObject.defaultLocale;
	        walk( (JSONObject) obj);
	      } catch (JSONException e) {
	    	 obj = JSONArray.defaultLocale;
	    	 walk((JSONArray) obj);
	      }
	    
	    return obj;
	  }
	  
	  
	  /**
	   * walk through the @json object. expend it when meet any reference
	   *
	   * @throws IOException
	   *             if any resource does not exist
	   * @throws JSONException
	   *             if any resource is not JSON object and array
	   */
	  private  void walk(JSONArray json) throws JSONException,
	      IOException {
	    for (int i = 0; i < json.size(); ++i) {
	      Object obj = json.get(i);
	  
	      if ((obj instanceof JSONObject == false &&
	    		  obj instanceof JSONArray == false)||obj.toString().equals("null")) {

	      } else if (obj instanceof JSONObject) {
	        walk((JSONObject) obj);
	      }
	    }
	  }
	    
	    
	
	 /**
	   * walk through the @json object. expend it when meet any reference
	   *
	   * @throws IOException
	   *             if any resource does not exist
	   * @throws JSONException
	   *             if any resource is not JSON object and array
	   */
	  @SuppressWarnings("unchecked")
	  private  void walk(JSONObject json) throws JSONException,IOException {
		  List<String> normalkeys = new ArrayList<String>();
		  List<String> hardkeys = new ArrayList<String>();
//
//		  for (Iterator it = json.keys(); it.hasNext();) {
//			  String key = (String) it.next();
//		      Object obj = json.get(key);
//			  if ((obj instanceof JSONObject == false &&
//					  obj instanceof JSONArray == false)||
//					  obj.toString().equals("null")){
//				  normalkeys.add(key);
//			  }else{
//				  hardkeys.add(key);
//			  }
//		  }
		  
	    for(String key : normalkeys){
		      Object obj = json.get(key);
		      itemmap.put(key, obj);
	    }
	    totallist.add(itemmap);
    	itemmap = new HashMap<String, Object>();  
	    
	    for(String key:hardkeys){
	    	Object obj = json.get(key);
	    	if (obj instanceof JSONObject) {	    	  
		        walk( (JSONObject) obj);
		      } else if (obj instanceof JSONArray) {
		        walk( (JSONArray) obj);
		      }
	    }
	  }
	

}
