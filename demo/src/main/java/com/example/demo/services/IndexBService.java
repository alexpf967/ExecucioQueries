package com.example.demo.services;

import com.example.demo.classes.*;
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

    public List<Entrada> getEntradas(long index_id) {
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


    public void updateEntradas(long index_id){
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
                        else {
                            if (e.getIndexB() == null) {
                                e.setIndexB(ib);
                                ib.add_fulla(e);
                                entradaRepository.save(e);
                            }
                        }
                    }
                }

            }
            entradaRepository.saveAll(entradas);
            saveIndexB(ib);
        }
    }
    public void update_Nfulles(long index_id) {
        IndexB ib = indexBRepository.findById(index_id).orElse(null);
        if(ib != null) {
            int card = ib.getFulles().size();
            int u = ib.getEntries_fulla();
            double fulles = (double) card /u;
            int nFulles = (int) Math.ceil(fulles);
            List<Entrada> le = this.getEntradas(ib.getId());

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
            System.out.println("Updatedating");
            this.updateEntradas(index_id);
            this.update_Nfulles(index_id);
        }
    }

    public List<Entrada> getFullaN (long index_id, int n) {
        IndexB ib = indexBRepository.findById(index_id).orElse(null);
        if(ib != null) {
            int nFulles = this.getNumFulles(ib.getId());
            if(n > 0 && n <= nFulles) {
                return entradaRepository.findFullaNIndexB(ib.getId(), n);
            }
        }
        return null;
    }
    public List<Entrada> getPrimeraFulla (long index_id) {
        IndexB ib = indexBRepository.findById(index_id).orElse(null);
        if(ib != null) {
            return getFullaN(ib.getId(), 1);
        }
        return null;
    }
    public boolean ultimaFulla(long index_id, int n) {
        return getNumFulles(index_id)==n;
    }

    public int cercaFulla(long index_id, long tupla_id) {
        IndexB ib = indexBRepository.findById(index_id).orElse(null);
        List<Entrada> le = entradaRepository.findByIndexBID(ib.getId());
        for(Entrada e : le) {
            if(e.getTupla_id()==tupla_id) return e.getnFulla();
        }
        return 0;
    }
    public int getNumFulles(long index_id) {
        IndexB ib = indexBRepository.findById(index_id).orElse(null);
        if (ib != null) {
            int card = ib.getFulles().size();
            int u = ib.getEntries_fulla();
            double fulles = (double) card /u;
            int nFulles = (int) Math.ceil(fulles);
            return nFulles;
        }
        return 0;
    }
}
