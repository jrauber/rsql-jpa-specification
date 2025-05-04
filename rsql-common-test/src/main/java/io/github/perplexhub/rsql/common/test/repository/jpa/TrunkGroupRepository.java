package io.github.perplexhub.rsql.common.test.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.github.perplexhub.rsql.common.test.model.TrunkGroup;

public interface TrunkGroupRepository extends JpaRepository<TrunkGroup, Integer>, JpaSpecificationExecutor<TrunkGroup> {

}
