package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "IndexHash")
public class IndexHash {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nom_indexHash", unique = true)
    private String nom_indexHash;
    @Column(name = "f_carrega")
    private double f_carrega;
    @Column(name = "tree_order")
    private int tree_order;
    @Column(name = "nBuckets")
    private int nBuckets;
    @Column(name = "entries_bucket")
    private int entries_bucket;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "indexHash", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Entrada> entrades;
    @ManyToOne
    @JoinColumn(name = "taula_id")
    private Taula taula;

    public IndexHash() {
        this.entrades = new LinkedHashSet<>();
    }

    public IndexHash(String nom_indexHash, double f_carrega, int tree_order, int nBuckets, Taula taula) {
        this.nom_indexHash = nom_indexHash;
        this.f_carrega = f_carrega;
        this.tree_order = tree_order;
        this.nBuckets = nBuckets;
        this.taula = taula;
        double entries = 2*tree_order*f_carrega;
        this.entries_bucket = (int)Math.ceil(entries);
        this.entrades = new LinkedHashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_indexHash() {
        return nom_indexHash;
    }

    public void setNom_indexHash(String nom_indexHash) {
        this.nom_indexHash = nom_indexHash;
    }

    public double getF_carrega() {
        return f_carrega;
    }

    public void setF_carrega(double f_carrega) {
        this.f_carrega = f_carrega;
    }

    public int getTree_order() {
        return tree_order;
    }

    public void setTree_order(int tree_order) {
        this.tree_order = tree_order;
    }

    public int getnBuckets() {
        return nBuckets;
    }

    public void setnBuckets(int nBuckets) {
        this.nBuckets = nBuckets;
    }

    public Set<Entrada> getEntrades() {
        return entrades;
    }

    public void setEntrades(Set<Entrada> entrades) {
        this.entrades = entrades;
    }

    public Taula getTaula() {
        return taula;
    }

    public void setTaula(Taula taula) {
        this.taula = taula;
    }
    public void add_entrada(Entrada e) {
        this.entrades.add(e);
    }
    public void remove_entrada(Entrada e) {
        this.entrades.remove(e);
    }
    public int getEntries_buckets() {
        return entries_bucket;
    }

    public int getEntries_bucket() {
        return entries_bucket;
    }

    public void setEntries_bucket(int entries_bucket) {
        this.entries_bucket = entries_bucket;
    }
}
