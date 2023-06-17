package com.example.demo.services;

import com.example.demo.DemoApplication;
import com.example.demo.classes.*;
import com.example.demo.repositories.EntradaRepository;
import com.example.demo.repositories.IndexHashRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.repositories.TuplaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private TaulaRepository taulaRepository;
    @Autowired
    private EntradaService entradaService;
    private int bucket_actual = 1;


    public IndexHash saveIndexHash(IndexHash indexHash) {return indexHashRepository.save(indexHash);}
    public long findIDByNomIndexHash (String nom) {
        long id= indexHashRepository.findIDByNomIndexHash(nom);
        update_indexH(id);
        return(id);
    }

    public List<Entrada> getEntradas(long index_id) {
        boolean exists = indexHashRepository.existsById(index_id);
        List<Entrada> se = new ArrayList<>();
        if (exists) se = entradaRepository.findByIndexHashID(index_id);
        return se;
    }
    public Entrada getEntrada(long entrada_id) {
        DemoApplication.sum_cost(1);
        return entradaService.getEntrada(entrada_id);
    }
    public void update_indexHash(long index_id) {
        long id = indexHashRepository.findTaulaIDByIndexHashID(index_id);
        if (taulaService.nTuplas(id) != entradaRepository.findByIndexHashID(index_id).size()) {
            List<Entrada> entradas = new ArrayList<>();
            boolean exists = indexHashRepository.existsById(index_id);
            if(exists) {
                long taula_id = indexHashRepository.findTaulaIDByIndexHashID(index_id);
                List<Bloc> sb = blocService.getBlocsByTaulaID(taula_id);
                for (int i = 0; i < sb.size(); ++i) {
                    List<Tupla> st = tuplaRepository.findByBlocID(sb.get(i).getId());
                    for (int j = 0; j < st.size(); ++j) {
                        Tupla t = tuplaRepository.findById(st.get(j).getId()).orElse(null);
                        if (t != null) {
                            Entrada e = entradaRepository.findByTuplaID(t.getId());
                            IndexHash indexHash = new IndexHash();
                            indexHash.setId(index_id);
                            int buckets = getnBuckets(index_id);
                            if (e == null) {
                                Entrada eNew = new Entrada(t.getId(), i, j, indexHash);
                                int tu = Math.toIntExact(t.getId());
                                int b = calculate_hash(buckets, tu);
                                eNew.setnBucket(b);
                                entradas.add(eNew);
                            } else {
                                if (e.getIndexHash() == null) {
                                    e.setIndexHash(indexHash);
                                    int tu = Math.toIntExact(t.getId());
                                    int b = calculate_hash(buckets, tu);
                                    e.setnBucket(b);
                                    entradaRepository.save(e);
                                }
                            }
                        }
                    }
                }
                entradaRepository.saveAll(entradas);
            }
        }
    }
    public void update_indexH(long indexHash_id) {
        List<Entrada> le = getEntradas(indexHash_id);
        long taula_id = indexHashRepository.findTaulaIDByIndexHashID(indexHash_id);
        List<Bloc> sb = blocService.getBlocsByTaulaID(taula_id);
        for(int i = 0; i < sb.size(); ++i) {
            List<Tupla> st = tuplaRepository.findByBlocID(sb.get(i).getId());
            for (int j = 0; j < st.size(); ++j) {
                Entrada e = entradaRepository.findByTuplaID(st.get(j).getId());
                if (e != null) le.remove(e);
            }
        }
        if(!le.isEmpty())entradaRepository.deleteAll(le);
        this.update_indexHash(indexHash_id);
    }
    public int calculate_hash(int maxBuckets, int tupla_id) {
        int res = (tupla_id % maxBuckets);
        return res+1;
    }

    public List<Entrada> getBucketN (long index_id, int n) {
        boolean exists = indexHashRepository.existsById(index_id);
        if(exists) {
            int nBuckets = getnBuckets(index_id);
            if(n > 0 && n <= nBuckets) {
                DemoApplication.sum_cost(1);
                return entradaRepository.findBucketNIndexHash(index_id, n);
            }
        }
        return null;
    }
    public List<Entrada> getPrimerBucket (long index_id) {
        boolean exists = indexHashRepository.existsById(index_id);
        if(exists) {
            return getBucketN(index_id, 1);
        }
        return null;
    }
    public int getnBuckets(long index_id) {
        return indexHashRepository.NbucketsIndexHash(index_id);
    }
    public boolean ultimBucket(long index_id, int n) {
        return getnBuckets(index_id)==n;
    }
    public int cercaBucket(long index_id, long tupla_id) {
        boolean exists = indexHashRepository.existsById(index_id);
        if(exists) {
            List<Entrada> le = entradaRepository.findByIndexHashID(index_id);
            for (Entrada e : le) {
                if (e.getTupla_id() == tupla_id) {
                    DemoApplication.sum_cost(2);
                    bucket_actual = e.getnBucket();
                    return e.getnBucket();
                }
            }
        }
        return 0;
    }

    public String consultarIndexHash (long indexh_id, String nom_indexh) {
        boolean exists = indexHashRepository.existsById(indexh_id);
        String res = "";
        if (exists) {
            long taula_id = indexHashRepository.findTaulaIDByIndexHashID(indexh_id);
            String nom_taula = taulaRepository.findNomByTaulaID(taula_id);
            int nBuckets = getnBuckets(indexh_id);
            res = "L'index B+ " + nom_indexh + " de la taula " + nom_taula + " té " + nBuckets +" buckets:\n";
            for(int i = 1; i <= nBuckets; ++i) {
                res += "El bucket " + i + " té les següents entrades: \n";
                List<Entrada> le = entradaRepository.findEntradaByIndexHashIDandNBucket(indexh_id, i);
                for (Entrada e : le) {
                    res += "{entrada_id=" + e.getId() + ", tupla_id="+ e.getTupla_id()+", nBloc="+e.getnBloc()+", nTupla="+ e.getnTupla()+"}\n";
                }
            }
        }
        return res;
    }

    public void primerBucket() {
        bucket_actual = 1;
    }
    public void seguentBucket() {
        bucket_actual += 1;
    }

    public List<Entrada> getBucket(long indexh_id) {
        return getBucketN(indexh_id, bucket_actual);
    }
    public int getBucket_actual() {
        return bucket_actual;
    }

    public String getIndexHashNomByTaulaID (long taula_id) {
        return indexHashRepository.findNomIndexHashByTaulaID(taula_id);
    }

}
