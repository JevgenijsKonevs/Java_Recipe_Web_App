package com.bootcamp.nomnom.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(){
        return "sadcupcake";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
