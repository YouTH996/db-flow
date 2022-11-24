package com.axfiber.dbflow.service.impl;

import com.axfiber.dbflow.dto.TableSchemaFormDto;
import com.axfiber.dbflow.service.SchemaService;
import com.axfiber.dbflow.utils.DbUtils;
import com.axfiber.dbflow.utils.exception.CommonException;
import org.springframework.stereotype.Service;

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
        String deleteSchemaSql = String.format("alter table %s drop %s", dto.getTableName(), dto.getField());
        DbUtils.executeUpdateSql(deleteSchemaSql);
    }
}
