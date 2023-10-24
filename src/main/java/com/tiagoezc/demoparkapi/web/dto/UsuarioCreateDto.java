package com.tiagoezc.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioCreateDto {

	@NotBlank
	@Email(regexp = "^(.{2,})@(.{2,})$", message = "Formato de e-mail inválido.")	
	private String username;
	@NotBlank
	@Size(min = 6, max = 6)
	private String password;
	
}
