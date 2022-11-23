package com.axfiber.dbflow.dto;

import lombok.Data;

/**
 * @author Zhan Xinjian
 * @date 2022/11/23
 */
@Data
public class SaveOrUpdateFormDto {
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
     * 主键值
     */
    private String keyVal;
    /**
     * 更新或需要修改的数据
     */
    private String data;
}
