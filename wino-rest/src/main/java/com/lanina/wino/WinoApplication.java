package com.lanina.wino;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(
		title="Wino and Travel API",
		version = "1.0",
		contact = @Contact(name="Alex Lanin", email="alex_lanin@ahoo.com")))
public class WinoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WinoApplication.class, args);
	}

}
