package io.github.perplexhub.rsql.jpa.repository.jpa.postgres;

import io.github.perplexhub.rsql.jpa.model.PostgresJsonEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostgresJsonEntityRepository extends JpaRepository<PostgresJsonEntity, UUID>,
    JpaSpecificationExecutor<PostgresJsonEntity> {

}
