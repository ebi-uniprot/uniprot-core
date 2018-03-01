package uk.ac.ebi.uniprot.parser.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Test;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.ffwriter.FlatfileWriter;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.UniProtFlatfileWriter;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObjectConverter;

public class FlatfileRoundTripTest {
	@Test
	public void testA0A0A0MSM0() {
		String filename = "/entryIT/A0A0A0MSM0.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr, true);
	}

	@Test
	public void testD6RDV7() {
		String filename = "/entryIT/D6RDV7.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr, true);
	}

	@Test
	public void testQ15758() {
		String filename = "/entryIT/Q15758.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr, true);
	}
	
	@Test
	public void testQ15758Txl() {
		String filename = "/entryIT/Q15758.txl";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr, false);
	}

	@Test
	public void testQ3SYC2() {
		String filename = "/entryIT/Q3SYC2.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr, true);
	}

	@Test
	public void testQ3SYC2Txl() {
		String filename = "/entryIT/Q3SYC2.txl";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr, false);
	}

	@Test
	public void testQ63HN8() {
		String filename = "/entryIT/Q63HN8.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr, true);
	}

	@Test
	public void testQ9NYP9() {
		String filename = "/entryIT/Q9NYP9.dat";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, true);
	}

	
	@Test
	public void testA0A176EY13Txl() {
		
		String filename = "/entryIT/A0A176EY13.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}

	@Test
	public void testA0A1Q9NR16Txl() {
		
		String filename = "/entryIT/A0A1Q9NR16.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}

	@Test
	public void testA0A1Z5JZG6Txl() {
		
		String filename = "/entryIT/A0A1Z5JZG6.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	@Test
	public void testA0A2D8TK40Txl() {
		
		String filename = "/entryIT/A0A2D8TK40.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	@Test
	public void testA0A2E5TRM0Txl() {
		
		String filename = "/entryIT/A0A2E5TRM0.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	@Test
	public void testA9N0W4Txl() {
		
		String filename = "/entryIT/A9N0W4.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	
	@Test
	public void testB3E7G1Txl() {
		
		String filename = "/entryIT/B3E7G1.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	@Test
	public void testH0ZNI7Txl() {
		
		String filename = "/entryIT/H0ZNI7.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	@Test
	public void testP55423Txl() {
		
		String filename = "/entryIT/P55423.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	@Test
	public void testQ1MS15Txl() {
		
		String filename = "/entryIT/Q1MS15.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	
	@Test
	public void testQ66GB9Txl() {
		
		String filename = "/entryIT/Q66GB9.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	
	
	@Test
	public void testS7U3X4Txl() {
		
		String filename = "/entryIT/S7U3X4.txl";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, false);
	}
	
	@Test
	public void testA0A0E2CV74Dat() {
		
		String filename = "/entryIT/A0A0E2CV74.dat";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, true);
	}
	@Test
	public void testA0A196N885Dat() {
		
		String filename = "/entryIT/A0A196N885.dat";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, true);
	}
	@Test
	public void testA8EZU1Dat() {
		
		String filename = "/entryIT/A8EZU1.dat";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, true);
	}
	@Test
	public void testQ32K04Dat() {
		
		String filename = "/entryIT/Q32K04.dat";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, true);
	}
	
	
	@Test
	public void testQ8DPW5Dat() {
		
		String filename = "/entryIT/Q8DPW5.dat";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr, true);
	}
	
	
	
	private void testEntry(String entryToParse, boolean ispublic) {
		UniprotLineParser<EntryObject> entryParser = new DefaultUniprotLineParserFactory().createEntryParser();
		EntryObject parse = entryParser.parse(entryToParse);
		assertNotNull(parse);

		EntryObjectConverter entryObjectConverter = new EntryObjectConverter();
		UniProtEntry converted = entryObjectConverter.convert(parse);
		FlatfileWriter<UniProtEntry> writer = new UniProtFlatfileWriter();
		
		assertNotNull(converted);
		String convertedEntryStr =writer.write(converted, ispublic);
		assertEquals(entryToParse, convertedEntryStr +"\n");

	}
	
	
	private String readEntryFromFile(String filename) {
		URL url = getClass().getResource(filename);
		CharSource charSource = Resources.asCharSource(url, Charset.defaultCharset());
		try {
			return charSource.read();
		} catch (IOException e) {
			System.out.println("io exceptions.");
			return "";
		}
	}
}
