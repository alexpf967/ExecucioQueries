package com.example.demo.controllers;

import com.example.demo.DemoApplication;
import com.example.demo.services.AlgorismeService;
import com.example.demo.services.IndexHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/algorisme")
public class AlgorismeController {
    @Autowired
    AlgorismeService algorismeService;
    @PostMapping("/executarAlgPath")
    public String executarAlgPath(@RequestParam String algorisme_path, Model m) {
        algorismeService.executarAlgorismePath(algorisme_path);
        int cost = DemoApplication.cost;
        m.addAttribute("mensaje", "El cost de l'algorisme executat es: "+cost);
        return "executarAlgPath";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = "Se produjo un error."; // Mensaje de error personalizado
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
