package org.uniprot.core.flatfile.parser.converter;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineConverter;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineObject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RgLineConverterTest {
	@Test
	public void test(){
		//"RG   The mouse genome sequencing consortium;\n";
		RgLineObject rgline = new RgLineObject();
		rgline.reference_groups.add("The mouse genome sequencing consortium");
		RgLineConverter converter = new RgLineConverter();
		List<String> ags = converter.convert(rgline);
		assertEquals(1, ags.size());
		assertEquals("The mouse genome sequencing consortium", ags.get(0));
	}
}
