module rsql.jpa.specification {
    requires jakarta.persistence;
    requires static lombok;
    requires spring.context;
    requires spring.core;
    requires rsql.querydsl;
    requires rsql.common;
    requires org.slf4j;
}