package io.github.perplexhub.rsql.common.test.repository.querydsl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import io.github.perplexhub.rsql.common.test.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>, JpaSpecificationExecutor<Company>, QuerydslPredicateExecutor<Company> {

}
