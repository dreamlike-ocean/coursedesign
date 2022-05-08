package com.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
