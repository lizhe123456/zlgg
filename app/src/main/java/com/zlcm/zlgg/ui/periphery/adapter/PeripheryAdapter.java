package com.zlcm.zlgg.ui.periphery.adapter;

import android.content.Context;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.adapter.BaseAdapter;
import com.zlcm.zlgg.base.adapter.BaseViewHolder;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;

/**
 * Created by lizhe on 2017/12/29.
 * 类介绍：
 */

public class PeripheryAdapter extends BaseAdapter<PeripheryDeviceBean.Device> {

    public PeripheryAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseViewHolder holder, PeripheryDeviceBean.Device item, int position) {
        holder.setBannerImg(R.id.banner, item.getAdvert());
        holder.setText(R.id.tv_address, item.getAddress());
    }

    @Override
    protected int getItemViewLayoutId(int position, PeripheryDeviceBean.Device item) {
        return R.layout.periphery_item;
    }


}
