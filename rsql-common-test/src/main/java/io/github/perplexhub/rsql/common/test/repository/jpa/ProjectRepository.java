package io.github.perplexhub.rsql.common.test.repository.jpa;

import io.github.perplexhub.rsql.common.test.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project> {

}
