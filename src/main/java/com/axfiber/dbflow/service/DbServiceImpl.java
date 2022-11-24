package com.axfiber.dbflow.service;

import com.alibaba.fastjson.JSON;
import com.axfiber.dbflow.dto.*;
import com.axfiber.dbflow.utils.DbUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

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
    public Map getTableSchema(String dataBase, String tableName) {
        String querySchemaSql = String.format("show full columns from %s", tableName);
        String[] names = null;
        try {

            Class<TableColumnDto> tableColumnDto = (Class<com.axfiber.dbflow.dto.TableColumnDto>) Class.forName("com.axfiber.dbflow.dto.TableColumnDto");
            names = Arrays.stream(tableColumnDto.getDeclaredFields()).map(Field::getName).toArray(String[]::new);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DbUtils.executeQuerySql(querySchemaSql, names);
    }

    @Override
    public Map queryPage(Map<String, Object> params) {
        HashMap<String, Map> map = new HashMap<>(2);
        String tableName = (String) params.get("tableName");
        String dataBase = (String) params.get("dataBase");
        int page = Integer.parseInt((String) params.get("page"));
        int limit = Integer.parseInt((String) params.get("limit"));
        String[] names = (String[]) ((List) this.getTableSchema(dataBase, tableName).get("Field")).stream().toArray(String[]::new);
        //封装分页SQL语句
        String queryDataSql = String.format("select * from %s limit %d , %d", tableName, (page - 1) * limit, limit);
        //还要获取总页数
        String queryTotalSql = String.format("select count(1) from %s", tableName);
        Map totalMap = DbUtils.executeQuerySql(queryTotalSql, "count(1)");
        Map dataMap = DbUtils.executeQuerySql(queryDataSql, names);
        map.put("total", totalMap);
        map.put("data", dataMap);
        return map;
    }

    @Override
    public Map info(String dataBase, String tableName, String primaryKey, String keyVal) {
        String queryInfoSql = String.format("select * from %s where %s = %s", tableName, primaryKey, keyVal);
        String[] names = (String[]) ((List) this.getTableSchema(dataBase, tableName).get("Field")).stream().toArray(String[]::new);
        return DbUtils.executeQuerySql(queryInfoSql, names);
    }

    @Override
    public void update(SaveOrUpdateFormDto dto) {
        //格式化更新语句
        List<SaveOrUpdateDto> list = JSON.parseArray(dto.getData(), SaveOrUpdateDto.class);
        String updatePrefixSql = String.format("update %s", dto.getTableName());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(updatePrefixSql);
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                stringBuilder.append(" set ");
            }
            stringBuilder.append(list.get(i).getKey());
            stringBuilder.append(" = '");
            stringBuilder.append(list.get(i).getValue());
            stringBuilder.append("'");
            if (i != list.size() - 1) {
                stringBuilder.append(",");
            }
        }
        String updateSuffixSql = String.format(" where %s = %s", dto.getPrimaryKey(), dto.getKeyVal());
        stringBuilder.append(updateSuffixSql);
        DbUtils.executeUpdateSql(stringBuilder.toString());
    }

    @Override
    public void save(SaveOrUpdateFormDto dto) {
        //格式化更新语句
        List<SaveOrUpdateDto> list = JSON.parseArray(dto.getData(), SaveOrUpdateDto.class);
        String savePrefixSql = String.format("insert into %s ( ", dto.getTableName());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(savePrefixSql);
        for (int i = 0; i < list.size(); i++) {

            stringBuilder.append(list.get(i).getKey());

            if (i != list.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(" ) values ( ");
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append("'");
            stringBuilder.append(list.get(i).getValue());
            stringBuilder.append("'");
            if (i != list.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(" )");
        DbUtils.executeUpdateSql(stringBuilder.toString());
    }

    @Override
    public void delete(DeleteFormDto dto) {
        List<String> keyList = JSON.parseArray(dto.getKeyList(), String.class);
        //格式化删除SQL语句
        for (String key : keyList) {
            String sql = String.format("delete from %s where %s = %s ", dto.getTableName(), dto.getPrimaryKey(), key);
            DbUtils.executeUpdateSql(sql);
        }
    }
}
