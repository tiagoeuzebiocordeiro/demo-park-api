package com.tiagoezc.demoparkapi.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiagoezc.demoparkapi.entity.Usuario;
import com.tiagoezc.demoparkapi.service.UsuarioService;
import com.tiagoezc.demoparkapi.web.dto.UsuarioCreateDto;
import com.tiagoezc.demoparkapi.web.dto.UsuarioResponseDto;
import com.tiagoezc.demoparkapi.web.dto.UsuarioSenhaDto;
import com.tiagoezc.demoparkapi.web.dto.mapper.UsuarioMapper;
import com.tiagoezc.demoparkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/*Documentação SWAGGER @Tag*/
@Tag(name = "Usuários", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;

	/* INICIO -> Documentação SWAGGER ( EM CIMA DO METODO POST) */
	
	@Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário"
			, responses = {
					@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", 
							content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))), 
					@ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema", 
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", 
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))

			})
	
	/* FIM -> Documentação SWAGGER*/
	@PostMapping
	public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto) {
		Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
		Usuario user = usuarioService.buscarPorId(id);
		return ResponseEntity.ok().body(UsuarioMapper.toDto(user));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
		@SuppressWarnings("unused")
		Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDto>> getAll() {
		List<Usuario> users = usuarioService.buscarTodos();
		return ResponseEntity.ok().body(UsuarioMapper.toListDto(users));
	}
	
}
