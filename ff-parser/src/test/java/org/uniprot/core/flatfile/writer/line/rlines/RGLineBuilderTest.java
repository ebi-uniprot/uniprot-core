package org.uniprot.core.flatfile.writer.line.rlines;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.rg.RGLineBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RGLineBuilderTest {
	private final RGLineBuilder builder = new RGLineBuilder();
	@Test
	public void test1(){
		List<String> ags = build();
		List<String> lines = builder.buildLine(ags, true, true);
		for(String line:lines){
			System.out.println(line);
		}
		assertEquals(3, lines.size());
		String expected ="RG   Institute for Genomic Research;";
		assertEquals(expected, lines.get(1));
	}
	@Test
	public void test2(){
		List<String> ags = build();
		List<String> lines = builder.buildLine(ags, false, true);
		for(String line:lines){
			System.out.println(line);
		}
		assertEquals(1, lines.size());
		String expected ="European Union Chromosome 3 Arabidopsis Sequencing Consortium; Institute for Genomic Research; Kazusa DNA Research Institute;";
		assertEquals(expected, lines.get(0));
	}
	private List<String> build(){
		//European Union Chromosome 3 Arabidopsis Sequencing Consortium; Institute for Genomic Research; Kazusa DNA Research Institute
	//	RG   European Union Chromosome 3 Arabidopsis Sequencing Consortium;
	//	RG   Institute for Genomic Research;
	//	RG   Kazusa DNA Research Institute;
		List<String> ags = new ArrayList<>();
		ags.add("European Union Chromosome 3 Arabidopsis Sequencing Consortium");
		ags.add("Institute for Genomic Research");
		ags.add("Kazusa DNA Research Institute");
		return ags;
	}
}
