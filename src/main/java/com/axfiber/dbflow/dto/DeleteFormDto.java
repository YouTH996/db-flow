package com.axfiber.dbflow.dto;

import lombok.Data;

/**
 * 数据删除表单
 *
 * @author Zhan Xinjian
 * @date 2022/11/23
 */
@Data
public class DeleteFormDto {
    /**
     * 数据库
     */
    private String dataBase;
    /**
     * 数据表
     */
    private String tableName;
    /**
     * 主键
     */
    private String primaryKey;
    /**
     * 主键值集合
     */
    private String keyList;
}
