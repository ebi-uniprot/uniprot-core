package org.uniprot.core.json.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/** @author lgonzales */
public abstract class JsonConfig {

    public abstract ObjectMapper getSimpleObjectMapper();

    public abstract ObjectMapper getFullObjectMapper();

    // common setting applicable to fullobject mapper
    public ObjectMapper getDefaultFullObjectMapper() {
        ObjectMapper objMapper = getDefaultObjectMapper();
        objMapper.setAnnotationIntrospector(new CustomAnnotationIntrospector());
        return objMapper;
    }

    // common setting applicable to skinny object mapper
    public ObjectMapper getDefaultSimpleObjectMapper() {
        ObjectMapper objMapper = getDefaultObjectMapper();
        objMapper.setAnnotationIntrospector(new SimpleAnnotationIntrospector());
        return objMapper;
    }

    private ObjectMapper getDefaultObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objMapper;
    }
}
