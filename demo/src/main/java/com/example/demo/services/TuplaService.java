package com.example.demo.services;

import com.example.demo.repositories.TuplaRepository;
import com.example.demo.tupla.Tupla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TuplaService {
    @Autowired
    private TuplaRepository tuplaRepository;
    public Tupla saveTupla(Tupla tupla) {
        return tuplaRepository.save(tupla);
    }

}
