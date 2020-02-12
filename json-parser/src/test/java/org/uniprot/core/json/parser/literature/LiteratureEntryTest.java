package org.uniprot.core.json.parser.literature;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.github.bohnman.squiggly.context.provider.SquigglyContextProvider;
import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.citation.LiteratureTest;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.builder.LiteratureEntryBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.bohnman.squiggly.context.provider.SimpleSquigglyContextProvider;
import com.github.bohnman.squiggly.filter.SquigglyPropertyFilter;
import com.github.bohnman.squiggly.parser.SquigglyParser;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.util.EnumDisplay;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/** @author lgonzales */
class LiteratureEntryTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureEntryBuilder builder = new LiteratureEntryBuilder();

        LiteratureEntry literatureEntry = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureEntry);
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureEntry literatureEntry = getCompleteLiteratureEntry();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureEntry);
        ValidateJson.verifyEmptyFields(literatureEntry);
    }

    static LiteratureEntry getCompleteLiteratureEntry() {
        return new LiteratureEntryBuilder()
                .citation(LiteratureTest.getCompleteLiterature())
                .statistics(LiteratureStatisticsTest.getCompleteLiteratureStatistics())
                .build();
    }

}
