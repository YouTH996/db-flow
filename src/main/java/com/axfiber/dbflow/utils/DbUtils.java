package com.axfiber.dbflow.utils;

import com.axfiber.dbflow.dto.TableSchemaDto;
import com.axfiber.dbflow.utils.exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        int i=0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            log.debug("执行SQL更新:{}",sql);
            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new CommonException("数据库更新失败！");
        }
        return i;
    }

    /**
     * 查询表结构
     *
     * @param dataBase  数据库
     * @param tableName 表名称
     * @return 表结构
     */
    public static List<TableSchemaDto> queryTableSchema(String dataBase,String tableName) {
        ArrayList<TableSchemaDto> list = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columnResultSet = metaData.getColumns(dataBase, dataBase, tableName, "%");
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
            //获取表的索引
            ResultSet keySet = metaData.getPrimaryKeys(dataBase, dataBase, tableName);
            while (keySet.next()) {
                String columnName= keySet.getString(4);
                list.forEach(item->{
                    if (item.getColumnName().equals(columnName)) {
                        item.setPrimaryKey(true);
                    }
                });
            }
        } catch (SQLException e) {
            throw new CommonException("数据库获取表结构失败！");
        }
        return list;
    }
}
