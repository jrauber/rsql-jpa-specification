package io.github.perplexhub.rsql.jpa.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration(proxyBeanMethods=false)
@EnableJpaRepositories(basePackages = { "io.github.perplexhub.rsql.common.test.repository.jpa" })
@EntityScan({ "io.github.perplexhub.rsql.common.test.model" })
@EnableTransactionManagement
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
