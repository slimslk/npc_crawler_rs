package net.dimmid.crawler_rs_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class CrawlerRsJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerRsJavaApplication.class, args);
	}

}
