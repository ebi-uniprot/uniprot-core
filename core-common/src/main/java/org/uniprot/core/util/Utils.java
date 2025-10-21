package org.uniprot.core.util;

import java.util.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Utils {

    private Utils() {}

    /**
     * Convert null to empty string or return actual
     *
     * @param string string null or non null
     * @return empty string or original string
     */
    @Nonnull
    public static String emptyOrString(@Nullable String string) {
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
    @Nonnull
    public static <T> List<T> emptyOrList(@Nullable List<T> list) {
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
    @Nonnull
    public static <T> List<T> modifiableList(@Nullable List<T> source) {
        if (source != null) {
            return new ArrayList<>(source);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Creates new LinkedHashSet from source. Can be expensive call depending on list size.
     *
     * @param source can be null or a LinkedHashSet
     * @param <T> Type of source LinkedHashSet
     * @return non null LinkedHashSet
     */
    @Nonnull
    public static <T> LinkedHashSet<T> modifiableLinkedHashSet(@Nullable LinkedHashSet<T> source) {
        if (source != null) {
            return new LinkedHashSet<>(source);
        } else {
            return new LinkedHashSet<>();
        }
    }

    /**
     * Creates new hash set from source. Can be expensive call depending on set size. Will change
     * the implementation of set to HashSet.
     *
     * @param source can be null or any Set
     * @param <T> Type of source Set
     * @return non null HashSet
     */
    @Nonnull
    public static <T> Set<T> modifiableSet(@Nullable Set<T> source) {
        if (source != null) {
            return new HashSet<>(source);
        } else {
            return new HashSet<>();
        }
    }

    /**
     * Add value in list or ignore if value or target list is null
     *
     * @param value value or null that you want to add in list
     * @param target list to add value, should be modifiable
     * @param <T> Type of value and list should be same
     * @return true if collection changed
     */
    public static <T> boolean addOrIgnoreNull(@Nullable T value, @Nullable Collection<T> target) {
        if (notNull(target) && notNull(value)) {
            return target.add(value);
        }
        return false;
    }

    /**
     * Add value in list or ignore if value is empty or target list is null
     *
     * @param value value or null that you want to add in list
     * @param target list to add value, should be modifiable
     * @return true if collection changed
     */
    public static boolean addOrIgnoreEmpty(@Nullable String value, @Nullable Collection<String> target) {
        if (notNull(target) && notNullNotEmpty(value)) {
            return target.add(value);
        }
        return false;
    }

    /**
     * Converts list to unmodifiable list
     *
     * @param targetList can be null or any list
     * @param <T> type of the list
     * @return Always returns non null un modifiable list
     */
    @Nonnull
    public static <T> List<T> unmodifiableList(@Nullable List<T> targetList) {
        if ((targetList == null) || targetList.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.unmodifiableList(targetList);
        }
    }

    /**
     * Converts Set to unmodifiable Set
     *
     * @param targetSet can be null or any Set
     * @param <T> type of the Set
     * @return Always returns non null un modifiable Set
     */
    @Nonnull
    public static <T> Set<T> unmodifiableSet(@Nullable Set<T> targetSet) {
        if ((targetSet == null) || targetSet.isEmpty()) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(targetSet);
        }
    }

    public static boolean nullOrEmpty(@Nullable String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean nullOrEmpty(@Nullable Collection<?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean nullOrEmpty(@Nullable Map<?, ?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean notNullNotEmpty(@Nullable String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean notNullNotEmpty(@Nullable Collection<?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notNullNotEmpty(@Nullable Map<?, ?> value) {
        return value != null && !value.isEmpty();
    }

    public static boolean notNull(@Nullable Object o) {
        return Objects.nonNull(o);
    }

    @Nullable
    public static String upperFirstChar(@Nullable String str) {
        if (nullOrEmpty(str)) return str;
        return Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }

    @Nullable
    public static String lowerFirstChar(@Nullable String str) {
        if (nullOrEmpty(str)) return str;
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    public static <T> void nullThrowIllegalArgument(@Nullable T value) {
        if (Objects.isNull(value)) throw new IllegalArgumentException("null not allowed");
    }

    public static void putOrIgnoreNull(String key, Object value, Map<String, Object> map) {
        if (Objects.nonNull(map) && Objects.nonNull(value) && notNullNotEmpty(key)) {
            map.put(key, value);
        }
    }
}
