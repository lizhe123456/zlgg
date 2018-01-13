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

public class OrderAdapter extends BaseAdapter<ChargInfoBean> {

    public OrderAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseViewHolder holder, ChargInfoBean item, int position) {
        holder.setText(R.id.desc,item.getDesc());
        holder.setGlieuImage(R.id.advert,item.getAdvert());
        holder.setText(R.id.time,item.getStartTime());
        holder.setText(R.id.duration,item.getDuration() + "天");
        if (item.getOrderState() == 0){
            holder.setText(R.id.order_state,"未支付");
            holder.setTextColor(R.id.order_state,R.color.red_1);
        }else {
            if (item.getAdvertState() == 0){
                holder.setText(R.id.order_state,"待审核");
                holder.setTextColor(R.id.order_state,R.color.red_1);
            }else if (item.getAdvertState() == 1){
                holder.setText(R.id.order_state,"已发布");
                holder.setTextColor(R.id.order_state,R.color.green);
            }else if (item.getAdvertState() == 2){
                holder.setText(R.id.order_state,"审核失败");
                holder.setTextColor(R.id.order_state,R.color.red_1);
            }
        }
        holder.setText(R.id.order_num,"订单号： "+item.getOrder_number());
        holder.setText(R.id.price,item.getPrice()+"");

    }

    @Override
    protected int getItemViewLayoutId(int position, ChargInfoBean item) {
        return R.layout.order_item;
    }
}
