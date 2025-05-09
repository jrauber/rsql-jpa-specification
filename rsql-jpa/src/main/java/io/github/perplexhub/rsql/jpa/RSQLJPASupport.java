package io.github.perplexhub.rsql.jpa;

import static java.util.stream.Collectors.toSet;

import java.util.*;
import java.util.stream.Stream;

import io.github.perplexhub.rsql.common.RSQLCommonSupport;
import io.github.perplexhub.rsql.common.RSQLCustomPredicate;
import io.github.perplexhub.rsql.common.RSQLOperators;
import io.github.perplexhub.rsql.common.RSQLVisitorBase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.util.StringUtils;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SuppressWarnings({ "serial" })
public class RSQLJPASupport extends RSQLCommonSupport {

	public RSQLJPASupport() {
		super();
	}

	public RSQLJPASupport(Map<String, EntityManager> entityManagerMap) {
		this(entityManagerMap, Map.of());
	}
	
	public RSQLJPASupport(Map<String, EntityManager> entityManagerMap, Map<EntityManager, Database> entityManagerDatabase) {
		super(entityManagerMap);
		RSQLVisitorBase.setEntityManagerDatabase(entityManagerDatabase);
	}

	public static <T> Specification<T> rsql(final String rsqlQuery) {
		return toSpecification(rsqlQuery, false, null);
	}

	public static <T> Specification<T> rsql(final String rsqlQuery, final boolean distinct) {
		return toSpecification(rsqlQuery, distinct, null);
	}

	public static <T> Specification<T> rsql(final String rsqlQuery, final Map<String, String> propertyPathMapper) {
		return toSpecification(rsqlQuery, false, propertyPathMapper);
	}

	public static <T> Specification<T> rsql(final String rsqlQuery, final boolean distinct, final Map<String, String> propertyPathMapper) {
		return toSpecification(rsqlQuery, distinct, propertyPathMapper);
	}

	public static <T> Specification<T> rsql(final String rsqlQuery, final List<RSQLCustomPredicate<?>> customPredicates) {
		return toSpecification(rsqlQuery, customPredicates);
	}

	public static <T> Specification<T> toSpecification(final String rsqlQuery) {
		return toSpecification(rsqlQuery, false, null, null, null);
	}

	public static <T> Specification<T> toSpecification(final String rsqlQuery, final Map<String, String> propertyPathMapper) {
		return toSpecification(rsqlQuery, false, propertyPathMapper, null, null);
	}

	public static <T> Specification<T> toSpecification(final String rsqlQuery, final boolean distinct) {
		return toSpecification(rsqlQuery, distinct, null, null, null);
	}

	public static <T> Specification<T> toSpecification(final String rsqlQuery, final boolean distinct, final Map<String, String> propertyPathMapper) {
		return toSpecification(rsqlQuery, distinct, propertyPathMapper, null, null);
	}

	public static <T> Specification<T> toSpecification(final String rsqlQuery, final List<RSQLCustomPredicate<?>> customPredicates) {
		return toSpecification(rsqlQuery, false, null, customPredicates, null);
	}

	public static <T> Specification<T> toSpecification(final String rsqlQuery, final Map<String, String> propertyPathMapper, final Map<String, JoinType> joinHints) {
		return toSpecification(rsqlQuery, false, propertyPathMapper, null, joinHints);
	}

	public static <T> Specification<T> toSpecification(final String rsqlQuery, final boolean distinct, final Map<String, String> propertyPathMapper, final Map<String, JoinType> joinHints) {
		return toSpecification(rsqlQuery, distinct, propertyPathMapper, null, joinHints);
	}

	public static <T> Specification<T> toSpecification(final String rsqlQuery, final Map<String, String> propertyPathMapper, final List<RSQLCustomPredicate<?>> customPredicates, final Map<String, JoinType> joinHints) {
		return toSpecification(rsqlQuery, false, propertyPathMapper, customPredicates, joinHints);
	}

