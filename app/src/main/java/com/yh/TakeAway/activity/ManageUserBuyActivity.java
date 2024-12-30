package com.yh.TakeAway.activity;



import static com.yh.TakeAway.utils.BlobImageHelper.setImageFromBytes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yh.TakeAway.Fragment.UserBuyFoodBusinessCommentFragment;
import com.yh.TakeAway.Fragment.UserBuyFoodBusinessFragment;
import com.yh.TakeAway.R;
import com.yh.TakeAway.dialog.UserBottomDialog;
import com.yh.TakeAway.entity.Vendor;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户购买界面
 */
public class ManageUserBuyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user_buy);

        //实现一个返回的功能
        Toolbar toolbar = findViewById(R.id.user_buy_bar);
        setSupportActionBar(toolbar);

        //返回有两种，采用跳转和管理
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });



        Vendor business =(Vendor)  getIntent().getSerializableExtra("business");
        //实现一个返回的功能
        ImageView businessImg = findViewById(R.id.user_buy_businessTx);
        setImageFromBytes(business.getImage(), businessImg);

        TextView businessName = findViewById(R.id.user_buy_businessName);
        businessName.setText(business.getVenname());

        TextView businessDes = findViewById(R.id.user_buy_businessDes);
        businessDes.setText("简介: "+business.getDesc());

        //实现显示数量
        TextView price =this.findViewById(R.id.user_buy_businessPrice);
        price.setText("0.00");
        TextView foodJson =this.findViewById(R.id.user_buy_businessFood);


        Button buy =this.findViewById(R.id.user_buy_businessBuy_con);
        //
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String foodJsonT = foodJson.getText().toString();
                JSONArray jsonArray=JSONArray.parseArray(foodJsonT);

                List<JSONObject> buyFoodList=new ArrayList<>();//存放素有购买商品的列表
                for (Object o : jsonArray) {
                    JSONObject temp = JSONObject.parseObject(o.toString());
                    if(!temp.get("num").equals("0")){
                        buyFoodList.add(temp);
                    }
                }
                if(foodJsonT.isEmpty()){
                    Toast.makeText(ManageUserBuyActivity.this, "未选择商品无法结算", Toast.LENGTH_SHORT).show();
                }else if(buyFoodList.size()==0){
                    Toast.makeText(ManageUserBuyActivity.this, "未选择商品无法结算", Toast.LENGTH_SHORT).show();
                }else{
                    UserBottomDialog userBottomDialog=new UserBottomDialog(ManageUserBuyActivity.this,business.getVendorID());
                }


            }
        });

        //点击结算按钮之后的事情


        TabLayout tabLayout = findViewById(R.id.user_buy_tab);
        ViewPager2 viewPager = findViewById(R.id.user_buy_pager);
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {

                if(position==0){
                    return new UserBuyFoodBusinessFragment(business.getVendorID());
                }else{
                    return new UserBuyFoodBusinessCommentFragment(business.getVendorID());
                }

            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        new TabLayoutMediator(tabLayout,viewPager,((tab, position) ->{
                if(position==0){
                    tab.setText("点餐");
                }else{
                    tab.setText("评论");
                }
        } )).attach();



    }
}