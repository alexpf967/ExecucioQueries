package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vistas")
public class VistasController {
    @GetMapping
    public String inici() {
        return "inici";
    }
    @GetMapping("/gestioTaules")
    public String gestioTaules() {
        return "gestioTaules";
    }
    @GetMapping("/gestioIndexB")
    public String gestioIndexB() {
        return "gestioIndexB";
    }
    @GetMapping("/gestioIndexHash")
    public String gestioIndexHash() {
        return "gestioIndexHash";
    }
    @GetMapping("/executarAlgorisme")
    public String executarAlgorisme() {
        return "executarAlgorisme";
    }
    @GetMapping("/crearTaula")
    public String crearTaula() {
        return "crearTaula";
    }
    @GetMapping("/populateTaula")
    public String populateTaula() {
        return "populateTaula";
    }
    @GetMapping("/addBlocTaula")
    public String addBlocTaula() {
        return "addBlocTaula";
    }
    @GetMapping("/addTuplaNBlocTaula")
    public String addTuplaNBlocTaula() {
        return "addTuplaNBlocTaula";
    }
    @GetMapping("/consultarTaula")
    public String consultarTaula() {
        return "consultarTaula";
    }


}
