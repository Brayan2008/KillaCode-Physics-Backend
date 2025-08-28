package app.killacode.back_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.killacode.back_app.model.Dias;
import app.killacode.back_app.model.Puntuacion_Semanal;
import app.killacode.back_app.model.Usuario;
import jakarta.transaction.Transactional;

public interface PuntacionRepository extends JpaRepository<Puntuacion_Semanal, Long> {
    
    //No usado
    @Query("SELECT p FROM Puntuacion_Semanal p JOIN p.id_usuario u WHERE u.id = :id_user AND p.dias = :dias")
    Optional<Puntuacion_Semanal> findByDias(@Param("id_user") long id_user, @Param("dias") Dias dias);

    @Transactional
    @Modifying
    @Query("UPDATE Puntuacion_Semanal p SET p.puntos = :puntos WHERE p.dias = :dias AND p.id_usuario = :id_usuario")
    int updatePuntos(@Param("id_usuario") Usuario id_usuario, @Param("dias") Dias dias, @Param("puntos") int puntos);

    //Esto se podria hacer en un storage procedure
}
