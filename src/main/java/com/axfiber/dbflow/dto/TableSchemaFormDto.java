package com.axfiber.dbflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 表结构Dto对象
 *
 * @author Zhan Xinjian
 * @date 2022/11/11
 */
@Data
public class TableSchemaFormDto {
    /**
     * 数据库
     */
    private String dataBase;
    /**
     * 数据表
     */
    private String tableName;
    /**
     * 旧字段名称
     */
    @JsonProperty("oldField")
    private String oldField;
    /**
     * 字段名称
     */
    @JsonProperty("Field")
    private String Field;
    /**
     * 字段类型
     */
    @JsonProperty("Type")
    private String Type;
    /**
     * 是否为空
     */
    @JsonProperty("Null")
    private String Null;
    /**
     * 索引
     */
    @JsonProperty("Key")
    private String Key;

    /**
     * 旧索引
     */
    @JsonProperty("oldKey")
    private String oldKey;

    /**
     * 默认值
     */
    @JsonProperty("Default")
    private String Default;
    /**
     * 额外
     */
    @JsonProperty("Extra")
    private String Extra;
    /**
     * 备注
     */
    @JsonProperty("Comment")
    private String Comment;
}
