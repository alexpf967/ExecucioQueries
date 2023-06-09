package com.example.demo.services;

import com.example.demo.DemoApplication;
import com.example.demo.classes.*;
import com.example.demo.repositories.EntradaRepository;
import com.example.demo.repositories.IndexBRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.repositories.TuplaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IndexBService {
    @Autowired
    private IndexBRepository indexBRepository;
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


    public IndexB saveIndexB(IndexB indexB) {return indexBRepository.save(indexB);}
    public long findIDByNomIndexB (String nom) {
        return indexBRepository.findIDByNomIndexB(nom);
    }

    public List<Entrada> getEntradas(long index_id) {
        boolean exists = indexBRepository.existsById(index_id);
        List<Entrada> se = new ArrayList<>();
        if (exists) se = entradaRepository.findByIndexBID(index_id);
        return se;
    }
    public Entrada getEntrada(long entrada_id) {
        DemoApplication.sum_cost(1);
        return entradaService.getEntrada(entrada_id);
    }

    public void updateEntradas(long index_id){
        boolean exists = indexBRepository.existsById(index_id);
        List<Entrada> entradas = new ArrayList<>();

        if(exists) {
            long taula_id = indexBRepository.findTaulaIDByIndexBID(index_id);
            List<Bloc> sb = blocService.getBlocsByTaulaID(taula_id);
            for(int i = 0; i < sb.size(); ++i) {
                List<Tupla> st = tuplaRepository.findByBlocID(sb.get(i).getId());
                for(int j = 0; j < st.size(); ++j) {
                    Tupla t = tuplaRepository.findById(st.get(j).getId()).orElse(null);
                    if (t != null) {
                        Entrada e = entradaRepository.findByTuplaID(t.getId());
                        IndexB ib = new IndexB();
                        ib.setId(index_id);
                        if (e == null) {
                            Entrada eNew = new Entrada(t.getId(), i, j, ib);
                            entradas.add(eNew);
                        }
                        else {
                            if (e.getIndexB() == null) {
                                e.setIndexB(ib);
                                entradaRepository.save(e);
                            }
                        }
                    }
                }

            }
            entradaRepository.saveAll(entradas);
        }
    }
    public void update_Nfulles(long index_id) {
        boolean exists = indexBRepository.existsById(index_id);
        if(exists) {
            int nFulles = getNumFulles(index_id);
            List<Entrada> le = this.getEntradas(index_id);
            int u = indexBRepository.EntriesFullaIndexHash(index_id);
            int cont = 0;
            for(int i = 0; i < nFulles; ++i) {
                for(int j = 0; j < u && cont < le.size(); ++j) {
                    Entrada e = entradaRepository.findById(le.get(cont).getId()).orElse(null);
                    if (e != null) {
                        if(e.getnFulla() == 0) {
                            e.setnFulla(i + 1);
                            entradaRepository.save(e);
                        }
                    }
                    ++cont;
                }
            }
        }
    }

    public void update_indexB(long index_id) {
        long id = indexBRepository.findTaulaIDByIndexBID(index_id);
        if (taulaService.nTuplas(id) != entradaRepository.getEntradaForIndexB(index_id)) {
            this.updateEntradas(index_id);
            this.update_Nfulles(index_id);
        }
    }

    public List<Entrada> getFullaN (long index_id, int n) {
        boolean exists = indexBRepository.existsById(index_id);
        if(exists) {
            int nFulles = this.getNumFulles(index_id);
            if(n > 0 && n <= nFulles) {
                DemoApplication.sum_cost(1);
                return entradaRepository.findFullaNIndexB(index_id, n);
            }
        }
        return null;
    }
    public List<Entrada> getPrimeraFulla (long index_id) {
        boolean exists = indexBRepository.existsById(index_id);
        if(exists) {
            return getFullaN(index_id, 1);
        }
        return null;
    }
    public boolean ultimaFulla(long index_id, int n) {
        return getNumFulles(index_id)==n;
    }

    public int cercaFulla(long index_id, long tupla_id) {
        boolean exists = indexBRepository.existsById(index_id);
        if(exists) {
            List<Entrada> le = entradaRepository.findByIndexBID(index_id);
            for (Entrada e : le) {
                if (e.getTupla_id() == tupla_id) {
                    int u = indexBRepository.EntriesFullaIndexHash(index_id);
                    long taula_id = indexBRepository.findTaulaIDByIndexBID(index_id);
                    int card = taulaService.nTuplas(taula_id);
                    double logu = Math.log10(u);
                    double logc = Math.log10(card);
                    double hh = logc / logu;
                    int h = (int) Math.ceil(hh);
                    DemoApplication.sum_cost(h + 1);
                    return e.getnFulla();
                }
            }
        }
        return 0;
    }
    public int getNumFulles(long index_id) {
        boolean exists = indexBRepository.existsById(index_id);
        if(exists) {
            long taula_id = indexBRepository.findTaulaIDByIndexBID(index_id);
            if (exists) {
                int card = taulaService.nTuplas(taula_id);
                int u = indexBRepository.EntriesFullaIndexHash(index_id);
                double fulles = (double) card / u;
                int nFulles = (int) Math.ceil(fulles);
                return nFulles;
            }
        }
        return 0;
    }
    public String consultarIndexB(long indexB_id, String nom_indexb) {
        boolean exists = indexBRepository.existsById(indexB_id);
        String res = "";
        if (exists) {
            long taula_id = indexBRepository.findTaulaIDByIndexBID(indexB_id);
            String nom_taula = taulaRepository.findNomByTaulaID(taula_id);
            int nFulles = getNumFulles(indexB_id);
            res = "L'index B+ " + nom_indexb + " de la taula " + nom_taula + " té " + nFulles +" fulles:\n";
            for(int i = 1; i <= nFulles; ++i) {
                res += "La fulla " + i + " té les següents entrades: \n";
                List<Entrada> le = entradaRepository.findEntradaByIndexBIDandNFulla(indexB_id, i);
                for (Entrada e : le) {
                    res += "{entrada_id=" + e.getId() + ", tupla_id="+ e.getTupla_id()+", nBloc="+e.getnBloc()+", nTupla="+ e.getnTupla()+"}\n";
                }
            }
        }
        return res;
    }
}
