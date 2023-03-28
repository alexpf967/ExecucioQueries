package com.example.demo.tupla;

public class Tupla {
    private Long id;
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
