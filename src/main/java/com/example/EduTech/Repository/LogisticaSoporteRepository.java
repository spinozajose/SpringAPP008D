package com.example.EduTech.Repository;

import com.example.EduTech.Model.Usuarios.LogisticaSoporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticaSoporteRepository extends JpaRepository<LogisticaSoporte, Integer> {
}
