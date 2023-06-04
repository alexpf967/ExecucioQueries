package com.example.demo.controllers;

import com.example.demo.classes.IndexB;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.IndexBRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.services.IndexBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/indexb")
public class IndexBController {
    @Autowired
    IndexBService indexBService;
    @Autowired
    private TaulaRepository taulaRepository;
    @Autowired
    private IndexBRepository indexBRepository;

    @PostMapping("/crearIndexB")
    public String crearIndexB(@RequestParam String nom_taula, @RequestParam String nom_indexb,@RequestParam String f_carrega, @RequestParam String tree_order,Model m) {
        double fc = Double.parseDouble(f_carrega);
        int to = Integer.parseInt(tree_order);
        Taula t = taulaRepository.findByNomTaula(nom_taula);
        IndexB indexB = new IndexB(nom_indexb, fc, to, t);
        indexB = indexBService.saveIndexB(indexB);
        indexBService.update_indexB(indexB.getId());
        m.addAttribute("mensaje", "S'ha creat correctament l'index B+ " + nom_indexb + " a la taula " + nom_taula);
        return "crearIndexB";
    }
    @PostMapping("/consultarIndexB")
    public String consultarIndexB(@RequestParam String nom_indexb,Model m) {
        long id = indexBRepository.findIDByNomIndexB(nom_indexb);
        indexBService.update_indexB(id);
        String content = indexBService.consultarIndexB(id, nom_indexb);
        String[] lineas = content.split("\n");
        m.addAttribute("mensaje", lineas);
        return "consultarIndexB";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = "Se produjo un error."; // Mensaje de error personalizado
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
