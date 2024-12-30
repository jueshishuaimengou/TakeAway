package com.yh.TakeAway.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;



import com.yh.TakeAway.R;
import com.yh.TakeAway.adapter.VendorListAdapter;

import com.yh.TakeAway.dao.VendorDao;

import com.yh.TakeAway.entity.Vendor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserHomeFragment extends Fragment {

    private View rootView;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Initialize ListView
        listView = rootView.findViewById(R.id.user_home_food_listView);

        // Fetch and set initial vendor list in the background
        fetchVendorListInBackground();

        // Set up SearchView for filtering
        SearchView searchView = rootView.findViewById(R.id.user_home_food_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchVendorByNameInBackground(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchVendorByNameInBackground(newText);
                return false;
            }
        });

        return rootView;
    }

    private void fetchVendorListInBackground() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Vendor> list = VendorDao.getAllVendorList();

            // 在主线程中更新 UI，首先确保 Activity 仍然有效
            if (getActivity() != null && !getActivity().isFinishing()) {
                getActivity().runOnUiThread(() -> updateListView(list));
            }
        });
    }

    private void fetchVendorByNameInBackground(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Vendor> list = VendorDao.getVendorByName(name);

            // 在主线程中更新 UI，首先确保 Activity 仍然有效
            if (getActivity() != null && !getActivity().isFinishing()) {
                getActivity().runOnUiThread(() -> updateListView(list));
            }
        });
    }

    private void updateListView(List<Vendor> list) {
        if (list == null || list.isEmpty()) {
            listView.setAdapter(null);
        } else {
            VendorListAdapter adapter = new VendorListAdapter(getContext(), list);
            listView.setAdapter(adapter);
        }
    }

}

