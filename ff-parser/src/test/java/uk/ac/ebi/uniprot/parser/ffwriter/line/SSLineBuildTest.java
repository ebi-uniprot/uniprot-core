package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.impl.SSEvidenceLineBuilder;
import uk.ac.ebi.uniprot.ffwriter.line.impl.SSInternalLineBuilder;
import uk.ac.ebi.uniprot.ffwriter.line.impl.SSLineBuilder;
import uk.ac.ebi.uniprot.ffwriter.line.impl.SSSourceLineBuilder;

public class SSLineBuildTest {
	UniProtFactory factory = UniProtFactory.INSTANCE;
	@Test
	public void testEvidenceLines() {
		String ssLines = "**EV ECO:0000250; -; XXX; 01-JAN-1900.\n"
				+"**EV ECO:0000313; Ensembl:ENSP00000483257; -; 05-JUN-2015.";
		Evidence evidence1 = EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000250");
		List<EvidenceLine> evidenceLines = new ArrayList<>();
		evidenceLines.add(
		factory.createEvidenceLine(evidence1, LocalDate.of(1900, 1, 1), "XXX"));
		Evidence evidence2 = EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000313|Ensembl:ENSP00000483257");
		evidenceLines.add(
				factory.createEvidenceLine(evidence2, LocalDate.of(2015, 6, 5), "-"));
		SSEvidenceLineBuilder builder = new SSEvidenceLineBuilder();
		FFLine ffLine = builder.build(evidenceLines);
		
		String resultString = ffLine.toString();
		//System.out.println(text.getText());
		System.out.println(resultString);
		assertEquals(ssLines, resultString);
	}
	
	@Test
	public void testInternalLines() {
		String ssLines ="**SO UPD; 37856; 05-DEC-2008.\n" + 
				"**ZB LYG, 07-FEB-2006; LIB, 17-MAR-2006; LYG, 04-MAY-2006; ISC, 14-DEC-2006;\n" + 
				"**ZB ALG/LYG, 08-JAN-2007; LYG, 09-JAN-2007; MCB, 09-JAN-2007; RUE, 05-JUN-2007;";
		List<InternalLine> internalLines = new ArrayList<>();
		internalLines.add(
		factory.createInternalLine(InternalLineType.SO, "UPD; 37856; 05-DEC-2008."));
		internalLines.add(
				factory.createInternalLine(InternalLineType.ZB, "LYG, 07-FEB-2006; LIB, 17-MAR-2006; LYG, 04-MAY-2006; ISC, 14-DEC-2006;"));
		internalLines.add(
				factory.createInternalLine(InternalLineType.ZB, "ALG/LYG, 08-JAN-2007; LYG, 09-JAN-2007; MCB, 09-JAN-2007; RUE, 05-JUN-2007;"));
		SSInternalLineBuilder builder = new SSInternalLineBuilder();
		FFLine ffLine = builder.build(internalLines);
		
		String resultString = ffLine.toString();
		//System.out.println(text.getText());
		System.out.println(resultString);
		assertEquals(ssLines, resultString);
		
	}
	
