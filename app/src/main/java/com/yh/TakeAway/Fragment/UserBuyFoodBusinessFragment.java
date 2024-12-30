package com.yh.TakeAway.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yh.TakeAway.R;
import com.yh.TakeAway.adapter.UserBuyFoodLIstAdapter;
import com.yh.TakeAway.dao.DishDao;
import com.yh.TakeAway.entity.Dish;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserBuyFoodBusinessFragment extends Fragment {

    private View rootview;
    private int businessId;

    public UserBuyFoodBusinessFragment(int businessId) {
        this.businessId = businessId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_user_buy_food, container, false);

        RecyclerView listView = rootview.findViewById(R.id.user_buy_food_listView);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchFoodListInBackground(businessId, listView);

        return rootview;
    }

    private void fetchFoodListInBackground(int businessId, RecyclerView listView) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<Dish> list = DishDao.getFoodListByVendorID(businessId);
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> updateRecyclerView(list, listView));
            }
        });
    }

    private void updateRecyclerView(List<Dish> list, RecyclerView listView) {
        if (list == null || list.isEmpty()) {
            listView.setAdapter(null);
        } else {
            UserBuyFoodLIstAdapter adapter = new UserBuyFoodLIstAdapter(list, getContext());
            listView.setAdapter(adapter);
        }
    }
}
