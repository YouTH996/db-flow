package com.axfiber.dbflow.service;

import com.axfiber.dbflow.dto.DbDto;

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
}
