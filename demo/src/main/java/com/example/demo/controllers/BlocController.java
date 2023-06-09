package com.example.demo.controllers;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Tupla;
import com.example.demo.services.BlocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bloc")
public class BlocController {
    @Autowired
    private BlocService blocService;

    @PostMapping("/addTupla")
    public void addTupla(@RequestParam long bloc, @RequestParam String tupla) {
        blocService.add_tupla(bloc, tupla);
    }

    @DeleteMapping("/deleteTupla")
    public void removeTupla(@RequestParam Bloc bloc, @RequestParam Tupla tupla) {
        //blocService.remove_tupla(bloc, tupla);
    }
    @GetMapping("/Ntuplas")
    @ResponseBody
    public int getNtuplas(@RequestParam long bloc_id) {
        return blocService.Ntuplas(bloc_id);
    }
    @PostMapping(consumes = "application/json")
    public Bloc saveBloc(@RequestBody Bloc bloc) {
        return blocService.saveBloc(bloc);
    }
}
