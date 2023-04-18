package com.example.demo.services;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TaulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TaulaService {
    @Autowired
    private TaulaRepository taulaRepository;
    @Autowired
    private BlocService blocService;
    @Autowired
    private BlocRepository blocRepository;

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
   /* public void remove_bloc(Taula taula, Bloc bloc) {
        taula.deleteBloc(bloc);
        blocService.removeBloc(bloc);
        saveTaula(taula);
    }*/

    public void showTaula(long taula_id) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        Set<Bloc> b = this.getAllBlocs(taula.getId());
        System.out.println("La Taula amb id="+taula.getId()+" te els seguentes blocs:");
        for(Bloc bloc : b) {
            blocService.printBloc(bloc);
        }
    }
    public void remove_bloc(long taula_id, long bloc_id) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        if (taula != null) {
            Bloc bloc = blocRepository.findById(bloc_id).orElse(null);
            taula.deleteBloc(bloc);
            blocService.removeBloc(bloc.getId());
            saveTaula(taula);
        }
    }
    public Set<Bloc> getAllBlocs(long taula_id){
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        return taula.getTaula();
    }

    public Bloc getNBloc (long taula_id, int n) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        List<Bloc> sb = blocService.getBlocByTaulaID(taula.getId());
        Bloc b = sb.get(n);
        return b;
    }


}
