package app.killacode.back_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.killacode.back_app.model.Practica;

public interface PracticaRepository extends JpaRepository<Practica, String> {
}
