package com.example.demo.services;


import com.example.demo.TestConfig;
import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TaulaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
class TestTaulaService {

    @MockBean
    private TaulaRepository taulaRepository;
    @MockBean
    private BlocRepository blocRepository;

    @Autowired
    TaulaService taulaService;
    @Autowired
    BlocService blocService;
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
        Taula taulaTest = taulaService.saveTaula(taulaExpected);
        Assertions.assertEquals(taulaExpected.getNom_taula(), taulaTest.getNom_taula());
    }
    @Test
    void addBlocRemoveBlocID() {
        Taula taulaExpected = new Taula("TestCreacioTaula");
        Taula taulaTest = taulaService.saveTaula(taulaExpected);
        Bloc b = new Bloc(taulaTest);
        Bloc b2 = new Bloc(taulaTest);
        Bloc bloc = blocService.saveBloc(b);
        Bloc bloc2 = blocService.saveBloc(b2);
        taulaService.add_bloc(taulaTest.getId());
        taulaService.remove_bloc(taulaTest.getId(), bloc2.getId());
        Assertions.assertEquals(taulaTest.getTaula().size(), 1);
    }

}
