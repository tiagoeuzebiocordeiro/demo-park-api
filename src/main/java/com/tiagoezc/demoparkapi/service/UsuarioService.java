package com.tiagoezc.demoparkapi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiagoezc.demoparkapi.entity.Usuario;
import com.tiagoezc.demoparkapi.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor /*Injeção de dependências via campo construtor*/
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	@Transactional /*Abrir, fechar e gerenciar a transação do método save*/
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	/*Aviso pro Spring que é apenas de consulta*/
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				() -> new RuntimeException("Usuário não encontrado.")
		);
	}

	@Transactional
	public Usuario editarSenha(Long id, String password) {
		Usuario user = buscarPorId(id);
		user.setPassword(password);
		return user;
	}

	
}
