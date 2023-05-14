package com.example.demo.services;

import com.example.demo.TestConfig;
import com.example.demo.classes.*;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.EntradaRepository;
import com.example.demo.repositories.IndexBRepository;
import com.example.demo.repositories.TuplaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class TestIndexBService {
    @MockBean
    private IndexBRepository indexBRepository;
    @MockBean
    private TuplaRepository tuplaRepository;
    @MockBean
    private EntradaRepository entradaRepository;
    @MockBean
    private BlocRepository blocRepository;

    @SpyBean
    private BlocService blocService;
    @SpyBean
    private IndexBService indexBService;

    @Test
    public void updateEntrades() {
        Taula taula = new Taula("TestIndexB");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexB ib = new IndexB("TestIndexB", 0.8, 5, taula);
        for(int i = 0; i < 5; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            ib.add_fulla(new Entrada(t.getId(), 0, i, ib));
            le.add(new Entrada(t.getId(), 0, i, ib));
        }
        taula.addBloc(b);
        ib.setId(1L);
        when(indexBRepository.findById(1L)).thenReturn(Optional.of(ib));
        List<Bloc> lb = new ArrayList<>(taula.getTaula());
        when(blocService.getBlocByTaulaID(1L)).thenReturn(lb);
        when(blocRepository.findByTaulaID(1L)).thenReturn(lb);
        List<Tupla> lt = new ArrayList<>(b.getBloc());
        when(tuplaRepository.findByBlocID(1L)).thenReturn(lt);
        Tupla t = new Tupla("tupla0", b);
        t.setId(0L);
        when(tuplaRepository.findById(0L)).thenReturn(Optional.of(t));
        Tupla t1 = new Tupla("tupla1", b);
        t.setId(1L);
        when(tuplaRepository.findById(1L)).thenReturn(Optional.of(t1));
        Tupla t2 = new Tupla("tupla2", b);
        t.setId(2L);
        when(tuplaRepository.findById(2L)).thenReturn(Optional.of(t2));
        Tupla t3 = new Tupla("tupla3", b);
        t.setId(3L);
        when(tuplaRepository.findById(3L)).thenReturn(Optional.of(t3));
        Tupla t4 = new Tupla("tupla4", b);
        t.setId(4L);
        when(tuplaRepository.findById(4L)).thenReturn(Optional.of(t4));
        when(entradaRepository.findByTuplaID(anyLong())).thenReturn(null);
        when(entradaRepository.save(any(Entrada.class))).thenReturn(new Entrada());
        when(entradaRepository.saveAll(any(List.class))).thenReturn(le);
        when(indexBService.saveIndexB(any(IndexB.class))).thenReturn(ib);
        when(indexBRepository.save(any(IndexB.class))).thenReturn(ib);



        indexBService.update_Nfulles(ib.getId());
        Assertions.assertEquals(5, ib.getFulles().size());

    }
    @Test
    public void update_Nfulles (){
        Taula taula = new Taula("TestIndexB");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexB ib = new IndexB("TestIndexB", 0.8, 5, taula);
        for(int i = 0; i < 5; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            Entrada en = new Entrada(t.getId(), 0, i, ib);
            en.setnFulla(1);
            ib.add_fulla(en);
            le.add(new Entrada(t.getId(), 0, i, ib));
        }
        taula.addBloc(b);
        ib.setId(1L);
        when(indexBRepository.findById(1L)).thenReturn(Optional.of(ib));
        when(indexBService.getEntradas(1L)).thenReturn(le);
        Entrada e = new Entrada(1L, 0, 0, ib);
        e.setnFulla(0);
        when(entradaRepository.findById(anyLong())).thenReturn(Optional.of(e));
        e.setnFulla(1);
        when(entradaRepository.save(any(Entrada.class))).thenReturn(e);

        indexBService.update_Nfulles(ib.getId());

        for(Entrada e1 : ib.getFulles()) {
            Assertions.assertEquals(1, e1.getnFulla());
        }
    }
    @Test
    public void getNentrada (){
        Taula taula = new Taula("TestIndexB");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexB ib = new IndexB("TestIndexB", 0.8, 5, taula);
        for(int i = 0; i < 5; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            ib.add_fulla(new Entrada(t.getId(), 0, i, ib));
            le.add(new Entrada(t.getId(), 0, i, ib));
        }
        taula.addBloc(b);
        ib.setId(1L);
        when(indexBRepository.findById(1L)).thenReturn(Optional.of(ib));
        when(entradaRepository.findByIndexBID(1L)).thenReturn(le);

        Entrada n = indexBService.getNEntrada(ib.getId(), 1);

        Assertions.assertEquals(1, n.getTupla_id());

    }
    @Test
    public void getNfulla (){
        Taula taula = new Taula("TestIndexB");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexB ib = new IndexB("TestIndexB", 0.8, 5, taula);
        for(int i = 0; i < 5; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            ib.add_fulla(new Entrada(t.getId(), 0, i, ib));
            le.add(new Entrada(t.getId(), 0, i, ib));
        }
        taula.addBloc(b);
        ib.setId(1L);
        when(indexBRepository.findById(1L)).thenReturn(Optional.of(ib));
        when(entradaRepository.findFullaNIndexB(1L, 1)).thenReturn(le);
        when(indexBService.getNumFulles(ib.getId())).thenReturn(1);

        List<Entrada> actual = indexBService.getFullaN(ib.getId(), 1);
        Assertions.assertEquals(le, actual);
    }
    @Test
    public void getPrimeraFulla (){
        Taula taula = new Taula("TestIndexB");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexB ib = new IndexB("TestIndexB", 0.8, 5, taula);
        for(int i = 0; i < 5; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            ib.add_fulla(new Entrada(t.getId(), 0, i, ib));
            le.add(new Entrada(t.getId(), 0, i, ib));
        }
        taula.addBloc(b);
        ib.setId(1L);
        when(indexBRepository.findById(1L)).thenReturn(Optional.of(ib));
        when(entradaRepository.findFullaNIndexB(1L, 1)).thenReturn(le);
        when(indexBService.getNumFulles(ib.getId())).thenReturn(1);

        List<Entrada> actual = indexBService.getPrimeraFulla(ib.getId());
        Assertions.assertEquals(le, actual);

    }
    @Test
    public void getNumFullaAndUltimaFulla () {
        Taula taula = new Taula("TestIndexB");
        taula.setId((long) 1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexB ib = new IndexB("TestIndexB", 0.8, 5, taula);
        for (int i = 0; i < 20; ++i) {
            Tupla t = new Tupla("tupla" + i, b);
            t.setId((long) i);
            b.addTupla(t);
            ib.add_fulla(new Entrada(t.getId(), 0, i, ib));
            le.add(new Entrada(t.getId(), 0, i, ib));
        }
        taula.addBloc(b);
        ib.setId(1L);

        when(indexBRepository.findById(1L)).thenReturn(Optional.of(ib));

        int n = indexBService.getNumFulles(ib.getId());
        boolean ultmina = indexBService.ultimaFulla(ib.getId(), n);

        Assertions.assertEquals(3, n);
        assert(ultmina);
    }

    @Test
    public void cercaFulla () {
        Taula taula = new Taula("TestIndexB");
        taula.setId((long) 1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexB ib = new IndexB("TestIndexB", 0.8, 5, taula);
        for (int i = 0; i < 20; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            Entrada en = new Entrada(t.getId(), 0, i, ib);
            en.setnFulla(1);
            ib.add_fulla(en);
            le.add(en);
        }
        taula.addBloc(b);
        ib.setId(1L);

        when(indexBRepository.findById(1L)).thenReturn(Optional.of(ib));
        when(entradaRepository.findByIndexBID(1L)).thenReturn(le);

        int n = indexBService.cercaFulla(ib.getId(), 18L);
        Assertions.assertEquals(1, n);
    }
}
