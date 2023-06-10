package com.example.demo.controllers;

import com.example.demo.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class TestVistasController {
    @InjectMocks
    private VistasController vistasController;
    @Test
    public void inici() {
        String res = vistasController.inici();
        Assertions.assertEquals("inici", res);
    }
    @Test
    public void gestioTaules() {
        String res = vistasController.gestioTaules();
        Assertions.assertEquals("gestioTaules", res);
    }
    @Test
    public void gestioIndexB() {
        String res = vistasController.gestioIndexB();
        Assertions.assertEquals("gestioIndexB", res);
    }
    @Test
    public void gestioIndexHash() {
        String res = vistasController.gestioIndexHash();
        Assertions.assertEquals("gestioIndexHash", res);    }
    @Test
    public void executarAlgorisme() {
        String res = vistasController.executarAlgorisme();
        Assertions.assertEquals("executarAlgorisme", res);
    }
    @Test
    public void crearTaula() {
        String res = vistasController.crearTaula();
        Assertions.assertEquals("crearTaula", res);
    }
    @Test
    public void populateTaula() {
        String res = vistasController.populateTaula();
        Assertions.assertEquals("populateTaula", res);
    }
    @Test
    public void addBlocTaula() {
        String res = vistasController.addBlocTaula();
        Assertions.assertEquals("addBlocTaula", res);
    }
    @Test
    public void addTuplaNBlocTaula() {
        String res = vistasController.addTuplaNBlocTaula();
        Assertions.assertEquals("addTuplaNBlocTaula", res);
    }
    @Test
    public void consultarTaula() {
        String res = vistasController.consultarTaula();
        Assertions.assertEquals("consultarTaula", res);
    }
    @Test
    public void escriureBlocTaula() {
        String res = vistasController.escriureBlocTaula();
        Assertions.assertEquals("escriureBlocTaula", res);
    }
    @Test
    public void crearIndexB() {
        String res = vistasController.crearIndexB();
        Assertions.assertEquals("crearIndexB", res);
    }
    @Test
    public void consultarIndexB() {
        String res = vistasController.consultarIndexB();
        Assertions.assertEquals("consultarIndexB", res);
    }
    @Test
    public void crearIndexHash() {
        String res = vistasController.crearIndexHash();
        Assertions.assertEquals("crearIndexHash", res);    }
    @Test
    public void consultarIndexHash() {
        String res = vistasController.consultarIndexHash();
        Assertions.assertEquals("consultarIndexHash", res);
    }
    @Test
    public void executarAlgPath() {
        String res = vistasController.executarAlgPath();
        Assertions.assertEquals("executarAlgPath", res);
    }
    @Test
    public void executarAlgContent() {
        String res = vistasController.executarAlgContent();
        Assertions.assertEquals("executarAlgContent", res);
    }
}
