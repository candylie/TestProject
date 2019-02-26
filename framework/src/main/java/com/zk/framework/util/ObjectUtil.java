package com.zk.framework.util;

import java.util.Map;

/**
 * -
 *
 * @author 张科
 * @date 2018/12/6.
 */
public class ObjectUtil {
    public static boolean isEmpty(Iterable list) {
        return list == null || !list.iterator().hasNext();
    }

    public static boolean isEmpty(Map list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Object[] list) {
        return list == null || list.length == 0;
    }

    public static boolean isEmpty(String[] list) {
        return list == null || list.length == 0;
    }

    public static boolean isEmpty(Character[] list) {
        return list == null || list.length == 0;
    }

    public static boolean isEmpty(Double[] list) {
        return list == null || list.length == 0;
    }

    public static boolean isEmpty(Float[] list) {
        return list == null || list.length == 0;
    }

    public static boolean isEmpty(Long[] list) {
        return list == null || list.length == 0;
    }

    public static boolean isEmpty(Integer[] list) {
        return list == null || list.length == 0;
    }

    public static boolean isEmpty(Boolean[] list) {
        return list == null || list.length == 0;
    }

}
