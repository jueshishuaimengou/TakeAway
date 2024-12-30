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
import com.yh.TakeAway.entity.OderItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * 这个是用来显示商家商品的一个adapter
 */
public class UserBuyFoodOrderDetailAdapter extends RecyclerView.Adapter<UserBuyFoodOrderDetailAdapter.OrderViewHolder> {

    private List<OderItem> list;

    public UserBuyFoodOrderDetailAdapter(List<OderItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.list_user_buy_food_order_detail_food_list, parent, false);
        return new OrderViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OderItem tem = list.get(position);

        // 图片设置（如果有的话）
        if (tem.getImg() != null) {
            setImageFromBytes(tem.getImg(), holder.imageView);
        }

        // 设置商品名称
        if (tem.getDishName() != null && !tem.getDishName().isEmpty()) {
            holder.name.setText(tem.getDishName());
        } else {
            holder.name.setText("未知商品");
        }

        if (tem.getQuantity() != 0) {
            holder.num.setText(String.valueOf(tem.getQuantity()));  // 将数字转为字符串
        } else {
            holder.num.setText("0");
        }


        // 设置价格（确保转换为 BigDecimal 时不会出错）
        try {
            BigDecimal priceZ = new BigDecimal(tem.getPrice());
            BigDecimal numZ = new BigDecimal(tem.getQuantity());

            String jg = priceZ.multiply(numZ).toString(); // 价格使用的是总价
            holder.price.setText(jg);
        } catch (NumberFormatException e) {
            holder.price.setText("0");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        TextView num;
        TextView price;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_user_buy_food_order_detail_food_list_img);
            name = itemView.findViewById(R.id.list_user_buy_food_order_detail_food_list_name);
            num = itemView.findViewById(R.id.list_user_buy_food_order_detail_food_list_num);
            price = itemView.findViewById(R.id.list_user_buy_food_order_detail_food_list_price);
        }
    }
}
