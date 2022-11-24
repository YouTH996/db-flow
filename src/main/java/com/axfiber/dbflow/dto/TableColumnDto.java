package com.axfiber.dbflow.dto;

import lombok.Data;

/**
 * @author Zhan Xinjian
 * @date 2022/11/24
 */
@Data
public class TableColumnDto {
    /**
     * 字段名称
     */
    private String Field;
    /**
     * 字段类型
     */
    private String Type;
    /**
     * 是否为空
     */
    private String Null;
    /**
     * 索引
     */
    private String Key;
    /**
     * 默认值
     */
    private String Default;
    /**
     * 额外
     */
    private String Extra;
    /**
     * 备注
     */
    private String Comment;
}
