package com.custom.chenxz.object.databean;

import java.util.ArrayList;

public class ImageProvider {

    /**
     * 大图
     *
     * @return
     */
    public static ArrayList<String> getBigImgUrls() {
        ArrayList<String> list = new ArrayList<>();
        list.add("http://pic18.nipic.com/20120103/8783405_180811375100_2.jpg");
        list.add("http://pic25.nipic.com/20121201/10258080_144012468179_2.jpg");
        return list;
    }

    /**
     * 小图，这里随便找两张小图的
     *
     * @return
     */
    public static ArrayList<String> getLowImgUrls() {
        ArrayList<String> list = new ArrayList<>();
        list.add("http://img.qq745.com/uploads/hzbimg/0907/hzb33617.png");
        list.add("http://img.qq745.com/uploads/hzbimg/0907/hzb33616.png");
        return list;
    }
}