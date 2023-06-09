package com.example.demo.services;

import com.example.demo.TestConfig;
import com.example.demo.classes.Entrada;
import com.example.demo.repositories.BlocRepository;
import com.example.demo.repositories.EntradaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class TestEntradaService {
    @MockBean
    private EntradaRepository entradaRepository;
    @SpyBean
    private EntradaService entradaService;

    @Test
    public void saveANDgetEntrada() {
        Entrada entrada = new Entrada();
        entrada.setId(1L);
        entrada.setnBloc(1);
        entrada.setnTupla(1);
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entrada);
        when(entradaRepository.findById(1L)).thenReturn(Optional.of(entrada));

        Entrada e = new Entrada();
        e.setId(1L);

        Entrada entr = entradaService.saveEntrada(e);
        Assertions.assertEquals(entrada, entr);
        entr=entradaService.getEntrada(e.getId());
        Assertions.assertEquals(entrada, entr);
    }
}
