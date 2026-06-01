package cl.GestionDrones.v1.usuarios.repository;

import cl.GestionDrones.v1.usuarios.model.Usuarios;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {


    Optional<Usuarios> findByCorreo(String correo);


    boolean existsByCorreo(String correo);

   
    @Query(value = "SELECT * FROM usuarios WHERE rol = :rol", nativeQuery = true)
    java.util.List<Usuarios> buscarPorRol(@Param("rol") String rol);
}
