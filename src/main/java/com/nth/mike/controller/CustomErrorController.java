package com.nth.mike.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError() {
        RedirectView redirectView = new RedirectView("/mike/error/not-found");
        ModelAndView modelAndView = new ModelAndView(redirectView);
        return modelAndView;
    }

    @GetMapping({ "/mike/error/not-found" })
    public String notFound() {
        return "views/web/not-found";
    }

    @GetMapping({ "/mike/error/unauthorized" })
    public String unauthorized() {
        return "views/web/unauthorized-page";
    }

}
