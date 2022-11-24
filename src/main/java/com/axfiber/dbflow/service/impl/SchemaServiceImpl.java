package com.axfiber.dbflow.service.impl;

import com.alibaba.fastjson.JSON;
import com.axfiber.dbflow.dto.TableSchemaFormDto;
import com.axfiber.dbflow.service.SchemaService;
import com.axfiber.dbflow.utils.DbUtils;
import com.axfiber.dbflow.utils.exception.CommonException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 表结构ServiceImpl
 *
 * @author Zhan Xinjian
 * @date 2022/11/24
 */
@Service
public class SchemaServiceImpl implements SchemaService {
    @Override
    public void update(TableSchemaFormDto dto) {
        if (!dto.getOldKey().equals("PRI")) {
            //封装表结构更新SQL
            String updateSchemaSql = String.format("alter table %s change %s %s %s %s %s %s comment '%s'", dto.getTableName(),
                    dto.getOldField(), dto.getField(), dto.getType() != null ? dto.getType() : "", dto.getNull().equals("NO") ? "null" : "not null",
                    dto.getDefault() != null ? dto.getDefault() : "", dto.getExtra() != null ? dto.getExtra() : "", dto.getComment());
            //存在主键索引的情况，不进行下列操作
            //如果存在索引更新，组装索引更新语句
            if (dto.getKey() != null) {
                //查看是否有旧索引
                if (dto.getOldKey() != null) {
                    //TODO 此处需要判断索引名称的问题
                    String dropIdxSql = String.format(" , drop index %s", "idx_" + dto.getOldField());
                    updateSchemaSql += dropIdxSql;
                }
                String updateIdxSql = String.format(" , add index %s (%s) using btree comment '%s'", "idx_" + dto.getField(), dto.getField(), dto.getField() + "索引");
                updateSchemaSql += updateIdxSql;
            }
            DbUtils.executeUpdateSql(updateSchemaSql);
        } else {
            throw new CommonException("暂不支持对主键进行修改!");
        }
    }

    @Override
    public void save(TableSchemaFormDto dto) {
        //封装表结构更新SQL
        String saveSchemaSql = String.format("alter table %s add %s %s %s %s %s comment '%s'", dto.getTableName(),
                dto.getField(), dto.getType() != null ? dto.getType() : "", dto.getNull().equals("NO") ? "null" : "not null",
                dto.getDefault() != null ? dto.getDefault() : "", dto.getExtra() != null ? dto.getExtra() : "", dto.getComment());
        //如果存在索引更新，组装索引更新语句
        if (dto.getKey() != null) {
            String saveIdxSql = String.format(" , add index %s (%s) using btree comment '%s'", "idx_" + dto.getField(), dto.getField(), dto.getField() + "索引");
            saveSchemaSql += saveIdxSql;
        }
        DbUtils.executeUpdateSql(saveSchemaSql);
    }

    @Override
    public void delete(TableSchemaFormDto dto) {
        //封装表结构更新SQL
        List<String> fieldList = JSON.parseArray(dto.getField(), String.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("alter table %s ", dto.getTableName()));
        for (int i = 0; i < fieldList.size(); i++) {
            stringBuilder.append(String.format("drop %s", fieldList.get(i)));
            if (i != fieldList.size() - 1) {
                stringBuilder.append(" ,");
            }
        }
        DbUtils.executeUpdateSql(stringBuilder.toString());
    }
}
