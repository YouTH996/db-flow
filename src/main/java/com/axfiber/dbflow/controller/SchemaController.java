package com.axfiber.dbflow.controller;

import com.axfiber.dbflow.dto.TableSchemaFormDto;
import com.axfiber.dbflow.service.SchemaService;
import com.axfiber.dbflow.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zhan Xinjian
 * @date 2022/11/24
 */
@RequestMapping("/schema")
@RestController
public class SchemaController {
    @Resource
    private SchemaService schemaService;

    /**
     * 更新
     */
    @PostMapping("/update")
    public R update(@RequestBody TableSchemaFormDto dto) {
        schemaService.update(dto);
        return R.ok();
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    public R save(@RequestBody TableSchemaFormDto dto) {
        schemaService.save(dto);
        return R.ok();
    }

    /**
     * 新增
     */
    @PostMapping("/delete")
    public R delete(@RequestBody TableSchemaFormDto dto) {
        schemaService.delete(dto);
        return R.ok();
    }
}
