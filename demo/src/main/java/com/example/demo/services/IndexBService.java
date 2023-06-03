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
    public Entrada getEntrada(long entrada_id) {
        DemoApplication.sum_cost(1);
        return entradaRepository.findById(entrada_id).orElse(null);
    }

    public Entrada getNEntrada (long index_id, int n) {
        IndexB indexB = indexBRepository.findById(index_id).orElse(null);
        List<Entrada> se = entradaRepository.findByIndexBID(indexB.getId());
        if (n < se.size()) {
            Entrada e = se.get(n);
            DemoApplication.sum_cost(1);
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
            this.updateEntradas(index_id);
            this.update_Nfulles(index_id);
        }
    }

    public List<Entrada> getFullaN (long index_id, int n) {
        IndexB ib = indexBRepository.findById(index_id).orElse(null);
        if(ib != null) {
            int nFulles = this.getNumFulles(index_id);
            if(n > 0 && n <= nFulles) {
                DemoApplication.sum_cost(1);
                return entradaRepository.findFullaNIndexB(index_id, n);
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
            if(e.getTupla_id()==tupla_id) {
                int u = ib.getEntries_fulla();
                int card = ib.getFulles().size();
                double logu = Math.log10(u);
                double logc= Math.log10(card);
                double hh = logc/logu;
                int h = (int) Math.ceil(hh);
                DemoApplication.sum_cost(h+1);
                return e.getnFulla();
            }
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
