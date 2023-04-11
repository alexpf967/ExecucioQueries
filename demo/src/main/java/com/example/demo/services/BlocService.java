package com.example.demo.services;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TuplaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BlocService {
    @Autowired
    private BlocRepository blocRepository;
    @Autowired
    private TuplaService tuplaService;

    public Bloc saveBloc(Bloc bloc) {return blocRepository.save(bloc);}

    public List<Bloc> saveBlocs(List<Bloc> blocs) {
        return (List<Bloc>) blocRepository.saveAll(blocs);
    }

    public Bloc getBlocById(long id) {
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
    public void remove_tupla(Bloc bloc, Tupla tupla) {
        bloc.deleteTupla(tupla);
        tuplaService.removeTupla(tupla);
        saveBloc(bloc);
    }

    public int Ntuplas (Bloc bloc) {
        return bloc.nFulles();
    }

    public Tupla getTuplaByID(Bloc bloc, long tupla_id) {
        Tupla t = bloc.getTupla(tupla_id);
        return t;

    }
    public void printBloc (Bloc bloc) {
        bloc.showBloc();
    }

    public Set<Tupla> getAllTuplas(Bloc bloc){
        return bloc.getBloc();
    }


}
