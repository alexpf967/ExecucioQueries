package com.example.demo.controllers;

import com.example.demo.TestConfig;
import com.example.demo.classes.IndexB;
import com.example.demo.classes.IndexHash;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.IndexBRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.services.IndexBService;
import com.example.demo.services.TaulaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestConfig.class})
public class TestIndexBController {
    @InjectMocks
    private IndexBController indexBController;
    @MockBean
    private TaulaRepository taulaRepository;
    @MockBean
    private IndexBRepository indexBRepository;
    @SpyBean
    private IndexBService indexBService;

    @Test
    public void crearIndexB() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        IndexB indexB = new IndexB("TestIB", 0.8, 5, taula);
        indexB.setId(1L);
        when(taulaRepository.findByNomTaula("TestTaula")).thenReturn(taula);
        when(indexBRepository.save(any(IndexB.class))).thenReturn(indexB);
        when(indexBService.saveIndexB(any(IndexB.class))).thenReturn(indexB);
        doNothing().when(indexBService).update_indexB(1L);
        String res = indexBController.crearIndexB("TestTaula", "TestIB", "0.8", "5", model);

        Assertions.assertEquals("crearIndexB", res);
        Mockito.verify(model).addAttribute("mensaje", "S'ha creat correctament l'index B+ " + "TestIB" + " a la taula " + nom_taula);

    }
    @Test
    public void consultarIndexB() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        IndexB indexB = new IndexB("TestIB", 0.8, 5, taula);
        indexB.setId(1L);
        String contingut = "Fulla 1\nFulla 2\nFulla 3";
        String [] linias = contingut.split("\n");
        when(indexBRepository.findIDByNomIndexB("TestIB")).thenReturn(1L);
        when(indexBService.consultarIndexB(1L,"TestIB")).thenReturn(contingut);
        doNothing().when(indexBService).update_indexB(1L);
        String res = indexBController.consultarIndexB("TestIB", model);

        Assertions.assertEquals("consultarIndexB", res);
        Mockito.verify(model).addAttribute("mensaje", linias);
    }
}
