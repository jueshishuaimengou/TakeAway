package com.yh.TakeAway.adapter;


import static com.yh.TakeAway.utils.BlobImageHelper.setImageFromBytes;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yh.TakeAway.R;
import com.yh.TakeAway.dao.UserDao;
import com.yh.TakeAway.entity.Review;
import com.yh.TakeAway.entity.User;
import com.yh.TakeAway.utils.BlobImageHelper;

import java.sql.Blob;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommentListAdapter extends ArrayAdapter<Review> {
    private List<Review> list;

    private Context context;


    public CommentListAdapter(@NonNull Context context, List<Review> list) {
        super(context, R.layout.list_man_comment_list,list);
        this.context=context;
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_man_comment_list, viewGroup, false);
        }

        Review tem = list.get(position);

        // 获取控件
        ImageView img = convertView.findViewById(R.id.man_comment_tx);
        TextView name = convertView.findViewById(R.id.man_comment_name);
        TextView c = convertView.findViewById(R.id.man_comment_con);
        TextView time = convertView.findViewById(R.id.man_comment_time);
        TextView userCon = convertView.findViewById(R.id.man_comment_userCon);
        ImageView imgZ = convertView.findViewById(R.id.man_comment_userImg);

        // 显示评论时间
        time.setText(tem.getTime().toString());

        // 显示评分内容
        int[] icoIdZ = {
                R.id.man_comment_one, R.id.man_comment_two,
                R.id.man_comment_three, R.id.man_comment_four, R.id.man_comment_five
        };
        int scoreZ = Integer.valueOf(tem.getV_rating());
    /*    String[] conA = {"非常差", "差", "一般", "满意", "非常满意"};
        c.setText(conA[scoreZ - 1]);
        for (int i = scoreZ; i < 5; i++) {
            ImageView temp = convertView.findViewById(icoIdZ[i]);
            temp.setImageResource(R.drawable.wxx); // 灰色星星
        }
        for (int i = 0; i < scoreZ; i++) {
            ImageView temp = convertView.findViewById(icoIdZ[i]);
            temp.setImageResource(R.drawable.xx); // 黄色星星
        }
*/
        // 显示评论内容
        userCon.setText(tem.getText());

        // 隐藏或显示图片
        if (tem.getImg() == null) {
            imgZ.setVisibility(View.GONE);
        } else {
            imgZ.setVisibility(View.VISIBLE);
            setImageFromBytes(tem.getImg(), imgZ);
        }

        // 异步加载用户信息
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            // 获取用户信息
            User commonUser = UserDao.getUserByUserID(tem.getUserID());
            // 切换到主线程更新UI
            new Handler(Looper.getMainLooper()).post(() -> {
                if (commonUser != null) {
                    name.setText(commonUser.getUsername());
                    // 如果用户有头像，也可以设置头像
                    if (commonUser.getProfile() != null) {
                        setImageFromBytes(commonUser.getProfile(), img);
                    }
                } else {
                    name.setText("未知用户");
                }
            });
        });

        return convertView;
    }

}
