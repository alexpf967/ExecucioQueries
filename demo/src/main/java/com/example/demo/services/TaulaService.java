package com.example.demo.services;

import com.example.demo.DemoApplication;
import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.repositories.TuplaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Taula findTaulaByNom (String nom) {
        return taulaRepository.findByNomTaula(nom);
    }

    public void add_bloc(long taula_id) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        if (taula != null) {
            Bloc bloc = new Bloc(taula);
            bloc=blocService.saveBloc(bloc);
            taula.addBloc(bloc);

            saveTaula(taula);
        }
    }
    public void remove_blocN(long taula_id, int blocN) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        if (taula != null) {
            Bloc bloc = this.getNBloc(taula_id, blocN);
            taula.deleteBloc(bloc);
            blocService.removeBloc(bloc.getId());
        }
    }
    public void remove_bloc(long taula_id, long bloc_id) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        if (taula != null) {
            Bloc bloc = blocService.getBlocById(bloc_id);
            taula.deleteBloc(bloc);
            blocService.removeBloc(bloc.getId());
        }
    }

    public void showTaula(long taula_id) {
        boolean exists = taulaRepository.existsById(taula_id);
        if (exists) {
            List<Bloc> b = blocService.getBlocByTaulaID(taula_id);
            System.out.println("La Taula amb id = "+taula_id+", té "+b.size()+" blocs:");
            for(Bloc bloc : b) {
                blocService.printBloc(bloc.getId());
            }
        }
    }
    public Set<Bloc> getAllBlocs(long taula_id){
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        return taula.getTaula();
    }

    public Bloc getNBloc (long taula_id, int n) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        List<Bloc> sb = blocService.getBlocByTaulaID(taula.getId());
        if (n > 0 && n <= sb.size()) {
            DemoApplication.sum_cost(1);
            Bloc b = sb.get(n-1);
            return b;
        }

        return null;
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
    public void addTupla_BlocN (long taula_id, int Nbloc, String atribut) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        Bloc b = this.getNBloc(taula_id, Nbloc);
        blocService.add_tupla(b.getId(), atribut);

    }
    public void removeTupla_BlocN (long taula_id, int Nbloc, int NTupla) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        Bloc b = this.getNBloc(taula_id, Nbloc);
        Tupla t = blocService.getNTupla(b.getId(), NTupla);
        blocService.remove_tupla(b.getId(), t.getId());
    }

    public void escriureBloc(long taula_id, long bloc_id, Bloc bloc) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        Bloc b = blocService.getBlocById(bloc_id);
        List<Tupla> st = tuplaRepository.findByBlocID(b.getId());

        for(Tupla ts : st) {
            Tupla tu1 = tuplaRepository.findById(ts.getId()).orElse(null);
            blocService.remove_tupla(b.getId(), tu1.getId());
        }

        List<Tupla> s = new ArrayList<>(bloc.getBloc());

        for(Tupla t : s) {
            blocService.add_tupla(b.getId(), t.getAtribut());
        }
        DemoApplication.sum_cost(1);
    }

    public void removeTaula(long taula_id) {
        Taula taula = taulaRepository.findById(taula_id).orElse(null);
        if (taula != null) {
            Set<Bloc> sb = taula.getTaula();
            for (Bloc b : sb) {
                Bloc bl = blocService.getBlocById(b.getId());
                this.remove_bloc(taula.getId(), bl.getId());
            }
            taulaRepository.deleteById(taula.getId());

        }

    }
    public Long getIDbynomTaula (String nom_taula) {
        return taulaRepository.findIDByNomTaula(nom_taula);
    }

    public int nBlocs (long taula_id) {
        return blocService.getBlocByTaulaID(taula_id).size();
    }

    public int nTuplas (long taula_id) {
        boolean exists = taulaRepository.existsById(taula_id);
        int cont = 0;
        if (exists) {
            int n = nBlocs(taula_id);
            for (int i = 0; i < n; ++i) {
                Bloc b = getNBloc(taula_id, i+1);
                cont += blocService.Ntuplas(b.getId());
            }
        }
        return cont;
    }

}
