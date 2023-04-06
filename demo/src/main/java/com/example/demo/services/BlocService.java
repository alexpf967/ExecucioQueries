package com.example.demo.services;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TuplaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void add_tupla(Bloc bloc, Tupla tupla) {
        bloc.addTupla(tupla);
        tuplaService.saveTupla(tupla);
        saveBloc(bloc);
    }
    public void remove_tupla(Bloc bloc, Tupla tupla) {
        bloc.deleteTupla(tupla);
        tuplaService.removeTupla(tupla);
        saveBloc(bloc);
    }



}
