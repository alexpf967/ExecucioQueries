package com.example.demo.controllers;

import com.example.demo.services.BlocService;
import com.example.demo.services.TaulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/taula")
public class TaulaController {
    @Autowired
    private TaulaService taulaService;
}
