module rsql.common {
    requires jakarta.persistence;
    requires java.sql;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires rsql.parser;
    requires spring.core;
    requires spring.orm;
    requires org.slf4j;

    exports io.github.perplexhub.rsql.common;
}