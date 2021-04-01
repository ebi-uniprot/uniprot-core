package org.uniprot.cv;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author lgonzales
 * @since 31/03/2021
 */
class FileParseExceptionTest {

    @Test
    void canCreateExceptionWithMessage() {
        String msg = "error msg";
        Exception e = new FileParseException(msg);
        assertEquals(msg, e.getMessage());
    }

    @Test
    void canCreateExceptionWithMessageAndThrowable() {
        String msg = "error msg";
        Throwable throwable = new Throwable("error msg2");
        Exception e = new FileParseException(msg, throwable);
        assertEquals(msg, e.getMessage());
        assertEquals(throwable, e.getCause());
    }
}
