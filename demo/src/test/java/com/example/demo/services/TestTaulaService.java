package com.example.demo.services;


import com.example.demo.TestConfig;
import com.example.demo.classes.Taula;
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

    @Autowired
    TaulaService taulaService;
    @BeforeEach
    public void setUp() {
        when(taulaRepository.save(any(Taula.class))).thenReturn(new Taula("TestTaula"));
    }

    @Test
    void creacioTaula() {
        Taula taulaExpected = new Taula("TestTaula");
        Taula taulaTest = taulaService.saveTaula(taulaExpected);
        Assertions.assertEquals(taulaExpected.getNom_taula(), taulaTest.getNom_taula());
    }

}
