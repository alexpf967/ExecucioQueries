package com.example.demo.repositories;

import com.example.demo.classes.IndexHash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexHashRepository extends JpaRepository<IndexHash, Long> {
}
