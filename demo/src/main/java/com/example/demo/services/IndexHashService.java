package com.example.demo.services;

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
    private TaulaService taulaService;
    @Autowired
    private BlocService blocService;
    @Autowired
    private TuplaRepository tuplaRepository;
    @Autowired
    private EntradaRepository entradaRepository;

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
    public Entrada getNEntrada (long index_id, int n) {
        IndexHash indexHash = indexHashRepository.findById(index_id).orElse(null);
        List<Entrada> se = entradaRepository.findByIndexHashID(indexHash.getId());
        if (n < se.size()) {
            Entrada e = se.get(n);
            return e;
        }
        return null;
    }
    public void update_indexHash(long index_id){
        IndexHash indexHash = indexHashRepository.findById(index_id).orElse(null);
        List<Entrada> entradas = new ArrayList<>();
        if (indexHash != null) {
            List<Bloc> sb = blocService.getBlocByTaulaID(indexHash.getTaula().getId());
            for(int i = 0; i < sb.size(); ++i) {
                List<Tupla> st = tuplaRepository.findByBlocID(sb.get(i).getId());
                for(int j = 0; j < st.size(); ++j) {
                    Tupla t = tuplaRepository.findById(st.get(j).getId()).orElse(null);
                    if (t != null) {
                        Entrada e = entradaRepository.findByTuplaID(t.getId());
                        if (e == null) {
                            Entrada eNew = new Entrada(t.getId(), i, j, indexHash);
                            int tu = Math.toIntExact(t.getId());
                            int b = calculate_hash(indexHash.getnBuckets(),tu);
                            eNew.setnBucket(b);
                            entradas.add(eNew);
                            indexHash.add_entrada(eNew);
                        }
                        else {
                            if (e.getIndexHash() == null) {
                                e.setIndexHash(indexHash);
                                int tu = Math.toIntExact(t.getId());
                                int b = calculate_hash(indexHash.getnBuckets(),tu);
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
   /* public void update_NBucket(long index_id) {
        IndexHash indexHash = indexHashRepository.findById(index_id).orElse(null);
        if(indexHash != null) {
            int u = indexHash.getEntries_buckets();
            List<Entrada> le = this.getEntradas(indexHash.getId());
            for(Entrada e : le) {
                Entrada e1 = entradaRepository.findById(e.getId()).orElse(null);
                int bucket = calculate_hash(indexHash.getnBuckets(), (int)e.getTupla_id());
                if (e1 != null) {
                    if (e1.getnBucket()==0) {
                        if(entradaRepository.findBucketNIndexHash(indexHash.getId(), bucket).size()<=u) {
                            e1.setnBucket(bucket);
                            entradaRepository.save(e1);
                        }
                    }
                }

            }
            /*for(int i = 0; i < le.size(); ++i) {
                Entrada e = entradaRepository.findById(le.get(i).getId()).orElse(null);
                if (e != null) {
                    int bucket = calculate_hash(indexHash.getId(), e.getTupla_id());
                    if (entradaRepository.findBucketNIndexHash(indexHash.getId(), bucket).size() <= nFulles) {
                        if (e != null) {
                            if (e.getnBucket() == 0) {
                                e.setnBucket(bucket);
                                entradaRepository.save(e);
                            }
                        }
                    }
                }
            }
        }
    }
    */

    public int calculate_hash(int maxBuckets, int tupla_id) {
        int res = (tupla_id % maxBuckets);
        return res+1;
    }
    /*public void update_indexHash(long index_id) {
        IndexHash indexHash = indexHashRepository.findById(index_id).orElse(null);
        if(indexHash != null) {
            this.updateEntradas(indexHash.getId());
            //this.update_NBucket(indexHash.getId());
        }
    }*/

}
