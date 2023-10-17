package com.tiagoezc.demoparkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiagoezc.demoparkapi.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
