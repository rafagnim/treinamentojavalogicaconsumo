package com.mvcestacoes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigMVC implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registro) {
        registro.addViewController("/").setViewName("home");
        registro.addViewController("/home").setViewName("home");
        registro.addViewController("*").setViewName("home");
    }
}