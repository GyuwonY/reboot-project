package com.example.orderservice.controller;

import com.example.orderservice.client.ErrorClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/errorful")
public class ErrorfulTestController {
    private final ErrorClient errorClient;

    public ErrorfulTestController(
            ErrorClient errorClient
    ) {
        this.errorClient = errorClient;
    }

    @GetMapping("/case1")
    public void case1(){
        errorClient.case1();
    }

    @GetMapping("/case2")
    public void case2(){
        errorClient.case2();
    }


    @GetMapping("/case3")
    public void case3(){
        errorClient.case3();
    }
}
