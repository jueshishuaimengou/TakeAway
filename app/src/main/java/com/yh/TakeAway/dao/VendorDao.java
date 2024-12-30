package com.yh.TakeAway.dao;


import com.yh.TakeAway.entity.Vendor;
import com.yh.TakeAway.utils.JDBCUtils;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendorDao{
    /**
     * 获取所有食品列表
     * @return List<FoodBean>
     */
    public static List<Vendor> getAllVendorList() {
        List<Vendor> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = JDBCUtils.getConn();
            if (connection == null) {
                throw new SQLException("数据库连接失败");
            }

            String sql = "SELECT * FROM vendor";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String desc = rs.getString("Desc");
                if (desc == null) {
                    desc = ""; // 处理NULL值
                }

                String phone = rs.getString("phone");
                if (phone == null) {
                    phone = ""; // 处理NULL值
                }

                String address = rs.getString("Address");
                if (address == null) {
                    address = ""; // 处理NULL值
                }

                Integer rating = rs.getObject("Rating") != null ? rs.getInt("Rating") : 0; // 处理NULL值

                // 直接使用rs.getBlob获取BLOB类型数据
               byte[] blob = rs.getBytes("image");

                Vendor vendor = new Vendor(
                        rs.getInt("VendorID"),
                        rs.getString("Venname"),
                        desc, // 使用空字符串替代NULL值
                        phone,
                        address,
                        rating,
                        blob // 传递Blob类型的数据
                );
                list.add(vendor);
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
     * 根据食品 ID 获取食品信息
     * @param vendorId  ID
     * @return FoodBean
     */
    public static Vendor getVendorById(int vendorId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vendor foodBean = null;

        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT * FROM vendor WHERE VendorID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, vendorId);
            rs = ps.executeQuery();
            if (rs.next()) {
                foodBean = new Vendor(
                        rs.getInt("VendorID"),
                        rs.getString("Venname"),
                        rs.getString("Desc"),
                        rs.getString("phone"),
                        rs.getString("Address"),
                        rs.getInt("Rating"),
                        rs.getBytes("image")
                );
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

        return foodBean;
    }

    public static List<Vendor> getVendorByName(String title) {
        List<Vendor> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String titleL="%"+title+"%";
        try {
            connection = JDBCUtils.getConn();
            String sql =  "SELECT * FROM vendor where Venname like ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,titleL);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vendor vendor = new Vendor(
                        rs.getInt("VendorID"),
                        rs.getString("Venname"),
                        rs.getString("Desc"),
                        rs.getString("phone"),
                        rs.getString("Address"),
                        rs.getInt("Rating"),
                        rs.getBytes("image")
                );
                list.add(vendor);
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
     * 添加食品
     * @param businessId 商家 ID
     * @param foodName 食品名称
     * @param des 食品描述
     * @param foodPrice 食品价格
     * @param img 食品图片路径
     * @return 操作结果
     */
   /* public static int addFood(String businessId, String foodName, String des, float foodPrice, String img) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JDBCUtils.getConn();
            String id = UUID.randomUUID().toString().replace("-", "");
            String sql = "INSERT INTO d_food (s_food_id, s_business_id, s_food_name, s_food_des, s_food_price, s_food_img) VALUES (?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, businessId);
            ps.setString(3, foodName);
            ps.setString(4, des);
            ps.setFloat(5, foodPrice);
            ps.setString(6, img);

            return ps.executeUpdate();
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

        return 0;
    }
*/
    /**
     * 删除食品
     * @param foodId 食品 ID
     * @return 操作结果
     */
 /*   public static int deleteFoodById(String foodId) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JDBCUtils.getConn();
            String sql = "DELETE FROM d_food WHERE s_food_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, foodId);

            return ps.executeUpdate();
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

        return 0;
    }
*/
    /**
     * 更新食品信息
     * @param foodId 食品 ID
     * @param foodName 食品名称
     * @param des 食品描述
     * @param foodPrice 食品价格
     * @param img 食品图片路径
     * @return 操作结果
     */
 /*   public static int updateFood(String foodId, String foodName, String des, float foodPrice, String img) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JDBCUtils.getConn();
            String sql = "UPDATE d_food SET s_food_name = ?, s_food_des = ?, s_food_price = ?, s_food_img = ? WHERE s_food_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, foodName);
            ps.setString(2, des);
            ps.setFloat(3, foodPrice);
            ps.setString(4, img);
            ps.setString(5, foodId);

            return ps.executeUpdate();
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

        return 0;
    }*/
}

