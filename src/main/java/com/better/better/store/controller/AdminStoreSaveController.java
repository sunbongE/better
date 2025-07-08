package com.better.better.store.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/admin/store")
public class AdminStoreSaveController {

    @PostMapping("/save")
    public String saveStore(){
        return "saveStore";
    }
}
