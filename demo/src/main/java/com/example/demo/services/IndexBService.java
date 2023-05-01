package com.example.demo.services;

import com.example.demo.classes.*;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.EntradaRepository;
import com.example.demo.repositories.IndexBRepository;
import com.example.demo.repositories.TuplaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
                        Entrada e = new Entrada(t.getId(), i, j, ib);
                        entradas.add(e);
                        ib.add_fulla(e);
                    }
                }

            }
            entradaRepository.saveAll(entradas);
            saveIndexB(ib);
        }

    }
    public List<Entrada> getfulles(long index_id) {
        IndexB ib = indexBRepository.findById(index_id).orElse(null);
        Comparator<Entrada> comparadorPorTuplaID = new Comparator<Entrada>() {
            @Override
            public int compare(Entrada o1, Entrada o2) {
                return Integer.compare((int)o1.getTupla_id(), (int)o2.getTupla_id());
            }
        };
        List<Entrada> se = new ArrayList<>(ib.getFulles());
        se.sort(comparadorPorTuplaID);

        return se;
    }

    public Entrada getNEntrada (long index_id, int n) {
        IndexB indexB = indexBRepository.findById(index_id).orElse(null);
        List<Entrada> se = entradaRepository.findByIndexBID(indexB.getId());
        if (n < se.size()) {
            Entrada e = se.get(n);
            return e;
        }
        return null;
    }

    public void updateFulles (long index_id){
        IndexB ib = indexBRepository.findById(index_id).orElse(null);
        List<Entrada> entradas = new ArrayList<>();
        if (ib != null) {
            List<Bloc> sb = blocService.getBlocByTaulaID(ib.getTaula().getId());
            for(int i = 0; i < sb.size(); ++i) {
                List<Tupla> st = tuplaRepository.findByBlocID(sb.get(i).getId());
                for(int j = 0; j < st.size(); ++j) {
                    Tupla t = tuplaRepository.findById(st.get(j).getId()).orElse(null);
                    if (t != null) {
                        Entrada e = entradaRepository.findByTuplaID(t.getId());
                        if (e == null) {
                            Entrada eNew = new Entrada(t.getId(), i, j, ib);
                            entradas.add(eNew);
                            ib.add_fulla(eNew);
                        }
                    }
                }

            }
            entradaRepository.saveAll(entradas);
            saveIndexB(ib);
        }

    }

}
