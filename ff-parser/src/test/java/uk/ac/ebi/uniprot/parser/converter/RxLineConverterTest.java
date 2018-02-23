package uk.ac.ebi.uniprot.parser.converter;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.parser.impl.rx.RxLineConverter;
import uk.ac.ebi.uniprot.parser.impl.rx.RxLineObject;

public class RxLineConverterTest {
	@Test
	public void test(){
		RxLineObject rxLine = new RxLineObject();
		//"RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;\n";
		RxLineObject.RX  rx = new RxLineObject.RX();
		rx.type =RxLineObject.DB.PubMed;
		rx.value ="15626370";
		rxLine.rxs.add(rx);
		RxLineObject.RX  rx2 = new RxLineObject.RX();
		rx2.type =RxLineObject.DB.DOI;
		rx2.value ="10.1016/j.toxicon.2004.10.011";
		rxLine.rxs.add(rx2);
		RxLineConverter converter = new RxLineConverter();
		CitationXrefs cxrefs = converter.convert(rxLine);
		TestCase.assertTrue(cxrefs.getTyped(CitationXrefType.PUBMED).isPresent());
		TestCase.assertTrue(cxrefs.getTyped(CitationXrefType.DOI).isPresent());
		TestCase.assertFalse(cxrefs.getTyped(CitationXrefType.AGRICOLA).isPresent());
		TestCase.assertEquals("15626370",cxrefs.getTyped(CitationXrefType.PUBMED).get().getId());
		TestCase.assertEquals("10.1016/j.toxicon.2004.10.011", cxrefs.getTyped(CitationXrefType.DOI).get().getId());
		
	}
}
