package com.yh.TakeAway.adapter;

import static com.yh.TakeAway.utils.BlobImageHelper.setImageFromBytes;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yh.TakeAway.R;
import com.yh.TakeAway.entity.Oder;
import com.yh.TakeAway.entity.OderItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * 这个是用来显示商家商品的一个adapter
 */
public class OrderNoFinishIstDetailAdapter extends RecyclerView.Adapter<OrderNoFinishIstDetailAdapter.OrderViewHolder> {


    private List<OderItem> list;

    public OrderNoFinishIstDetailAdapter(List<OderItem> list) {
        //super(context, R.layout.list_man_order_no_finish_detail_food_list,list);
        this.list=list;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.list_user_buy_food_order_detail_food_list, parent, false);

        return new OrderViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OderItem tem = list.get(position);

        if (tem.getImg() != null) {
            setImageFromBytes(tem.getImg(), holder.imageView);
        }
        holder.name.setText(tem.getDishName());
        if (tem.getQuantity() != 0) {
            holder.num.setText(String.valueOf(tem.getQuantity()));  // 将数字转为字符串
        } else {
            holder.num.setText("0");
        }

        BigDecimal priceZ=new BigDecimal(tem.getPrice());
        BigDecimal numZ=new BigDecimal(tem.getQuantity());

        String jg = priceZ.multiply(numZ).toString();//价格使用的是总价
        holder.price.setText(jg);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * 获取商品总结
     * @return
     */
    public String getSumPrice() {
        // 确保 list 不为 null
        if (list == null || list.isEmpty()) {
            return "0";  // 如果 list 为空，直接返回 0
        }

        BigDecimal total = new BigDecimal(0);
        for (OderItem orderDetailBean : list) {
            BigDecimal priceZ = new BigDecimal(orderDetailBean.getPrice());
            BigDecimal numZ = new BigDecimal(orderDetailBean.getQuantity());
            BigDecimal dj = priceZ.multiply(numZ); // 计算总价
            total = total.add(dj);  // 累加到 total
        }

        return total.toString();  // 返回总价
    }




    static class  OrderViewHolder extends  RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        TextView num;
        TextView price;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.list_user_buy_food_order_detail_food_list_img);
            name=itemView.findViewById(R.id.list_user_buy_food_order_detail_food_list_name);
            num=itemView.findViewById(R.id.list_user_buy_food_order_detail_food_list_num);
            price=itemView.findViewById(R.id.list_user_buy_food_order_detail_food_list_price);

        }
    }





}
