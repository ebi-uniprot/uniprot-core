package org.uniprot.utils.uniprotkb;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.impl.GoTermBuilder;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;

/**
 * Created 30/11/2020
 *
 * @author Edd
 */
class UniProtKBEntryUtilsTest {
    @Test
    void canExtractGOTermsFromEntry() {
        UniProtKBEntry entry = Mockito.mock(UniProtKBEntry.class);

        when(entry.getUniProtCrossReferencesByType("GO"))
                .thenReturn(
                        asList(
                                new UniProtCrossReferenceBuilder().id("GO:0000001").build(),
                                new UniProtCrossReferenceBuilder().id("GO:0000002").build()));

        List<GoTerm> goTerms = UniProtKBEntryUtils.getGOTerms(entry);

        assertThat(
                goTerms,
                contains(
                        new GoTermBuilder()
                                .id("GO:0000001")
                                .name("mitochondrion inheritance")
                                .build(),
                        new GoTermBuilder()
                                .id("GO:0000002")
                                .name("mitochondrial genome maintenance")
                                .build()));
    }
}
