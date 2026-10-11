package com.reparaciones.sistema.repository;

import com.reparaciones.sistema.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNombre(String nombre);

    List<Cliente> findByApellido(String apellido);

    Optional<Cliente> findByCorreo(String correo);
}