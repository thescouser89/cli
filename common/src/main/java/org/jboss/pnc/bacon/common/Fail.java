package org.jboss.pnc.bacon.common;

import lombok.extern.slf4j.Slf4j;
import org.jboss.pnc.bacon.common.exception.FatalException;

@Slf4j
public class Fail {

    public static void fail(String reason) {
        failIfNull(null, reason);
    }

    public static void failIfNull(Object object, String reason) {
        if (object == null) {
            log.error(reason);
            throw new FatalException();
        }
    }

    public static void failIfFalse(boolean result, String reason) {
        if (!result) {
            log.error(reason);
            throw new FatalException();
        }
    }

    /**
     * Validate if string is a positive number of not. If the string cannot be parsed into a number, then return false.
     * The number can be an integer or a double.
     *
     * @param number
     * @return result
     */
    public static boolean validateIfPositiveNumber(String number) {
        try {
            double value = Double.valueOf(number);
            if (value < 0) {
                log.warn("Number {} is negative", number);
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            log.warn("{} is not a number!", number);
            return false;
        }
    }
}
