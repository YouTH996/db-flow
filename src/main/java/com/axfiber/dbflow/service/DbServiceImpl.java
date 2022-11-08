package com.axfiber.dbflow.service;

import com.axfiber.dbflow.dto.DbDto;
import com.axfiber.dbflow.utils.DbUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Zhan Xinjian
 * @date 2022/11/7
 */
@Service
public class DbServiceImpl implements DbService{
    @Override
    public Map getDbConn(DbDto dbDto) {
        //封装url参数
        String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", dbDto.getHost(), dbDto.getPort(),dbDto.getDataBase());
        DbUtils.getDbConn(url,dbDto.getUser(),dbDto.getPassword());
        //构造查询表sql
        String queryDataBaseSql = "show tables";
        return DbUtils.executeQuerySql(queryDataBaseSql, String.format("Tables_in_%s", dbDto.getDataBase()));
    }
}
