package io.github.perplexhub.rsql.jpa;

import java.util.Map;

import io.github.perplexhub.rsql.common.RSQLCommonSupport;
import jakarta.persistence.EntityManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = { "io.github.perplexhub.rsql.common.test.repository.jpa", "io.github.perplexhub.rsql.jpa.repository.jpa" })
@EntityScan({ "io.github.perplexhub.rsql.common.test.model", "io.github.perplexhub.rsql.jpa" })
@EnableTransactionManagement
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RSQLCommonSupport rsqlCommonSupport(Map<String, EntityManager> entityManagerMap) {
		return new RSQLCommonSupport(entityManagerMap);
	}

}
