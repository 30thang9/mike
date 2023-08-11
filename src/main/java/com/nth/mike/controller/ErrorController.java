package com.nth.mike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mike/error")
public class ErrorController {

    @GetMapping({ "/not-found" })
    public String notFound() {
        return "views/web/not-found";
    }

    @GetMapping({ "/unauthorized" })
    public String unauthorized() {
        return "views/web/unauthorized-page";
    }
}
