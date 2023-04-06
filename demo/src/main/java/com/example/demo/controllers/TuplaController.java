package com.example.demo.controllers;


import com.example.demo.services.TuplaService;
import com.example.demo.classes.Tupla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tupla")
public class TuplaController {
    @Autowired
    private TuplaService tuplaService;

    @PostMapping("/add")
    public Tupla saveTupla(@RequestBody Tupla tupla) {
        return tuplaService.saveTupla(tupla);
    }

}
