package uk.ac.ebi.uniprot.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtil {
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

    public static String getDigest(byte[] input, String type) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(type);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] output = md.digest(input);
        return byteArrayToHexString(output);
    }

    /**
     * This function is call getDigest function by using type"MD5"
     *
     * @param seq
     * @return Hex string
     */
    public static String getMD5(String seq) {
        return getDigest(seq, "MD5");
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
