package com.example.demo.tupla;

import jakarta.persistence.*;


@Entity
@Table(name = "Tupla")
public class Tupla {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "atribut")
    private String atribut;

    public Tupla() {}

    public Tupla(Long id, String atribut) {
        this.id = id;
        this.atribut = atribut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAtribut() {
        return atribut;
    }

    public void setAtribut(String atribut) {
        this.atribut = atribut;
    }
}
