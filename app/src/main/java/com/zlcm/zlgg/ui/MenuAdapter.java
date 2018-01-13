package com.zlcm.zlgg.ui;


import android.content.Context;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.adapter.BaseAdapter;
import com.zlcm.zlgg.base.adapter.BaseViewHolder;
import com.zlcm.zlgg.model.bean.MenuBean;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class MenuAdapter extends BaseAdapter<MenuBean> {


    public MenuAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseViewHolder holder, MenuBean item, int position) {
        holder.setText(R.id.title,item.getTitle())
                .setImageResource(R.id.iv_img,item.getImg());
        holder.setVisible(R.id.money,false);
//        if (position == 2){
//            holder.setVisible(R.id.money,true);
//        }
    }

    @Override
    protected int getItemViewLayoutId(int position, MenuBean item) {
        return R.layout.menu_item;
    }

}
