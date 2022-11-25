package com.axfiber.dbflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Zhan Xinjian
 * @date 2022/11/25
 */
@Controller
public class IndexController {
    @RequestMapping(value = {"/","/index"})
    public String index() {
        return "index.html";
    }
}
