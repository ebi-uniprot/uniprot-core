package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.rx.RxLineObject;

public class RxLineParserTest {
	@Test
	public void test() {
		String rxLines = "RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;\n";
		UniprotLineParser<RxLineObject> parser = new DefaultUniprotLineParserFactory().createRxLineParser();
		RxLineObject obj = parser.parse(rxLines);
		assertEquals(2, obj.rxs.size());
		verify(obj.rxs.get(0), RxLineObject.DB.PubMed, "15626370");
		verify(obj.rxs.get(1), RxLineObject.DB.DOI, "10.1016/j.toxicon.2004.10.011");

	}
	
	private void verify(RxLineObject.RX obj, RxLineObject.DB db, String value) {
		assertEquals(db, obj.type);
		assertEquals(value, obj.value);
	}
	@Test
	public void test1() {
		String rxLines = "RX   PubMed=12788972; DOI=10.1073/pnas.1130426100;\n";
		UniprotLineParser<RxLineObject> parser = new DefaultUniprotLineParserFactory().createRxLineParser();
		RxLineObject obj = parser.parse(rxLines);
		assertEquals(2, obj.rxs.size());
		verify(obj.rxs.get(0), RxLineObject.DB.PubMed, "12788972");
		verify(obj.rxs.get(1), RxLineObject.DB.DOI, "10.1073/pnas.1130426100");

	}
	@Test
	public void test2() {
		String rxLines = "RX   PubMed=16912294; DOI=10.1128/JVI.00464-06;\n";
		UniprotLineParser<RxLineObject> parser = new DefaultUniprotLineParserFactory().createRxLineParser();
		RxLineObject obj = parser.parse(rxLines);
		assertEquals(2, obj.rxs.size());
		verify(obj.rxs.get(0), RxLineObject.DB.PubMed, "16912294");
		verify(obj.rxs.get(1), RxLineObject.DB.DOI, "10.1128/JVI.00464-06");

	}
	@Test
	public void test3() {
		String rxLines = "RX   PubMed=14577811; DOI=10.1597/1545-1569(2003)040<0632:AMMITS>2.0.CO;2;\n";
		UniprotLineParser<RxLineObject> parser = new DefaultUniprotLineParserFactory().createRxLineParser();
		RxLineObject obj = parser.parse(rxLines);
		assertEquals(2, obj.rxs.size());
		verify(obj.rxs.get(0), RxLineObject.DB.PubMed, "14577811");
		verify(obj.rxs.get(1), RxLineObject.DB.DOI, "10.1597/1545-1569(2003)040<0632:AMMITS>2.0.CO;2");

	}	
	@Test
	public void test4() {
		String rxLines = "RX   PubMed=15060122; DOI=10.1136/jmg 2003.012781;\n";
		UniprotLineParser<RxLineObject> parser = new DefaultUniprotLineParserFactory().createRxLineParser();
		RxLineObject obj = parser.parse(rxLines);
		assertEquals(2, obj.rxs.size());
		verify(obj.rxs.get(0), RxLineObject.DB.PubMed, "15060122");
		verify(obj.rxs.get(1), RxLineObject.DB.DOI, "10.1136/jmg 2003.012781");

	}	
	@Test
	public void test5() {
		String rxLines = "RX   PubMed=5;\n";
		UniprotLineParser<RxLineObject> parser = new DefaultUniprotLineParserFactory().createRxLineParser();
		RxLineObject obj = parser.parse(rxLines);
		assertEquals(1, obj.rxs.size());
		verify(obj.rxs.get(0), RxLineObject.DB.PubMed, "5");
	//	verify(obj.rxs.get(1), RxLineObject.DB.DOI, "10.1136/jmg 2003.012781");

	}	
	@Test
	public void test6() {
		String rxLines = "RX   DOI=10.1002/(SICI)1097-0061(199612)12:15<1549::AID-YEA42>3.3.CO;2-J;\n";
		UniprotLineParser<RxLineObject> parser = new DefaultUniprotLineParserFactory().createRxLineParser();
		RxLineObject obj = parser.parse(rxLines);
		assertEquals(1, obj.rxs.size());
	//	verify(obj.rxs.get(0), RxLineObject.DB.PubMed, "5");
		verify(obj.rxs.get(0), RxLineObject.DB.DOI, "10.1002/(SICI)1097-0061(199612)12:15<1549::AID-YEA42>3.3.CO;2-J");

	}
	@Test
	public void test7() {
		String rxLines = "RX   PubMed=12112860;\n"
				+"RX   DOI=10.1002/1615-9861(200206)2:6<765::AID-PROT765>3.0.CO;2-V;\n"
				;
		UniprotLineParser<RxLineObject> parser = new DefaultUniprotLineParserFactory().createRxLineParser();
		RxLineObject obj = parser.parse(rxLines);
		assertEquals(2, obj.rxs.size());
		verify(obj.rxs.get(0), RxLineObject.DB.PubMed, "12112860");
		verify(obj.rxs.get(1), RxLineObject.DB.DOI, "10.1002/1615-9861(200206)2:6<765::AID-PROT765>3.0.CO;2-V");

	}
}

