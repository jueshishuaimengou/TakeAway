package com.yh.TakeAway.dao;


import com.yh.TakeAway.entity.Dish;
import com.yh.TakeAway.utils.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishDao {

    /**
     * 获取所有食品列表
     * @return List<FoodBean>
     */
    public static List<Dish> getAllFoodList() {
        List<Dish> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT * FROM dish";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Dish foodBean = new Dish(
                        rs.getInt("DishID"),
                        rs.getString("Dishname"),
                        rs.getString("Desc"),
                        rs.getFloat("Price"),
                        rs.getInt("Catagory"),
                        rs.getBytes("VendorID"), rs.getInt("Image")
                );
                list.add(foodBean);
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

    public static List<Dish> getFoodListByVendorID(int vendorid) {
        List<Dish> list = new ArrayList<>();
        Connection connection = null;
        connection = JDBCUtils.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM dish WHERE VendorID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, vendorid);
            rs = ps.executeQuery();

            while (rs.next()) {
                Dish foodBean = new Dish(
                        rs.getInt("DishID"),
                        rs.getString("Dishname"),
                        rs.getString("Desc"),
                        rs.getFloat("Price"),
                        rs.getInt("Catagory"),
                        rs.getBytes("Image"),
                        rs.getInt("VendorID")
                );
                list.add(foodBean);
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
     * @param foodId 食品 ID
     * @return FoodBean
     */
    public static Dish getFoodById(int foodId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Dish foodBean = null;

        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT * FROM dish WHERE dishID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, foodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                foodBean = new Dish(
                        rs.getInt("DishID"),
                        rs.getString("Dishname"),
                        rs.getString("Desc"),
                        rs.getFloat("Price"),
                        rs.getInt("Catagory"),
                        rs.getBytes("Image"),
                        rs.getInt("VendorID")
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

    /**
     * 获取当前月的销售数量
     * @param DishID
     * @return
     */

    public static int getMouSalesNumVen(int DishID){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        List<Integer> list=new ArrayList<>();
        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT * \n" +
                    "FROM oderitem \n" +
                    "NATURAL JOIN oder\n" +
                    "WHERE OderID = ? \n" ;
                //    "  AND Status = 3 \n" +
                //    "  AND DATE_FORMAT(OderTime, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m');\n";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, DishID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int temp= rs.getInt("Quantity");
                list.add(temp);
            }
            for (int s : list) {
                total+=s;
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
        return  total;
    }
    public static int getMouSalesNum(int DishID){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int total = 0;
        List<Integer> list=new ArrayList<>();
        try {
            connection = JDBCUtils.getConn();
            String sql = "SELECT * \n" +
                    "FROM oderitem \n" +
                    "NATURAL JOIN oder\n" +
                    "WHERE OderID = ? \n" ;
                  /*  "  AND Status = 3 \n" +
                    "  AND DATE_FORMAT(OderTime, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m');\n";*/
            ps = connection.prepareStatement(sql);
            ps.setInt(1, DishID);
            rs = ps.executeQuery();
            while (rs.next()) {
                int temp= rs.getInt("Quantity");
                list.add(temp);
            }
            for (int s : list) {
                total+=s;
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
        return  total;
    }

    /**
     * 通过订单ID来获取商品详情内容，商品ID
     * @param orderId
     * @param foodId
     * @return
     */



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
