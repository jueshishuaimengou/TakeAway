package com.yh.TakeAway.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.yh.TakeAway.R;
import com.yh.TakeAway.adapter.OrderNoFinishUserAdapter;
import com.yh.TakeAway.dao.OderDao;
import com.yh.TakeAway.dao.UserDao;
import com.yh.TakeAway.entity.Oder;
import com.yh.TakeAway.entity.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserNoFinishOrderFragment extends Fragment {
    View rootview;//根石头

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_manage_user_order_no_finish, container, false);

        ListView listView = rootview.findViewById(R.id.user_my_order_no_finish_listView);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString("account", "root");//如果这个值没有添加则使用默认的
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            int sta = 1;
            List<Oder> list = OderDao.getOrdersByPhone(phone, sta);
            new Handler(Looper.getMainLooper()).post(() -> {
                OrderNoFinishUserAdapter orderNoFinishIstAdapter = new OrderNoFinishUserAdapter(rootview.getContext(), list);
                if (list == null || list.size() == 0) {
                    listView.setAdapter(null);
                } else {
                    listView.setAdapter(orderNoFinishIstAdapter);
                }
            });

        });


        return rootview;
    }
}
