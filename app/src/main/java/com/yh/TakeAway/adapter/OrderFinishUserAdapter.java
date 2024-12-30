package com.yh.TakeAway.adapter;

import static com.yh.TakeAway.utils.BlobImageHelper.setImageFromBytes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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

public class OrderFinishUserAdapter extends ArrayAdapter<Oder> {


    private List<Oder> list;

    private Context context;
    List<OderItem> detailList;
    private User user;
    public OrderFinishUserAdapter(@NonNull Context context, List<Oder> list) {
        super(context, R.layout.list_user_order_finish_list,list);
        this.context=context;
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup){
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.list_user_order_finish_list,viewGroup,false);
        }
        ImageView img=convertView.findViewById(R.id.list_user_order_finish_list_img);
        //TextView a=convertView.findViewById(R.id.xz);
        //a.setText("AAA");
        TextView receivePeo=convertView.findViewById(R.id.list_user_order_finish_list_receivePeo);
        TextView receiveAdderss=convertView.findViewById(R.id.list_user_order_finish_list_receiveAdderss);
        TextView phone=convertView.findViewById(R.id.list_user_order_finish_list_receivePhone);
        RecyclerView listDe=convertView.findViewById(R.id.list_user_order_finish_list_foodList);
        Oder tem = list.get(position);
        TextView name=convertView.findViewById(R.id.list_user_order_finish_list_name);
        TextView time=convertView.findViewById(R.id.list_user_order_finish_list_time);
        int userId=tem.getUserID();
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            user= UserDao.getUserByUserID(userId);
            if (getContext() != null) {
                ((Activity) getContext()).runOnUiThread(() -> {
                    // 设置评分和销售量
                  setImageFromBytes(user.getProfile(), img);
                });
            }
            ((Activity) getContext()).runOnUiThread(()->{
                time.setText(tem.getOderTime().toString());
                name.setText(user.getUsername());
                String address[]=tem.getAddress().split("-");
                receivePeo.setText(address[0]);
                receiveAdderss.setText(address[2]);
                phone.setText(address[1]);
                phone.setText(address[1]);
            });
        });


        //需要加载用户的头像，但是这读取的数据当中没事用户的头像这就很尴尬

        executorService.execute(()->{
            detailList= OderDao.getOrderItemsByOrderId(tem.getOderID());
        });

        //再加载一个listview
        OrderNoFinishIstDetailAdapter de=new OrderNoFinishIstDetailAdapter(detailList);
        listDe.setLayoutManager(new LinearLayoutManager(getContext()));
        if(detailList==null||detailList.size()==0){
            listDe.setAdapter(null);
        }else{
            listDe.setAdapter(de);
            de.notifyDataSetChanged();
        }



        TextView sumPrice=convertView.findViewById(R.id.list_user_order_finish_list_sumPrice);
        sumPrice.setText(de.getSumPrice());


        Button cancel =convertView.findViewById(R.id.list_user_order_finish_list_comment);
        TextView sta=convertView.findViewById(R.id.list_user_order_finish_list_sta);
        if(tem.getStatus()==1){
            sta.setText("订单待处理");
            cancel.setVisibility(View.GONE);
        }
        if(tem.getStatus()==2){
            sta.setText("订单已取消");
            cancel.setVisibility(View.GONE);
        }
        if(tem.getStatus()==3){
            sta.setText("订单已完成");
            cancel.setVisibility(View.VISIBLE);
        }



        cancel.setOnClickListener(new View.OnClickListener() {//是评论订单内容
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}
