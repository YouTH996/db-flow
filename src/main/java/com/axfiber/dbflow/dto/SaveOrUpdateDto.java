package com.axfiber.dbflow.dto;

import lombok.Data;

/**
 * 数据更新/新增表单JSON dto对象
 *
 * @author Zhan Xinjian
 * @date 2022/11/23
 */
@Data
public class SaveOrUpdateDto {
    /**
     * 键
     */
    private String key;
    /**
     * 值
     */
    private String value;
}
