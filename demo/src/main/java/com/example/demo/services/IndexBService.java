package com.example.demo.services;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.IndexB;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.IndexBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexBService {
    @Autowired
    private IndexBRepository indexBRepository;
    @Autowired
    private TaulaService taulaService;

    public IndexB saveIndexB(IndexB indexB) {return indexBRepository.save(indexB);}

}
