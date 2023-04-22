package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Entrada")
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "tupla_id", unique = true, nullable = false)
    private long tupla_id;
    @Column(name = "nBloc")
    private int nBloc;
    @Column(name = "nTupla")
    private int nTupla;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "entrada_indexB",
            joinColumns = {@JoinColumn(name = "entrada_id")},
            inverseJoinColumns = {@JoinColumn(name = "indexB_id")}
    )
    private Set<IndexB> indexs;

    public Entrada() {
    }

    public Entrada(long tupla_id, int nBloc, int nTupla) {
        this.tupla_id = tupla_id;
        this.nBloc = nBloc;
        this.nTupla = nTupla;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTupla_id() {
        return tupla_id;
    }

    public void setTupla_id(long tupla_id) {
        this.tupla_id = tupla_id;
    }

    public int getnBloc() {
        return nBloc;
    }

    public void setnBloc(int nBloc) {
        this.nBloc = nBloc;
    }

    public int getnTupla() {
        return nTupla;
    }

    public void setnTupla(int nTupla) {
        this.nTupla = nTupla;
    }
    public void add_indexB(IndexB indexB) {
        this.indexs.add(indexB);
    }
    public void remove_indexB(IndexB indexB) {
        this.indexs.remove(indexB);
    }
}
