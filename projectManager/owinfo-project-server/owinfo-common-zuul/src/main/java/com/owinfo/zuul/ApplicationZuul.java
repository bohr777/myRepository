package com.owinfo.zuul;

import com.owinfo.zuul.filter.CustomZuulFilter;
import com.owinfo.zuul.filter.ExceptionZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Created by liyue on 2017/9/25.
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class ApplicationZuul {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationZuul.class, args);
    }

    @Bean
    public CustomZuulFilter customZuulFilter() {
        return new CustomZuulFilter();
    }

    @Bean
    public ExceptionZuulFilter exceptionZuulFilter() {
        return new ExceptionZuulFilter();
    }
}
