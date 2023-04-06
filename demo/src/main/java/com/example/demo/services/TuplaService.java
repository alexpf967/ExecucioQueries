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

    public List<Tupla> saveTupla(List<Tupla> tuplas) {
        return (List<Tupla>) tuplaRepository.saveAll(tuplas);
    }
    public void removeTupla(Tupla tupla) {
        tuplaRepository.delete(tupla);
    }


    public Tupla getTuplaById(long id) {
        return tuplaRepository.findById(id).orElse(null);
    }
    public List<Tupla> getTuplasByAtribut(String atribut) {
        return tuplaRepository.findByAtribut(atribut);
    }
    public List<Tupla> getTuplasByBlocID(long bloc_id) {
        return tuplaRepository.findByBlocID(bloc_id);
    }


}
