package com.yh.TakeAway.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {
    private static final String TAG = "mysql-takeaway-JDBCUtils";

    // JDBC 配置
    private static final String DRIVER = "com.mysql.jdbc.Driver"; // MySQL Connector/J 5.x 驱动
    private static final String DB_NAME = "takeaway";                  // 数据库名称
    private static final String USER = "root";                      // 数据库用户名
    private static final String PASSWORD = "123456";                // 数据库密码
    private static final String HOST = "10.0.2.2";                 // 本地地址
    private static final int PORT = 3306;                           // MySQL 默认端口

    public static Connection getConn() {
        Connection connection = null;
        try {
            // 加载 JDBC 驱动
            Class.forName(DRIVER);

            // 构建数据库连接 URL
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME ;

            // 获取数据库连接
            connection = DriverManager.getConnection(url, USER, PASSWORD);
            if (connection == null) {
                System.out.println(TAG + ": 无法建立数据库连接");
            }
        } catch (ClassNotFoundException e) {
            System.out.println(TAG + ": JDBC 驱动加载失败 - " + e.getMessage());
        } catch (SQLException e) {
            System.out.println(TAG + ": 数据库连接失败 - " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}


