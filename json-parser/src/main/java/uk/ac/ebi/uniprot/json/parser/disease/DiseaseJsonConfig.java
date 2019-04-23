package uk.ac.ebi.uniprot.json.parser.disease;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.ac.ebi.uniprot.cv.disease.Disease;
import uk.ac.ebi.uniprot.cv.disease.impl.DiseaseImpl;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordImpl;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.DatabaseType;
import uk.ac.ebi.uniprot.domain.Value;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.citation.impl.*;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.DefaultDatabaseType;
import uk.ac.ebi.uniprot.domain.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.proteome.*;
import uk.ac.ebi.uniprot.domain.proteome.impl.*;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyImpl;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;
import uk.ac.ebi.uniprot.json.parser.CustomAnnotationIntrospector;
import uk.ac.ebi.uniprot.json.parser.JsonConfig;
import uk.ac.ebi.uniprot.json.parser.SimpleAnnotationIntrospector;
import uk.ac.ebi.uniprot.json.parser.deserializer.LocalDateDeserializer;
import uk.ac.ebi.uniprot.json.parser.serializer.*;
import uk.ac.ebi.uniprot.json.parser.uniprot.serializer.UniProtAccessionSerializer;

import java.time.LocalDate;

public class DiseaseJsonConfig implements JsonConfig {
    private static DiseaseJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper prettyMapper;

    private DiseaseJsonConfig() {
        this.objectMapper = initObjectMapper();
        this.prettyMapper = initPrettyObjectMapper();
    }

    public static synchronized DiseaseJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DiseaseJsonConfig();
        }
        return INSTANCE;
    }

    @Override
    public ObjectMapper getPrettyObjectMapper() {
        return this.prettyMapper;
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }


    private ObjectMapper initObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        objMapper.setAnnotationIntrospector(new CustomAnnotationIntrospector());

        SimpleModule mod = new SimpleModule();
        mod.addAbstractTypeMapping(Disease.class, DiseaseImpl.class);
        mod.addAbstractTypeMapping(Keyword.class, KeywordImpl.class);

        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initPrettyObjectMapper() {
        ObjectMapper prettyObjMapper = new ObjectMapper();
        prettyObjMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        prettyObjMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        prettyObjMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        prettyObjMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        prettyObjMapper.setAnnotationIntrospector(new SimpleAnnotationIntrospector());
        return prettyObjMapper;
    }



}
