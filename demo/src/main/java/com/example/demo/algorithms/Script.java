package com.example.demo.algorithms;


import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("singleton")
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
    private static int cost;

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
        cost = 0;
    }

    public void execute() {
        System.out.println("INICI ALGORISME");
        Taula taula = taulaRepository.findByNomTaula("SS4");
        int blocs = taulaService.nBlocs(taula.getId());
        for(int i = 0; i < blocs; ++i) {
            Bloc b = taulaService.getNBloc(taula.getId(), i);
            blocService.printBloc(b.getId());
        }


        System.out.println("FINAL ALGORISME");
        System.out.println(cost);


    }
    public static void sum_cost(int n) {
        cost = cost+n;
    }

}
