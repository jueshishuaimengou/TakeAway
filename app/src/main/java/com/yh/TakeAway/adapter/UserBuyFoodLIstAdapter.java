package com.yh.TakeAway.adapter;


import static com.yh.TakeAway.utils.BlobImageHelper.setImageFromBytes;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yh.TakeAway.R;
import com.yh.TakeAway.activity.ManageUserBuyActivity;
import com.yh.TakeAway.dao.DishDao;
import com.yh.TakeAway.dao.VendorDao;
import com.yh.TakeAway.entity.Dish;
import com.yh.TakeAway.entity.Vendor;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserBuyFoodLIstAdapter extends RecyclerView.Adapter<UserBuyFoodLIstAdapter.UserBuyFoodViewHolder> {

    private List<Dish> list;



    private Context contextFather;
    JSONArray jsonArray ;
    public UserBuyFoodLIstAdapter(List<Dish> list,Context contextFather) {
        this.list=list;
        this.contextFather=contextFather;
        jsonArray=new JSONArray();//;
        for (Dish foodBean : list) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("foodId",foodBean.getDishID());
            jsonObject.put("num","0");
            jsonArray.add(jsonObject);
        }
    }


    @NonNull
    @Override
    public UserBuyFoodLIstAdapter.UserBuyFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.list_user_buy_food_list, parent, false);
        return new UserBuyFoodLIstAdapter.UserBuyFoodViewHolder(convertView);
    }


    @Override
    public void onBindViewHolder(@NonNull UserBuyFoodLIstAdapter.UserBuyFoodViewHolder holder, int position) {
        Dish tem = list.get(position);

        int businessId=tem.getVendorID();//商家的账号
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Vendor businessUser = VendorDao.getVendorById(businessId);//获取商家信息
        });


        //价格
        ManageUserBuyActivity fatherView = (ManageUserBuyActivity) contextFather;
        TextView priceZ = fatherView.findViewById(R.id.user_buy_businessPrice);
        TextView food = fatherView.findViewById(R.id.user_buy_businessFood);//存放购买那些商品
        String foodJson=food.getText().toString();


        if(foodJson.isEmpty()){//代表第一次向里面放东西
            //将所有的商品都放入这个food，这个商家的
            food.setText(jsonArray.toJSONString());
        }


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numT=holder.numZ.getText().toString();
                int sl=Integer.valueOf(numT)+1;//0
                holder.numZ.setText(String.valueOf(sl));//是数量
                //
                BigDecimal priceB=new BigDecimal(tem.getPrice());//19.86

                BigDecimal sumPriceF=new BigDecimal(priceZ.getText().toString());

                //0  19.8  2*19.8

                BigDecimal jgB = sumPriceF.add(priceB);//计算后的价格
                priceZ.setText(jgB.toString());

                String foodJson=food.getText().toString();//获取存放购买内容
                JSONArray z = JSONArray.parseArray(foodJson);

                JSONArray newJson=new JSONArray();
                for (Object o : z) {
                    JSONObject temJSon = JSONObject.parseObject(o.toString());
                    if(temJSon.get("foodId").equals(tem.getDishID())){
                        temJSon.put("num",holder.numZ.getText());
                    }
                    newJson.add(temJSon);
                }
                food.setText(newJson.toJSONString());
                Log.d("AAAA",food.getText().toString());

            }
        });

        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numT=holder.numZ.getText().toString();
                int sl=Integer.valueOf(numT)-1;//0
                if(sl>=0){
                    holder.numZ.setText(String.valueOf(sl));
                    BigDecimal sumPriceF=new BigDecimal(priceZ.getText().toString());
                    //
                    BigDecimal priceB=new BigDecimal(tem.getPrice());
                    BigDecimal jgB = sumPriceF.subtract(priceB);//计算后的价格
                    priceZ.setText(jgB.toString());

                    String foodJson=food.getText().toString();//获取存放购买内容
                    JSONArray z = JSONArray.parseArray(foodJson);

                    JSONArray newJson=new JSONArray();
                    for (Object o : z) {
                        JSONObject temJSon = JSONObject.parseObject(o.toString());
                        if(temJSon.get("foodId").equals(tem.getDishID())){
                            temJSon.put("num",holder.numZ.getText());
                        }
                        newJson.add(temJSon);
                    }
                    food.setText(newJson.toJSONString());
                    Log.d("AAAA",food.getText().toString());
                }

            }
        });



        //统计当前月份的销量
        //setImageFromBlob(tem.getImage(),holder.img);
        holder.name.setText(tem.getDishname());
        holder.price.setText("价格:"+tem.getPrice());
        holder.des.setText("描述:"+tem.getDesc());
        setImageFromBytes(tem.getImage(),holder.img);
        executorService.execute(()->{
           int saleNum = DishDao.getMouSalesNum(tem.getDishID());
           holder.num.setText("月销:"+String.valueOf(saleNum));
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class  UserBuyFoodViewHolder extends  RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        TextView num;
        TextView price;
        TextView des;

        ImageView add;
        TextView numZ;
        ImageView sub;

        public UserBuyFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.user_buy_food_list_foodImg);
            name=itemView.findViewById(R.id.user_buy_food_list_name);
            num=itemView.findViewById(R.id.user_buy_food_list_saleNum);//不在咱们的数据当中
            price=itemView.findViewById(R.id.user_buy_food_list_price);
            des=itemView.findViewById(R.id.user_buy_food_list_des);

            add=itemView.findViewById(R.id.user_buy_food_list_add_num);//加
            numZ=itemView.findViewById(R.id.user_buy_food_list_num);//数量
            sub=itemView.findViewById(R.id.user_buy_food_list_sub_num);//减

        }
    }

}