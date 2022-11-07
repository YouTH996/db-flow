package com.axfiber.dbflow.service;

import com.axfiber.dbflow.dto.DbDto;

/**
 * @author Zhan Xinjian
 * @date 2022/11/7
 */
public interface DbService {
    /**
     * 获取数据库连接
     * @param dbDto 数据库参数DTO
     */
    void getDbConn(DbDto dbDto);
}
