package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
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
    @Column(name = "nFulla")
    private int nFulla;
    @Column(name = "nBucket")
    private int nBucket;

    @ManyToOne
    @JoinColumn(name = "indexB_id")
    private IndexB indexB;
    @ManyToOne
    @JoinColumn(name = "indexHash_id")
    private IndexHash indexHash;


    public Entrada() {
    }

    public Entrada(long tupla_id, int nBloc, int nTupla, IndexB indexB) {
        this.tupla_id = tupla_id;
        this.nBloc = nBloc;
        this.nTupla = nTupla;
        this.indexB = indexB;
    }
    public Entrada(long tupla_id, int nBloc, int nTupla, IndexHash indexHash) {
        this.tupla_id = tupla_id;
        this.nBloc = nBloc;
        this.nTupla = nTupla;
        this.indexHash = indexHash;
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

    public int getnFulla() {
        return nFulla;
    }

    public void setnFulla(int nFulla) {
        this.nFulla = nFulla;
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

    public IndexB getIndexB() {
        return indexB;
    }

    public void setIndexB(IndexB indexB) {
        this.indexB = indexB;
    }

    public IndexHash getIndexHash() {
        return indexHash;
    }

    public void setIndexHash(IndexHash indexHash) {
        this.indexHash = indexHash;
    }

    public int getnBucket() {
        return nBucket;
    }

    public void setnBucket(int nBucket) {
        this.nBucket = nBucket;
    }
}
