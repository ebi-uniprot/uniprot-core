package org.uniprot.core.flatfile.parser.converter;

import junit.framework.TestCase;
import org.junit.Test;
import org.uniprot.core.flatfile.parser.impl.rt.RtLineConverter;
import org.uniprot.core.flatfile.parser.impl.rt.RtLineObject;

public class RtLineConverterTest {
	@Test
	public void test(){
		RtLineObject rt =new RtLineObject();
		rt.title ="A novel adapter protein employs a phosphotyrosine binding domain";
		RtLineConverter converter = new RtLineConverter();
		
		String title = converter.convert(rt);
		TestCase.assertEquals("A novel adapter protein employs a phosphotyrosine binding domain", title);
		
	}
}
