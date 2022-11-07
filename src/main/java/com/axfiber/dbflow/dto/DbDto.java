package com.axfiber.dbflow.dto;

import lombok.Data;

/**
 * 数据库参数DTO
 *
 * @author Zhan Xinjian
 * @date 2022/11/7
 */
@Data
public class DbDto {
    /**
     * Host
     */
    private String host;
    /**
     * Port
     */
    private String port;
    /**
     * user
     */
    private String user;
    /**
     * password
     */
    private String password;
    /**
     * Database
     */
    private String dataBase;

}
