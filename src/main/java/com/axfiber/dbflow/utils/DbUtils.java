package com.axfiber.dbflow.utils;

import com.axfiber.dbflow.dto.TableSchemaDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        ResultSet resultSet;
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

    /**
     * 查询表结构
     * @param tableName 表名称
     * @return 表结构
     */
    public static List<TableSchemaDto> queryTableSchema(String tableName) {
        ArrayList<TableSchemaDto> list = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            // 获取所有表
            ResultSet tableResultSet = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
            while (tableResultSet.next()) {
                // 获取表字段结构
                ResultSet columnResultSet = metaData.getColumns(null, "%", tableName, "%");
                while (columnResultSet.next()) {
                    // 字段名称
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    // 数据类型
                    String columnType = columnResultSet.getString("TYPE_NAME");
                    // 字段长度
                    int dataSize = columnResultSet.getInt("COLUMN_SIZE");
                    // 是否为空 1 代表可空 0 代表不可为空
                    int nullable = columnResultSet.getInt("NULLABLE");
                    // 描述
                    String remarks = columnResultSet.getString("REMARKS");
                    TableSchemaDto dto = new TableSchemaDto();
                    dto.setColumnName(columnName);
                    dto.setColumnType(columnType);
                    dto.setDataSize(dataSize);
                    dto.setNullable(nullable);
                    dto.setRemarks(remarks);
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
