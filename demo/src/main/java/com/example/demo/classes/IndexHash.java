package com.example.demo.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "IndexB")
public class IndexHash {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nom_indexHash", unique = true, nullable = false)
    private String nom_indexHash;
}