	public static <T> Specification<T> toSpecification(final String rsqlQuery, final boolean distinct, final Map<String, String> propertyPathMapper, final List<RSQLCustomPredicate<?>> customPredicates, final Map<String, JoinType> joinHints) {
		return toSpecification(rsqlQuery, distinct, propertyPathMapper, customPredicates, joinHints, null, null);
	}

	public static <T> Specification<T> toSpecification(
			final String rsqlQuery,
			final Map<String, String> propertyPathMapper,
			final List<RSQLCustomPredicate<?>> customPredicates,
			final Map<String, JoinType> joinHints,
			final Map<Class<?>, List<String>> propertyWhitelist,
			final Map<Class<?>, List<String>> propertyBlacklist) {
		return toSpecification(rsqlQuery, false, propertyPathMapper, customPredicates, joinHints, propertyWhitelist, propertyBlacklist);
	}

	public static <T> Specification<T> toSpecification(final QuerySupport querySupport) {
		log.debug("toSpecification({})", querySupport);
		return (root, query, cb) -> {
			query.distinct(querySupport.isDistinct());
			if (!StringUtils.hasText(querySupport.getRsqlQuery())) {
				return null;
			}

			Set<ComparisonOperator> supportedOperators = RSQLOperators.supportedOperators();
			if (querySupport.getCustomPredicates() != null) {
				Stream<ComparisonOperator> customOperators = querySupport.getCustomPredicates().stream()
						.map(RSQLCustomPredicate::getOperator)
						.filter(Objects::nonNull);

				supportedOperators = Stream.concat(supportedOperators.stream(), customOperators).collect(toSet());
			}

			Node rsql = new RSQLParser(supportedOperators).parse(querySupport.getRsqlQuery());
			RSQLJPAPredicateConverter visitor = new RSQLJPAPredicateConverter(cb, querySupport.getPropertyPathMapper(),
					querySupport.getCustomPredicates(), querySupport.getJoinHints(),
					querySupport.getProcedureWhiteList(), querySupport.getProcedureBlackList(),
					querySupport.isStrictEquality(), querySupport.getLikeEscapeCharacter());

			visitor.setPropertyWhitelist(querySupport.getPropertyWhitelist());
			visitor.setPropertyBlacklist(querySupport.getPropertyBlacklist());

			return rsql.accept(visitor, root);
		};
	}

	public static <T> Specification<T> toSpecification(
			final String rsqlQuery,
			final boolean distinct,
			final Map<String, String> propertyPathMapper,
			final List<RSQLCustomPredicate<?>> customPredicates,
			final Map<String, JoinType> joinHints,
			final Map<Class<?>, List<String>> propertyWhitelist,
			final Map<Class<?>, List<String>> propertyBlacklist) {
		return toSpecification(QuerySupport.builder()
				.rsqlQuery(rsqlQuery)
				.distinct(distinct)
				.joinHints(joinHints)
				.propertyPathMapper(propertyPathMapper)
				.customPredicates(customPredicates)
				.propertyWhitelist(propertyWhitelist)
				.propertyBlacklist(propertyBlacklist)
				.build());
	}

	public static <T> Specification<T> toSort(@Nullable final String sortQuery) {
		return toSort(sortQuery, Collections.emptyMap());
	}

	public static <T> Specification<T> toSort(@Nullable final String sortQuery, final Map<String, String> propertyPathMapper) {
		return toSort(SortSupport.builder().sortQuery(sortQuery).propertyPathMapper(propertyPathMapper).build());
	}

	/**
	 * Add orderBy(s) to {@code CriteriaQuery}.
	 * Example: {@code "field1,asc;field2,desc;field3.subfield1,asc"}
	 *
	 * @param sortSupport - sort support
	 * @param <T>
	 * @return {@code Specification} with specified order by
	 */
	public static <T> Specification<T> toSort(final SortSupport sortSupport) {
		log.debug("toSort({})", sortSupport);
		return (root, query, cb) -> {
			if (StringUtils.hasText(sortSupport.getSortQuery())) {
				final List<Order> orders = SortUtils.parseSort(sortSupport, root, cb);
				query.orderBy(orders);
			}
			return null;
		};
	}

