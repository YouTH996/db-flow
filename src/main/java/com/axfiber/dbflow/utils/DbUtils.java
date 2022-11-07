package com.axfiber.dbflow.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Zhan Xinjian
 * @date 2022/11/7
 */
public class DbUtils {
    /**
     * 数据库连接常量
     */
    private static Connection connection = null;

    public static void getDbConn(String url, String userName, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
