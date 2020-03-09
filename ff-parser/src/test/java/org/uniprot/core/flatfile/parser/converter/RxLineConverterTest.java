package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.impl.BookBuilder;
import org.uniprot.core.flatfile.parser.impl.rx.RxLineConverter;
import org.uniprot.core.flatfile.parser.impl.rx.RxLineObject;
import org.uniprot.core.impl.CrossReferenceBuilder;

class RxLineConverterTest {
    @Test
    void test() {
        RxLineObject rxLine = new RxLineObject();
        // "RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;\n";
        RxLineObject.RX rx = new RxLineObject.RX();
        rx.type = RxLineObject.DB.PubMed;
        rx.value = "15626370";
        rxLine.rxs.add(rx);
        RxLineObject.RX rx2 = new RxLineObject.RX();
        rx2.type = RxLineObject.DB.DOI;
        rx2.value = "10.1016/j.toxicon.2004.10.011";
        rxLine.rxs.add(rx2);
        RxLineConverter converter = new RxLineConverter();
        List<CrossReference<CitationDatabase>> cxrefs = converter.convert(rxLine);
        Citation citation = new BookBuilder().citationCrossReferencesSet(cxrefs).build();

        CrossReference<CitationDatabase> wrongXref =
                new CrossReferenceBuilder<CitationDatabase>().build();
        CrossReference<CitationDatabase> pubmedXref =
                citation.getCitationCrossReferenceByType(CitationDatabase.PUBMED).orElse(wrongXref);
        assertNotEquals(pubmedXref, wrongXref);
        CrossReference<CitationDatabase> doiXref =
                citation.getCitationCrossReferenceByType(CitationDatabase.DOI).orElse(wrongXref);
        assertNotEquals(doiXref, wrongXref);
        CrossReference<CitationDatabase> agricolaXref =
                citation.getCitationCrossReferenceByType(CitationDatabase.AGRICOLA)
                        .orElse(wrongXref);
        assertEquals(agricolaXref, wrongXref);

        assertEquals("15626370", pubmedXref.getId());
        assertEquals("10.1016/j.toxicon.2004.10.011", doiXref.getId());
    }
}
