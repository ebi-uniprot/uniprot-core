package org.uniprot.core.flatfile.parser.converter;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.builder.BookBuilder;
import org.uniprot.core.flatfile.parser.impl.rx.RxLineConverter;
import org.uniprot.core.flatfile.parser.impl.rx.RxLineObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        DBCrossReference<CitationXrefType> wrongXref = new DBCrossReferenceBuilder<CitationXrefType>().build();
        DBCrossReference<CitationXrefType> pubmedXref = citation
                .getCitationXrefsByType(CitationXrefType.PUBMED).orElse(wrongXref);
        assertNotEquals(pubmedXref, wrongXref);
        DBCrossReference<CitationXrefType> doiXref = citation
                .getCitationXrefsByType(CitationXrefType.DOI).orElse(wrongXref);
        assertNotEquals(doiXref, wrongXref);
        DBCrossReference<CitationXrefType> agricolaXref = citation
                .getCitationXrefsByType(CitationXrefType.AGRICOLA).orElse(wrongXref);
        assertEquals(agricolaXref, wrongXref);

        TestCase.assertEquals("15626370", pubmedXref.getId());
        TestCase.assertEquals("10.1016/j.toxicon.2004.10.011", doiXref.getId());
    }
}