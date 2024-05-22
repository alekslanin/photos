package com.lanina.wino;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.function.AggregateWindowEmulationQueryTransformer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@SpringBootApplication
@OpenAPIDefinition(info=@Info(
		title="Wino and Travel API",
		version = "1.0",
		contact = @Contact(name="Alex Lanin", email="alex_lanin@ahoo.com")))
@Slf4j
public class WinoApplication {

	public static void main(String[] args) throws UnknownHostException {
		log.info("Host Address :: " + InetAddress.getLocalHost().getHostAddress());
		log.info("Operation System ::: " + getOperatingSystem());

		Arrays.stream(SpringApplication.run(WinoApplication.class, args).getBeanDefinitionNames()).sorted().forEach(x -> log.info("LOADED :: " + x));
	}

	public static String getOperatingSystem() {
		return System.getProperty("os.name");
	}
}
