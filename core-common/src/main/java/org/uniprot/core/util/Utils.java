package org.uniprot.core.util;

import java.io.InputStream;
import java.util.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class Utils {
    /**
     * Convert null to empty string or return actual
     *
     * @param string string null or non null
     * @return empty string or original string
     */
    public static @NotNull String emptyOrString(@Null String string) {
        if (string == null) return "";
        else return string;
    }

    /**
     * return new Array list when input list is null else return actual list
     *
     * @param list null or list
     * @param <T> Type of list
     * @return non null list or new array list
     */
    public static <T> @NotNull List<T> emptyOrList(@Null List<T> list) {
        if (list == null) return new ArrayList<>();
        else return list;
    }

    /**
     * Creates new array list from source. Can be expensive call depending on list size. Will change
     * the implementation of list to array list.
     *
     * @param source can be null or any list
     * @param <T> Type of source list
     * @return non null array list
     */
    public static <T> @NotNull List<T> modifiableList(@Null List<T> source) {
        if (source != null) {
            return new ArrayList<>(source);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Add value in list or ignore if value is null
     *
     * @param value value or null when you want to add in list
     * @param target list to add value, should be notNull and modifiable
     * @param <T> Type of value and list should be same
     */
    public static <T> void addOrIgnoreNull(@Null T value, @NotNull List<T> target) {
        if (value != null) {
            target.add(value);
        }
    }

    /**
     * Converts list to unmodifiable list
     *
     * @param targetList can be null or any list
     * @param <T> type of the list
     * @return Always returns non null un modifiable list
     */
    public static <T> @NotNull List<T> unmodifiableList(@Null List<T> targetList) {
        if ((targetList == null) || targetList.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(targetList);
        }
    }

    public static @NotNull String loadPropertyInput(@NotNull InputStream configFile) {
        Scanner s = new Scanner(configFile).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static boolean nullOrEmpty(@Null String value) {
        return value == null || value.isEmpty();
    }

    public static boolean nullOrEmpty(@Null List<?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean nullOrEmpty(@Null Map<?, ?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean notNullOrEmpty(@Null String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notNullOrEmpty(@Null List<?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notNullOrEmpty(@Null Map<?, ?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notNull(@Null Object o) {
        return Objects.nonNull(o);
    }

    public static @Null String upperFirstChar(@Null String str) {
        if (nullOrEmpty(str)) return str;
        return Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }

    public static @Null String lowerFirstChar(@Null String str) {
        if (nullOrEmpty(str)) return str;
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
