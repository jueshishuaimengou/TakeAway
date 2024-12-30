package com.yh.TakeAway.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.yh.TakeAway.R;
import com.yh.TakeAway.activity.ManageUserBuyActivity;
import com.yh.TakeAway.adapter.AddressListAdapter;

import com.yh.TakeAway.adapter.UserBuyFoodOrderDetailAdapter;
import com.yh.TakeAway.dao.AddressDao;
import com.yh.TakeAway.dao.DishDao;
import com.yh.TakeAway.dao.OderDao;
import com.yh.TakeAway.dao.UserDao;
import com.yh.TakeAway.entity.Address;
import com.yh.TakeAway.entity.Dish;
import com.yh.TakeAway.entity.Oder;
import com.yh.TakeAway.entity.OderItem;
import com.yh.TakeAway.entity.User;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.text.TextUtils;

public class UserBottomDialog {

    private Context context;
    private ManageUserBuyActivity man;
    private int businessId;
    private User user;  // 添加成员变量用于存储用户信息
    private List <Address> addressList;
    public UserBottomDialog(Context context, int businessId) {
        this.context = context;
        this.man = (ManageUserBuyActivity) context;
        this.businessId = businessId;
        init();
    }

    private void init() {
        View bottomSheetLayout = man.getLayoutInflater().inflate(R.layout.user_buy_food_bottom_meu_dialog, null); // 找到布局文件
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(bottomSheetLayout);
        bottomSheetDialog.show();

        // 使用异步加载用户信息
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString("account", "root"); // 如果这个值没有添加则使用默认的
        // 加载用户信息并更新UI
        executorService.execute(() -> {
            user = UserDao.getUserByPhone(phone);  // 将用户信息保存到成员变量中
            addressList = AddressDao.getAllAddressByUserId(user.getUserID());
            // 更新UI需要在主线程执行
            new Handler(Looper.getMainLooper()).post(() -> {
                TextView userName = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_name);
                userName.setText(user.getUsername());

                // 加载收货地址列表
                RecyclerView addressRecycle = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_address_list);

                AddressListAdapter addressListAdapter = new AddressListAdapter(bottomSheetLayout, addressList);
                addressRecycle.setLayoutManager(new LinearLayoutManager(context));
                addressRecycle.setAdapter(addressListAdapter);
            });
        });

        // 实现加载头像
        ImageView userTx = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_img);
        // setImageFromBlob(user.getProfile(), userTx);

        TextView userBuyOderTime = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_time);
        LocalDate currentDate = LocalDate.now();
        Date time = Date.valueOf(currentDate.toString());  // 当前时间

        // 加载收货信息
        TextView receivePeo = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_receivePeo);
        receivePeo.setText("");

        TextView receiveAddress = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_receiveAddress);
        receiveAddress.setText("");

        TextView receivePhone = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_receivePhone);
        receivePhone.setText("");

        // 获取所有商品信息
        RecyclerView listView = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_food_list);
        TextView buyFoodListT = man.findViewById(R.id.user_buy_businessFood);
        String buyFoodListJSON = buyFoodListT.getText().toString();
        JSONArray jsonArray = JSONArray.parseArray(buyFoodListJSON);

        List<OderItem> list = new CopyOnWriteArrayList<>(); // 使用线程安全的集合来避免并发问题

        for (Object o : jsonArray) {
            JSONObject temp = JSONObject.parseObject(o.toString());
            OderItem orderDetailBean = new OderItem();
            orderDetailBean.setDishID(temp.getInteger("foodId"));
            orderDetailBean.setQuantity(temp.getInteger("num"));

            if (temp.getString("num").equals("0")) {
                continue;
            }

            // 异步加载菜品信息
            executorService.execute(() -> {
                Dish food = DishDao.getFoodById(temp.getInteger("foodId"));
                if (food != null) {
                    orderDetailBean.setPrice(food.getPrice());
                    orderDetailBean.setImg(food.getImage());
                    orderDetailBean.setDishName(food.getDishname());

                    // 将更新操作放入主线程
                    new Handler(Looper.getMainLooper()).post(() -> {
                        list.add(orderDetailBean);
                        UserBuyFoodOrderDetailAdapter de = new UserBuyFoodOrderDetailAdapter(list);
                        listView.setLayoutManager(new LinearLayoutManager(context));
                        listView.setAdapter(de);
                        de.notifyDataSetChanged();
                    });
                }
            });
        }

        // 更新总价
        TextView businessPrice = man.findViewById(R.id.user_buy_businessPrice);
        TextView sumPrice = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_sumPrice);
        sumPrice.setText(businessPrice.getText().toString());

        // 取消按钮点击事件
        Button cancelButton = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_cancel);
        cancelButton.setOnClickListener(v -> bottomSheetDialog.cancel());

        // 确认订单按钮点击事件
        Button okButton = bottomSheetLayout.findViewById(R.id.user_buy_food_bottom_meu_dialog_okOrder);
        okButton.setOnClickListener(v -> {
            // 创建订单，向详情表和订单表插入数据
            if (TextUtils.isEmpty(receivePeo.getText()) || TextUtils.isEmpty(receiveAddress.getText()) || TextUtils.isEmpty(receivePhone.getText())) {
                // 在主线程中显示 Toast
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(bottomSheetLayout.getContext(), "请选择收货地址", Toast.LENGTH_SHORT).show()
                );
            } else {
                String address = receivePeo.getText().toString() + "-" + receiveAddress.getText().toString() + "-" + receivePhone.getText().toString();
                executorService.execute(() -> {
                    if (user != null) {
                        Oder oder = new Oder(user.getUserID(), businessId, 1, time, address);
                        int a = OderDao.insertOder(oder);
                        for (OderItem orderDetailBean : list) {
                            orderDetailBean.setOderID(a);
                            orderDetailBean.setImg(DishDao.getFoodById(orderDetailBean.getDishID()).getImage());
                        }
                        if (a >= 1) {
                            // 插入订单详情
                            for (OderItem orderDetailBean : list) {
                                OderDao.insertOrderItem(orderDetailBean);
                            }
                            bottomSheetDialog.cancel();
                            // 在主线程中显示 Toast
                            new Handler(Looper.getMainLooper()).post(() ->
                                    Toast.makeText(bottomSheetLayout.getContext(), "支付成功", Toast.LENGTH_SHORT).show()
                            );
                        } else {
                            // 在主线程中显示 Toast
                            new Handler(Looper.getMainLooper()).post(() ->
                                    Toast.makeText(bottomSheetLayout.getContext(), "购买失败", Toast.LENGTH_SHORT).show()
                            );
                        }
                    } else {
                        // 在主线程中显示 Toast
                        new Handler(Looper.getMainLooper()).post(() ->
                                Toast.makeText(bottomSheetLayout.getContext(), "用户信息加载失败", Toast.LENGTH_SHORT).show()
                        );
                    }
                });
            }
        });
    }
}

