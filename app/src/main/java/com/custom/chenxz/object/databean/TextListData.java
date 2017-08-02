package com.custom.chenxz.object.databean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class TextListData {
    public static List<TextListData> textListDatas = new ArrayList<>();
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TextListData(String msg) {
        this.msg = msg;
    }

    public TextListData() {
    }

    static {
        for (int i = 1; i <= 50; i++) {
            textListDatas.add(new TextListData("这是第" + i + "条数据"));
        }
    }
}
