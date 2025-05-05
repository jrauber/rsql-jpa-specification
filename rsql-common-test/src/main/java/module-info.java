module rsql.common.test {
    requires com.querydsl.core;
    requires jakarta.persistence;
    requires java.compiler;
    requires static lombok;
    requires spring.data.commons;
    requires spring.data.jpa;


    exports io.github.perplexhub.rsql.common.test.model;
    exports io.github.perplexhub.rsql.common.test.model.account;
    exports io.github.perplexhub.rsql.common.test.repository.jpa;
    exports io.github.perplexhub.rsql.common.test.repository.querydsl;

    opens io.github.perplexhub.rsql.common.test.model.account to org.hibernate.orm.core, spring.core;
    opens io.github.perplexhub.rsql.common.test.model to org.hibernate.orm.core, spring.core;
}