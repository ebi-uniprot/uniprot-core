package org.uniprot.core.util.property;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyExceptionTest {
  @Test
  void canCreateExceptionWithMessage() {
    String msg = "any msg";
    Exception e = new PropertyException(msg);
    assertEquals(msg, e.getMessage());
  }

  @Test
  void canCreateExceptionWithMessageAndThrowable() {
    String msg = "any msg";
    Throwable throwable = new Throwable("msg2");
    Exception e = new PropertyException(msg, throwable);
    assertAll(
      () -> assertEquals(msg, e.getMessage()),
      () -> assertEquals(throwable, e.getCause())
    );
  }

  @Test
  void canCreateExceptionWithThrowable() {
    String msg = "msg2";
    Throwable throwable = new Throwable(msg);
    Exception e = new PropertyException(throwable);
    assertAll(
      () -> assertEquals(msg, e.getMessage()),
      () -> assertEquals(throwable, e.getCause())
    );
  }
}