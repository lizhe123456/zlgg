package com.zlcm.zlgg.ui.release.adapter;

import android.content.Context;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.adapter.BaseAdapter;
import com.zlcm.zlgg.base.adapter.BaseViewHolder;
import com.zlcm.zlgg.model.bean.ChargInfoBean;

/**
 * Created by lizhe on 2018/1/7.
 * 类介绍：
 */

public class AddressListAdapter extends BaseAdapter<ChargInfoBean.Device> {

    public AddressListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseViewHolder holder, ChargInfoBean.Device item, int position) {
        holder.setText(R.id.address,item.getAddress());
    }

    @Override
    protected int getItemViewLayoutId(int position, ChargInfoBean.Device item) {
        return R.layout.address_list_item;
    }
}
