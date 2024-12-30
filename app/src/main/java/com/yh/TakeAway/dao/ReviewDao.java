package com.yh.TakeAway.dao;


import com.yh.TakeAway.entity.Review;
import com.yh.TakeAway.utils.JDBCUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.sql.*;


public class ReviewDao {

    /**
     * 获取指定 VendorID 的评论
     * @param vendorId 商家 ID
     * @return 评论列表
     */
    public static List<Review> getReviewsByVendorId(int vendorId) {
        List<Review> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT * FROM reviews WHERE VendorID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, vendorId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setReviewID(rs.getInt("ReviewID"));
                review.setOderID(rs.getInt("OderID"));
                review.setVendorID(rs.getInt("VendorID"));
                review.setV_rating(rs.getInt("v_rating"));
                review.setTime(rs.getTimestamp("time"));
                review.setText(rs.getString("text"));
                review.setImg(rs.getBytes("img"));
                list.add(review);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 获取商家的平均评分
     * @param vendorId 商家 ID
     * @return 平均评分
     */
    public static double getAvgRatingByVendorId(int vendorId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        double avgRating = 0.0;

        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT AVG(v_rating) AS avg_rating FROM reviews WHERE VendorID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, vendorId);
            rs = ps.executeQuery();

            if (rs.next()) {
                avgRating = rs.getDouble("avg_rating");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return avgRating;
    }

    /**
     * 插入新的评论
     * @param oderID 订单 ID
     * @param vendorID 商家 ID
     * @param rating 评分
     * @param text 评论内容
     * @param img 图片
     * @return 插入结果（1 表示成功，0 表示失败）
     */
    public static int insertReview(int oderID, int vendorID, int rating, String text, byte[] img) {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            connection = JDBCUtils.getConn();
            String sql = "INSERT INTO reviews (OderID, VendorID, v_rating, time, text, img) VALUES (?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);

            ps.setInt(1, oderID);
            ps.setInt(2, vendorID);
            ps.setInt(3, rating);
            ps.setTimestamp(4, new Timestamp(new Date().getTime()));
            ps.setString(5, text);
            ps.setBytes(6, img);

            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

