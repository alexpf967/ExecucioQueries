package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Taula")
public class Taula {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom_taula", unique = true, nullable = false)
    private String nom_taula;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "taula", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bloc> taula;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "taula", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IndexB> indexBSet;

    public Taula() {
        this.taula = new LinkedHashSet<>();
    }

    public Taula(String nom_taula) {
        this.taula = new LinkedHashSet<>();
        this.nom_taula = nom_taula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_taula() {
        return nom_taula;
    }

    public void setNom_taula(String nom_taula) {
        this.nom_taula = nom_taula;
    }

    public Set<Bloc> getTaula() {
        return taula;
    }

    public void setTaula(Set<Bloc> taula) {
        this.taula = taula;
    }

    public void setIndexBSet(Set<IndexB> indexs) {
        this.indexBSet= indexs;
    }
    public void addindexB(IndexB indexB) {
        this.indexBSet.add(indexB);
    }
    public void removeindexB(IndexB indexB) {
        this.indexBSet.remove(indexB);
    }

    public void addBloc(Bloc bloc) {
        taula.add(bloc);
    }

    public void deleteBloc(Bloc bloc) {
        taula.remove(bloc);
    }
    public int nBlocs () {
        return taula.size();
    }


}
