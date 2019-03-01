package uk.ac.ebi.uniprot.flatfile.parser.converter;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rx.RxLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.rx.RxLineObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class RxLineConverterTest {
    @Test
    void test() {
        RxLineObject rxLine = new RxLineObject();
        //"RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;\n";
        RxLineObject.RX rx = new RxLineObject.RX();
        rx.type = RxLineObject.DB.PubMed;
        rx.value = "15626370";
        rxLine.rxs.add(rx);
        RxLineObject.RX rx2 = new RxLineObject.RX();
        rx2.type = RxLineObject.DB.DOI;
        rx2.value = "10.1016/j.toxicon.2004.10.011";
        rxLine.rxs.add(rx2);
        RxLineConverter converter = new RxLineConverter();
        List<DBCrossReference<CitationXrefType>> cxrefs = converter.convert(rxLine);
        Citation citation = new BookBuilder().citationXrefs(cxrefs).build();

        DBCrossReference<CitationXrefType> pubmedXref = citation
                .getCitationXrefsByType(CitationXrefType.PUBMED).orElse(null);
        assertNotNull(pubmedXref);
        DBCrossReference<CitationXrefType> doiXref = citation
                .getCitationXrefsByType(CitationXrefType.DOI).orElse(null);
        assertNotNull(doiXref);
        DBCrossReference<CitationXrefType> agricolaXref = citation
                .getCitationXrefsByType(CitationXrefType.AGRICOLA).orElse(null);
        assertNull(agricolaXref);

        TestCase.assertEquals("15626370", pubmedXref.getId());
        TestCase.assertEquals("10.1016/j.toxicon.2004.10.011", doiXref.getId());
    }
}
