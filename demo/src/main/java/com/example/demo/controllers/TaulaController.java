package com.example.demo.controllers;

import com.example.demo.classes.Taula;
import com.example.demo.services.BlocService;
import com.example.demo.services.TaulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/taula")
public class TaulaController {
    @Autowired
    private TaulaService taulaService;

    @PostMapping("/crearTaula")
    public long crearTaula(@RequestParam String nom_taula) {
        Taula t = new Taula(nom_taula);
        t = taulaService.saveTaula(t);
        return t.getId();
    }
}
