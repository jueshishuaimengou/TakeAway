package com.yh.TakeAway.adapter;


import static com.yh.TakeAway.utils.BlobImageHelper.setImageFromBytes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yh.TakeAway.R;

import com.yh.TakeAway.activity.ManageUserBuyActivity;
import com.yh.TakeAway.dao.DishDao;
import com.yh.TakeAway.dao.ReviewDao;
import com.yh.TakeAway.entity.Vendor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VendorListAdapter extends ArrayAdapter<Vendor> {

    private List<Vendor> vendors;

    public VendorListAdapter(@NonNull Context context, List<Vendor> vendors) {
        super(context, R.layout.vendor_list_show, vendors);
        this.vendors = vendors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.vendor_list_show, parent, false);
        }

        Vendor vendor = vendors.get(position);

        // 视图组件
        ImageView img = convertView.findViewById(R.id.user_vendor_list_foodImg);
        TextView name = convertView.findViewById(R.id.user_vendor_list_name);
        TextView saleNum = convertView.findViewById(R.id.user_vendor_list_saleNum);
        TextView address = convertView.findViewById(R.id.user_vendor_list_address);
        TextView description = convertView.findViewById(R.id.user_vendor_list_des);
        TextView phone = convertView.findViewById(R.id.user_vendor_list_phone);
        TextView businessRating = convertView.findViewById(R.id.user_vendor_list_businessPf);

        // 设置商家信息
        setImageFromBytes(vendor.getImage(), img);
        name.setText(vendor.getVenname());
        address.setText(vendor.getAddress());
        description.setText("描述: " + vendor.getDesc());
        phone.setText("联系电话: " + vendor.getPhone());

        // 使用异步线程获取评分和销售量
        fetchVendorDataAsync(vendor, businessRating, saleNum);

        // 设置点击事件，跳转到商家页面
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ManageUserBuyActivity.class);
            intent.putExtra("business", vendor);
            getContext().startActivity(intent);
        });

        return convertView;
    }

    // 异步获取评分和销售量
    private void fetchVendorDataAsync(Vendor vendor, TextView businessRating, TextView saleNum) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            // 获取评分和销售量
            Double rating = ReviewDao.getAvgRatingByVendorId(vendor.getVendorID());
            int totalSales = DishDao.getMouSalesNumVen(vendor.getVendorID());

            // 更新UI，注意这里需要回到主线程
            if (getContext() != null) {
                ((Activity) getContext()).runOnUiThread(() -> {
                    // 设置评分和销售量
                    businessRating.setText(rating != null ? rating + " 分" : "暂无评分");
                    saleNum.setText("总销售: " + totalSales);
                });
            }
        });
    }


}

