package com.abinbev.consumer.model.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfos {

    @GetMapping("/userinfos")
    public Principal getUserInfos(Principal principal){
        return principal;
    }
}
