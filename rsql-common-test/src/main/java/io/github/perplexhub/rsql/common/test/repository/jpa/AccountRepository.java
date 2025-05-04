package io.github.perplexhub.rsql.common.test.repository.jpa;

import io.github.perplexhub.rsql.common.test.model.account.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AccountRepository extends JpaRepository<AccountEntity, String>, JpaSpecificationExecutor<AccountEntity> {
}
