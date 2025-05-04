module rsql.querydsl.spring.boot.starter {
    requires static lombok;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.orm;
    requires jakarta.persistence;
    requires rsql.common;
    requires org.slf4j;

    exports io.github.perplexhub.rsql.querydsl.starter;
}