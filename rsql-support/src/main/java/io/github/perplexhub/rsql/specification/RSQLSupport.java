package io.github.perplexhub.rsql.specification;

import java.util.Map;
import java.util.Properties;

import io.github.perplexhub.rsql.querydsl.RSQLQueryDslSupport;
import jakarta.persistence.EntityManager;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RSQLSupport extends RSQLQueryDslSupport {

	public RSQLSupport(Map<String, EntityManager> entityManagerMap) {
		super(entityManagerMap);
		log.info("RSQLSupport is initialized.");
	}

	protected String getVersion() {
		try {
			Properties prop = new Properties();
			prop.load(prop.getClass().getResourceAsStream("/META-INF/maven/io.github.perplexhub/rsql-support/pom.properties"));
			String version = prop.getProperty("version");
			return StringUtils.hasText(version) ? "[" + version + "] " : "";
		} catch (Exception e) {
			return "";
		}
	}

}
