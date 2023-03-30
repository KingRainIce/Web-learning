package myssm.util;

/**
 * @title:StringUtil
 * @Author Ice
 * @Date: 2022/9/11 23:10
 * @Version 1.0
 */

public class StringUtil {

    public static boolean isEmpty(String string){
        return string==null || "".equals(string);
    }

    public static boolean isNotEmpty(String string){
        return !isEmpty(string);
    }
}