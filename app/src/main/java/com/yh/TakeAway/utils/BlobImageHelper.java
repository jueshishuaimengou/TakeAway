package com.yh.TakeAway.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.sql.Blob;
import java.sql.SQLException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;

public class BlobImageHelper {

    /**
     * 从 byte[] 数据中提取图像并设置到 ImageView。
     *
     * @param imageBytes 图像的 byte[] 数据。
     * @param imageView  要设置图像的 ImageView。
     */
    public static void setImageFromBytes(byte[] imageBytes, ImageView imageView) {
        if (imageBytes != null && imageView != null) {
            // 转换 byte[] 为 Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            // 设置到 ImageView
            imageView.setImageBitmap(bitmap);
        } /*else {
            throw new IllegalArgumentException("imageBytes or imageView is null.");
        }*/
    }

    /**
     * 从 ImageView 获取当前图像并转换为 byte[]。
     *
     * @param imageView 要获取图像的 ImageView。
     * @return 图像的 byte[] 数据，如果 ImageView 为空则返回 null。
     */
    public static byte[] getBytesFromImageView(ImageView imageView) {
        if (imageView != null) {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            // 将 Bitmap 转换为 byte[]
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }
}
