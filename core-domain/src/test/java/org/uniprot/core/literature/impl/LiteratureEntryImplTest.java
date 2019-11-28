package org.uniprot.core.literature.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.citation.impl.PublicationDateImpl;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.builder.LiteratureEntryBuilder;

class LiteratureEntryImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        LiteratureEntry obj = new LiteratureEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        LiteratureEntry impl =
                new LiteratureEntryImpl(
                        65L,
                        "do",
                        "title",
                        Collections.singletonList("group"),
                        Collections.emptyList(),
                        false,
                        new PublicationDateImpl("date"),
                        null,
                        "fp",
                        "lp",
                        "vol",
                        "lia",
                        Collections.emptyList(),
                        null);
        LiteratureEntry obj = new LiteratureEntryBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void toStringMethod() {
        assertEquals(
                "LiteratureEntryImpl{pubmedId='100', doiId='doi Id', title='title', authoringGroup=[authoring group], authors=[author name], completeAuthorList=false, publicationDate=PublicationDateImpl{value='21-06-2019'}, journal=JournalImpl{name='journal Name'}, firstPage='first Page', lastPage='last Page', volume='volume', literatureAbstract='literature Abstract', statistics=LiteratureStatisticsImpl{reviewedProteinCount=10, unreviewedProteinCount=20, mappedProteinCount=30}, literatureMappedReferences=[LiteratureMappedReferenceImpl{uniprotAccession=P12345, source='source value', sourceId='source Id', sourceCategory=[source category], annotation='annotation value'}]}",
                ObjectsForTests.createCompleteLiteratureEntryWithAdd().toString());
    }
}
