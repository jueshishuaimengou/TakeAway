package com.yh.TakeAway.dao;

import com.yh.TakeAway.entity.Address;
import com.yh.TakeAway.entity.Dish;
import com.yh.TakeAway.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDao {
    public static List<Address> getAllAddressByUserId(int userid) {
        List<Address> list = new ArrayList<>();
        Connection connection = null;
        connection = JDBCUtils.getConn();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM address WHERE UserID = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userid);
            rs = ps.executeQuery();

            while (rs.next()) {
                Address foodBean = new Address(
                        rs.getInt("AddressID"),
                        rs.getInt("UserID"),
                        rs.getString("Address")
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
}
