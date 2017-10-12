package com.flyman.app.util.string;

import android.text.TextUtils;

/**
 *  @ClassName ChenkNullUtil
 *  @description 字符串检查工具
 *
 *  @author Flyman
 *  @date 2017/4/7 15:54
 */
public class ChenkNullUtil {
    public static String getNullString(String str)
    {
        String target ="";
        if(TextUtils.isEmpty(str) == false)
        {
            target = str;
        }
        return target;
    }


    /**
     * 检查对象是否为空
     * 空 true
     * 不为空 false
     *
     * @param obj Objects
     * @return boolean true为空 false不为空
     */
    public static boolean isNullObj(Object obj) {
        boolean isNull = false;
        if (obj == null) {
            isNull = true;
        }
        return isNull;
    }
}
