package com.example.demo;

import com.example.demo.repositories.TuplaRepository;
import com.example.demo.tupla.Tupla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
public class provaDades {

    @Autowired
    private TuplaRepository tuplaRepository;
    //private final Logger log = LoggerFactory.getLogger(TareaConfig.class);

    @Async
    @Scheduled(cron = "1 * * * *") // cada 30 min
    public void insertarDades() {
        Tupla t = new Tupla();
        t.setAtribut("alexP1");
        tuplaRepository.save(t);
    }
}
