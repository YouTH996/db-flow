package com.axfiber.dbflow.service;

import com.axfiber.dbflow.dto.TableSchemaFormDto;

/**
 * 表结构Service
 *
 * @author Zhan Xinjian
 * @date 2022/11/24
 */
public interface SchemaService {
    /**
     * 更新表结构
     * @param dto 表结构更新表单
     */
    void update(TableSchemaFormDto dto);

    /**
     * 新增表结构
     * @param dto 表结构更新表单
     */
    void save(TableSchemaFormDto dto);

    /**
     * 删除表结构
     * @param dto 表结构更新表单
     */
    void delete(TableSchemaFormDto dto);
}
