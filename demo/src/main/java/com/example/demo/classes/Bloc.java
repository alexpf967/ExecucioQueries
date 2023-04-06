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
    @OneToMany(mappedBy = "bloc", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tupla> bloc;

    public Bloc(){
        bloc = new LinkedHashSet<>();
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

    public int nFulles () {
        return bloc.size();
    }

    public Tupla getTupla (Long id_tupla) {
        for(Tupla t : bloc) {
            if (t.getId() == id_tupla) return t;
        }
        return null;
    }

    public void addTupla(Tupla t) {bloc.add(t);}

    public void deleteTupla(Tupla t) {bloc.remove(t);}


    public void showBloc() {
        System.out.println("El bloc amb id = " + this.id + ", té " + this.nFulles() + " tuples:");
        for(Tupla t : bloc) {
            System.out.println("{id=" + t.getId() + ", atribut=" + t.getAtribut() +"}");
        }
    }
}
