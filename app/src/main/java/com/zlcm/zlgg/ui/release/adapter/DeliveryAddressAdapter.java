package com.zlcm.zlgg.ui.release.adapter;

import android.content.Context;
import android.view.View;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.app.Constants;
import com.zlcm.zlgg.base.adapter.BaseAdapter;
import com.zlcm.zlgg.base.adapter.BaseViewHolder;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizhe on 2018/1/4.
 * 类介绍：
 */

public class DeliveryAddressAdapter extends BaseAdapter<PeripheryDeviceBean.Device>{


    private OnCheckeedListener onCheckeedListener;

    private ArrayList<Integer> device = new ArrayList<>();

    public DeliveryAddressAdapter(Context context) {
        super(context);
    }

    @Override
    public void addFirstDataSet(List<PeripheryDeviceBean.Device> data) {
        super.addFirstDataSet(data);
        for (int i = 0; i < getDataSource().size(); i++) {
            if (getDataSource().get(i).isCheckeed()){
                if (!device.contains(getDataSource().get(i).getDid())) {
                    device.add(getDataSource().get(i).getDid());
                }
            }
        }
    }

    @Override
    public void addMoreDataSet(List<PeripheryDeviceBean.Device> data) {
        super.addMoreDataSet(data);
        for (int i = 0; i < getDataSource().size(); i++) {
            if (getDataSource().get(i).isCheckeed()){
                if (!device.contains(getDataSource().get(i).getDid())) {
                    device.add(getDataSource().get(i).getDid());
                }
            }
        }
    }

    public void setOnCheckeedListener(OnCheckeedListener onCheckeedListener) {
        this.onCheckeedListener = onCheckeedListener;
    }

    @Override
    protected void bindDataToItemView(final BaseViewHolder holder, final PeripheryDeviceBean.Device item, int position) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < item.getAdvert().size(); i++) {
            list.add(Constants.ZL_URL + "/"+ item.getAdvert().get(i));
        }
        holder.setBannerImg(R.id.banner, list);
        holder.setText(R.id.tv_address, item.getAddress());
        if (item.isCheckeed()){
            holder.setImageResource(R.id.checkeed,R.drawable.checked);
        }else {
            holder.setImageResource(R.id.checkeed,R.drawable.un_checked);
        }

        holder.setOnClickListener(R.id.checkeed, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.isCheckeed()){
                    item.setCheckeed(false);
                    holder.setImageResource(R.id.checkeed,R.drawable.un_checked);
                    device.remove((Integer) item.getDid());
                }else {
                    item.setCheckeed(true);
                    holder.setImageResource(R.id.checkeed,R.drawable.checked);
                    if (!device.contains(item.getDid())) {
                        device.add(item.getDid());
                    }
                }
                onCheckeedListener.onCheckeed(device);
            }
        });
    }

    @Override
    protected int getItemViewLayoutId(int position, PeripheryDeviceBean.Device item) {
        return R.layout.device_item;
    }

    public interface OnCheckeedListener{
        void onCheckeed(ArrayList<Integer> device);
    }
}
