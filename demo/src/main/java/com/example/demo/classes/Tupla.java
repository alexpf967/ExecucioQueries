package com.example.demo.classes;

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

    @ManyToOne
    @JoinColumn(name = "bloc_id")
    private Bloc bloc;

    public void setBloc(Bloc bloc) {
        this.bloc = bloc;
    }

    public Tupla() {}

    public Tupla(String atribut, Bloc bloc) {
        this.bloc = bloc;
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
