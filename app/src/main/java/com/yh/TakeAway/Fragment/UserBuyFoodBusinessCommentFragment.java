package com.yh.TakeAway.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import com.yh.TakeAway.R;
import com.yh.TakeAway.adapter.CommentListAdapter;
import com.yh.TakeAway.dao.ReviewDao;
import com.yh.TakeAway.entity.Review;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserBuyFoodBusinessCommentFragment extends Fragment {




    private int businessId;
    public UserBuyFoodBusinessCommentFragment(int businessId){
        this.businessId=businessId;
    }




    View rootview;//根石头

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_use_buy_food_comment, container, false);

        //接下来要写的是适配器
        ListView listView = rootview.findViewById(R.id.user_buy_food_comment_listView);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            // 获取数据（子线程中）
            List<Review> list = ReviewDao.getReviewsByVendorId(businessId);

            // 切换回主线程更新UI
            new Handler(Looper.getMainLooper()).post(() -> {
                if (list == null || list.isEmpty()) {
                    listView.setAdapter(null);
                } else {
                    CommentListAdapter commentListAdapter = new CommentListAdapter(getContext(), list);
                    listView.setAdapter(commentListAdapter);
                }
            });
        });




        return rootview;
    }


}