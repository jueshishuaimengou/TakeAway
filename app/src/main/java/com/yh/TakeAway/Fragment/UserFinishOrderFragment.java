package com.yh.TakeAway.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.yh.TakeAway.R;
import com.yh.TakeAway.activity.MainActivity;
import com.yh.TakeAway.adapter.OrderFinishUserAdapter;
import com.yh.TakeAway.adapter.OrderNoFinishUserAdapter;
import com.yh.TakeAway.dao.OderDao;
import com.yh.TakeAway.entity.Oder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserFinishOrderFragment extends Fragment {
    View rootview;//根石头
    private  List<Oder> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.fragment_manage_user_order_finish, container, false);

        ListView listView= rootview.findViewById(R.id.user_my_order_finish_listView);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String phone=sharedPreferences.getString("account","root");//如果这个值没有添加则使用默认的
        int sta=3;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
           list = OderDao.getOrdersByPhone(phone,sta);
           getActivity().runOnUiThread(()->{
               OrderFinishUserAdapter orderFinishIstAdapter=new OrderFinishUserAdapter(rootview.getContext(),list);
               if(list==null||list.size()==0){
                   listView.setAdapter(null);
               }else{
                   listView.setAdapter(orderFinishIstAdapter);
               }
           });
        });


        MainActivity man = (MainActivity) getActivity();

        ImageView imgBack=rootview.findViewById(R.id.user_my_order_finish_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                man.showMy();
            }
        });



        return rootview;
    }



}
