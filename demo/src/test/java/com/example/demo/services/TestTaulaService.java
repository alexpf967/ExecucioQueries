package com.example.demo.services;


import com.example.demo.TestConfig;
import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.repositories.TuplaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
class TestTaulaService {

    @MockBean
    private TaulaRepository taulaRepository;
    @MockBean
    private BlocRepository blocRepository;
    @MockBean
    private TuplaRepository tuplaRepository;

    @SpyBean
    private TaulaService taulaService;
    @SpyBean
    private BlocService blocService;
    @SpyBean
    private TuplaService tuplaService;
    @BeforeEach
    public void setUp() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc bloc = new Bloc(taula);
        bloc.setId((long)10);
        Bloc bloc2 = new Bloc(taula);
        bloc.setId((long)11);

        taula.addBloc(bloc);
        taula.addBloc(bloc2);
        taula.deleteBloc(bloc2);
        when(taulaRepository.save(any(Taula.class))).thenReturn(taula);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);


    }
    @Test
    void creacioTaulaNom() {
        Taula taulaExpected = new Taula("TestCreacioTaula");
        taulaExpected.setId((long)1);
        when(taulaRepository.save(any(Taula.class))).thenReturn(taulaExpected);
        Taula taulaTest = taulaService.saveTaula(taulaExpected);
        Assertions.assertEquals(taulaExpected.getId(), taulaTest.getId());
    }
    @Test
    void addAndRemoveBlocToTaula() {
        Taula taulaExpected = new Taula("TestCreacioTaula");
        Taula taulaTest = taulaService.saveTaula(taulaExpected);
        Bloc b = new Bloc(taulaTest);
        Bloc b2 = new Bloc(taulaTest);
        Bloc bloc = blocService.saveBloc(b);
        Bloc bloc2 = blocService.saveBloc(b2);
        taulaService.add_bloc(taulaTest.getId());
        taulaService.add_bloc(taulaTest.getId());
        taulaService.remove_bloc(taulaTest.getId(), bloc2.getId());
        Assertions.assertEquals(taulaTest.getTaula().size(), 1);
    }
    @Test
    public void showTaula() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Bloc b2 = new Bloc(taula);
        b2.setId(2L);
        Tupla t = new Tupla("testTupla1", b);
        t.setId(1L);
        Tupla t2 = new Tupla("testTupla2", b);
        t2.setId(2L);
        b.addTupla(t);
        b2.addTupla(t2);
        taula.addBloc(b);
        taula.addBloc(b2);
        List<Tupla> st = new ArrayList<Tupla>();
        st.add(t);
        List<Tupla> st2 = new ArrayList<Tupla>();
        st2.add(t2);
        List<Bloc> sb = new ArrayList<Bloc>();
        sb.add(b);
        sb.add(b2);
        when(taulaRepository.existsById(anyLong())).thenReturn(true);
        when(taulaRepository.findById(anyLong())).thenReturn(Optional.of(taula));
        when(blocService.getBlocByTaulaID(anyLong())).thenReturn(sb);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(b));
        when(blocRepository.findById(2L)).thenReturn(Optional.of(b2));
        when(tuplaRepository.findByBlocID(1L)).thenReturn(st);
        when(tuplaService.getTuplasByBlocID(2L)).thenReturn(st2);




        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        taulaService.showTaula(taula.getId());
        System.setOut(oldOut);
        String output = baos.toString();
        Assertions.assertEquals("La Taula amb id = 1, té 2 blocs:\r\nEl bloc amb id = 1, té 1 tuples:\r\n{id=1, atribut=testTupla1}\r\nEl bloc amb id = 2, té 1 tuples:\r\n{id=2, atribut=testTupla2}\r\n", output);

    }
    @Test
    void getNBlocToTaula() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId(1L);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Bloc b2 = new Bloc(taula);
        b2.setId(2L);
        List<Bloc> sb = new ArrayList<Bloc>();
        sb.add(b);
        sb.add(b2);
        when(taulaRepository.existsById(anyLong())).thenReturn(true);
        when(taulaRepository.findById(anyLong())).thenReturn(Optional.of(taula));
        when(blocService.getBlocByTaulaID(anyLong())).thenReturn(sb);

        Bloc b00 = taulaService.getNBloc(taula.getId(), 1);
        Bloc b01 = taulaService.getNBloc(taula.getId(), 2);
        Bloc b02 = taulaService.getNBloc(taula.getId(), 3);


        Assertions.assertEquals(1L, b00.getId());
        Assertions.assertEquals(2L, b01.getId());
        Assertions.assertNull(b02);

    }
    @Test
    void populateTaula() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId(1L);
        Bloc b = new Bloc(taula);
        b.setId(1L);

        List<Bloc> sb = new ArrayList<Bloc>();
        sb.add(b);
        List<Tupla> st = new ArrayList<>();
        for(int i = 0; i < 10; ++i) {
            Tupla t = new Tupla("populate", b);
            st.add(t);
        }

        b.setBloc(new HashSet<>(st));
        taula.addBloc(b);


        when(taulaRepository.save(any(Taula.class))).thenReturn(taula);
        Taula actual = new Taula("TestCreacioTaula");
        actual.setId(1L);
        //actual = taulaService.saveTaula(actual);


        when(taulaRepository.save(any(Taula.class))).thenReturn(taula);
        when(taulaRepository.findByNomTaula(anyString())).thenReturn(actual);
        when(taulaRepository.findById(anyLong())).thenReturn(Optional.of(actual));
        when(blocRepository.save(any(Bloc.class))).thenReturn(b);
        Bloc bloc = new Bloc(taula);
        bloc.setId(11L);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));



        taulaService.populate(actual.getNom_taula(), 1, 10);

        Assertions.assertEquals("TestCreacioTaula", actual.getNom_taula());
        Assertions.assertEquals(1, actual.getTaula().size());
        List<Bloc> lb = new ArrayList<>(actual.getTaula());

        Assertions.assertEquals(10, lb.get(0).getBloc().size());

    }

    @Test
    public void addTuplaBlocNTaula() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId(1L);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Bloc b2 = new Bloc(taula);
        b2.setId(2L);
        List<Bloc> sb = new ArrayList<Bloc>();
        sb.add(b);
        sb.add(b2);
        taula.addBloc(b);
        taula.addBloc(b2);

        when(taulaRepository.existsById(anyLong())).thenReturn(true);
        when(taulaRepository.findById(anyLong())).thenReturn(Optional.of(taula));
        when(blocService.getBlocByTaulaID(anyLong())).thenReturn(sb);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(b));
        when(blocRepository.findById(2L)).thenReturn(Optional.of(b2));

        //when(tuplaService.saveTupla(any(Tupla.class))).thenReturn(st2);

        taulaService.addTupla_BlocN(taula.getId(), 1, "BlocN");
        taulaService.addTupla_BlocN(taula.getId(), 2, "BlocN");

        List<Bloc> lb = new ArrayList<>(taula.getTaula());
        Assertions.assertEquals(1, lb.get(0).nTuplas());
        Assertions.assertEquals(1, lb.get(1).nTuplas());

    }
    @Test
    public void removeTuplaBlocNTaula() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId(1L);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Tupla t1 = new Tupla("t1", b);
        t1.setId(1L);
        b.addTupla(t1);
        Tupla t2 = new Tupla("t2", b);
        t2.setId(2L);
        b.addTupla(t2);
        Tupla t3 = new Tupla("t3", b);
        t3.setId(3L);
        b.addTupla(t3);
        List<Bloc> sb = new ArrayList<Bloc>();
        sb.add(b);
        taula.addBloc(b);

        when(taulaRepository.existsById(anyLong())).thenReturn(true);
        when(taulaRepository.findById(anyLong())).thenReturn(Optional.of(taula));
        when(blocService.getBlocByTaulaID(anyLong())).thenReturn(sb);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(b));
        when(tuplaService.getTuplasByBlocID(1L)).thenReturn(new ArrayList<>(b.getBloc()));
        when(tuplaService.getTuplaById(2L)).thenReturn(t2);

        List<Tupla> lt= new ArrayList<>(b.getBloc());
        Tupla t = lt.get(1);
        when(blocService.getNTupla(1L, 1)).thenReturn(t);

        taulaService.removeTupla_BlocN(taula.getId(), 1, 1);
        List<Bloc> lb = new ArrayList<>(taula.getTaula());
        Bloc bloc = lb.get(0);
        List<Tupla> l = new ArrayList<>(bloc.getBloc());

        Assertions.assertEquals(2, l.size());
        Assertions.assertEquals("t1", l.get(0).getAtribut());
        Assertions.assertEquals("t3", l.get(1).getAtribut());

    }

    @Test
    public void escriureBlocNTaula() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId(1L);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Tupla t1 = new Tupla("t1", b);
        t1.setId(1L);
        b.addTupla(t1);
        Tupla t2 = new Tupla("t2", b);
        t2.setId(2L);
        b.addTupla(t2);
        Tupla t3 = new Tupla("t3", b);
        t3.setId(3L);
        b.addTupla(t3);
        List<Bloc> sb = new ArrayList<Bloc>();
        sb.add(b);
        List<Tupla> st = new ArrayList<Tupla>();
        st.add(t1);
        st.add(t2);
        st.add(t3);
        taula.addBloc(b);

        Bloc swap = new Bloc();
        Tupla t11 = new Tupla("swap1", swap);
        t11.setId(10L);
        swap.addTupla(t11);
        Tupla t12 = new Tupla("swap2", swap);
        t12.setId(20L);
        swap.addTupla(t12);
        Tupla t13 = new Tupla("swap3", swap);
        t13.setId(30L);
        swap.addTupla(t13);
        when(taulaRepository.findById(anyLong())).thenReturn(Optional.of(taula));
        when(blocService.getBlocById(1L)).thenReturn(b);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(b));
        when(tuplaRepository.findById(1L)).thenReturn(Optional.of(t1));
        when(tuplaRepository.findById(2L)).thenReturn(Optional.of(t2));
        when(tuplaRepository.findById(3L)).thenReturn(Optional.of(t3));
        when(tuplaRepository.findByBlocID(1L)).thenReturn(st);
        when(tuplaService.getTuplasByBlocID(1L)).thenReturn(st);
        when(tuplaService.getTuplaById(1L)).thenReturn(t1);
        when(tuplaService.getTuplaById(2L)).thenReturn(t2);
        when(tuplaService.getTuplaById(3L)).thenReturn(t3);
        when(tuplaService.saveTupla(t11)).thenReturn(t11);
        when(tuplaService.saveTupla(t12)).thenReturn(t12);
        when(tuplaService.saveTupla(t13)).thenReturn(t13);


        taulaService.escriureBloc(taula.getId(), 1L, swap);
        List<Bloc> lb = new ArrayList<>(taula.getTaula());
        List<Tupla> lt = new ArrayList<>(lb.get(0).getBloc());
        Assertions.assertEquals(1, lb.size());
        Assertions.assertEquals(3, lt.size());


        Assertions.assertEquals("swap1", lt.get(0).getAtribut());
        Assertions.assertEquals("swap2", lt.get(1).getAtribut());
        Assertions.assertEquals("swap3", lt.get(2).getAtribut());


    }

}
