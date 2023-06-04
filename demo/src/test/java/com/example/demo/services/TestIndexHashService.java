package com.example.demo.services;

import com.example.demo.TestConfig;
import com.example.demo.classes.*;
import com.example.demo.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class TestIndexHashService {
    @MockBean
    private IndexHashRepository indexHashRepository;
    @MockBean
    private TuplaRepository tuplaRepository;
    @MockBean
    private EntradaRepository entradaRepository;
    @MockBean
    private BlocRepository blocRepository;
    @MockBean
    private TaulaRepository taulaRepository;

    @SpyBean
    private BlocService blocService;
    @SpyBean
    private IndexHashService indexHashService;


    @Test
    public void calculate_hash() {
        int maxBuckets = 5;
        int tupla_id = 1234;
        int n = indexHashService.calculate_hash(maxBuckets, tupla_id);
        Assertions.assertEquals(5, n);
    }
    @Test
    public void update_indexHash() {
        Taula taula = new Taula("TestIndexHash");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();

        IndexHash ih = new IndexHash("TestIndexHash", 0.8, 5,5, taula);
        ih.setId(1L);
        for(int i = 0; i < 5; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            Entrada e = new Entrada(t.getId(), 0, i, ih);
            int tupla_id = Math.toIntExact(t.getId());
            e.setnBucket(indexHashService.calculate_hash(ih.getnBuckets(), tupla_id));
            ih.add_entrada(e);
            le.add(e);
        }
        taula.addBloc(b);
        indexHashService.update_indexHash(ih.getId());
        when(indexHashRepository.findById(1L)).thenReturn(Optional.of(ih));
        when(indexHashRepository.existsById(1L)).thenReturn(true);
        when(taulaRepository.existsById(anyLong())).thenReturn(true);


        List<Bloc> lb = new ArrayList<>(taula.getTaula());
        when(blocService.getBlocByTaulaID(1L)).thenReturn(lb);
        when(blocRepository.findByTaulaID(1L)).thenReturn(lb);
        List<Tupla> lt = new ArrayList<>(b.getBloc());
        when(tuplaRepository.findByBlocID(1L)).thenReturn(lt);
        Tupla t = new Tupla("tupla0", b);
        t.setId(0L);
        when(tuplaRepository.findById(0L)).thenReturn(Optional.of(t));
        Tupla t1 = new Tupla("tupla1", b);
        t1.setId(1L);
        when(tuplaRepository.findById(1L)).thenReturn(Optional.of(t1));
        Tupla t2 = new Tupla("tupla2", b);
        t2.setId(2L);
        when(tuplaRepository.findById(2L)).thenReturn(Optional.of(t2));
        Tupla t3 = new Tupla("tupla3", b);
        t3.setId(3L);
        when(tuplaRepository.findById(3L)).thenReturn(Optional.of(t3));
        Tupla t4 = new Tupla("tupla4", b);
        t4.setId(4L);
        when(tuplaRepository.findById(4L)).thenReturn(Optional.of(t4));
        when(entradaRepository.findByTuplaID(anyLong())).thenReturn(null);
        when(entradaRepository.saveAll(any(List.class))).thenReturn(le);
        when(indexHashService.saveIndexHash(any(IndexHash.class))).thenReturn(ih);
        when(indexHashRepository.save(any(IndexHash.class))).thenReturn(ih);

        IndexHash ih1 = new IndexHash("TestIndexHash", 0.8, 5,5, taula);
        ih1.setId(1L);
        indexHashService.update_indexHash(ih1.getId());

        List<Entrada> la = new ArrayList<>(ih1.getEntrades());
        for(Entrada e : la) {
            long tupla_id = e.getTupla_id();
            for(Entrada e2 : le) {
                if (tupla_id == e2.getTupla_id()) Assertions.assertEquals(e2.getnBucket(), e.getnBucket());
            }
        }
    }
    @Test
    public void getNentrada (){
        Taula taula = new Taula("TestIndexHash");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexHash ih = new IndexHash("TestIndexHash", 0.8, 5, 5, taula);
        for(int i = 0; i < 5; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            ih.add_entrada(new Entrada(t.getId(), 0, i, ih));
            le.add(new Entrada(t.getId(), 0, i, ih));
        }
        taula.addBloc(b);
        ih.setId(1L);
        when(indexHashRepository.findById(1L)).thenReturn(Optional.of(ih));
        when(entradaRepository.findByIndexHashID(1L)).thenReturn(le);

        Entrada n = indexHashService.getNEntrada(ih.getId(), 1);

        Assertions.assertEquals(1, n.getTupla_id());

    }

    @Test
    public void getBucketN (){
        Taula taula = new Taula("TestIndexHash");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexHash ih = new IndexHash("TestIndexHash", 0.8, 5, 3, taula);
        for(int i = 0; i < 5; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            Entrada e = new Entrada(t.getId(), 0, i, ih);
            int tupla_id = Math.toIntExact(t.getId());
            e.setnBucket(indexHashService.calculate_hash(ih.getnBuckets(), tupla_id));
            if(e.getnBucket()==1) le.add(e);
            ih.add_entrada(e);
        }
        taula.addBloc(b);
        ih.setId(1L);
        when(indexHashRepository.findById(1L)).thenReturn(Optional.of(ih));
        when(entradaRepository.findBucketNIndexHash(1L, 1)).thenReturn(le);

        List<Entrada> actual = indexHashService.getBucketN(ih.getId(), 1);
        Assertions.assertEquals(le, actual);
    }
    @Test
    public void getPrimerBucket (){
        Taula taula = new Taula("TestIndexHash");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexHash ih = new IndexHash("TestIndexHash", 0.8, 5, 3, taula);
        for(int i = 0; i < 5; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            Entrada e = new Entrada(t.getId(), 0, i, ih);
            int tupla_id = Math.toIntExact(t.getId());
            e.setnBucket(indexHashService.calculate_hash(ih.getnBuckets(), tupla_id));
            if(e.getnBucket()==1) le.add(e);
            ih.add_entrada(e);
        }
        taula.addBloc(b);
        ih.setId(1L);
        when(indexHashRepository.findById(1L)).thenReturn(Optional.of(ih));
        when(entradaRepository.findBucketNIndexHash(1L, 1)).thenReturn(le);

        List<Entrada> actual = indexHashService.getPrimerBucket(ih.getId());
        Assertions.assertEquals(le, actual);
    }
    @Test
    public void getnBucketsAndUltimBucket() {
        Taula taula = new Taula("TestIndexHash");
        taula.setId((long)1);
        IndexHash ih = new IndexHash("TestIndexHash", 0.8, 5, 3, taula);
        ih.setId(1L);
        when(indexHashRepository.findById(1L)).thenReturn(Optional.of(ih));
        when(indexHashRepository.NbucketsIndexHash(anyLong())).thenReturn(3);

        int n = indexHashService.getnBuckets(ih.getId());
        boolean b = indexHashService.ultimBucket(ih.getId(), n);
        Assertions.assertEquals(3, n);
        assert (b);

    }
    @Test
    public void cercaBucket () {
        Taula taula = new Taula("TestIndexHash");
        taula.setId((long) 1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        List<Entrada> le = new ArrayList<>();
        IndexHash ih = new IndexHash("TestIndexHash", 0.8, 5, 5, taula);
        for (int i = 0; i < 20; ++i) {
            Tupla t = new Tupla("tupla"+i, b);
            t.setId((long)i);
            b.addTupla(t);
            Entrada e = new Entrada(t.getId(), 0, i, ih);
            int tupla_id = Math.toIntExact(t.getId());
            e.setnBucket(indexHashService.calculate_hash(ih.getnBuckets(), tupla_id));
            ih.add_entrada(e);
            le.add(e);
        }
        taula.addBloc(b);
        ih.setId(1L);

        when(indexHashRepository.findById(1L)).thenReturn(Optional.of(ih));
        when(entradaRepository.findByIndexHashID(1L)).thenReturn(le);

        int n = indexHashService.cercaBucket(ih.getId(), 18L);
        Assertions.assertEquals(4, n);
    }

}
