package com.axfiber.dbflow.service;

import com.axfiber.dbflow.dto.DbDto;
import com.axfiber.dbflow.dto.SaveOrUpdateFormDto;
import com.axfiber.dbflow.dto.TableSchemaDto;

import java.util.List;
import java.util.Map;

/**
 * @author Zhan Xinjian
 * @date 2022/11/7
 */
public interface DbService {
    /**
     * 获取数据库连接
     * @param dbDto 数据库参数DTO
     * @return 返回结果
     */
    Map getDbConn(DbDto dbDto);

    /**
     * 查询表结构
     *
     * @param dataBase 数据库名称
     * @param tableName 表名称
     * @return 表结构
     */
    List<TableSchemaDto> getTableSchema(String dataBase,String tableName);

    /**
     * 获取表数据
     * @param params 分页参数
     * @return 表数据
     */
    Map queryPage(Map<String, Object> params);

    /**
     * 根据主键查询
     * @param dataBase 数据库名称
     * @param tableName 表名称
     * @param primaryKey 主键
     * @param keyVal 主键值
     * @return 数据
     */
    Map info( String dataBase,String tableName,String primaryKey,String keyVal);

    void update(SaveOrUpdateFormDto dto);
}
