package com.tiagoezc.demoparkapi.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class SpringTimezoneConfig {

	/*Após a classe ser inicializada pelo Spring o método construtor é inicializado, e após a inicialização do construtor
	 * o primeiro método será executado */
	@PostConstruct 
	public void timezoneConfig() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}
	
}


