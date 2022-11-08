package com.axfiber.dbflow.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * sql查询器
     *
     * @param sql       sql
     * @param sqlColumn 查询列
     * @return 插叙结果
     */
    public static Map executeQuerySql(String sql, String... sqlColumn) {
        ResultSet resultSet = null;
        HashMap<String, ArrayList<String>> map = new HashMap<>(sqlColumn.length);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                for (String s : sqlColumn) {
                    String columnRs = resultSet.getString(s);
                    ArrayList<String> currentMap = map.get(s);
                    if (currentMap != null) {
                        if (currentMap.isEmpty()) {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(columnRs);
                        } else {
                            currentMap.add(columnRs);
                        }
                    } else {
                        ArrayList<String> list = new ArrayList<>();
                        list.add(columnRs);
                        map.put(s, list);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
