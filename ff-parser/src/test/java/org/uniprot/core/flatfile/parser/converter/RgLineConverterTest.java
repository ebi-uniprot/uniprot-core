package org.uniprot.core.flatfile.parser.converter;

import junit.framework.TestCase;
import org.junit.Test;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineConverter;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineObject;

import java.util.List;

public class RgLineConverterTest {
	@Test
	public void test(){
		//"RG   The mouse genome sequencing consortium;\n";
		RgLineObject rgline = new RgLineObject();
		rgline.reference_groups.add("The mouse genome sequencing consortium");
		RgLineConverter converter = new RgLineConverter();
		List<String> ags = converter.convert(rgline);
		TestCase.assertEquals(1, ags.size());
		TestCase.assertEquals("The mouse genome sequencing consortium", 
				ags.get(0));
		
	}
}
