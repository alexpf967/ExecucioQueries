package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "IndexB")
public class IndexB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nom_indexB", unique = true, nullable = false)
    private String nom_indexB;
    @Column(name = "f_carrega")
    private double f_carrega;
    @Column(name = "tree_order")
    private int tree_order;
    @Column(name = "entries_fulla")
    private int entries_fulla;

    /*@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "indexB_entrada",
            joinColumns = {@JoinColumn(name = "indexB_id")},
            inverseJoinColumns = {@JoinColumn(name = "entrada_id")}
    )*/
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "indexB", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Entrada> fulles;
    @ManyToOne
    @JoinColumn(name = "taula_id")
    private Taula taula;

    public IndexB() {
        this.fulles = new LinkedHashSet<>();
    }

    public IndexB(String nom_indexB, double f_carrega, int tree_order, Taula taula) {
        this.nom_indexB = nom_indexB;
        this.f_carrega = f_carrega;
        this.tree_order = tree_order;
        this.taula = taula;
        this.fulles = new LinkedHashSet<>();
        double entries = 2*tree_order*f_carrega;
        this.entries_fulla = (int)Math.ceil(entries);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_indexB() {
        return nom_indexB;
    }

    public void setNom_indexB(String nom_indexB) {
        this.nom_indexB = nom_indexB;
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

    public Set<Entrada> getFulles() {
        return fulles;
    }

    public void setFulles(Set<Entrada> fulles) {
        this.fulles = fulles;
    }

    public Taula getTaula() {
        return taula;
    }

    public void setTaula(Taula taula) {
        this.taula = taula;
    }

    public void add_fulla(Entrada e) {
        this.fulles.add(e);
    }
    public void remove_fulla(Entrada e) {
        this.fulles.remove(e);
    }

    public int getEntries_fulla() {
        return entries_fulla;
    }

    public void setEntries_fulla(int entries_fulla) {
        this.entries_fulla = entries_fulla;
    }
}
