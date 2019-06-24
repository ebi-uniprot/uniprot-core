package uk.ac.ebi.uniprot.json.parser.literature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.domain.citation.Journal;
import uk.ac.ebi.uniprot.domain.citation.PublicationDate;
import uk.ac.ebi.uniprot.domain.citation.impl.AuthorImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.JournalImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.PublicationDateImpl;
import uk.ac.ebi.uniprot.domain.literature.LiteratureEntry;
import uk.ac.ebi.uniprot.domain.literature.LiteratureStatistics;
import uk.ac.ebi.uniprot.domain.literature.impl.LiteratureEntryImpl;
import uk.ac.ebi.uniprot.domain.literature.impl.LiteratureStatisticsImpl;
import uk.ac.ebi.uniprot.json.parser.JsonConfig;
import uk.ac.ebi.uniprot.json.parser.serializer.AuthorSerializer;
import uk.ac.ebi.uniprot.json.parser.serializer.JournalSerializer;
import uk.ac.ebi.uniprot.json.parser.serializer.PublicationDateSerializer;

public class LiteratureJsonConfig extends JsonConfig {
    private static LiteratureJsonConfig INSTANCE;

    private final ObjectMapper objectMapper;
    private final ObjectMapper simpleMapper; // mapper without unwanted fields like type, lighter version

    private LiteratureJsonConfig() {
        this.objectMapper = initFullObjectMapper();
        this.simpleMapper = initSimpleObjectMapper();
    }

    public static synchronized LiteratureJsonConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LiteratureJsonConfig();
        }
        return INSTANCE;
    }

    @Override
    public ObjectMapper getSimpleObjectMapper() {
        return this.simpleMapper;
    }

    @Override
    public ObjectMapper getFullObjectMapper() {
        return this.objectMapper;
    }


    private ObjectMapper initFullObjectMapper() {
        ObjectMapper objMapper = getDefaultFullObjectMapper();

        // customise the default mapper
        SimpleModule mod = new SimpleModule();
        mod.addAbstractTypeMapping(LiteratureEntry.class, LiteratureEntryImpl.class);
        mod.addAbstractTypeMapping(LiteratureStatistics.class, LiteratureStatisticsImpl.class);
        mod.addAbstractTypeMapping(PublicationDate.class, PublicationDateImpl.class);
        mod.addAbstractTypeMapping(Author.class, AuthorImpl.class);
        mod.addAbstractTypeMapping(Journal.class, JournalImpl.class);
        objMapper.registerModule(mod);

        return objMapper;
    }

    private ObjectMapper initSimpleObjectMapper() {
        ObjectMapper prettyObjMapper = getDefaultSimpleObjectMapper();

        SimpleModule simpleMod = new SimpleModule();
        simpleMod.addSerializer(AuthorImpl.class, new AuthorSerializer());
        simpleMod.addSerializer(PublicationDateImpl.class, new PublicationDateSerializer());
        simpleMod.addSerializer(JournalImpl.class, new JournalSerializer());

        prettyObjMapper.registerModule(simpleMod);
        return prettyObjMapper;
    }
}

