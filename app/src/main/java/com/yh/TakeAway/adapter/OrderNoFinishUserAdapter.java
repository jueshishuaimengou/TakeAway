package com.yh.TakeAway.adapter;

import static com.yh.TakeAway.utils.BlobImageHelper.setImageFromBytes;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yh.TakeAway.R;
import com.yh.TakeAway.dao.OderDao;
import com.yh.TakeAway.dao.UserDao;
import com.yh.TakeAway.entity.Oder;
import com.yh.TakeAway.entity.OderItem;
import com.yh.TakeAway.entity.User;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderNoFinishUserAdapter extends ArrayAdapter<Oder> {

    private List<Oder> list;
    private Context context;

    public OrderNoFinishUserAdapter(@NonNull Context context, List<Oder> list) {
        super(context, R.layout.list_user_order_no_finish_list, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_user_order_no_finish_list, parent, false);
        }

        Oder tem = list.get(position);
        ImageView imageView = convertView.findViewById(R.id.list_user_order_no_finish_list_img);
        TextView name = convertView.findViewById(R.id.list_user_order_no_finish_list_name);
        // 获取用户信息并加载头像
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            User commonUser = UserDao.getUserByUserID(tem.getUserID());
            new Handler(Looper.getMainLooper()).post(() -> {
                if (commonUser != null && commonUser.getProfile() != null) {
                    setImageFromBytes(commonUser.getProfile(), imageView);
                }
                // 设置用户名
                if (commonUser != null) {
                    name.setText(commonUser.getUsername());
                    // 如果用户有头像，也可以设置头像
                } else {
                    name.setText("未知用户");
                }
            });
        });

        // 设置订单时间
        TextView time = convertView.findViewById(R.id.list_user_order_no_finish_list_time);
        time.setText(tem.getOderTime().toString());

        // 处理地址
        String[] address = tem.getAddress().split("-");
        if (address.length >= 3) {
            TextView receivePeo = convertView.findViewById(R.id.list_user_order_no_finish_list_receivePeo);
            receivePeo.setText(address[0]);

            TextView receiveAddress = convertView.findViewById(R.id.list_user_order_no_finish_list_receiveAdderss);
            receiveAddress.setText(address[2]);

            TextView phone = convertView.findViewById(R.id.list_user_order_no_finish_list_receivePhone);
            phone.setText(address[1]);
        }

        // 处理订单详情的 RecyclerView
        RecyclerView listDe = convertView.findViewById(R.id.list_user_order_no_finish_list_foodList);
        TextView sumPrice = convertView.findViewById(R.id.list_user_order_no_finish_list_sumPrice);
        executorService.execute(()->{
            List<OderItem> detailList = OderDao.getOrderItemsByOrderId(tem.getOderID());
            if (getContext() != null) {
                ((Activity) getContext()).runOnUiThread(() -> {
                    // 设置评分和销售量
                    OrderNoFinishIstDetailAdapter de = new OrderNoFinishIstDetailAdapter(detailList);
                    listDe.setLayoutManager(new LinearLayoutManager(getContext()));
                    listDe.setAdapter(de);
                    sumPrice.setText(de.getSumPrice());
                });
            }
        });



        // 更新总价


        // 设置取消订单按钮的点击事件
        Button cancel = convertView.findViewById(R.id.list_user_order_no_finish_list_cancelOrder);
        cancel.setOnClickListener(v -> {
            executorService.execute(()->{
                int a = OderDao.updateOrderStatus(tem.getOderID(), 2);
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (a == 1) {
                        list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(getContext(), "取消订单成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "取消订单失败", Toast.LENGTH_SHORT).show();
                    }
                });

            });

        });

        return convertView;
    }
}
