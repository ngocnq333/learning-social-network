package com.solution.ntq.controller;

import com.solution.ntq.service.IClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;


@Controller
public class ClazzController {
    @Autowired
    IClazzService iClazzService;

    @GetMapping("/add-data")
    public String getData() throws ParseException {
        iClazzService.addAllData();
        return "ok";

    }


}
