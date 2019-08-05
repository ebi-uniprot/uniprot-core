package org.uniprot.core.flatfile.antlr;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.dt.DtLineObject;

import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DtLineParserTest {
	@Test
	public void test() {
		 String dtLines = "DT   28-JUN-2011, integrated into UniProtKB/Swiss-Prot.\n"
				 +"DT   19-JUL-2004, sequence version 1.\n"
				 +"DT   18-APR-2012, entry version 24.\n"

                 ;
		 UniprotLineParser<DtLineObject> parser = new DefaultUniprotLineParserFactory().createDtLineParser();
		 DtLineObject obj = parser.parse(dtLines);
		 assertTrue(obj.isSiwssprot);
		 assertEquals(1, obj.seq_version);
		 assertEquals(24, obj.entry_version);
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-yyyy");
		 
		 assertEquals("28-Jun-2011", formatter.format(obj.integration_date) );
		 assertEquals("19-Jul-2004", formatter.format(obj.seq_date) );
		 assertEquals("18-Apr-2012", formatter.format(obj.entry_date) );

	}
	
}
