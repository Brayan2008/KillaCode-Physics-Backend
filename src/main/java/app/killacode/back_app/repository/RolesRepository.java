package app.killacode.back_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.killacode.back_app.model.RolEnum;
import app.killacode.back_app.model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

    @Query("SELECT r FROM Roles r WHERE r.rol_name = :rolName")
    Optional<Roles> findByRolName(@Param("rolName") RolEnum rolName);
}
