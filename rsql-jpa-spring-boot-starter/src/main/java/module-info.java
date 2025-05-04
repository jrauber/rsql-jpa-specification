module rsql.jpa.spring.boot.starter.jpa {
    requires static lombok;
    requires rsql.jpa;
    requires rsql.common;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.orm;
    requires org.slf4j;
    requires java.naming;

    exports io.github.perplexhub.rsql.jpa.starter;
}