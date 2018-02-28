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
import uk.ac.ebi.uniprot.ffwriter.line.FlatfileWriter;
import uk.ac.ebi.uniprot.ffwriter.line.impl.UniProtFlatfileWriter;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
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
