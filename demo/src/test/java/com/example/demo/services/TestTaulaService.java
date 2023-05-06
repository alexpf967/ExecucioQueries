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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    public void printTaula() {
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

}
