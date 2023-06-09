package com.example.demo.services;

import com.example.demo.TestConfig;
import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TuplaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class TestBlocService {
    @MockBean
    private BlocRepository blocRepository;
    @MockBean
    private TuplaRepository tuplaRepository;
    @SpyBean
    private BlocService blocService;
    @SpyBean
    private TuplaService tuplaService;

    @Test
    public void addTuplaToBloc() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId((long)1);
        when(blocRepository.findById(any(long.class))).thenReturn(Optional.of(b));
        when(tuplaRepository.save(any(Tupla.class))).thenReturn(new Tupla("hola",b));

        blocService.add_tupla(b.getId(), "tupla1");
        blocService.add_tupla(b.getId(), "tupla2");

        Assertions.assertEquals(2, b.nTuplas());
    }
    @Test
    public void addTuplaToBlocAndRemove() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Tupla t = new Tupla("testTupla1", b);
        t.setId(1L);
        Tupla t2 = new Tupla("testTupla2", b);
        t2.setId(2L);
        Tupla t3 = new Tupla("testTupla3", b);
        t3.setId(3L);
        b.addTupla(t);
        b.addTupla(t2);
        b.addTupla(t3);
        List<Tupla> st = new ArrayList<Tupla>();
        st.add(t);
        st.add(t2);
        st.add(t3);
        when(tuplaRepository.save(any(Tupla.class))).thenReturn(new Tupla("testTuplaX",b));
        when(blocRepository.findById(anyLong())).thenReturn(Optional.of(b));
        when(tuplaRepository.findByBlocID(anyLong())).thenReturn(st);
        when(tuplaService.getTuplasByBlocID(anyLong())).thenReturn(st);
        when(blocService.getNTupla(anyLong(), eq(1))).thenReturn(t2);


        blocService.remove_Ntupla(b.getId(), 1);

        Assertions.assertEquals(2, b.nTuplas());

    }

    @Test
    public void printBloc() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Tupla t = new Tupla("testTupla1", b);
        t.setId(1L);
        Tupla t2 = new Tupla("testTupla2", b);
        t2.setId(2L);
        Tupla t3 = new Tupla("testTupla3", b);
        t3.setId(3L);
        b.addTupla(t);
        b.addTupla(t2);
        b.addTupla(t3);
        List<Tupla> st = new ArrayList<Tupla>();
        st.add(t);
        st.add(t2);
        st.add(t3);
        when(blocRepository.findById(anyLong())).thenReturn(Optional.of(b));
        when(tuplaRepository.findByBlocID(anyLong())).thenReturn(st);
        when(tuplaService.getTuplasByBlocID(anyLong())).thenReturn(st);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(baos));
        blocService.printBloc(b.getId());
        System.setOut(oldOut);
        String output = baos.toString();
        Assertions.assertEquals("El bloc amb id = 1, té 3 tuples:\r\n{id=1, atribut=testTupla1}\r\n{id=2, atribut=testTupla2}\r\n{id=3, atribut=testTupla3}\r\n", output);

    }

    @Test
    public void NTuplas() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Tupla t = new Tupla("testTupla1", b);
        t.setId(1L);
        Tupla t2 = new Tupla("testTupla2", b);
        t2.setId(2L);
        Tupla t3 = new Tupla("testTupla3", b);
        t3.setId(3L);
        b.addTupla(t);
        b.addTupla(t2);
        b.addTupla(t3);
        when(blocRepository.findById(anyLong())).thenReturn(Optional.of(b));

        int actual = blocService.Ntuplas(b.getId());
        Assertions.assertEquals(3, actual);
    }

    @Test
    public void getNTupla() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Tupla t = new Tupla("testTupla1", b);
        t.setId(1L);
        Tupla t2 = new Tupla("testTupla2", b);
        t2.setId(2L);
        Tupla t3 = new Tupla("testTupla3", b);
        t3.setId(3L);
        b.addTupla(t);
        b.addTupla(t2);
        b.addTupla(t3);
        List<Tupla> st = new ArrayList<Tupla>();
        st.add(t);
        st.add(t2);
        st.add(t3);
        when(blocRepository.findById(anyLong())).thenReturn(Optional.of(b));
        when(tuplaRepository.findByBlocID(anyLong())).thenReturn(st);
        when(tuplaService.getTuplasByBlocID(anyLong())).thenReturn(st);

        Tupla actual = blocService.getNTupla(b.getId(), 1);
        Assertions.assertEquals("testTupla2", actual.getAtribut());
        Assertions.assertEquals(2, actual.getId());

    }
    @Test
    public void getBlocsByTaulaID() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Bloc b2 = new Bloc(taula);
        b2.setId(2L);
        Bloc b3 = new Bloc(taula);
        b3.setId(3L);
        taula.addBloc(b);
        taula.addBloc(b2);
        taula.addBloc(b3);
        List<Bloc> sb = new ArrayList<>();
        sb.add(b);
        sb.add(b2);
        sb.add(b3);
        when(blocRepository.findByTaulaID(1L)).thenReturn(sb);

        List<Bloc> res = blocService.getBlocsByTaulaID(taula.getId());

        Assertions.assertEquals(sb,res);

    }
    @Test
    public void getAllTuplas() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Tupla t = new Tupla("testTupla1", b);
        t.setId(1L);
        Tupla t2 = new Tupla("testTupla2", b);
        t2.setId(2L);
        Tupla t3 = new Tupla("testTupla3", b);
        t3.setId(3L);
        b.addTupla(t);
        b.addTupla(t2);
        b.addTupla(t3);
        List<Tupla> st = new ArrayList<Tupla>();
        st.add(t);
        st.add(t2);
        st.add(t3);
        when(tuplaService.getTuplasByBlocID(1L)).thenReturn(st);


        List<Tupla> res = blocService.getAllTuplas(b.getId());
        Assertions.assertEquals(st,res);

    }
}
