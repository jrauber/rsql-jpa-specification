package io.github.perplexhub.rsql.jpa.repository.jpa.postgres;

import io.github.perplexhub.rsql.jpa.model.EntityWithJsonb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EntityWithJsonbRepository extends JpaRepository<EntityWithJsonb, UUID>,
    JpaSpecificationExecutor<EntityWithJsonb> {
}
