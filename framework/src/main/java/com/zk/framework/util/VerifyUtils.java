package com.zk.framework.util;

import android.text.TextUtils;

import java.util.List;

/**
 * @author ssw
 */
public class VerifyUtils {

    private VerifyUtils() {
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isEmptyStr(String str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isEmptyList(List list) {
        return list == null || list.isEmpty();
    }
}
