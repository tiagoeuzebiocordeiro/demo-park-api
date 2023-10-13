package com.tiagoezc.demoparkapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*; // As próximas anotações eu irei importar tudo desse pacote
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String username;
	
	@Column(name = "password", nullable = false, length = 200) /*Tamanho 200 pois temos que levar em consideração a senha criptografada*/
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 25) /*Tamanho aqui é de acordo com a String do ENUM*/
	private Role role;
	
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao; // Momento de um insert
	
	@Column(name = "data_modificacao")
	private LocalDateTime dataModificacao;
	
	/*Auditoria*/
	
	@Column(name = "criado_por")
	private String criadoPor;
	
	@Column(name = "modificado_por")
	private String modificadoPor;
	
	/*Fim auditoria*/
	
	public enum Role {
		ROLE_ADMIN, ROLE_CLIENTE
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + "]";
	}
}
