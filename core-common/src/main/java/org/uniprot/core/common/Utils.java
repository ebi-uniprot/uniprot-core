package org.uniprot.core.common;

import java.io.InputStream;
import java.util.*;

public class Utils {
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

    public static boolean notEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notEmpty(List<?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notEmpty(Map<?, ?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean nonNull(Object o) {
        return Objects.nonNull(o);
    }
}
