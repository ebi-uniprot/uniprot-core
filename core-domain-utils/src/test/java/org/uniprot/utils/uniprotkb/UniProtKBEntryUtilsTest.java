package org.uniprot.utils.uniprotkb;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.uniprot.core.cv.go.Go;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.GoEvidenceType;
import org.uniprot.core.cv.go.impl.GoBuilder;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

/**
 * Created 30/11/2020
 *
 * @author Edd
 */
class UniProtKBEntryUtilsTest {
    @Test
    void canExtractGOTermsFromEntry() {
        UniProtKBEntry entry = Mockito.mock(UniProtKBEntry.class);
        //      DR   GO; GO:0005737; C:cytoplasm; IEA:UniProtKB-SubCell.
        //     DR   GO; GO:0005524; F:ATP binding; IEA:UniProtKB-UniRule.
        String id1 = "GO:0005737";
        String description1 = "C:cytoplasm";
        String evidence1 = "IBA:UniProtKB-SubCell";
        UniProtKBCrossReference xref1 = createGoXref(id1, description1, evidence1);
        String id2 = "GO:0005524";
        String description2 = "F:ATP binding";
        String evidence2 = "IEA:UniProtKB-UniRule";
        UniProtKBCrossReference xref2 = createGoXref(id2, description2, evidence2);
        when(entry.getUniProtCrossReferencesByType("GO")).thenReturn(asList(xref1, xref2));

        List<Go> goes = UniProtKBEntryUtils.getGOTerms(entry);

        assertThat(
                goes,
                contains(
                        new GoBuilder()
                                .id("GO:0005737")
                                .name("cytoplasm")
                                .aspect(GoAspect.COMPONENT)
                                .goEvidenceType(GoEvidenceType.IBA)
                                .goEvidenceSource("UniProtKB-SubCell")
                                .build(),
                        new GoBuilder()
                                .id("GO:0005524")
                                .name("ATP binding")
                                .aspect(GoAspect.FUNCTION)
                                .goEvidenceType(GoEvidenceType.IEA)
                                .goEvidenceSource("UniProtKB-UniRule")
                                .build()));
    }

    @Test
    void testToGoNotGo() {
        UniProtKBDatabase type = new UniProtKBDatabaseImpl("EMBL");
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder().database(type).id("AE013218").build();
        Go go = UniProtKBEntryUtils.toGo(xref);
        assertNull(go);
    }

    @Test
    void testToGo() {
        String id = "GO:0002161";
        String description = "F:aminoacyl-tRNA editing activity";
        String evidence = "IEA:InterPro";
        UniProtKBCrossReference xref = createGoXref(id, description, evidence);
        Go go = UniProtKBEntryUtils.toGo(xref);
        assertNotNull(go);
        assertEquals(id, go.getId());
        assertEquals("aminoacyl-tRNA editing activity", go.getName());
        assertEquals(GoAspect.FUNCTION, go.getAspect());
        assertEquals(GoEvidenceType.IEA, go.getGoEvidenceType());
        assertEquals("InterPro", go.getGoEvidenceSource());
    }

    private UniProtKBCrossReference createGoXref(String id, String description, String evidence) {
        UniProtKBDatabase type = new UniProtKBDatabaseImpl("GO");
        return new UniProtCrossReferenceBuilder()
                .database(type)
                .id(id)
                .propertiesAdd("GoTerm", description)
                .propertiesAdd("GoEvidenceType", evidence)
                .build();
    }
}
