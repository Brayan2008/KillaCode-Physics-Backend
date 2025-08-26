package app.killacode.back_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.killacode.back_app.model.Puntuacion_Semanal;

public interface PuntacionRepository extends JpaRepository<Puntuacion_Semanal, Integer>{}
