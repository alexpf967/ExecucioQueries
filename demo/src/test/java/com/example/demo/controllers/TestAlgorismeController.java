package com.example.demo.controllers;

import com.example.demo.TestConfig;
import com.example.demo.services.AlgorismeService;
import com.example.demo.services.IndexHashService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestConfig.class})
public class TestAlgorismeController {
    @InjectMocks
    private AlgorismeController algorismeController;
    @SpyBean
    private AlgorismeService algorismeService;
    @Test
    public void executarAlgPath() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);
        MockMultipartFile file = new MockMultipartFile(
                "algorisme_file",
                "test.txt",
                "text/plain",
                "public void execute(){}".getBytes(StandardCharsets.UTF_8)
        );

        doNothing().when(algorismeService).carregarAlgContent(anyString());
        String res = algorismeController.executarAlgPath(file, model);

        Assertions.assertEquals("executarAlgPath", res);
        String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
        verify(algorismeService).carregarAlgContent(fileContent);
        verify(model).addAttribute("mensaje", "Algorisme carregat correctament");

    }
    @Test
    public void executarAlgContent() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);

        String content = "funcio execute del algorisme completa";

        doNothing().when(algorismeService).carregarAlgContent(anyString());
        String res = algorismeController.executarAlgContent(content, model);

        Assertions.assertEquals("executarAlgContent", res);
        verify(algorismeService).carregarAlgContent(content);
        verify(model).addAttribute("mensaje", "Algorisme carregat correctament");

    }
    @Test
    public void executarP() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);
        doNothing().when(algorismeService).executar();
        String res = algorismeController.executarP(model);
        String output = "El resultat del algorisme: \n"+"\nEl cost de l'algorisme executat es: 0";
        String[] lineas = output.split("\n");

        Assertions.assertEquals("executarAlgPath", res);
        verify(model).addAttribute("resultat", lineas);
    }
    @Test
    public void executarC() throws Exception {
        MockitoAnnotations.initMocks(this);
        Model model = Mockito.mock(Model.class);
        doNothing().when(algorismeService).executar();
        String res = algorismeController.executarC(model);
        String output = "El resultat del algorisme: \n"+"\nEl cost de l'algorisme executat es: 0";
        String[] lineas = output.split("\n");

        Assertions.assertEquals("executarAlgContent", res);
        verify(model).addAttribute("resultat", lineas);
    }
}
