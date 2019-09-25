package org.uniprot.core.util;

import java.io.InputStream;
import java.util.*;

public class Utils {
    /**
     * Convert null to empty string or return actual
     * @param value string null or non null
     * @return empty string or original string
     */
    public static String nullToEmpty(String value) {
        if (value == null)
            return "";
        else
            return value;
    }

    public static <T> List<T> nonNullList(List<T> source) {
        if (source != null) {
            return new ArrayList<>(source);
        } else {
            return new ArrayList<>();
        }
    }

    public static <T> void nonNullAdd(T source, List<T> target) {
        if (source != null) {
            target.add(source);
        }
    }

    public static <T> List<T> nonNullUnmodifiableList(List<T> value) {
        if ((value == null) || value.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(value);
        }
    }

    public static String loadPropertyInput(InputStream configFile) {
        Scanner s = new Scanner(configFile).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static boolean nullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean nullOrEmpty(List<?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean nullOrEmpty(Map<?, ?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean notNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notNullOrEmpty(List<?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notNullOrEmpty(Map<?, ?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean nonNull(Object o) {
        return Objects.nonNull(o);
    }
    public static String upperFirstChar(String str) {
    	if(nullOrEmpty(str))
    		return str;
    	return Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }
    public static String lowerFirstChar(String str) {
    	if(nullOrEmpty(str))
    		return str;
    	return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
