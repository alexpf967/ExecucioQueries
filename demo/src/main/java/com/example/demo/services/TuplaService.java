package com.example.demo.services;

import com.example.demo.repositories.TuplaRepository;
import com.example.demo.classes.Tupla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TuplaService {
    @Autowired
    private TuplaRepository tuplaRepository;
    public Tupla saveTupla(Tupla tupla) {
        return tuplaRepository.save(tupla);
    }
    public void removeTupla(long tupla_id) {
        tuplaRepository.deleteTupla(tupla_id);
    }

    public Tupla getTuplaById(long id) {
        return tuplaRepository.findById(id).orElse(null);
    }
    public List<Tupla> getTuplasByBlocID(long bloc_id) {
        return tuplaRepository.findByBlocID(bloc_id);
    }


}
