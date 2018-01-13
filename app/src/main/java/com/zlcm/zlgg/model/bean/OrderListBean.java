package com.zlcm.zlgg.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizhe on 2018/1/7.
 * 类介绍：
 */

public class OrderListBean implements Serializable {

    private List<ChargInfoBean> order;

    public List<ChargInfoBean> getList() {
        return order;
    }

    public void setList(List<ChargInfoBean> order) {
        this.order = order;
    }
}
