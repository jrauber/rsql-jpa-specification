package io.github.perplexhub.rsql.common;

import java.util.Collections;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RSQLSimpleConverter extends RSQLVisitorBase<Void, MultiValueMap<String, String>> {

	public RSQLSimpleConverter() {
		super();
	}

	@Override
	public Void visit(ComparisonNode node, MultiValueMap<String, String> map) {
		log.debug("visit(node:{},map:{})", node, map);
		map.addAll(node.getSelector(), node.getArguments());
		return null;
	}

	@Override
	public Void visit(AndNode node, MultiValueMap<String, String> map) {
		log.debug("visit(node:{},map:{})", node, map);
		node.getChildren().forEach(n -> n.accept(this, map));
		return null;
	}

	@Override
	public Void visit(OrNode node, MultiValueMap<String, String> map) {
		log.debug("visit(node:{},map:{})", node, map);
		node.getChildren().forEach(n -> n.accept(this, map));
		return null;
	}

	@Override
	public Map<String, String> getPropertyPathMapper() {
		return Collections.emptyMap();
	}

}
