package org.uniprot.core.json.parser;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.bohnman.squiggly.filter.SquigglyPropertyFilter;
import com.github.bohnman.squiggly.filter.SquigglyPropertyFilterMixin;

/** @author lgonzales */
public abstract class JsonConfig {

    public abstract ObjectMapper getSimpleObjectMapper();

    public abstract ObjectMapper getFullObjectMapper();

    // common setting applicable to fullobject mapper
    public ObjectMapper getDefaultFullObjectMapper() {
        ObjectMapper objMapper = getDefaultObjectMapper();
        objMapper.setAnnotationIntrospector(new FullAnnotationIntrospector());
        return objMapper;
    }

    public ObjectMapper getDefaultSimpleObjectMapper() {
        ObjectMapper objMapper = getDefaultObjectMapper();
        objMapper.setAnnotationIntrospector(new SimpleAnnotationIntrospector());
        return objMapper;
    }

    protected ObjectMapper getSimpleObjectMapperWithIgnoredTypes(Set<Class<?>> ignoredTypes) {
        ObjectMapper objMapper = getDefaultObjectMapper();
        objMapper.setAnnotationIntrospector(new SimpleAnnotationIntrospector(ignoredTypes));
        return objMapper;
    }

    private ObjectMapper getDefaultObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objMapper.addMixIn(Object.class, SquigglyPropertyFilterMixin.class);
        PropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();
        SimpleFilterProvider filterProvider =
                new SimpleFilterProvider().addFilter(SquigglyPropertyFilter.FILTER_ID, filter);
        objMapper.setFilterProvider(filterProvider);
        return objMapper;
    }
}
