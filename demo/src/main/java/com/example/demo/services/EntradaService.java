package com.example.demo.services;

import com.example.demo.classes.Entrada;
import com.example.demo.classes.IndexB;
import com.example.demo.repositories.EntradaRepository;
import com.example.demo.repositories.IndexBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntradaService {
    @Autowired
    private EntradaRepository entradaRepository;
    @Autowired
    private TaulaService taulaService;
    public Entrada saveEntrada(Entrada entrada) {return entradaRepository.save(entrada);}

}
