package org.uniprot.core.literature.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.literature.LiteratureStoreEntry;
import org.uniprot.core.literature.builder.LiteratureStoreEntryBuilder;

/**
 * @author lgonzales
 * @since 2019-12-05
 */
class LiteratureStoreEntryImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        LiteratureStoreEntry obj = new LiteratureStoreEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        LiteratureStoreEntry impl = new LiteratureStoreEntryImpl(null, Collections.emptyList());
        LiteratureStoreEntry obj = LiteratureStoreEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void toStringMethod() {
        assertEquals(
                "LiteratureStoreEntryImpl{literatureEntry=LiteratureEntryImpl{pubmedId=100, doiId='doi Id', title='title', authoringGroup=[authoring group], authors=[author name], completeAuthorList=false, publicationDate=PublicationDateImpl{value='21-06-2019'}, journal=JournalImpl{name='journal Name'}, firstPage='first Page', lastPage='last Page', volume='volume', literatureAbstract='literature Abstract', statistics=LiteratureStatisticsImpl{reviewedProteinCount=10, unreviewedProteinCount=20, mappedProteinCount=30}}, literatureMappedReferences=[LiteratureMappedReferenceImpl{uniprotAccession=P12345, source='source value', sourceId='source Id', sourceCategory=[source category], annotation='annotation value'}]}",
                ObjectsForTests.createCompleteLiteratureStoreEntry().toString());
    }
}
