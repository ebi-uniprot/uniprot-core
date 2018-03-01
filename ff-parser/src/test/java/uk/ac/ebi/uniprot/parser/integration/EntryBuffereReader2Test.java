package uk.ac.ebi.uniprot.parser.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.impl.EntryBufferReader2;

public class EntryBuffereReader2Test {
	@Test
	public void testSimple() { 
		String filename= "src/test/resources/entryIT/entryReader2.dat";
		try (EntryBufferReader2 reader = new EntryBufferReader2(filename);) {
		
		
		verify("test1", reader, false);
		verify("test2", reader, false);
		verify("test3", reader, false);
		verify("test4", reader,false);
		verify(null, reader, false);
		}catch(Exception e) {
			fail("Test failed");
		}
	}
	@Test
	public void test2Entries() { 
		String filename= "src/test/resources/entryIT/A8EZU1_D6RDV7.dat";
		try (EntryBufferReader2 reader = new EntryBufferReader2(filename);) {
		String entry1 = readFile("src/test/resources/entryIT/A8EZU1.dat");	
		verify(entry1, reader, true);
		String entry2 = readFile("src/test/resources/entryIT/D6RDV7.dat");	
		verify(entry2, reader, true);
		verify(null, reader, true);
		}catch(Exception e) {
			fail("Test failed");
		}
	}

	
	private void verify(String data, EntryBufferReader2 reader, boolean hasEnd) throws IOException {
		if(data ==null) {
			assertNull(reader.next());
			return;
		}
		String expected = data;
		if(!hasEnd)
			expected =data +"\n//\n";
		String entry = reader.next();
		assertEquals(expected, entry);
	}
	private String readFile(String file)  throws IOException {
		return new String(Files.readAllBytes(Paths.get(file)));
	}
}
