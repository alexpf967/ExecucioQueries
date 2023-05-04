package com.example.demo.services;

import com.example.demo.TestConfig;
import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.classes.Tupla;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TuplaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
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

        Assertions.assertEquals(b.nFulles(), 2);
    }
    @Test
    public void addTuplaToBlocAndRemove() {
        Taula taula = new Taula("TestCreacioTaula");
        taula.setId((long)1);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        Tupla t = new Tupla("hola", b);
        t.setId(1L);
        Tupla t2 = new Tupla("hola", b);
        t2.setId(2L);
        Tupla t3 = new Tupla("hola", b);
        t.setId((long)3);
        b.addTupla(t);
        b.addTupla(t2);
        b.addTupla(t3);
        List<Tupla> st = new ArrayList<Tupla>();
        st.add(t);
        st.add(t2);
        st.add(t3);
        when(tuplaRepository.save(any(Tupla.class))).thenReturn(new Tupla("hola",b));
        when(blocRepository.findById(anyLong())).thenReturn(Optional.of(b));
        doReturn(st).when(tuplaRepository).findByBlocID(anyLong());
        when(tuplaService.getTuplasByBlocID(anyLong())).thenReturn(st);
        when(blocService.getNTupla(anyLong(), eq(1))).thenReturn(t2);


        //when(tuplaRepository.findByBlocID(any(long.class))).thenReturn(st);
        //when(tuplaService.getTuplasByBlocID(any(long.class))).thenReturn(st);

        blocService.remove_Ntupla(b.getId(), 1);

        Assertions.assertEquals(b.nFulles(), 2);

    }


}
