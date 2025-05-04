package io.github.perplexhub.rsql.common;

public class FunctionBlackListedException extends io.github.perplexhub.rsql.common.RSQLException {

    public FunctionBlackListedException(String functionName) {
        super(String.format("Function '%s' is blacklisted", functionName));
    }
}
