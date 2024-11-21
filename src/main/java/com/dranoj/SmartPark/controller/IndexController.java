package com.dranoj.SmartPark.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Hidden
    @GetMapping("/")
    public String test(){
        return "SmartPark is running";
    }
}
