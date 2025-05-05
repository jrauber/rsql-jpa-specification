package io.github.perplexhub.rsql.querydsl.starter;

import io.github.perplexhub.rsql.common.test.model.Company;
import io.github.perplexhub.rsql.common.test.model.QCompany;
import io.github.perplexhub.rsql.common.test.model.QUser;
import io.github.perplexhub.rsql.common.test.model.User;
import io.github.perplexhub.rsql.common.test.repository.querydsl.CompanyRepository;
import io.github.perplexhub.rsql.common.test.repository.querydsl.UserRepository;
import io.github.perplexhub.rsql.querydsl.RSQLQueryDslSupport;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Slf4j
@SpringBootTest
class RSQLQueryDslSupportTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Test
	final void testEqual() {
		String rsql = "id==2";
		List<User> users = (List<User>) userRepository.findAll(RSQLQueryDslSupport.toPredicate(rsql, QUser.user));
		long count = users.size();
		log.info("rsql: {} -> count: {}", rsql, count);
		assertThat(rsql, count, is(1L));
		assertThat(rsql, users.get(0).getName(), equalTo("February"));

		rsql = "id=='2'";
		List<Company> companys = (List<Company>) companyRepository.findAll(RSQLQueryDslSupport.toPredicate(rsql, QCompany.company));
		count = companys.size();
		log.info("rsql: {} -> count: {}", rsql, count);
		assertThat(rsql, count, is(1L));
		assertThat(rsql, companys.get(0).getName(), equalTo("World Inc"));

		rsql = "company.id=='2'";
		users = (List<User>) userRepository.findAll(RSQLQueryDslSupport.toPredicate(rsql, QUser.user));
		count = users.size();
		log.info("rsql: {} -> count: {}", rsql, count);
		assertThat(rsql, count, is(4L));
		assertThat(rsql, users.get(0).getName(), equalTo("March"));
		assertThat(rsql, users.get(1).getName(), equalTo("April"));
		assertThat(rsql, users.get(2).getName(), equalTo("May"));
		assertThat(rsql, users.get(3).getName(), equalTo("June"));
		assertThat(rsql, users.get(0).getCompany().getName(), equalTo("World Inc"));

		rsql = "name==''";
		companys = (List<Company>) companyRepository.findAll(RSQLQueryDslSupport.toPredicate(rsql, QCompany.company));
		count = companys.size();
		log.info("rsql: {} -> count: {}", rsql, count);
		assertThat(rsql, count, is(1L));
		assertThat(rsql, companys.get(0).getCode(), equalTo("empty"));
		assertThat(rsql, companys.get(0).getName(), equalTo(""));

		rsql = "userRoles.id.roleId=='2'";
		users = (List<User>) userRepository.findAll(RSQLQueryDslSupport.toPredicate(rsql, QUser.user));
		count = users.size();
		log.info("rsql: {} -> count: {}", rsql, count);
		assertThat(rsql, count, is(3L));

		rsql = "userRoles.role.code=='admin'";
		users = (List<User>) userRepository.findAll(RSQLQueryDslSupport.toPredicate(rsql, QUser.user));
		count = users.size();
		log.info("rsql: {} -> count: {}", rsql, count);
		assertThat(rsql, count, is(3L));

		users = userRepository.findAll(RSQLQueryDslSupport.toSpecification(rsql));
		count = users.size();
		log.info("rsql: {} -> count: {}", rsql, count);
		assertThat(rsql, count, is(3L));
	}

	@Test
	final void testBetweenDateTime() {
		String rsql = "createDate=bt=('2018-01-01 12:34:56', '2018-12-31 10:34:56')";
		List<User> users = (List<User>) userRepository.findAll(RSQLQueryDslSupport.toPredicate(rsql, QUser.user));
		long count = users.size();
		log.info("rsql: {} -> count: {}", rsql, count);
		assertThat(rsql, count, is(11L));
	}

}
