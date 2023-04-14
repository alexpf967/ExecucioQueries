package com.example.demo.services;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TaulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaulaService {
    @Autowired
    private TaulaRepository taulaRepository;
    @Autowired
    private BlocService blocService;
    public Taula saveTaula(Taula taula) {return taulaRepository.save(taula);}
    public void add_bloc(long taula_id) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        if (taula != null) {
            Bloc bloc = new Bloc(taula);
            bloc=blocService.saveBloc(bloc);
            taula.addBloc(bloc);

            saveTaula(taula);
        }
    }
}
