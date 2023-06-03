package com.example.demo.controllers;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.classes.Tupla;
import com.example.demo.services.BlocService;
import com.example.demo.services.TaulaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
    @PostMapping("/populateTaula")
    public String populateTaula(@RequestParam String nom_taula, @RequestParam String nBloc, @RequestParam String nTupla, Model m) {
        int nB=Integer.parseInt(nBloc);
        int nT=Integer.parseInt(nTupla);
        taulaService.populate(nom_taula, nB, nT);
        m.addAttribute("mensaje", "S'ha fet populate correctament la Taula " + nom_taula);
        return "populateTaula";
    }
    @PostMapping("/addBlocTaula")
    public String addBlocTaula(@RequestParam String nom_taula, Model m) {
        long id = taulaService.getIDbynomTaula(nom_taula);
        taulaService.add_bloc(id);
        m.addAttribute("mensaje", "S'ha afegit correctament un bloc la Taula " + nom_taula);
        return "addBlocTaula";
    }
    @PostMapping("/addTuplaNBlocTaula")
    public String addTuplaNBlocTaula(@RequestParam String nom_taula, String Nbloc, String atribut, Model m) {
        long id = taulaService.getIDbynomTaula(nom_taula);
        int nB=Integer.parseInt(Nbloc);
        taulaService.addTupla_BlocN(id, nB, atribut);
        m.addAttribute("mensaje", "S'ha afegit correctament la tupla amb atribut: "+ atribut+" al bloc " + nB + " de la Taula " + nom_taula);
        return "addTuplaNBlocTaula";
    }
    @PostMapping("/consultarTaula")
    public String consultarTaula(@RequestParam String nom_taula, Model m) {
        long id = taulaService.getIDbynomTaula(nom_taula);
        String content = taulaService.consultarTaula(id);
        String[] lineas = content.split("\n");
        m.addAttribute("mensaje", lineas);
        return "consultarTaula";
    }
    @PostMapping("/escriureBlocTaula")
    public String escriureBlocTaula(@RequestParam String nom_taula, @RequestParam String blocid, @RequestParam String atributs, Model m) {
        String str [] = atributs.split(",");
        long nB=Long.parseLong(blocid);
        long id = taulaService.getIDbynomTaula(nom_taula);
        Bloc b = new Bloc();
        for(String s : str) {
            Tupla t = new Tupla();
            t.setAtribut(s);
            b.addTupla(t);
        }
        taulaService.escriureBloc(id, nB, b);
        m.addAttribute("mensaje", "S'ha escrit correctament el bloc amb id: "+blocid);
        return "escriureBlocTaula";
    }


    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        String errorMessage = "Se produjo un error."; // Mensaje de error personalizado
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }

}
