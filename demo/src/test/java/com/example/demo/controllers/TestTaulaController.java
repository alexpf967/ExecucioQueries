package com.example.demo.controllers;
import com.example.demo.TestConfig;
import com.example.demo.classes.Bloc;
import com.example.demo.classes.Taula;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.TaulaRepository;
import com.example.demo.services.AlgorismeService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestConfig.class})
public class TestTaulaController {
    @InjectMocks
    private TaulaController taulaController;
    @SpyBean
    private TaulaService taulaService;
    @MockBean
    private TaulaRepository taulaRepository;
    @MockBean
    private BlocRepository blocRepository;

    @Test
    public void testCrearTaula() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        when(taulaRepository.save(any(Taula.class))).thenReturn(taula);
        when(taulaService.saveTaula(any(Taula.class))).thenReturn(taula);
        String res = taulaController.crearTaula(nom_taula, model);

        Assertions.assertEquals("crearTaula", res);
        Mockito.verify(model).addAttribute("mensaje", "S'ha creat correctament la taula " + nom_taula + " amb ID: " + taula.getId());
    }
    @Test
    public void populateTaula() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        when(taulaRepository.save(any(Taula.class))).thenReturn(taula);
        when(taulaRepository.findByNomTaula(nom_taula)).thenReturn(taula);
        when(taulaRepository.findById(1L)).thenReturn(Optional.of(taula));
        when(taulaService.saveTaula(any(Taula.class))).thenReturn(taula);

        String res = taulaController.populateTaula(nom_taula, "2", "4", model);

        Assertions.assertEquals("populateTaula", res);
        Mockito.verify(model).addAttribute("mensaje", "S'ha fet populate correctament la taula " + nom_taula);
    }
    @Test
    public void addBlocTaula() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        Bloc b = new Bloc(taula);
        when(taulaRepository.existsById(1L)).thenReturn(true);
        when(taulaRepository.findById(1L)).thenReturn(Optional.of(taula));
        when(blocRepository.save(any(Bloc.class))).thenReturn(b);

        String res = taulaController.addBlocTaula(nom_taula, model);

        Assertions.assertEquals("addBlocTaula", res);
        Mockito.verify(model).addAttribute("mensaje", "S'ha afegit correctament un bloc la taula " + nom_taula);

    }
    @Test
    public void addTuplaNBlocTaula() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        Bloc b = new Bloc(taula);
        taula.addBloc(b);
        when(taulaRepository.existsById(1L)).thenReturn(true);
        when(taulaRepository.findById(1L)).thenReturn(Optional.of(taula));
        String nb = "1";
        String atribut = "atribut";
        String res = taulaController.addTuplaNBlocTaula(nom_taula, nb, atribut, model);

        Assertions.assertEquals("addTuplaNBlocTaula", res);
        Mockito.verify(model).addAttribute("mensaje", "S'ha afegit correctament la tupla amb atribut: " + atribut + " al bloc " + nb + " de la taula " + nom_taula);
    }
    @Test
    public void consultarTaula() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        Bloc b = new Bloc(taula);
        taula.addBloc(b);
        String contingut = "Bloc 1\nBloc 2\nBloc 3";
        String [] linias = contingut.split("\n");
        when(taulaService.consultarTaula(1L)).thenReturn(contingut);
        when(taulaService.getIDbynomTaula("TestTaula")).thenReturn(1L);

        String res = taulaController.consultarTaula(nom_taula, model);

        Assertions.assertEquals("consultarTaula", res);
        Mockito.verify(model).addAttribute("mensaje", linias);
    }
    @Test
    public void escriureBlocTaula() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String nom_taula = "TestTaula";
        Taula taula = new Taula(nom_taula);
        taula.setId(1L);
        Bloc b = new Bloc(taula);
        b.setId(1L);
        taula.addBloc(b);
        String blocid = "1";
        String atributs = "atr1, atr2, atr3";
        when(taulaService.getIDbynomTaula("TestTaula")).thenReturn(1L);

        String res = taulaController.escriureBlocTaula(nom_taula, blocid, atributs, model);

        Assertions.assertEquals("escriureBlocTaula", res);
        Mockito.verify(model).addAttribute("mensaje", "S'ha escrit correctament el bloc amb id: " + blocid);

    }

}
