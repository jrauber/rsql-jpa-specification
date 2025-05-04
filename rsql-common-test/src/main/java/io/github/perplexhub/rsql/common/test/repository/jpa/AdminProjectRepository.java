package io.github.perplexhub.rsql.common.test.repository.jpa;

import io.github.perplexhub.rsql.common.test.model.AdminProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminProjectRepository extends JpaRepository<AdminProject, Integer>, JpaSpecificationExecutor<AdminProject> {

}
