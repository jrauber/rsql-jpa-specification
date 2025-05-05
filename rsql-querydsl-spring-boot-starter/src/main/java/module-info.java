module rsql.querydsl.spring.boot.starter {
    requires static lombok;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.orm;
    requires jakarta.persistence;
    requires rsql.common;
    requires rsql.querydsl;
    requires rsql.common.test;
    requires org.slf4j;

    exports io.github.perplexhub.rsql.querydsl.starter;

    opens io.github.perplexhub.rsql.querydsl.starter to spring.core;
}