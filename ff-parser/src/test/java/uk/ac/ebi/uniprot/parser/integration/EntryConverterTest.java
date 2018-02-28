package uk.ac.ebi.uniprot.parser.integration;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObjectConverter;

public class EntryConverterTest {
	@Test
	public void testA0A0A0MSM0() {
		String filename = "/entryIT/A0A0A0MSM0.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr);
	}

	@Test
	public void testD6RDV7() {
		String filename = "/entryIT/D6RDV7.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr);
	}

	@Test
	public void testQ15758() {
		String filename = "/entryIT/Q15758.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr);
	}

	@Test
	public void testQ3SYC2() {
		String filename = "/entryIT/Q3SYC2.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr);
	}

	@Test
	public void testQ63HN8() {
		String filename = "/entryIT/Q63HN8.dat";
		String entryStr = readEntryFromFile(filename);
		System.out.println(entryStr);
		testEntry(entryStr);
	}

	@Test
	public void testQ9NYP9() {
		String filename = "/entryIT/Q9NYP9.dat";
		String entryStr = readEntryFromFile(filename);
		// System.out.println(entryStr);
		testEntry(entryStr);
	}

	private void testEntry(String entryToParse) {
		UniprotLineParser<EntryObject> entryParser = new DefaultUniprotLineParserFactory().createEntryParser();
		EntryObject parse = entryParser.parse(entryToParse);
		assertNotNull(parse);

		EntryObjectConverter entryObjectConverter = new EntryObjectConverter();
		UniProtEntry convert = entryObjectConverter.convert(parse);
		assertNotNull(convert);

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
