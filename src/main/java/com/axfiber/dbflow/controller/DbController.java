package com.axfiber.dbflow.controller;

import com.axfiber.dbflow.dto.DbDto;
import com.axfiber.dbflow.dto.SaveOrUpdateFormDto;
import com.axfiber.dbflow.dto.TableSchemaDto;
import com.axfiber.dbflow.service.DbService;
import com.axfiber.dbflow.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 数据库Controller层
 *
 * @author Zhan Xinjian
 * @date 2022/11/7
 */
@RequestMapping("/db")
@RestController
public class DbController {
    @Resource
    private DbService dbService;

    /**
     * 获取数据库连接
     *
     * @param dbDto 数据库参数DTO
     * @return 结果
     */
    @PostMapping("/getDbConn")
    public R getDbConn(@RequestBody DbDto dbDto) {
        Map map = dbService.getDbConn(dbDto);
        return R.ok().put("map", map);
    }

    /**
     * 查询表结构
     *
     * @param tableName 表名称
     * @return 表结构
     */
    @GetMapping("/getTableSchema")
    public R getTableSchema(@RequestParam("dataBase") String dataBase, @RequestParam("tableName") String tableName) {
        List<TableSchemaDto> list = dbService.getTableSchema(dataBase, tableName);
        return R.ok().put("list", list);
    }

    /**
     * 获取表数据
     *
     * @param params 分页参数
     * @return 表数据
     */
    @GetMapping("/getDataList")
    public R getDataList(@RequestParam Map<String, Object> params) {
        Map map = dbService.queryPage(params);
        return R.ok().put("map", map);
    }

    /**
     * 根据主键查询
     */
    @GetMapping("/info")
    public R info(@RequestParam("dataBase") String dataBase, @RequestParam("tableName") String tableName,
                  @RequestParam("primaryKey") String primaryKey, @RequestParam("keyVal") String keyVal) {
        Map map = dbService.info(dataBase, tableName, primaryKey, keyVal);
        return R.ok().put("map", map);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R update(@RequestBody SaveOrUpdateFormDto dto) {
        dbService.update(dto);
        return R.ok();
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    public R save(@RequestBody SaveOrUpdateFormDto dto) {
        dbService.save(dto);
        return R.ok();
    }

}
