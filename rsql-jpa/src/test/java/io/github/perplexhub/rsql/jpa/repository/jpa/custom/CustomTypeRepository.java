package io.github.perplexhub.rsql.jpa.repository.jpa.custom;

import io.github.perplexhub.rsql.jpa.custom.EntityWithCustomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomTypeRepository extends JpaRepository<EntityWithCustomType, Long>, JpaSpecificationExecutor<EntityWithCustomType> {
}
