package com.example.demo.controllers;

import com.example.demo.classes.IndexB;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.services.IndexBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/indexb")
public class IndexBController {
    @Autowired
    IndexBService indexBService;
    @Autowired
    private TaulaRepository taulaRepository;

    @PostMapping("/crearIndexB")
    public String crearTaula(@RequestParam String nom_taula, @RequestParam String nom_indexb,@RequestParam String f_carrega, @RequestParam String tree_order,Model m) {
        double fc = Double.parseDouble(f_carrega);
        int to = Integer.parseInt(tree_order);
        Taula t = taulaRepository.findByNomTaula(nom_taula);
        IndexB indexB = new IndexB(nom_indexb, fc, to, t);
        indexB = indexBService.saveIndexB(indexB);
        indexBService.update_indexB(indexB.getId());
        m.addAttribute("mensaje", "S'ha creat correctament l'index B+ " + nom_indexb + " a la taula " + nom_taula);
        return "crearIndexB";
    }
}
