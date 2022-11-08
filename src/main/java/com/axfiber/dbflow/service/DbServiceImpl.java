package com.axfiber.dbflow.service;

import com.axfiber.dbflow.dto.DbDto;
import com.axfiber.dbflow.utils.DbUtils;
import org.springframework.stereotype.Service;

/**
 * @author Zhan Xinjian
 * @date 2022/11/7
 */
@Service
public class DbServiceImpl implements DbService{
    @Override
    public void getDbConn(DbDto dbDto) {
        //封装url参数
        String url = String.format("jdbc:mysql://%s:%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", dbDto.getHost(), dbDto.getPort());
        DbUtils.getDbConn(url,dbDto.getUser(),dbDto.getPassword());

    }
}
