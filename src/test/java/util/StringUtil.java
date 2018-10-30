

/**
 * Created by hp on 2018/7/9.
 */
package util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String humpToLine(String str){
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static Boolean isNull(String str){
        if(str == null || "".equalsIgnoreCase(str.trim())){
            return true;
        }
        return false;
    }
}
