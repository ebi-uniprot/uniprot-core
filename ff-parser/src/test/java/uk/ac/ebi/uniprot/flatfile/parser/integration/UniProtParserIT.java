package uk.ac.ebi.uniprot.flatfile.parser.integration;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.flatfile.parser.UniProtEntryIterator;
import uk.ac.ebi.uniprot.flatfile.parser.UniProtParserHelper;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class UniProtParserIT {
	@Test
	public void testParse() {
		String filename = "/entryIT/Q32K04.dat";
		String entryStr = readEntryFromFile(filename);
		UniProtEntry entry =UniProtParserHelper.parse(entryStr);
		assertNotNull(entry);
		assertEquals("Q32K04", entry.getPrimaryAccession().getValue());
	}
	@Test
	public void testParseWithIgnore() {
		String filename = "/entryIT/A0A176EY13.txl";
		String entryStr = readEntryFromFile(filename);
		UniProtEntry entry =UniProtParserHelper.parse(entryStr);
		assertNotNull(entry);
		assertEquals("A0A176EY13", entry.getPrimaryAccession().getValue());
	}
	
	@Test
	public void testParseFile() {
		String filename = "src/test/resources/entryIT/A8EZU1_D6RDV7.dat";
		UniProtEntryIterator iterator = UniProtParserHelper.parseFile(filename, "", "", "", "");
		assertTrue(iterator.hasNext());
		UniProtEntry entry = iterator.next();
		assertNotNull(entry);
		Set<String> accs =new TreeSet<>( Arrays.asList(new String[] {"A8EZU1", "D6RDV7"}));
		Set<String > expected = new TreeSet<>();
		expected.add( entry.getPrimaryAccession().getValue());
	
		assertTrue(iterator.hasNext());
		 entry = iterator.next();
		assertNotNull(entry);
		expected.add( entry.getPrimaryAccession().getValue());
		assertEquals(accs, expected);
		assertFalse(iterator.hasNext());
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
