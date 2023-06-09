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
    @GetMapping("/escriureBlocTaula")
    public String escriureBlocTaula() {
        return "escriureBlocTaula";
    }
    @GetMapping("/crearIndexB")
    public String crearIndexB() {
        return "crearIndexB";
    }
    @GetMapping("/consultarIndexB")
    public String consultarIndexB() {
        return "consultarIndexB";
    }
    @GetMapping("/crearIndexHash")
    public String crearIndexHash() {
        return "crearIndexHash";
    }
    @GetMapping("/consultarIndexHash")
    public String consultarIndexHash() {
        return "consultarIndexHash";
    }
    @GetMapping("/executarAlgPath")
    public String executarAlgPath() {
        return "executarAlgPath";
    }
    @GetMapping("/executarAlgContent")
    public String executarAlgContent() {
        return "executarAlgContent";
    }




}
