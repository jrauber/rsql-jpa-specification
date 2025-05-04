module rsql.jpa {
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires rsql.common;
    requires rsql.parser;
    requires spring.core;
    requires spring.data.commons;
    requires spring.data.jpa;
    requires spring.orm;
    requires org.slf4j;

    exports io.github.perplexhub.rsql.jpa;
    exports io.github.perplexhub.rsql.jpa.jsonb;

    opens io.github.perplexhub.rsql.jpa.custom to spring.core, org.hibernate.orm.core;
    opens io.github.perplexhub.rsql.jpa.model to spring.core, org.hibernate.orm.core;
    opens io.github.perplexhub.rsql.jpa to spring.core, org.hibernate.orm.core;
}