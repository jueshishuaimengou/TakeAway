package com.yh.TakeAway.Fragment;



import static com.yh.TakeAway.utils.BlobImageHelper.setImageFromBytes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.yh.TakeAway.R;
import com.yh.TakeAway.activity.MainActivity;
import com.yh.TakeAway.dao.UserDao;
import com.yh.TakeAway.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ManageUserMyFragment extends Fragment {

    View rootview;//根石头
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.fragment_manage_user_my, container, false);
        TextView des =rootview.findViewById(R.id.user_manage_user_my_address);//账号
        TextView name =rootview.findViewById(R.id.user_manage_user_my_name);//账号
        ImageView tx=rootview.findViewById(R.id.user_manage_user_my_tx);//头像
        TextView accountZ =rootview.findViewById(R.id.user_manage_user_my_account);//账号
        //第一步需要实现，管理员信息的加载，信息的加载就必须，要有一个账号
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String phone=sharedPreferences.getString("account","root");//如果这个值没有添加则使用默认的

        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            user = UserDao.getUserByPhone(phone);
            new Handler(Looper.getMainLooper()).post(()->{
                accountZ.setText(user.getPhone());
                name.setText(user.getUsername());
                des.setText("注册时间:"+user.getRegistertime().toString());
                setImageFromBytes(user.getProfile(), tx);
            });
        });



        /**
         * 推出系统
         */
        MainActivity mainActivity =(MainActivity) getActivity();
        TextView exit =rootview.findViewById(R.id.user_manage_user_my_exit);//账号
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mainActivity.finishAffinity();
            }
        });
        //注销
        TextView zx =rootview.findViewById(R.id.user_manage_user_my_zx);//账号
        zx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });




        TextView orderMan =rootview.findViewById(R.id.user_manage_user_my_order);//账号
        orderMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.showOrder();
            }
        });
/*
        TextView orderManA =rootview.findViewById(R.id.user_manage_user_my_res_address);//账号
        orderManA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ManageUserAddressActivity.class);
                startActivity(intent);
            }
        });*/

        return rootview;
    }

}