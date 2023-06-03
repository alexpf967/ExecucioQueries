package com.example.demo.controllers;

import com.example.demo.classes.Taula;
import com.example.demo.services.BlocService;
import com.example.demo.services.TaulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/taula")
public class TaulaController {
    @Autowired
    private TaulaService taulaService;

    @PostMapping("/crearTaula")
    public String crearTaula(@RequestParam String nom_taula, Model m) {
        Taula t = new Taula(nom_taula);
        t = taulaService.saveTaula(t);
        m.addAttribute("mensaje", "S'ha creat correctament la Taula " + nom_taula + " amb ID: " + t.getId());
        return "crearTaula";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = "Se produjo un error."; // Mensaje de error personalizado
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}
