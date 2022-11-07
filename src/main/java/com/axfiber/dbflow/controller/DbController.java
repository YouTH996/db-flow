package com.axfiber.dbflow.controller;

import com.axfiber.dbflow.dto.DbDto;
import com.axfiber.dbflow.service.DbService;
import com.axfiber.dbflow.utils.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
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
     * @param dbDto 数据库参数DTO
     * @return 结果
     */
    @PostMapping("/getDbConn")
    public R getDbConn(@RequestBody DbDto dbDto) {
        dbService.getDbConn(dbDto);
        return R.ok();
    }
}
