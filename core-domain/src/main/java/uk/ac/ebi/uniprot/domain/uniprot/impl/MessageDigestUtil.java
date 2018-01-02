package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtil {
    private static long[] crc64Array = new long[256];

    /**
     * Initialization of _crc64Array.
     */
    static {
        for (int i = 0; i <= 255; ++i) {
            long k = i;

            for (int j = 0; j < 8; ++j) {
                if ((k & 1) != 0) {
                    k = (k >>> 1) ^ 0xd800000000000000l;
                } else {
                    k = k >>> 1;
                }
            }
            crc64Array[i] = k;
        }
    }
    static final byte[] HEX_CHAR_TABLE = {
            (byte) '0', (byte) '1', (byte) '2', (byte) '3',
            (byte) '4', (byte) '5', (byte) '6', (byte) '7',
            (byte) '8', (byte) '9', (byte) 'A', (byte) 'B',
            (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F'
    };

    private MessageDigestUtil() {
        // prevent instatiation
    }

    /**
     * This function call MessageDigest class digest to get bytes and convert the byte array to Hex String
     *
     * @param seq
     * @param type
     *            Message Digest type,
     * @return String
     */
    public static String getDigest(String seq, String type) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(type);
        } catch (NoSuchAlgorithmException e) {
            return seq;
        }
        byte[] input = seq.getBytes();
        byte[] output = md.digest(input);
        return byteArrayToHexString(output);
    }

    public static String getCrc64(String sequence) {
        long crc64Number = 0;

        for (int i = 0; i < sequence.length(); ++i) {
            char symbol = sequence.charAt(i);

            long a = crc64Number >>> 8;
            long b = (crc64Number ^ symbol) & 0xff;

            crc64Number = a ^ crc64Array[(int) b];
        }

        String crc64String = Long.toHexString(crc64Number).toUpperCase();

        StringBuilder crc64 = new StringBuilder("0000000000000000");

        crc64.replace(crc64.length() - crc64String.length(),
                crc64.length(),
                crc64String);

        return crc64.toString();
    }
   
    private static String byteArrayToHexString(byte[] in) {
        byte ch = 0x00;
        int i = 0;
        if (in == null || in.length <= 0)
            return null;

        String[] pseudo = {"0", "1", "2",
                "3", "4", "5", "6", "7", "8",
                "9", "A", "B", "C", "D", "E",
                "F"};
        StringBuilder out = new StringBuilder(in.length * 2);

        while (i < in.length) {
            ch = (byte) (in[i] & 0xF0); // Strip offhigh nibble
            ch = (byte) (ch >>> 4);
            // shift the bits down
            ch = (byte) (ch & 0x0F);
            // must do this is high order bit is on!
            out.append(pseudo[ch]); // convert thenibble to a String Character
            ch = (byte) (in[i] & 0x0F); // Strip offlow nibble
            out.append(pseudo[ch]); // convert thenibble to a String Character
            i++;
        }
        return new String(out);
    }

}
