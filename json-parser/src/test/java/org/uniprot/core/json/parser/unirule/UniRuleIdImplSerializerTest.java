package org.uniprot.core.json.parser.unirule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.UniRuleId;
import org.uniprot.core.unirule.impl.UniRuleIdBuilder;
import org.uniprot.core.unirule.impl.UniRuleIdBuilderTest;
import org.uniprot.core.unirule.impl.UniRuleIdImpl;

public class UniRuleIdImplSerializerTest {
    private ObjectMapper objectMapper;
    private UniRuleId entry;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(UniRuleIdImpl.class, new UniRuleIdImplSerializer());
        this.objectMapper.registerModule(simpleModule);
        this.entry = UniRuleIdBuilderTest.createObject();
    }

    @Test
    void testWriteJson() throws JsonProcessingException {
        String json = this.objectMapper.writeValueAsString(this.entry);
        Assertions.assertNotNull(json);
        Assertions.assertEquals(this.entry.getValue(), json.replaceAll("\"", ""));
    }

    @Test
    void testWriteJsonWithNullValue() throws JsonProcessingException {
        UniRuleIdBuilder builder = new UniRuleIdBuilder(null);
        UniRuleId entry = builder.build();
        String json = this.objectMapper.writeValueAsString(entry);
        Assertions.assertNotNull(json);
        Assertions.assertEquals("null", json.replaceAll("\"", ""));
    }

    @Test
    void testWriteJsonWithEmptyValue() throws JsonProcessingException {
        UniRuleIdBuilder builder = new UniRuleIdBuilder("");
        UniRuleId entry = builder.build();
        String json = this.objectMapper.writeValueAsString(entry);
        Assertions.assertNotNull(json);
        Assertions.assertEquals(entry.getValue(), json.replaceAll("\"", ""));
    }
}
