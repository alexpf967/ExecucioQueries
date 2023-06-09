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
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/api/algorisme")
public class AlgorismeController {
    @Autowired
    AlgorismeService algorismeService;
    @PostMapping("/executarAlgPath")
    public String executarAlgPath(@RequestParam("algorisme_file") MultipartFile file, Model m) throws IOException {
        String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
        algorismeService.carregarAlgContent(fileContent);
        m.addAttribute("mensaje", "Algorisme carregat correctament");
        return "executarAlgPath";
    }
    @PostMapping("/executarAlgContent")
    public String executarAlgContent(@RequestParam String executarAlgContent, Model m) throws IOException {
        algorismeService.carregarAlgContent(executarAlgContent);
        m.addAttribute("mensaje", "Algorisme carregat correctament");
        return "executarAlgContent";
    }
    @PostMapping("/executarP")
    public String executarP( Model m) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
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
    public String executarC( Model m) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
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
        String errorMessage = ex.getMessage();
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