	@Test
	public void testSourceLine() {
		String ssLines =
                "**   #################     SOURCE SECTION     ##################\n"
                +"**   LOCUS       999163         29 aa\n"
                +"**               26-SEP-1995";
		List<SourceLine> sourceLines = new ArrayList<>();
		sourceLines.add(
		factory.createSourceLine("LOCUS       999163         29 aa"));
		sourceLines.add(
				factory.createSourceLine("            26-SEP-1995"));
		SSSourceLineBuilder builder = new SSSourceLineBuilder();
		FFLine ffLine = builder.build(sourceLines);
		
		String resultString = ffLine.toString();
		//System.out.println(text.getText());
		System.out.println(resultString);
		assertEquals(ssLines, resultString);
	}
	@Test
	public void testInterSection1() {
		String ssLines ="**\n"
                +"**   #################    INTERNAL SECTION    ##################\n"
                +"**EV ECO:0000313; ProtImp:UP123; -; 07-NOV-2006.\n"
                +"**EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
                +"**EV ECO:0000256; SAAS:SAAS022644_004_000329; -; 11-FEB-2014.\n"
                +"**DG dg-000-000-614_P;\n"
                +"**ZD YOK, 19-NOV-2004;"
                ;
		Evidence evidence1 = EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000313|ProtImp:UP123");
		List<EvidenceLine> evidenceLines = new ArrayList<>();
		evidenceLines.add(
		factory.createEvidenceLine(evidence1, LocalDate.of(2006, 11, 7), "-"));
		Evidence evidence2 = EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000256|HAMAP-Rule:MF_01417");
		evidenceLines.add(
				factory.createEvidenceLine(evidence2, LocalDate.of(2010, 10, 1), "-"));
		Evidence evidence3 = EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000256|SAAS:SAAS022644_004_000329");
		evidenceLines.add(
				factory.createEvidenceLine(evidence3, LocalDate.of(2014, 2, 11), "-"));
		List<InternalLine> internalLines = new ArrayList<>();
		internalLines.add(
		factory.createInternalLine(InternalLineType.DG, "dg-000-000-614_P;"));
		internalLines.add(
				factory.createInternalLine(InternalLineType.ZD, "YOK, 19-NOV-2004;"));
		InternalSection is = factory.createInternalSection(internalLines, evidenceLines, null);
		SSLineBuilder builder = new SSLineBuilder();
		
		FFLine ffLine = builder.build(is);
		
		String resultString = ffLine.toString();
		//System.out.println(text.getText());
		System.out.println(resultString);
		assertEquals(ssLines, resultString);
	}
	
	@Test
	public void testInterSectionWithSource() {
		String ssLines ="**\n"
                +"**   #################     SOURCE SECTION     ##################\n"
                +"**   LOCUS       999163         29 aa\n"
                +"**               26-SEP-1995\n" 		
                +"**   #################    INTERNAL SECTION    ##################\n"
                +"**EV ECO:0000313; ProtImp:UP123; -; 07-NOV-2006.\n"
                +"**EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
                +"**EV ECO:0000256; SAAS:SAAS022644_004_000329; -; 11-FEB-2014.\n"
                +"**DG dg-000-000-614_P;\n"
                +"**ZD YOK, 19-NOV-2004;"
                ;
		Evidence evidence1 = EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000313|ProtImp:UP123");
		List<EvidenceLine> evidenceLines = new ArrayList<>();
		evidenceLines.add(
		factory.createEvidenceLine(evidence1, LocalDate.of(2006, 11, 7), "-"));
		Evidence evidence2 = EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000256|HAMAP-Rule:MF_01417");
		evidenceLines.add(
				factory.createEvidenceLine(evidence2, LocalDate.of(2010, 10, 1), "-"));
		Evidence evidence3 = EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000256|SAAS:SAAS022644_004_000329");
		evidenceLines.add(
				factory.createEvidenceLine(evidence3, LocalDate.of(2014, 2, 11), "-"));
		List<InternalLine> internalLines = new ArrayList<>();
		internalLines.add(
		factory.createInternalLine(InternalLineType.DG, "dg-000-000-614_P;"));
		internalLines.add(
				factory.createInternalLine(InternalLineType.ZD, "YOK, 19-NOV-2004;"));
		
		List<SourceLine> sourceLines = new ArrayList<>();
		sourceLines.add(
		factory.createSourceLine("LOCUS       999163         29 aa"));
		sourceLines.add(
				factory.createSourceLine("            26-SEP-1995"));
		InternalSection is = factory.createInternalSection(internalLines, evidenceLines, sourceLines);
		SSLineBuilder builder = new SSLineBuilder();
		
		FFLine ffLine = builder.build(is);
		
		String resultString = ffLine.toString();
		//System.out.println(text.getText());
		System.out.println(resultString);
		assertEquals(ssLines, resultString);
	}
}
