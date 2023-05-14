package com.example.demo;

import com.example.demo.repositories.*;
import com.example.demo.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public BlocRepository blocRepository() {
        return null;
    }
    @Bean
    public EntradaRepository entradaRepository() {
        return null;
    }
    @Bean
    public IndexBRepository indexBRepository() {
        return null;
    }
    @Bean
    public TaulaRepository taulaRepository() {
        return null;
    }
    @Bean
    public TuplaRepository tuplaRepository() {
        return null;
    }
    @Bean
    public IndexHashRepository indexHashRepository() {
        return null;
    }

    @Bean
    public BlocService blocService() {
        return new BlocService();
    }
    @Bean
    public EntradaService entradaService() {
        return new EntradaService();
    }
    @Bean
    public IndexBService indexBService() {
        return new IndexBService();
    }
    @Bean
    public TaulaService taulaService() {
        return new TaulaService();
    }
    @Bean
    public TuplaService tuplaService() {
        return new TuplaService();
    }
    @Bean
    public TestIndexHashService indexHashService() {
        return new TestIndexHashService();
    }




}
