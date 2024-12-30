package com.yh.TakeAway.adapter;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yh.TakeAway.R;
import com.yh.TakeAway.dao.UserDao;
import com.yh.TakeAway.entity.Address;
import com.yh.TakeAway.entity.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressViewHolder> {
    private List<Address> list;
    private View parent;
    private User user;

    public AddressListAdapter(View parent, List<Address> list) {
        this.list = list;
        this.parent = parent;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.list_user_address_list, parent, false);
        return new AddressViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address tem = list.get(position);

        // 先获取用户信息
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            user = UserDao.getUserByUserID(tem.getUserID()); // 异步加载用户数据

            // 在主线程中更新UI
            new Handler(Looper.getMainLooper()).post(() -> {
                if (user != null) {
                    holder.peo.setText(user.getUsername());
                    holder.phone.setText(user.getPhone());
                }

                holder.address.setText(tem.getAddress());  // 地址信息是同步加载的，直接设置

                // 点击事件
                holder.itemView.setOnClickListener(v -> {
                    // 确保用户数据已经加载
                    if (user != null) {
                        TextView receivePeo = parent.findViewById(R.id.user_buy_food_bottom_meu_dialog_receivePeo);
                        TextView receiveAddress = parent.findViewById(R.id.user_buy_food_bottom_meu_dialog_receiveAddress);
                        TextView receivePhone = parent.findViewById(R.id.user_buy_food_bottom_meu_dialog_receivePhone);

                        receivePeo.setText(user.getUsername());
                        receiveAddress.setText(tem.getAddress());
                        receivePhone.setText(user.getPhone());
                    }
                });
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView peo;
        TextView address;
        TextView phone;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            peo = itemView.findViewById(R.id.list_user_address_receivePeo);
            address = itemView.findViewById(R.id.list_user_address_receiveAddress);
            phone = itemView.findViewById(R.id.list_user_address_receivePhone);
        }
    }
}
