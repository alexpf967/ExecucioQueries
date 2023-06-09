package com.example.demo.services;

import com.example.demo.DemoApplication;
import com.example.demo.classes.Bloc;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BlocService {
    @Autowired
    private BlocRepository blocRepository;
    @Autowired
    private TuplaService tuplaService;

    public Bloc saveBloc(Bloc bloc) {return blocRepository.save(bloc);}

    public void removeBloc(long bloc_id) {
        Bloc bloc = blocRepository.findById(bloc_id).orElse(null);
        if (bloc != null) {
            Set<Tupla> st = bloc.getBloc();
            for(Tupla t : st) {
                Tupla t2 = tuplaService.getTuplaById(t.getId());
                remove_tupla(bloc.getId(), t2.getId());
            }
            blocRepository.deleteBloc(bloc.getId());
        }
    }
    public Bloc getBlocById(long id) {
        DemoApplication.sum_cost(1);
        return blocRepository.findById(id).orElse(null);
    }

    public void add_tupla(long bloc_id, String atribut) {
        Bloc bloc = blocRepository.findById(bloc_id).orElse(null);
        if (bloc != null) {
            Tupla tupla = new Tupla(atribut, bloc);
            bloc.addTupla(tupla);
            tuplaService.saveTupla(tupla);
            saveBloc(bloc);
        }
    }
    public void remove_tupla(long bloc_id, long tupla_id) {
        Bloc bloc = blocRepository.findById(bloc_id).orElse(null);
        if (bloc != null) {
            Tupla tupla = tuplaService.getTuplaById(tupla_id);
            bloc.deleteTupla(tupla);
            tuplaService.removeTupla(tupla_id);


        }
    }
    public void remove_Ntupla(long bloc_id, int tuplaN) {
        Bloc bloc = blocRepository.findById(bloc_id).orElse(null);
        if (bloc != null) {
            Tupla tupla = this.getNTupla(bloc.getId(), tuplaN);
            bloc.deleteTupla(tupla);
            tuplaService.removeTupla(tupla.getId());


        }
    }
    public int Ntuplas (long bloc_id) {
        return tuplaService.getTuplasByBlocID(bloc_id).size();
    }

    public Tupla getNTupla (long bloc_id, int n) {
        Bloc bloc = blocRepository.findById(bloc_id).orElse(null);
        List<Tupla> st = tuplaService.getTuplasByBlocID(bloc.getId());
        Tupla t = st.get(n);
        return t;
    }
    public List<Bloc> getBlocsByTaulaID(long taula_id) {
        return blocRepository.findByTaulaID(taula_id);
    }

    public void printBloc (long bloc_id) {
        Bloc bloc = blocRepository.findById(bloc_id).orElse(null);
        if (bloc != null) {
            List<Tupla> lt = tuplaService.getTuplasByBlocID(bloc.getId());
            System.out.println("El bloc amb id = " + bloc.getId() + ", té " + bloc.nTuplas() + " tuples:");
            for (Tupla t : lt) {
                System.out.println("{id=" + t.getId() + ", atribut=" + t.getAtribut() + "}");
            }
        }
    }

    public List<Tupla> getAllTuplas(long bloc_id){
        return tuplaService.getTuplasByBlocID(bloc_id);
    }
    public String consultarBloc (long bloc_id) {
        Bloc bloc = blocRepository.findById(bloc_id).orElse(null);
        String res = "";
        if (bloc != null) {
            List<Tupla> lt = tuplaService.getTuplasByBlocID(bloc.getId());
            res = "El bloc amb id = " + bloc.getId() + ", té " + bloc.nTuplas() + " tuples:\n";
            for (Tupla t : lt) {
                res += "{id=" + t.getId() + ", atribut=" + t.getAtribut() + "}\n";
            }
        }
        return res;
    }

}
