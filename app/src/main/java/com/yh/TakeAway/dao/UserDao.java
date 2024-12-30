package com.yh.TakeAway.dao;

import android.util.Log;


import com.yh.TakeAway.entity.User;
import com.yh.TakeAway.utils.JDBCUtils;

import java.io.InputStream;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserDao {
    private static final String TAG = "mysql-takeaway-UserDao";


    public int login(String Phone, String Password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int msg = 0;

        try {
            connection = JDBCUtils.getConn();
            if (connection == null) {
                return 0; // 数据库连接失败
            }

            String sql = "SELECT * FROM User WHERE Phone = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,Phone);
            rs = ps.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("Password");
                if (Password.equals(dbPassword)) {
                    msg = 1; // 登录成功
                } else {
                    msg = 2; // 密码错误
                }
            } else {
                msg = 3; // 用户不存在
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "异常login：" + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return msg;
    }
    public static User getUserByPhone(String phone) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user=new User();
        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT *  FROM user WHERE Phone = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1 ,phone);
            rs = ps.executeQuery();

            if (rs.next()) {
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setPhone(rs.getString("Phone"));
                user.setProfile(rs.getBytes("profile"));
                user.setRegistertime(rs.getDate("registertime"));
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
        return user;
    }

    public boolean register(User user, InputStream profileImageStream) {
        // 根据数据库名称，建立连接
        Connection connection = JDBCUtils.getConn();

        try {
            // 插入 SQL 语句，profile 字段为 BLOB 类型
            String sql = "INSERT INTO user(UserID, Username, Password, Phone, profile, registertime) VALUES (?, ?, ?, ?, ?, ?)";

            if (connection != null) { // connection 不为 null 表示与数据库建立了连接
                PreparedStatement ps = connection.prepareStatement(sql);

                if (ps != null) {
                    // 将数据插入数据库
                    ps.setInt(1, user.getUserID());
                    ps.setString(2, user.getUsername());
                    ps.setString(3, user.getPassword());
                    ps.setString(4, user.getPhone());

                    // 设置头像为 BLOB 数据
                    if (profileImageStream != null) {
                        // 将输入流设置为 BLOB 类型
                        ps.setBinaryStream(5, profileImageStream);
                    } else {
                        // 如果没有选择头像，设置为 NULL
                        ps.setNull(5, Types.BLOB);
                    }

                    // 设置注册时间
                    ps.setDate(6, user.getRegistertime());

                    // 执行 SQL 查询语句并返回结果
                    int rs = ps.executeUpdate();
                    return rs > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MySQL-Register", "异常register：" + e.getMessage());
        }

        return false;
    }

    public static User getUserByUserID(int UserID) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user=new User();
        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT * FROM user WHERE UserID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, UserID);
            rs = ps.executeQuery();

            if (rs.next()) {
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setPhone(rs.getString("Phone"));
                user.setProfile(rs.getBytes("profile"));
                user.setRegistertime(rs.getDate("registertime"));
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
        return user;
    }
}
