package com.custom.chenxz.object.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/2.
 */

public class CollectionsUtils {
    private static final String TAG = "CollectionsUtils";

    /**
     * 完全去除重复的数据，不保留
     *
     * @param list
     */
    public static void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        Log.e(TAG, "removeDuplicateWithOrder: " + list);
    }

    /**
     * 去除重复的数据保留一个
     *
     * @param list
     */
    public static void removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
    }
}
