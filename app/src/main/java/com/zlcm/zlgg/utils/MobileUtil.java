package com.zlcm.zlgg.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class MobileUtil {

    public static boolean isMobile(String mobile){
        String regex = "^((1[0-9][0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        if(mobile.length() != 11){
            return false;
        }else{
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobile);
            boolean isMatch = m.matches();
            if(isMatch){
                return true;
            } else {
                return false;
            }
        }
    }
}
