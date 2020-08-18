package org.jboss.pnc.bacon.common;

import org.jboss.pnc.bacon.common.exception.FatalException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FailTest {

    @Test
    void fail() {
        assertThrows(FatalException.class, () -> Fail.fail("Why not"));

    }

    @Test
    void failIfNull() {
        assertThrows(FatalException.class, () -> Fail.failIfNull(null, "Testing if null"));
        assertDoesNotThrow(() -> Fail.failIfNull("not null", "This should not be thrown"));
    }

    @Test
    void validateIfPositiveNumber() {
        assertTrue(Fail.validateIfPositiveNumber("1.23"));
        assertTrue(Fail.validateIfPositiveNumber("55"));

        assertFalse(Fail.validateIfPositiveNumber("-55"));
        assertFalse(Fail.validateIfPositiveNumber("-5.5"));
        assertFalse(Fail.validateIfPositiveNumber("-5.5.5"));
        assertFalse(Fail.validateIfPositiveNumber("5.5.5"));
        assertFalse(Fail.validateIfPositiveNumber("hello-world"));
    }

    @Test
    void failIfFalse() {
        assertThrows(FatalException.class, () -> Fail.failIfFalse(false, "Testing if false"));
        assertDoesNotThrow(() -> Fail.failIfFalse(true, "This should not be thrown"));
    }
}
