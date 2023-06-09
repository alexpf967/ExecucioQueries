package com.example.demo.services;

import com.example.demo.TestConfig;
import com.example.demo.classes.Bloc;
import com.example.demo.classes.Entrada;
import com.example.demo.classes.Taula;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.TuplaRepository;
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
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class TestTuplaService {
    @MockBean
    private TuplaRepository tuplaRepository;
    @SpyBean
    private TuplaService tuplaService;

    @Test
    public void saveTupla() {
        Tupla tupla = new Tupla();
        tupla.setId(1L);

        when(tuplaRepository.save(any(Tupla.class))).thenReturn(tupla);

        Tupla t = new Tupla();
        t.setId(1L);
        t = tuplaService.saveTupla(t);

        Assertions.assertEquals(tupla, t);

    }
    @Test
    public void getTuplaById() {
        Tupla tupla = new Tupla();
        tupla.setId(1L);

        when(tuplaRepository.findById(1L)).thenReturn(Optional.of(tupla));

        Tupla t = tuplaService.getTuplaById(1L);

        Assertions.assertEquals(tupla, t);

    }
    @Test
    public void getTuplasByBlocID() {
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
        when(tuplaRepository.findByBlocID(1L)).thenReturn(st);

        List<Tupla> res = tuplaService.getTuplasByBlocID(b.getId());
        Assertions.assertEquals(st, res);

    }
}
