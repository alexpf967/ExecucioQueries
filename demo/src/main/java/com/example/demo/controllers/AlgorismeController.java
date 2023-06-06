package com.example.demo.controllers;

import com.example.demo.DemoApplication;
import com.example.demo.services.AlgorismeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/api/algorisme")
public class AlgorismeController {
    @Autowired
    AlgorismeService algorismeService;
    @PostMapping("/executarAlgPath")
    public String executarAlgPath(@RequestParam String algorisme_path, Model m) {
        algorismeService.carregarAlgPath(algorisme_path);
        m.addAttribute("mensaje", "Algorisme carregat correctament");
        return "executarAlgPath";
    }
    @PostMapping("/executarAlgContent")
    public String executarAlgContent(@RequestParam String executarAlgContent, Model m) {
        algorismeService.carregarAlgContent(executarAlgContent);
        m.addAttribute("mensaje", "Algorisme carregat correctament");
        return "executarAlgContent";
    }
    @PostMapping("/executarP")
    public String executarP( Model m) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        PrintStream originalOut = System.out;
        System.setOut(printStream);
        algorismeService.executar();
        String resultat = "El resultat del algorisme: \n"+baos.toString();
        int cost = DemoApplication.cost;
        resultat= resultat+"\nEl cost de l'algorisme executat es: "+cost+ "\n";
        String[] lineas = resultat.split("\n");
        m.addAttribute("resultat", lineas);
        System.setOut(originalOut);
        return "executarAlgPath";
    }
    @PostMapping("/executarC")
    public String executarC( Model m) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        PrintStream originalOut = System.out;
        System.setOut(printStream);
        algorismeService.executar();
        String resultat = "El resultat del algorisme: \n"+baos.toString();
        int cost = DemoApplication.cost;
        resultat= resultat+"\nEl cost de l'algorisme executat es: "+cost+ "\n";
        String[] lineas = resultat.split("\n");
        m.addAttribute("resultat", lineas);
        System.setOut(originalOut);
        return "executarAlgContent";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = "Se produjo un error."; // Mensaje de error personalizado
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
