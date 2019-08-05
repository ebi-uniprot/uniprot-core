package uk.ac.ebi.uniprot.flatfile.parser.converter;

import org.junit.Test;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.UniProtId;

import uk.ac.ebi.uniprot.flatfile.parser.impl.id.IdLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.id.IdLineObject;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class IdLineConverterTest {
	private IdLineConverter converter = new IdLineConverter();
	@Test
	public void testConverter() throws Exception{
		//ID   001R_FRG3G              Reviewed;         256 AA
		IdLineObject idObj = new IdLineObject();
		idObj.setReviewed(true);
		idObj.setEntryName("001R_FRG3G");
		idObj.setSequenceLength (256);
		 Map.Entry<UniProtId, UniProtEntryType> uniObj = converter.convert(idObj);	
		
		 assertEquals("001R_FRG3G", uniObj.getKey().getValue() );
		 assertEquals(UniProtEntryType.SWISSPROT, uniObj.getValue());
	
	}
	@Test
	public void testConverter2() throws Exception{
		//ID   001R_FRG3G              Reviewed;         256 AA
		IdLineObject idObj = new IdLineObject();
		idObj.setReviewed(false);
		idObj.setEntryName("001R_FRG3G");
		idObj.setSequenceLength (256);
		
		 Map.Entry<UniProtId, UniProtEntryType> uniObj = converter.convert(idObj);	
			
		 assertEquals("001R_FRG3G", uniObj.getKey().getValue() );
		 assertEquals(UniProtEntryType.TREMBL, uniObj.getValue());
	}
}
