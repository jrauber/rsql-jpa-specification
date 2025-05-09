package io.github.perplexhub.rsql.querydsl.starter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import io.github.perplexhub.rsql.common.RSQLCommonSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration(proxyBeanMethods=false)
@EnableJpaRepositories({ "io.github.perplexhub.rsql.common.test.repository.querydsl" })
@EntityScan({" io.github.perplexhub.rsql.common.test.model "})
@EnableTransactionManagement
@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Object rsqlConfiguration(RSQLCommonSupport rsqlCommonSupport) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		RSQLCommonSupport.addConverter(Timestamp.class, s -> {
			try {
				return new Timestamp(sdf.parse(s).getTime());
			} catch (Exception e) {
				return null;
			}
		});
		return rsqlCommonSupport;
	}

}
