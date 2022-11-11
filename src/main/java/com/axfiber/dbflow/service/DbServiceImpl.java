package com.axfiber.dbflow.service;

import com.axfiber.dbflow.dto.DbDto;
import com.axfiber.dbflow.dto.TableSchemaDto;
import com.axfiber.dbflow.utils.DbUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zhan Xinjian
 * @date 2022/11/7
 */
@Service
public class DbServiceImpl implements DbService {
    @Override
    public Map getDbConn(DbDto dbDto) {
        //封装url参数
        String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false", dbDto.getHost(), dbDto.getPort(), dbDto.getDataBase());
        DbUtils.getDbConn(url, dbDto.getUser(), dbDto.getPassword());
        //构造查询表sql
        String queryDataBaseSql = "show tables";
        return DbUtils.executeQuerySql(queryDataBaseSql, String.format("Tables_in_%s", dbDto.getDataBase()));
    }

    @Override
    public List<TableSchemaDto> getTableSchema(String tableName) {
        return DbUtils.queryTableSchema(tableName);
    }

    @Override
    public Map queryPage(Map<String, Object> params) {
        HashMap<String, Map> map = new HashMap<>(2);
        String tableName = (String) params.get("tableName");
        int page = Integer.parseInt((String) params.get("page"));
        int limit =Integer.parseInt( (String) params.get("limit"));
        String[] names = this.getTableSchema(tableName).stream().map(TableSchemaDto::getColumnName).toArray(String[]::new);
        //封装分页SQL语句
        String queryDataSql = String.format("select * from %s limit %d , %d", tableName, (page - 1) * limit , limit);
        //还要获取总页数
        String queryTotalSql = String.format("select count(1) from %s", tableName);
        Map totalMap = DbUtils.executeQuerySql(queryTotalSql, "count(1)");
        Map dataMap = DbUtils.executeQuerySql(queryDataSql, names);
        map.put("total", totalMap);
        map.put("data", dataMap);
        return map;
    }
}
