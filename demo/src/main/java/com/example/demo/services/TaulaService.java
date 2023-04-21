package com.example.demo.services;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.repositories.TuplaRepository;
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
    @Autowired
    private TuplaRepository tuplaRepository;

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

    public void populate (String nom_taula, int nBlocs, int nTuplas) {
        Taula taula = taulaRepository.findByNomTaula(nom_taula);
        for (int i = 0; i < nBlocs; ++i) {
            this.add_bloc(taula.getId());
        }
        taula = taulaRepository.findById(taula.getId()).orElse(null);

        if (taula != null) {
            Set<Bloc> sb = taula.getTaula();
            for (Bloc b : sb) {
                Bloc bb = blocService.getBlocById(b.getId());
                for (int i = 0; i < nTuplas; ++i) blocService.add_tupla(bb.getId(), "populate"+bb.getId()+"-"+i);
            }
        }
    }
    public void swapBloc(long taula_id, long bloc_id, Bloc bloc) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        Bloc b = blocService.getBlocById(bloc_id);
        List<Tupla> st = tuplaRepository.findByBlocID(b.getId());

        for(Tupla ts : st) {
            Tupla tu1 = tuplaRepository.findById(ts.getId()).orElse(null);
            blocService.remove_tupla(b.getId(), tu1.getId());
        }

        Set<Tupla> s = bloc.getBloc();
        for(Tupla t : s) {
            Tupla tu = tuplaRepository.findById(t.getId()).orElse(null);
            blocService.add_tupla(b.getId(), tu.getAtribut());
        }

        taulaRepository.save(taula);
    }


}
