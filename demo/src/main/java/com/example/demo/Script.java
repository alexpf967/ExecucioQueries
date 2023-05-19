package com.example.demo;


import com.example.demo.classes.Taula;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.repositories.TuplaRepository;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
public class Script implements CommandLineRunner {
    private BlocService bs;
    private IndexBService ibs;
    private IndexHashService ihs;
    private TaulaService taulaService;
    private TuplaService ts;
    private TuplaRepository tuplaRepository;
    private TaulaRepository taulaRepository;

    @Autowired
    public Script(TaulaService taulaService, TaulaRepository taulaRepository) {
        this.taulaService = taulaService;
        this.taulaRepository = taulaRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hola script");
        Taula t = taulaRepository.findByNomTaula("TAULAINDEX30");
        taulaService.showTaula(t.getId());
        System.out.println("Adeu script");

    }

}
