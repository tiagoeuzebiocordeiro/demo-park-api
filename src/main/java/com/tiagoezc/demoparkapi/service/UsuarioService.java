package com.tiagoezc.demoparkapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiagoezc.demoparkapi.entity.Usuario;
import com.tiagoezc.demoparkapi.exception.EntityNotFoundException;
import com.tiagoezc.demoparkapi.exception.InvalidPasswordException;
import com.tiagoezc.demoparkapi.exception.UsernameUniqueViolationException;
import com.tiagoezc.demoparkapi.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor /*Injeção de dependências via campo construtor*/
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	@Transactional /*Abrir, fechar e gerenciar a transação do método save*/
	public Usuario salvar(Usuario usuario) {
		try {
			return usuarioRepository.save(usuario);

		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(String.format("Username '%s' já cadastrado", usuario.getUsername()));
		}
	}

	/*Aviso pro Spring que é apenas de consulta*/
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Usuário Id: %s não encontrado.", id))
		);
	}

	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		if (!novaSenha.equals(confirmaSenha)) {
			throw new InvalidPasswordException("Nova senha não confere com a confirmação de senha.");
		}
		Usuario user = buscarPorId(id);
		if (!user.getPassword().equals(senhaAtual)) {
			throw new InvalidPasswordException("Sua senha não confere.");
		}
		
		if (senhaAtual.equals(novaSenha)) {
			throw new InvalidPasswordException("A nova senha não pode ser igual a senha atual.");
		}
		
		user.setPassword(novaSenha);
		return user;
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}

}
