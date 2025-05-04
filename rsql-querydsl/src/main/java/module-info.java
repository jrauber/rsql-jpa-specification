module rsql.querydsl {
    requires com.querydsl.core;
    requires static lombok;
    requires rsql.jpa;
    requires rsql.common;
    requires org.slf4j;
    requires jakarta.persistence;
    requires rsql.parser;
    requires spring.core;

    exports io.github.perplexhub.rsql.querydsl;
}