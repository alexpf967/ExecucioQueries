package com.example.demo.classes;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Bloc")
public class Bloc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bloc", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tupla> bloc;
    @ManyToOne
    @JoinColumn(name = "taula_id")
    private Taula taula;

    public Bloc() {
        this.bloc = new LinkedHashSet<>();
    }

    public Bloc(Taula taula){
        this.bloc = new LinkedHashSet<>();
        this.taula = taula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Tupla> getBloc() {
        return bloc;
    }

    public void setBloc(Set<Tupla> bloc) {
        this.bloc = bloc;
    }

    public int nTuplas () {
        return bloc.size();
    }


    public Tupla getTupla (long id_tupla) {
        for(Tupla t : bloc) {
            if (t.getId().equals(id_tupla)) return t;
        }
        return null;
    }

    public void addTupla(Tupla t) {
        bloc.add(t);
    }

    public void deleteTupla(Tupla t) {
        bloc.remove(t);
    }


    public void showBloc() {
        System.out.println("El bloc amb id = " + this.id + ", té " + this.nTuplas() + " tuples:");
        for(Tupla t : bloc) {
            System.out.println("{id=" + t.getId() + ", atribut=" + t.getAtribut() +"}");
        }
    }

    public void setTaula(Taula taula) {
        this.taula = taula;
    }
}