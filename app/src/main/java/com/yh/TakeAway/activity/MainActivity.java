package com.yh.TakeAway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.yh.TakeAway.Fragment.UserFinishOrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.yh.TakeAway.Fragment.ManageUserMyFragment;
import com.yh.TakeAway.Fragment.UserHomeFragment;
import com.yh.TakeAway.R;
import com.yh.TakeAway.Fragment.UserNoFinishOrderFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        FragmentManager fragment_container = getSupportFragmentManager();
        FragmentTransaction transaction = fragment_container.beginTransaction();

        //实现第一次访问加载的的界面

        Intent intent = getIntent();
        String sta = intent.getStringExtra("sta");
        if (sta == null) {
            transaction.replace(R.id.user_manage_frame, new UserHomeFragment());

            transaction.commit();
        } else {
            transaction.replace(R.id.user_manage_frame, new ManageUserMyFragment());
            transaction.commit();
        }

        BottomNavigationView bottomNavigationView=findViewById(R.id.user_manage_bottom_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager f = getSupportFragmentManager();
                FragmentTransaction transaction1 = f.beginTransaction();

                int id = item.getItemId();

                if (id == R.id.user_manage_bottom_menu_home) {
                    // 替换为 UserHomeFragment
                    transaction1.replace(R.id.user_manage_frame, new UserHomeFragment());
                } else if (id == R.id.user_manage_bottom_menu_noFinish) {
                    // 替换为 UserNoFinishOrderFragment
                    transaction1.replace(R.id.user_manage_frame, new UserNoFinishOrderFragment());
                } else if (id == R.id.user_manage_bottom_menu_my) {
                    // 替换为 ManageUserMyFragment
                    transaction1.replace(R.id.user_manage_frame, new ManageUserMyFragment());
                }

                // 最后只调用一次 commit()
                transaction1.commit();

                return true;
            }
        });


        //实现搜索功能


    }


    public void showOrder(){
        FragmentManager fragment_container=this.getSupportFragmentManager();
        FragmentTransaction transaction= fragment_container.beginTransaction();
        transaction.replace(R.id.user_manage_frame,new UserFinishOrderFragment());
        transaction.commit();
    }

    public void showMy(){
        FragmentManager fragment_container=this.getSupportFragmentManager();
        FragmentTransaction transaction= fragment_container.beginTransaction();
        transaction.replace(R.id.user_manage_frame,new ManageUserMyFragment());
        transaction.commit();
    }




}
