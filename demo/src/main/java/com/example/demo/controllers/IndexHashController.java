package com.example.demo.controllers;

import com.example.demo.classes.IndexB;
import com.example.demo.classes.IndexHash;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.IndexHashRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.services.IndexBService;
import com.example.demo.services.IndexHashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/indexhash")
public class IndexHashController {
    @Autowired
    IndexHashService indexHashService;
    @Autowired
    private TaulaRepository taulaRepository;
    @Autowired
    private IndexHashRepository indexHashRepository;
    @PostMapping("/crearIndexHash")
    public String crearIndexHash(@RequestParam String nom_taula, @RequestParam String nom_indexh, @RequestParam String f_carrega, @RequestParam String tree_order, @RequestParam String nBuckets, Model m) {
        double fc = Double.parseDouble(f_carrega);
        int to = Integer.parseInt(tree_order);
        int nb = Integer.parseInt(nBuckets);
        Taula t = taulaRepository.findByNomTaula(nom_taula);
        IndexHash ih = new IndexHash(nom_indexh, fc, to, nb, t);
        ih = indexHashService.saveIndexHash(ih);
        indexHashService.update_indexHash(ih.getId());
        m.addAttribute("mensaje", "S'ha creat correctament l'index Hash " + nom_indexh + " a la taula " + nom_taula);
        return "crearIndexHash";
    }
    @PostMapping("/consultarIndexHash")
    public String consultarIndexHash(@RequestParam String nom_indexh, Model m) {
        long id = indexHashRepository.findIDByNomIndexHash(nom_indexh);
        indexHashService.update_indexHash(id);
        String content = indexHashService.consultarIndexHash(id, nom_indexh);
        String[] lineas = content.split("\n");
        m.addAttribute("mensaje", lineas);
        return "consultarIndexHash";
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = "Se produjo un error."; // Mensaje de error personalizado
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
