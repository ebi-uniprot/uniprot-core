package org.uniprot.core.util;

import java.math.BigDecimal;

import javax.annotation.Nonnull;

public class Crc64 {

    private static final long[] crc64Array = new long[256];

    static {
        for (int i = 0; i <= 255; ++i) {
            long k = i;

            for (int j = 0; j < 8; ++j) {
                if ((k & 1) != 0) {
                    k = (k >>> 1) ^ 0xd800000000000000L;
                } else {
                    k = k >>> 1;
                }
            }
            crc64Array[i] = k;
        }
    }

    private Crc64() {
        // prevent instantiation
    }

    /**
     * Returns the crc64 checksum for the given sequence.
     *
     * @param sequence sequence
     * @return the crc64 checksum for the sequence
     */
    public static long getCrc64Long(@Nonnull String sequence) {
        // x64 + x4 + x3 + x1 + 1
        long pOLY64Reverse = 0xd800000000000000L;

        long reminder = 0;
        for (int i = 0; i < sequence.length(); i++) {

            reminder = reminder ^ sequence.charAt(i);

            for (int bit = 7; bit >= 0; bit--) {
                if ((reminder & 0x1) == 1) {
                    reminder = (reminder >>> 1) ^ pOLY64Reverse;
                } else {
                    reminder = reminder >>> 1;
                }
            }
        }
        return reminder;
    }

    public static BigDecimal getCrc64BD(@Nonnull String sequence) {
        long val = getCrc64Long(sequence);
        if (val > 0) {
            return new BigDecimal(val);
        }
        BigDecimal bd = new BigDecimal(val);
        bd = bd.add(new BigDecimal(Long.MAX_VALUE));
        bd = bd.add(new BigDecimal(Long.MAX_VALUE));
        bd = bd.add(new BigDecimal(2));
        return bd;
    }

    public static String getCrc64(@Nonnull String sequence) {
        long crc64Number = 0;

        for (int i = 0; i < sequence.length(); ++i) {
            char symbol = sequence.charAt(i);

            long a = crc64Number >>> 8;
            long b = (crc64Number ^ symbol) & 0xff;

            crc64Number = a ^ crc64Array[(int) b];
        }

        String crc64String = Long.toHexString(crc64Number).toUpperCase();

        StringBuilder crc64 = new StringBuilder("0000000000000000");

        crc64.replace(crc64.length() - crc64String.length(), crc64.length(), crc64String);

        return crc64.toString();
    }
}
