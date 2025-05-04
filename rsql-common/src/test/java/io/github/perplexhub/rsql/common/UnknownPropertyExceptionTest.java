package io.github.perplexhub.rsql.common;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.perplexhub.rsql.common.test.model.User;
import org.junit.jupiter.api.Test;

public class UnknownPropertyExceptionTest {

  @Test
  public void shouldCreateProperMessage() {
    //given
    UnknownPropertyException ex = new UnknownPropertyException("firstName", User.class);

    //when
    String message = ex.getMessage();

    //then
    assertThat(message).isEqualTo("Unknown property: firstName from entity io.github.perplexhub.rsql.common.test.model.User");
  }

}