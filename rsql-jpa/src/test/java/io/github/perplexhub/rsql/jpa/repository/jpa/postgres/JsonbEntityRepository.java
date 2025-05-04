package io.github.perplexhub.rsql.jpa.repository.jpa.postgres;

import io.github.perplexhub.rsql.jpa.model.JsonbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JsonbEntityRepository extends JpaRepository<JsonbEntity, UUID> {
}

