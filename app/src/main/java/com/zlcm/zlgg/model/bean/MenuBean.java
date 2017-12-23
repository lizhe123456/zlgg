package com.zlcm.zlgg.model.bean;

import java.io.Serializable;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 * 也可做成服务传动态数据
 */

public class MenuBean implements Serializable{

    //标题图片
    private int img;
    //标题
    private String title;

    public MenuBean(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
