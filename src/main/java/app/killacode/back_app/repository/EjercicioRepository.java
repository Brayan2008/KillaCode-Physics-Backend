package app.killacode.back_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
 
import app.killacode.back_app.model.Ejercicio;

public interface EjercicioRepository extends JpaRepository<Ejercicio, String>{}