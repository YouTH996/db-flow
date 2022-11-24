package com.axfiber.dbflow.utils;

import com.axfiber.dbflow.utils.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhan Xinjian
 * @date 2022/11/7
 */
@Slf4j
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
            throw new CommonException("数据库连接失败！");
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
        ResultSet resultSet;
        HashMap<String, ArrayList<String>> map = new HashMap<>(sqlColumn.length);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            log.debug("执行SQL查询:{}",sql);
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
            throw new CommonException("数据库查询失败！");
        }
        return map;
    }

    /**
     * sql更新、删除、查询器
     * @param sql  更新、删除、查询 sql
     * @return 影响结果
     */
    public static int executeUpdateSql(String sql) {
        int i;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            log.debug("执行SQL更新:{}",sql);
            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CommonException("数据库更新失败！");
        }
        return i;
    }
}
