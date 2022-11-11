package com.axfiber.dbflow.dto;

import lombok.Data;

/**
 * 表结构Dto对象
 *
 * @author Zhan Xinjian
 * @date 2022/11/11
 */
@Data
public class TableSchemaDto {
    /**
     * 字段名称
     */
    private String columnName;
    /**
     * 数据类型
     */
    private String columnType;
    /**
     * 字段长度
     */
    private Integer dataSize;
    /**
     * 是否为空
     */
    private Integer nullable;
    /**
     * 备注
     */
    private String remarks;
}
