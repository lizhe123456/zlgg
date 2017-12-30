package com.zlcm.zlgg.ui.hot.adapter;

import android.content.Context;
import android.text.format.DateUtils;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.adapter.BaseAdapter;
import com.zlcm.zlgg.base.adapter.BaseViewHolder;
import com.zlcm.zlgg.model.bean.AdvertBean;
import com.zlcm.zlgg.utils.TimeUtils;

/**
 * Created by lizhe on 2017/12/28.
 * 类介绍：热门广告适配器
 */

public class HotAdapter extends BaseAdapter<AdvertBean> {


    public HotAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseViewHolder holder, AdvertBean item, int position) {
        holder.setText(R.id.name,item.getNickname())
                .setGlieuImage(R.id.advert,item.getAdvertImg())
                .setGlieuImage(R.id.img,item.getAvatar())
                .setText(R.id.desc,item.getTextInfo())
                .setText(R.id.address,item.getAddress())
                .setText(R.id.tv_time, item.getStartTime());
        if (item.getSid() != 0){
            holder.setText(R.id.ao,"商家")
                    .setBackgroundResource(R.id.ao,R.drawable.tv_store);
        }else {
            holder.setText(R.id.ao,"个人")
                    .setBackgroundResource(R.id.ao,R.drawable.tv_personal);
        }
        if (item.getCredit() > 50){
            holder.setText(R.id.credit,item.getCredit()+"")
                    .setTextColor(R.id.credit,R.color.green);
        }else {
            holder.setText(R.id.credit,item.getCredit()+"  差")
                    .setTextColor(R.id.credit,R.color.red_1);
        }
    }

    @Override
    protected int getItemViewLayoutId(int position, AdvertBean item) {
        return R.layout.advert_item;
    }
}
