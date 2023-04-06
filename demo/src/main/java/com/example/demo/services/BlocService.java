package com.example.demo.services;

import com.example.demo.bloc.Bloc;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TuplaRepository;
import com.example.demo.tupla.Tupla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BlocService {
    @Autowired
    private BlocRepository blocRepository;
    @Autowired
    private TuplaRepository tuplaRepository;

    public Bloc saveBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    public List<Bloc> saveBloc(List<Bloc> blocs) {
        return (List<Bloc>) blocRepository.saveAll(blocs);
    }

    public Bloc getBlocById(long id) {
        return blocRepository.findById(id).orElse(null);
    }





}