	/**
	 * Returns a single entity matching the given {@link Specification} or {@link Optional#empty()} if none found.
	 *
	 * @param jpaSpecificationExecutor JPA repository
	 * @param rsqlQuery can be {@literal null}.
	 * @return never {@literal null}.
	 * @throws org.springframework.dao.IncorrectResultSizeDataAccessException if more than one entity found.
	 */
	public static Optional<?> findOne(JpaSpecificationExecutor<?> jpaSpecificationExecutor, @Nullable String rsqlQuery) {
		return jpaSpecificationExecutor.findOne(toSpecification(rsqlQuery));
	}

	/**
	 * Returns all entities matching the given {@link Specification}.
	 *
	 * @param jpaSpecificationExecutor JPA repository
	 * @param rsqlQuery can be {@literal null}.
	 * @return never {@literal null}.
	 */
	public static List<?> findAll(JpaSpecificationExecutor<?> jpaSpecificationExecutor, @Nullable String rsqlQuery) {
		return jpaSpecificationExecutor.findAll(toSpecification(rsqlQuery));
	}

	/**
	 * Returns a {@link Page} of entities matching the given {@link Specification}.
	 *
	 * @param jpaSpecificationExecutor JPA repository
	 * @param rsqlQuery can be {@literal null}.
	 * @param pageable must not be {@literal null}.
	 * @return never {@literal null}.
	 */
	public static Page<?> findAll(JpaSpecificationExecutor<?> jpaSpecificationExecutor, @Nullable String rsqlQuery, Pageable pageable) {
		return jpaSpecificationExecutor.findAll(toSpecification(rsqlQuery), pageable);
	}

	/**
	 * Returns all entities matching the given {@link Specification} and {@link Sort}.
	 *
	 * @param jpaSpecificationExecutor JPA repository
	 * @param rsqlQuery can be {@literal null}.
	 * @param sort must not be {@literal null}.
	 * @return never {@literal null}.
	 */
	public static List<?> findAll(JpaSpecificationExecutor<?> jpaSpecificationExecutor, @Nullable String rsqlQuery, Sort sort) {
		return jpaSpecificationExecutor.findAll(toSpecification(rsqlQuery), sort);
	}

	/**
	 * Returns all entities matching the given {@link Specification} and {@link Sort}.
	 *
	 * @param jpaSpecificationExecutor JPA repository
	 * @param rsqlQuery can be {@literal null}.
	 * @param sort can be {@literal null}, comma delimited.
	 * @return never {@literal null}.
	 */
	public static List<?> findAll(JpaSpecificationExecutor<?> jpaSpecificationExecutor, @Nullable String rsqlQuery, @Nullable String sort) {
		return StringUtils.hasText(sort)
				? jpaSpecificationExecutor.findAll(toSpecification(rsqlQuery), Sort.by(Direction.ASC, StringUtils.commaDelimitedListToStringArray(sort)))
				: jpaSpecificationExecutor.findAll(toSpecification(rsqlQuery));
	}

	/**
	 * Returns the number of instances that the given {@link Specification} will return.
	 *
	 * @param jpaSpecificationExecutor JPA repository
	 * @param rsqlQuery the {@link Specification} to count instances for. Can be {@literal null}.
	 * @return the number of instances.
	 */
	public static long count(JpaSpecificationExecutor<?> jpaSpecificationExecutor, @Nullable String rsqlQuery) {
		return jpaSpecificationExecutor.count(toSpecification(rsqlQuery));
	}

	protected String getVersion() {
		try {
			Properties prop = new Properties();
			prop.load(getClass().getResourceAsStream("/META-INF/maven/io.github.perplexhub/rsql-jpa/pom.properties"));
			String version = prop.getProperty("version");
			return StringUtils.hasText(version) ? "[" + version + "] " : "";
		} catch (Exception e) {
			return "";
		}
	}

}
