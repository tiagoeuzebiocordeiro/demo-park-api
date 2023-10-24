package com.tiagoezc.demoparkapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.tiagoezc.demoparkapi.web.dto.UsuarioCreateDto;
import com.tiagoezc.demoparkapi.web.dto.UsuarioResponseDto;
import com.tiagoezc.demoparkapi.web.exception.ErrorMessage;

/*IT de Integração
 * ué, mas o teste não é de ponto a ponto? 
 * Sim, mas o teste de p a p tbm é considerado um tipo de teste de integração (ponto a ponto  IT)
 * */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/resources/sql/usuarios/usuarios-insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/resources/sql/usuarios/usuarios-delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

	@Autowired
	WebTestClient testClient;

	@Test
	public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201() {

		UsuarioResponseDto responseBody = testClient.post().uri("/api/v1/usuarios")
				.contentType(MediaType.APPLICATION_JSON).bodyValue(new UsuarioCreateDto("tody@gmail.com", "123456"))
				.exchange().expectStatus().isCreated().expectBody(UsuarioResponseDto.class).returnResult()
				.getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@gmail.com").isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE").isNotNull();
		/* a senha nao vai ser testada pq n retornamos a senha no response */

	}

	@Test
	public void createUsuario_ComUsernameInvalido_RetornarErrorMessageStatus422() {

		/*Teste 1*/
		
		ErrorMessage responseBody = testClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("", "123456")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

		/*FIM Teste 1*/

		
		/* Teste #2 */
		responseBody = testClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("tody@", "123456")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		
		/*FIM Teste 2*/

		/* Teste #3 */
		responseBody = testClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioCreateDto("tody@gmail", "123456")).exchange().expectStatus().isEqualTo(422)
				.expectBody(ErrorMessage.class).returnResult().getResponseBody();

		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

		/*FIM Teste 3*/

		
	}

}
