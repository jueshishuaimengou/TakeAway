package com.yh.TakeAway.dao;

import com.yh.TakeAway.entity.Oder;
import com.yh.TakeAway.entity.OderItem;
import com.yh.TakeAway.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OderDao {


        /**
         * 更新订单状态
         * @param orderId 订单 ID
         * @param newStatus 新状态
         * @return 操作结果（1 表示成功，0 表示失败）
         */
        public static int updateOrderStatus(int orderId, int newStatus) {
            Connection connection = null;
            PreparedStatement ps = null;
            int result = 0;

            try {
                connection = JDBCUtils.getConn();
                String sql = "UPDATE oder SET Status = ? WHERE OderID = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, newStatus);
                ps.setInt(2, orderId);
                result = ps.executeUpdate();
            } catch (SQLException e) {
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

        /**
         * 添加新订单
         * @param order 订单对象
         * @return 操作结果（1 表示成功，0 表示失败）
         */
        public static int insertOder(Oder order) {
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            int generatedOderID = 0;

            try {
                connection = JDBCUtils.getConn();
                String sql = "INSERT INTO oder (UserID, VendorID, Status, OderTime, Address) VALUES (?, ?, ?, ?, ?)";

                // 使用 Statement.RETURN_GENERATED_KEYS 来返回自动生成的主键
                ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, order.getUserID());
                ps.setInt(2, order.getVendorID());
                ps.setInt(3, order.getStatus());
                ps.setTimestamp(4, new Timestamp(order.getOderTime().getTime()));
                ps.setString(5, order.getAddress());

                // 执行插入操作
                int result = ps.executeUpdate();

                // 如果插入成功，获取自动生成的 OderID
                if (result > 0) {
                    rs = ps.getGeneratedKeys(); // 获取生成的主键
                    if (rs.next()) {
                        generatedOderID = rs.getInt(1);  // 获取第一个生成的主键 (OderID)
                        order.setOderID(generatedOderID);  // 设置生成的 OderID 到订单对象中
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (rs != null) rs.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // 返回生成的 OderID
            return generatedOderID;
        }

    /**
         * 获取所有订单
         * @return 订单列表
         */
        public static List<Oder> getAllOrders() {
            List<Oder> list = new ArrayList<>();
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                connection = JDBCUtils.getConn();
                String sql = "SELECT * FROM oder ORDER BY OderTime DESC";
                ps = connection.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Oder order = new Oder();
                    order.setOderID(rs.getInt("OderID"));
                    order.setUserID(rs.getInt("UserID"));
                    order.setVendorID(rs.getInt("VendorID"));
                    order.setStatus(rs.getInt("Status"));
                    order.setOderTime(rs.getTimestamp("OderTime"));
                    order.setAddress(rs.getString("AddressID"));
                    list.add(order);
                }
            } catch (SQLException e) {
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
         * 根据订单状态获取订单
         * @param status 订单状态
         * @return 订单列表
         */
        public static List<Oder> getOrdersByStatus(int status) {
            List<Oder> list = new ArrayList<>();
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                connection = JDBCUtils.getConn();
                String sql = "SELECT * FROM oder WHERE Status = ? ORDER BY OderTime DESC";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, status);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Oder order = new Oder();
                    order.setOderID(rs.getInt("OderID"));
                    order.setUserID(rs.getInt("UserID"));
                    order.setVendorID(rs.getInt("VendorID"));
                    order.setStatus(rs.getInt("Status"));
                    order.setOderTime(rs.getTimestamp("OderTime"));
                    order.setAddress(rs.getString("AddressID"));
                    list.add(order);
                }
            } catch (SQLException e) {
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


        public static List<Oder> getOrdersByPhone(String phone,int status) {
            List<Oder> list = new ArrayList<>();
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                connection = JDBCUtils.getConn();
                String sql = "SELECT * FROM oder NATURAL JOIN user WHERE phone = ? AND Status = ? ORDER BY OderTime DESC";
                ps = connection.prepareStatement(sql);
                ps.setString(1,phone);
                ps.setInt(2, status);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Oder order = new Oder();
                    order.setOderID(rs.getInt("OderID"));
                    order.setUserID(rs.getInt("UserID"));
                    order.setVendorID(rs.getInt("VendorID"));
                    order.setStatus(rs.getInt("Status"));
                    order.setOderTime(rs.getTimestamp("OderTime"));
                    order.setAddress(rs.getString("Address"));
                    list.add(order);
                }
            } catch (SQLException e) {
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
         * 添加订单项
         * @param orderItem 订单项对象
         * @return 操作结果（1 表示成功，0 表示失败）
         */
        public static int insertOrderItem(OderItem orderItem) {
            Connection connection = null;
            PreparedStatement ps = null;
            int result = 0;

            try {
                connection = JDBCUtils.getConn();
                String sql = "INSERT INTO oderitem ( OderID,DishID, Quantity, Price,Dishname,Img) VALUES ( ?, ?, ?, ?,?,?)";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, orderItem.getOderID());
                ps.setInt(2, orderItem.getDishID());
                ps.setInt(3, orderItem.getQuantity());
                ps.setDouble(4, orderItem.getPrice());
                ps.setString(5, orderItem.getDishName());
                ps.setBytes(6,orderItem.getImg());
                result = ps.executeUpdate();
            } catch (SQLException e) {
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

        /**
         * 获取订单的所有订单项
         * @param orderId 订单 ID
         * @return 订单项列表
         */
        public static List<OderItem> getOrderItemsByOrderId(int orderId) {
            List<OderItem> list = new ArrayList<>();
            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                connection = JDBCUtils.getConn();
                String sql = "SELECT * FROM oderitem WHERE OderID = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, orderId);
                rs = ps.executeQuery();

                while (rs.next()) {
                    OderItem orderItem = new OderItem();
                    orderItem.setOderItemID(rs.getInt("OderItemID"));
                    orderItem.setOderID(rs.getInt("OderID"));
                    orderItem.setDishID(rs.getInt("DishID"));
                    orderItem.setQuantity(rs.getInt("Quantity"));
                    orderItem.setPrice(rs.getDouble("Price"));
                    orderItem.setImg(rs.getBytes("Img"));
                    list.add(orderItem);
                }
            } catch (SQLException e) {
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
         * 更新订单项
         * @param orderItem 订单项对象
         * @return 操作结果（1 表示成功，0 表示失败）
         */
  /*      public static int updateOrderItem(OrderItem orderItem) {
            Connection connection = null;
            PreparedStatement ps = null;
            int result = 0;

            try {
                connection = JDBCUtils.getConn();
                String sql = "UPDATE order_items SET ProductID = ?, Quantity = ?, Price = ? WHERE ItemID = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, orderItem.getProductID());
                ps.setInt(2, orderItem.getQuantity());
                ps.setDouble(3, orderItem.getPrice());
                ps.setInt(4, orderItem.getItemID());
                result = ps.executeUpdate();
            } catch (SQLException e) {
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
*/
        /**
         * 删除订单项
         * @param itemId 订单项 ID
         * @return 操作结果（1 表示成功，0 表示失败）
         */

        /*
        public static int deleteOrderItem(int itemId) {
            Connection connection = null;
            PreparedStatement ps = null;
            int result = 0;

            try {
                connection = JDBCUtils.getConn();
                String sql = "DELETE FROM order_items WHERE ItemID = ?";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, itemId);
                result = ps.executeUpdate();
            } catch (SQLException e) {
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
        }*/
}
