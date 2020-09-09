package org.uniprot.core.json.parser;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.github.bohnman.squiggly.filter.SquigglyPropertyFilter;
import com.github.bohnman.squiggly.filter.SquigglyPropertyFilterMixin;

/** @author lgonzales */
public abstract class JsonConfig {

    public abstract ObjectMapper getSimpleObjectMapper();

    public abstract ObjectMapper getFullObjectMapper();

    // common setting applicable to fullobject mapper
    public ObjectMapper getDefaultFullObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper(new SmileFactory());
       setDefaultObjectMapperConfig(objMapper);
        objMapper.setAnnotationIntrospector(new FullAnnotationIntrospector());
        return objMapper;
    }

    // common setting applicable to skinny object mapper
    public ObjectMapper getDefaultSimpleObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        setDefaultObjectMapperConfig(objMapper);
        objMapper.setAnnotationIntrospector(new SimpleAnnotationIntrospector());
        return objMapper;
    }

    private void setDefaultObjectMapperConfig(ObjectMapper objMapper) {
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objMapper.addMixIn(Object.class, SquigglyPropertyFilterMixin.class);

        PropertyFilter filter = SimpleBeanPropertyFilter.serializeAll();
        SimpleFilterProvider filterProvider =
                new SimpleFilterProvider().addFilter(SquigglyPropertyFilter.FILTER_ID, filter);
        objMapper.setFilterProvider(filterProvider);
    }
}
