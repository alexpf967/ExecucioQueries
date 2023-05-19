package com.example.demo.algorithms;


import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Script {
    private TuplaService tuplaService;
    private BlocService blocService;
    private TaulaService taulaService;
    private IndexBService indexBService;
    private IndexHashService indexHashService;

    private TuplaRepository tuplaRepository;
    private BlocRepository blocRepository;
    private TaulaRepository taulaRepository;
    private IndexBRepository indexBRepository;
    private IndexHashRepository indexHashRepository;

    @Autowired
    public Script(TuplaService tuplaService, BlocService blocService, TaulaService taulaService, IndexBService indexBService, IndexHashService indexHashService, TuplaRepository tuplaRepository, BlocRepository blocRepository, TaulaRepository taulaRepository, IndexBRepository indexBRepository, IndexHashRepository indexHashRepository) {
        this.tuplaService = tuplaService;
        this.blocService = blocService;
        this.taulaService = taulaService;
        this.indexBService = indexBService;
        this.indexHashService = indexHashService;
        this.tuplaRepository = tuplaRepository;
        this.blocRepository = blocRepository;
        this.taulaRepository = taulaRepository;
        this.indexBRepository = indexBRepository;
        this.indexHashRepository = indexHashRepository;
    }

    public void execute() {
        System.out.println("INICI ALGORISME");
        Taula taula = taulaRepository.findByNomTaula("it1");
        List<Bloc> lb = blocService.getBlocByTaulaID(taula.getId());
        for(Bloc b : lb) {
            Bloc b2 = blocRepository.findById(b.getId()).orElse(null);
            b2.showBloc();
        }

        System.out.println("FINAL ALGORISME");

    }

}
