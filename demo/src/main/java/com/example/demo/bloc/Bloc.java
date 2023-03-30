package com.example.demo.bloc;

import com.example.demo.tupla.Tupla;

import java.util.ArrayList;
import java.util.List;

public class Bloc {
    private Long id;
    private List<Tupla> bloc;

    public Bloc(){
        bloc = new ArrayList<>();
    }

    public Bloc(Long id, List<Tupla> bloc) {
        this.id = id;
        this.bloc = bloc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Tupla> getBloc() {
        return bloc;
    }

    public void setBloc(List<Tupla> bloc) {
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

    public void afegirTupla(Tupla t) {
        bloc.add(t);
    }

    public void mostrar_bloc() {
        System.out.println("El bloc amb id = " + this.id + ", té " + this.nFulles() + " tuples:");
        for(Tupla t : bloc) {
            System.out.println("{id=" + t.getId() + ", atribut=" + t.getAtribut() +"}");
        }
    }
}
