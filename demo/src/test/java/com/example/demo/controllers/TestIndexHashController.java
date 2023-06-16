package com.example.demo.controllers;

import com.example.demo.TestConfig;
import com.example.demo.classes.IndexB;
import com.example.demo.classes.IndexHash;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.IndexBRepository;
import com.example.demo.repositories.IndexHashRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.services.IndexBService;
import com.example.demo.services.IndexHashService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
public class TestIndexHashController {
    @InjectMocks
    private IndexHashController indexHashController;
    @MockBean
    private TaulaRepository taulaRepository;
    @MockBean
    private IndexHashRepository indexHashRepository;
    @SpyBean
    private IndexHashService indexHashService;
    @Test
    public void crearIndexHash() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        IndexHash indexH = new IndexHash("TestIH", 0.8, 5, 4, taula);
        indexH.setId(1L);
        when(taulaRepository.findByNomTaula("TestTaula")).thenReturn(taula);
        when(indexHashRepository.save(any(IndexHash.class))).thenReturn(indexH);
        when(indexHashService.saveIndexHash(any(IndexHash.class))).thenReturn(indexH);
        doNothing().when(indexHashService).update_indexH(1L);
        String res = indexHashController.crearIndexHash("TestTaula", "TestIH", "0.8", "5", "4", model);

        Assertions.assertEquals("crearIndexHash", res);
        Mockito.verify(model).addAttribute("mensaje", "S'ha creat correctament l'index Hash " + "TestIH" + " a la taula " + nom_taula);

    }
    @Test
    public void consultarIndexHash() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        IndexHash indexH = new IndexHash("TestIH", 0.8, 5, 4, taula);
        indexH.setId(1L);
        String contingut = "Bucket 1\nBucket 2\nBucket 3";
        String [] linias = contingut.split("\n");
        when(indexHashRepository.findIDByNomIndexHash("TestIH")).thenReturn(1L);
        when(indexHashService.consultarIndexHash(1L,"TestIH")).thenReturn(contingut);
        doNothing().when(indexHashService).update_indexH(1L);
        String res = indexHashController.consultarIndexHash("TestIH", model);

        Assertions.assertEquals("consultarIndexHash", res);
        Mockito.verify(model).addAttribute("mensaje", linias);
    }
}
