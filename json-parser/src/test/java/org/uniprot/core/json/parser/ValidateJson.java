package org.uniprot.core.json.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.impl.CrossReferenceImpl;
import org.uniprot.core.json.parser.uniprot.UniprotKBJsonConfig;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryImpl;
import org.uniprot.core.util.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidateJson {

    private static final Logger logger = LoggerFactory.getLogger(ValidateJson.class);

    public static <T> void verifyJsonRoundTripParser(T obj) {
        try {
            ObjectMapper mapper = UniprotKBJsonConfig.getInstance().getFullObjectMapper();
            verifyJsonRoundTripParser(mapper, obj);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public static <T> void verifyJsonRoundTripParser(ObjectMapper mapper, T obj) {
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            // logger.debug(json);
            T converted = mapper.readValue(json, (Class<T>) obj.getClass());
            assertEquals(obj, converted);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    public static <T> JsonNode getJsonNodeFromSerializeOnlyMapper(ObjectMapper mapper, T obj) {
        JsonNode jsonNode = null;
        try {
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            logger.info(jsonString);
            jsonNode = mapper.reader().readTree(jsonString);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        return jsonNode;
    }

    public static <T> JsonNode getJsonNodeFromSerializeOnlyMapper(T obj) {
        ObjectMapper mapper = UniprotKBJsonConfig.getInstance().getSimpleObjectMapper();
        return getJsonNodeFromSerializeOnlyMapper(mapper, obj);
    }

    /**
     * This test fail if there is an null or empty field left.
     *
     * @param obj The object that must be checked
     * @param <T> The object that must be checked
     */
    public static <T> void verifyEmptyFields(T obj) {
        verifyEmptyFields(obj, null, null);
    }

    /**
     * This test fail if there is an null or empty field left.
     *
     * @param obj The object that must be checked
     * @param <T> The object that must be checked
     */
    public static <T> void verifyEmptyFields(T obj, String ignoreField) {
        verifyEmptyFields(obj, null, ignoreField);
    }

    private static <T> void verifyEmptyFields(T obj, String propertyName, String ignoredField) {
        try {
            if (obj == null) {
                checkAndFail(propertyName, ignoredField, " can not be null in the complete test");
            } else {
                if (obj instanceof String) {
                    if (((String) obj).isEmpty()) {
                        checkAndFail(
                                propertyName,
                                ignoredField,
                                " can not be empty in the complete test");
                    }
                } else if (obj instanceof Number) {
                    if (((Number) obj).intValue() == 0) {
                        checkAndFail(propertyName, ignoredField, " can not be zero");
                    }
                } else if (obj instanceof Boolean) {
                    if (!((Boolean) obj)) {
                        checkAndFail(propertyName, ignoredField, " must be true");
                    }
                } else if (obj instanceof Collection) {
                    if (((Collection) obj).isEmpty()) {
                        checkAndFail(propertyName, ignoredField, " must not be empty");
                    } else {
                        ((Collection) obj)
                                .forEach(
                                        item ->
                                                verifyEmptyFields(
                                                        item, propertyName, ignoredField));
                    }
                } else if (obj instanceof Map) {
                    if (((Map) obj).isEmpty()) {
                        checkAndFail(propertyName, ignoredField, " must not be empty");
                    } else {
                        ((Map<String, Object>) obj)
                                .entrySet()
                                .forEach(
                                        entry ->
                                                verifyEmptyFields(
                                                        entry.getValue(),
                                                        propertyName,
                                                        ignoredField));
                    }
                } else if (!(obj instanceof Enum)) {
                    for (Field field : getInheritedPrivateFields(obj.getClass())) {
                        if (!Modifier.isStatic(field.getModifiers()) && isJsonField(field, obj)) {
                            field.setAccessible(true);
                            verifyEmptyFields(field.get(obj), field.getName(), ignoredField);
                        }
                    }
                }
            }

        } catch (Exception e) {
            if (obj != null) {
                fail("Error: object " + obj.getClass() + " is not complete : " + e.getMessage());
            } else {
                fail("Error: not expected null object: " + e.getMessage());
            }
        }
    }

    private static void checkAndFail(String propertyName, String ignoredField, String message) {
        if (ignoredField == null
                || (Utils.notNullNotEmpty(ignoredField) && !propertyName.equals(ignoredField))) {
            fail(propertyName + message);
        } else {
            logger.warn("property ignored: " + ignoredField);
        }
    }

    /**
     * There are some fields that are not being used in json and we should ignore than if they are
     * null
     *
     * @param field field object
     * @param object the object that is being passed
     * @return if it is a valid json field (default is true)
     */
    private static <T> boolean isJsonField(Field field, T object) {
        boolean result = true;
        if (field.getName().equals("properties") && object instanceof CrossReferenceImpl) {
            result = false;
        }
        if (field.getName().equals("inactiveReason") && object instanceof UniProtKBEntryImpl) {
            result = false;
        }
        return result;
    }

    private static List<Field> getInheritedPrivateFields(Class<?> type) {
        List<Field> result = new ArrayList<>();

        Class<?> i = type;
        while (i != null && i != Object.class) {
            Collections.addAll(result, i.getDeclaredFields());
            i = i.getSuperclass();
        }

        return result;
    }

    public static void validateValueEvidence(
            JsonNode valueEvidence, String value, String evidenceCode, String source, String id) {
        assertNotNull(valueEvidence);
        assertNotNull(valueEvidence.get("value"));
        assertEquals(value, valueEvidence.get("value").asText());

        assertNotNull(valueEvidence.get("evidences"));
        assertEquals(1, valueEvidence.get("evidences").size());
        validateEvidence(valueEvidence.get("evidences").get(0), evidenceCode, source, id);
    }

    public static void validateEvidence(
            JsonNode evidence, String evidenceCode, String source, String id) {
        assertNotNull(evidence);
        assertNotNull(evidence.get("evidenceCode"));
        assertEquals(evidenceCode, evidence.get("evidenceCode").asText());
        assertNotNull(evidence.get("source"));
        assertEquals(source, evidence.get("source").asText());
        assertNotNull(evidence.get("id"));
        assertEquals(id, evidence.get("id").asText());
    }
}
