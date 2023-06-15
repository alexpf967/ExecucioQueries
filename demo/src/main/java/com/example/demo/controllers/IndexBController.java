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
    public String crearIndexB(@RequestParam String nom_taula, @RequestParam String nom_indexb,@RequestParam String f_carrega, @RequestParam String tree_order,Model m) throws Exception {
        try {
            double fc = Double.parseDouble(f_carrega);
            int to = Integer.parseInt(tree_order);
            if(fc < 0.0 || fc > 1.0) throw new RuntimeException("El factor de carrega ha d'estar entre 0 i 1");
            if(to < 1) throw new RuntimeException("L'ordre del arbre ha de ser un enter positiu >=1");
            Taula t = taulaRepository.findByNomTaula(nom_taula);
            String nom = indexBService.getIndexBNomByTaulaID(t.getId());
            if(nom != null) {
                throw new RuntimeException("Ja existeix un indexB amb nom = "+nom+" en la taula "+nom_taula);
            }
            else {
                IndexB indexB = new IndexB(nom_indexb, fc, to, t);
                indexB = indexBService.saveIndexB(indexB);
                indexBService.update_indexB(indexB.getId());
                m.addAttribute("mensaje", "S'ha creat correctament l'index B+ " + nom_indexb + " a la taula " + nom_taula);
                return "crearIndexB";
            }
        }catch (Exception e) {
            String nom = indexBService.getIndexBNomByTaulaID(taulaRepository.findIDByNomTaula(nom_taula));
            if (e.getMessage().equals("El factor de carrega ha d'estar entre 0 i 1"))throw new Exception("El factor de carrega ha d'estar entre 0 i 1");
            else if (e.getMessage().equals("Ja existeix un indexB amb nom = "+nom+" en la taula "+nom_taula))throw new Exception("Ja existeix un indexB amb nom = "+nom+" en la taula "+nom_taula);
            else if (e.getMessage().equals("L'ordre del arbre ha de ser un enter positiu >=1"))throw new Exception("L'ordre del arbre ha de ser un enter positiu >=1");
            else throw new Exception("Ja existeix un index B+ amb el nom indicat o no existeix cap taula amb el nom indicat ");
        }
    }
    @GetMapping("/consultarIndexB")
    public String consultarIndexB(@RequestParam String nom_indexb,Model m) throws Exception {
        try {
            long id = indexBRepository.findIDByNomIndexB(nom_indexb);
            indexBService.update_indexB(id);
            String content = indexBService.consultarIndexB(id, nom_indexb);
            String[] lineas = content.split("\n");
            m.addAttribute("mensaje", lineas);
            return "consultarIndexB";
        }catch (Exception e) {
            throw new Exception("No existeix cap index B+ amb el nom indicat");
        }
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = ex.getMessage();
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
