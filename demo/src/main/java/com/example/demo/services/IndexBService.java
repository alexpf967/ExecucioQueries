package com.example.demo.services;

import com.example.demo.classes.Bloc;
import com.example.demo.classes.Entrada;
import com.example.demo.classes.IndexB;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.EntradaRepository;
import com.example.demo.repositories.IndexBRepository;
import com.example.demo.repositories.TuplaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IndexBService {
    @Autowired
    private IndexBRepository indexBRepository;
    @Autowired
    private TaulaService taulaService;
    @Autowired
    private BlocService blocService;
    @Autowired
    private TuplaRepository tuplaRepository;
    @Autowired
    private EntradaRepository entradaRepository;

    public IndexB saveIndexB(IndexB indexB) {return indexBRepository.save(indexB);}

    public void setfulles(long index_id) {
        IndexB ib = indexBRepository.findById(index_id).orElse(null);
        List<Entrada> entradas = new ArrayList<>();
        if (ib != null) {
            List<Bloc> sb = blocService.getBlocByTaulaID(ib.getTaula().getId());
            for(int i = 0; i < sb.size(); ++i) {
                List<Tupla> st = tuplaRepository.findByBlocID(sb.get(i).getId());
                for(int j = 0; j < st.size(); ++j) {
                    Tupla t = tuplaRepository.findById(st.get(j).getId()).orElse(null);
                    if (t != null) {
                        Entrada e = new Entrada(t.getId(), i, j);
                        entradas.add(e);
                        ib.add_fulla(e);
                    }
                }

            }
            entradaRepository.saveAll(entradas);
            saveIndexB(ib);
        }

    }

}
