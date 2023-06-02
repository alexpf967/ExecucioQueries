package com.example.demo.services;

import com.example.demo.DemoApplication;
import com.example.demo.classes.*;
import com.example.demo.repositories.EntradaRepository;
import com.example.demo.repositories.IndexHashRepository;
import com.example.demo.repositories.TuplaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class IndexHashService {
    @Autowired
    private IndexHashRepository indexHashRepository;
    @Autowired
    private BlocService blocService;
    @Autowired
    private TuplaRepository tuplaRepository;
    @Autowired
    private EntradaRepository entradaRepository;
    @Autowired
    private TaulaService taulaService;

    public IndexHash saveIndexHash(IndexHash indexHash) {return indexHashRepository.save(indexHash);}
    public List<Entrada> getEntradas(long index_id) {
        IndexHash ih = indexHashRepository.findById(index_id).orElse(null);
        Comparator<Entrada> comparadorPorTuplaID = new Comparator<Entrada>() {
            @Override
            public int compare(Entrada o1, Entrada o2) {
                return Integer.compare((int)o1.getTupla_id(), (int)o2.getTupla_id());
            }
        };
        List<Entrada> se = new ArrayList<>(ih.getEntrades());
        se.sort(comparadorPorTuplaID);

        return se;
    }
    public Entrada getEntrada(long entrada_id) {
        DemoApplication.sum_cost(1);
        return entradaRepository.findById(entrada_id).orElse(null);
    }
    public Entrada getNEntrada (long index_id, int n) {
        IndexHash indexHash = indexHashRepository.findById(index_id).orElse(null);
        List<Entrada> se = entradaRepository.findByIndexHashID(indexHash.getId());
        if (n < se.size()) {
            Entrada e = se.get(n);
            return e;
        }
        return null;
    }
    public void update_indexHash(long index_id) {
        long id = indexHashRepository.findTaulaIDByIndexHashID(index_id);
        if (taulaService.nTuplas(id) != entradaRepository.findByIndexHashID(index_id).size()) {
            IndexHash indexHash = indexHashRepository.findById(index_id).orElse(null);
            List<Entrada> entradas = new ArrayList<>();
            if (indexHash != null) {
                List<Bloc> sb = blocService.getBlocByTaulaID(indexHash.getTaula().getId());
                for (int i = 0; i < sb.size(); ++i) {
                    List<Tupla> st = tuplaRepository.findByBlocID(sb.get(i).getId());
                    for (int j = 0; j < st.size(); ++j) {
                        Tupla t = tuplaRepository.findById(st.get(j).getId()).orElse(null);
                        if (t != null) {
                            Entrada e = entradaRepository.findByTuplaID(t.getId());
                            if (e == null) {
                                Entrada eNew = new Entrada(t.getId(), i, j, indexHash);
                                int tu = Math.toIntExact(t.getId());
                                int b = calculate_hash(indexHash.getnBuckets(), tu);
                                eNew.setnBucket(b);
                                entradas.add(eNew);
                                indexHash.add_entrada(eNew);
                            } else {
                                if (e.getIndexHash() == null) {
                                    e.setIndexHash(indexHash);
                                    int tu = Math.toIntExact(t.getId());
                                    int b = calculate_hash(indexHash.getnBuckets(), tu);
                                    e.setnBucket(b);
                                    indexHash.add_entrada(e);
                                    entradaRepository.save(e);
                                }
                            }
                        }
                    }
                }
                entradaRepository.saveAll(entradas);
                saveIndexHash(indexHash);
            }
        }
    }
    public int calculate_hash(int maxBuckets, int tupla_id) {
        int res = (tupla_id % maxBuckets);
        return res+1;
    }

    public List<Entrada> getBucketN (long index_id, int n) {
        IndexHash ih = indexHashRepository.findById(index_id).orElse(null);
        if(ih != null) {
            int nBuckets = ih.getnBuckets();
            if(n > 0 && n <= nBuckets) {
                DemoApplication.sum_cost(1);
                return entradaRepository.findBucketNIndexHash(ih.getId(), n);
            }
        }
        return null;
    }
    public List<Entrada> getPrimerBucket (long index_id) {
        IndexHash ih = indexHashRepository.findById(index_id).orElse(null);
        if(ih != null) {
            return getBucketN(ih.getId(), 1);
        }
        return null;
    }
    public int getnBuckets(long index_id) {
        IndexHash ih = indexHashRepository.findById(index_id).orElse(null);
        return ih.getnBuckets();
    }
    public boolean ultimBucket(long index_id, int n) {
        return getnBuckets(index_id)==n;
    }
    public int cercaBucket(long index_id, long tupla_id) {
        IndexHash ih = indexHashRepository.findById(index_id).orElse(null);
        List<Entrada> le = entradaRepository.findByIndexHashID(ih.getId());
        for(Entrada e : le) {
            if(e.getTupla_id()==tupla_id) {
                DemoApplication.sum_cost(2);
                return e.getnBucket();
            }
        }
        return 0;
    }

}
