package com.uptime.config;

import com.uptime.interceptor.ReactAppInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {



    @Bean
    public ExecutorService executorService(){
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    @Autowired
    private ReactAppInterceptor reactAppInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(reactAppInterceptor);
    }

}
