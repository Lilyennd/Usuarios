package cl.GestionDrones.v1.usuarios.repository;

import cl.GestionDrones.v1.usuarios.model.Usuarios;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {


}
