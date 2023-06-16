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
import org.springframework.web.bind.annotation.*;

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
    public String crearIndexHash(@RequestParam String nom_taula, @RequestParam String nom_indexh, @RequestParam String f_carrega, @RequestParam String tree_order, @RequestParam String nBuckets, Model m) throws Exception {
        try {
            double fc = Double.parseDouble(f_carrega);
            int to = Integer.parseInt(tree_order);
            int nb = Integer.parseInt(nBuckets);
            if(fc < 0.0 || fc > 1.0) throw new RuntimeException("El factor de carrega ha d'estar entre 0 i 1");
            if(to < 1) throw new RuntimeException("L'ordre del arbre ha de ser un enter positiu >=1");
            if(nb < 1) throw new RuntimeException("El nombre de buckets ha de ser un enter positiu >=1");
            Taula t = taulaRepository.findByNomTaula(nom_taula);
            String nom = indexHashService.getIndexHashNomByTaulaID(t.getId());
            if(nom != null) {
                throw new RuntimeException("Ja existeix un indexHash amb nom = "+nom+" en la taula "+nom_taula);
            }
            else {
                IndexHash ih = new IndexHash(nom_indexh, fc, to, nb, t);
                ih = indexHashService.saveIndexHash(ih);
                indexHashService.update_indexH(ih.getId());
                m.addAttribute("mensaje", "S'ha creat correctament l'index Hash " + nom_indexh + " a la taula " + nom_taula);
                return "crearIndexHash";
            }
        }catch (Exception e) {
            Long id = taulaRepository.findIDByNomTaula(nom_taula);
            String nom = "";
            if (id != null) {
                nom = indexHashService.getIndexHashNomByTaulaID(id);
            }
            if (e.getMessage().equals("El factor de carrega ha d'estar entre 0 i 1"))throw new Exception("El factor de carrega ha d'estar entre 0 i 1");
            else if (e.getMessage().equals("Ja existeix un indexHash amb nom = "+nom+" en la taula "+nom_taula))throw new Exception("Ja existeix un indexHash amb nom = "+nom+" en la taula "+nom_taula);
            else if (e.getMessage().equals("L'ordre del arbre ha de ser un enter positiu >=1"))throw new Exception("L'ordre del arbre ha de ser un enter positiu >=1");
            else if (e.getMessage().equals("El nombre de buckets ha de ser un enter positiu >=1"))throw new Exception("El nombre de buckets ha de ser un enter positiu >=1");
            else throw new Exception("Ja existeix un index hash amb el nom indicat o no existeix cap taula amb el nom indicat ");
        }
    }
    @GetMapping("/consultarIndexHash")
    public String consultarIndexHash(@RequestParam String nom_indexh, Model m) throws Exception {
        try {
            long id = indexHashRepository.findIDByNomIndexHash(nom_indexh);
            indexHashService.update_indexH(id);
            String content = indexHashService.consultarIndexHash(id, nom_indexh);
            String[] lineas = content.split("\n");
            m.addAttribute("mensaje", lineas);
            return "consultarIndexHash";
        }catch (Exception e) {
            throw new Exception("No existeix cap index hash amb el nom indicat");
        }
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = ex.getMessage();
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
