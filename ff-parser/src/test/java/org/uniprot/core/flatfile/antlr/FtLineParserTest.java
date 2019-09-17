package org.uniprot.core.flatfile.antlr;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineConverter;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject.FTType;
import org.uniprot.core.uniprot.feature.Feature;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FtLineParserTest {
	@Test
	void testChain() {
		 String ftLines = "FT   CHAIN        20    873       104 kDa microneme/rhoptry antigen.\n"
				 +"FT                                /FTId=PRO_0000232680.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.CHAIN, "20", "873", "104 kDa microneme/rhoptry antigen", "PRO_0000232680");		 
	}

	@Test
	void testHelix() {
		 String ftLines = "FT   HELIX      33     83\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.HELIX, "33", "83", null, null);
	}

	@Test
	void testMutagenMultiLineText() {
		 String ftLines = "FT   MUTAGEN     119    119       C->R,E,A: Loss of cADPr hydrolase and\n"
				 +"FT                                ADP-ribosyl cyclase activity.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.MUTAGEN, "119", "119", "C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity", null);
	}
	
	@Test
	void testVarSeqMultiLineText() {
		 String ftLines = "FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL\n"
				 +"FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in\n"
				 +"FT                                isoform 2).\n"
				 +"FT                                /FTId=VSP_004370.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.VAR_SEQ, "33", "83",
				 "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)", "VSP_004370");
	}

	@Test
	void testVarSeqWraper1() {
		 String ftLines = "FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL\n"
				 +"FT                                -> SECLTYGKQPLTSFHPFTSQMPP (in\n"
				 +"FT                                isoform 2).\n"
				 +"FT                                /FTId=VSP_004370.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.VAR_SEQ, "33", "83",
				 "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL-> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)", "VSP_004370");
	}

	@Test
	void testVarSeq() {
		String ftLines ="FT   VAR_SEQ       1     31       MLTCNKAGSRMVVDAANSNGPFQPVVLLHIR -> MPNKNK\n" +
				"FT                                KEKESPKAGKSGKSSKEGQDTVESEQISVRKNSLVAVPSTV\n" +
				"FT                                SAKIKVPVSQPIVKKDKRQNSSRFSASNNRELQKLPSLK\n" +
				"FT                                (in isoform 4).\n" +
				"FT                                {ECO:0000303|PubMed:14702039}.\n" +
				"FT                                /FTId=VSP_043645.\n";
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 System.out.println(obj.fts.get(0).ft_text);
		 String desc ="MLTCNKAGSRMVVDAANSNGPFQPVVLLHIR -> MPNKNKKEKESPKAGKSGKSSKEGQDTVESEQISVRKNSLVAVPSTV"
				 + "SAKIKVPVSQPIVKKDKRQNSSRFSASNNRELQKLPSLK(in isoform 4)";
		 System.out.println(desc);
		// verify(obj.fts.get(0), FTType.VAR_SEQ, "1", "31",  desc, "VSP_043645");
		 FtLineConverter converter = new FtLineConverter();
		 List<Feature> features = converter.convert(obj);
		 assertEquals(1, features.size());
		 Feature  feature = features.get(0);
		 assertEquals("MLTCNKAGSRMVVDAANSNGPFQPVVLLHIR", feature.getAlternativeSequence().getOriginalSequence());

		 assertEquals("MPNKNKKEKESPKAGKSGKSSKEGQDTVESEQISVRKNSLVAVPSTVSAKIKVPVSQPIVKKDKRQNSSRFSASNNRELQKLPSLK", feature.getAlternativeSequence().getAlternativeSequences().get(0));

	}
	
	@Test
	void testVarSeq2() {
		String ftLines ="FT   VAR_SEQ       1      1       M -> MTDRQTDTAPSPSAHLLAGGLPTVDAAASREEPKPA\n" +
				"FT                                SPSRRGSASRAGPGRASETM (in isoform L-VEGF-\n" +
				"FT                                1). {ECO:0000305}.\n" +
				"FT                                /FTId=VSP_038746.\n";
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 System.out.println(obj.fts.get(0).ft_text);
		 String desc ="M -> MTDRQTDTAPSPSAHLLAGGLPTVDAAASREEPKPA"
				 + "SPSRRGSASRAGPGRASETM (in isoform L-VEGF-1). {ECO:0000305}";
		 System.out.println(desc);
		// verify(obj.fts.get(0), FTType.VAR_SEQ, "1", "31",  desc, "VSP_043645");
		 FtLineConverter converter = new FtLineConverter();
		 List<Feature> features = converter.convert(obj);
		 assertEquals(1, features.size());
		 Feature  feature = features.get(0);
		 assertEquals("M", feature.getAlternativeSequence().getOriginalSequence());

		 assertEquals("MTDRQTDTAPSPSAHLLAGGLPTVDAAASREEPKPASPSRRGSASRAGPGRASETM", feature.getAlternativeSequence().getAlternativeSequences().get(0));
		 assertEquals("in isoform L-VEGF-1", feature.getDescription().getValue());

	}
	
	@Test
	void testMultiFt() {
		 String ftLines = "FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL\n"
				 +"FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in\n"
				 +"FT                                isoform 2).\n"
				 +"FT                                /FTId=VSP_004370.\n"
				 +"FT   MUTAGEN     119    119       C->R,E,A: Loss of cADPr hydrolase and\n"
				 +"FT                                ADP-ribosyl cyclase activity.\n"
				 +"FT   HELIX        33     83\n"
				 +"FT   TURN          3     33\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(4, obj.fts.size());
		 verify(obj.fts.get(0), FTType.VAR_SEQ, "33", "83",
				 "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)", "VSP_004370");
		 verify(obj.fts.get(1), FTType.MUTAGEN, "119", "119",
				 "C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity", null);
		 verify(obj.fts.get(2), FTType.HELIX, "33", "83",  null, null);
		 verify(obj.fts.get(3), FTType.TURN, "3", "33",  null, null);

	}
	
	@Test
	void testWithPotential() {
		 String ftLines = "FT   CARBOHYD     61     61       N-linked (GlcNAc...); by host\n"
				 +"FT                                (Potential).\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.CARBOHYD, "61", "61",
				 "N-linked (GlcNAc...); by host (Potential)", null);
	}

	@Test
	void testUnknown() {
		 String ftLines = "FT   TRANSIT       1      ?       Mitochondrion (Potential).\n"
				 +"FT   CHAIN         ?    610       Protein ABC1 homolog, mitochondrial.\n"
				 +"FT                                /FTId=PRO_0000000261.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(2, obj.fts.size());
		 verify(obj.fts.get(0), FTType.TRANSIT, "1", "?",
				 "Mitochondrion (Potential)", null);

		 verify(obj.fts.get(1), FTType.CHAIN, "?", "610",
				 "Protein ABC1 homolog, mitochondrial", "PRO_0000000261");
	}

	@Test
	void testWithEvidence() {
		 String ftLines = "FT   METAL       186    186       Calcium; via carbonyl oxygen. {ECO:0000006|PubMed:20858735,\n"
				 +"FT                                ECO:0000006|PubMed:23640942}.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.METAL, "186", "186",
				 "Calcium; via carbonyl oxygen", null);
		 verifyEvidences(obj, obj.fts.get(0),  Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006|PubMed:23640942"}) );
	}

	@Test
	void testWithEvidence2() {
		 String ftLines = "FT   HELIX      33     83       {ECO:0000313|EMBL:BAG16761.1}.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.HELIX, "33", "83",
				 "", null);
		 verifyEvidences(obj, obj.fts.get(0),  Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}) );
	}
	
	@Test
	void testWithEvidence3() {
		 String ftLines = "FT   REGION      237    240       Sulfate 1 binding.\n"
				 +"FT   REGION      275    277       Phosphate 2 binding. {ECO:0000006|PubMed:20858735, ECO:0000006}.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(2, obj.fts.size());
		 verify(obj.fts.get(0), FTType.REGION, "237", "240",
				 "Sulfate 1 binding", null);
		 verifyEvidences(obj, obj.fts.get(0),  null );
		 verify(obj.fts.get(1), FTType.REGION, "275", "277",
				 "Phosphate 2 binding", null);
		 verifyEvidences(obj, obj.fts.get(1),  Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006"}) );
	}

	@Test
	void testWithEvidence4() {
		 String ftLines = "FT   TRANSMEM     57     77       Helical; (Potential). {ECO:0000257|HAMAP-\n"
				 +"FT                                Rule:MF_03021}.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.TRANSMEM, "57", "77",
				 "Helical; (Potential)", null);
		 verifyEvidences(obj, obj.fts.get(0),  Arrays.asList(new String[] {"ECO:0000257|HAMAP-Rule:MF_03021"}) );

	}
	
	@Test
	void testWithEvidence5() {
		 String ftLines = "FT   TRANSMEM     57     77       Helical; (Potential). {ECO:0000257|\n"
				 +"FT                                HAMAP-Rule:MF_03021}.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.TRANSMEM, "57", "77",
				 "Helical; (Potential)", null);
		 verifyEvidences(obj, obj.fts.get(0),  Arrays.asList(new String[] {"ECO:0000257|HAMAP-Rule:MF_03021"}) );

	}

	@Test
	void testWithEvidence6() {
		 String ftLines = "FT   TRANSMEM     57     77       Helical; (Potential). {ECO:\n"
				 +"FT                                0000257|HAMAP-Rule:MF_03021}.\n"
                 ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.TRANSMEM, "57", "77",
				 "Helical; (Potential)", null);
		 verifyEvidences(obj, obj.fts.get(0),  Arrays.asList(new String[] {"ECO:0000257|HAMAP-Rule:MF_03021"}) );

	}

	@Test
	void testConflictFeature() {
		String ftLine =
				"FT   CONFLICT      1      1       A -> Q (in Ref. 1; BAA37160/BAA37165 and\n" +
				"FT                                2).\n";
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLine);
		 assertEquals(1, obj.fts.size());
		 verify(obj.fts.get(0), FTType.CONFLICT, "1", "1",
				 "A -> Q (in Ref. 1; BAA37160/BAA37165 and 2)", null);
		 verifyEvidences(obj, obj.fts.get(0),  null );
	}

	@Test
	void testConflictFeature2() {
		String ftLine =
				"FT   CONFLICT    149    176       KREICYFQLYPDYIEQNIRSVRFNCYTK -> IERNMLLST\n" +
				"FT                                VS (in Ref. 4; CAA78385). {ECO:0000305}.\n";
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLine);
		 assertEquals(1, obj.fts.size());
		 System.out.println(obj.fts.get(0).ft_text);
		 verify(obj.fts.get(0), FTType.CONFLICT, "149", "176",
				 "KREICYFQLYPDYIEQNIRSVRFNCYTK -> IERNMLLSTVS (in Ref. 4; CAA78385)", null);
		 verifyEvidences(obj, obj.fts.get(0),  Arrays.asList("ECO:0000305") );
	}

	@Test
	void testConflictFeatureWithSlash() {
		String ftLine =
				"FT   CONFLICT    430    432       ALL -> DLV (in Ref. 1; BAA85929/BAA85930/\n" +
				"FT                                BAA85931). {ECO:0000305}.\n";
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLine);
		 assertEquals(1, obj.fts.size());
		 System.out.println(obj.fts.get(0).ft_text);
		 verify(obj.fts.get(0), FTType.CONFLICT, "430", "432",
				 "ALL -> DLV (in Ref. 1; BAA85929/BAA85930/BAA85931)", null);
		 verifyEvidences(obj, obj.fts.get(0),  Arrays.asList("ECO:0000305") );
	}
	
	@Test
	void testVariantWithMulti() {
		String ftLine =
				"FT   VARIANT     267    294       ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPD\n" +
				"FT                                LKVPVVQKVTKRLGVTSPD.\n";
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLine);
		 assertEquals(1, obj.fts.size());
		 System.out.println(obj.fts.get(0).ft_text);
		 verify(obj.fts.get(0), FTType.VARIANT, "267", "294",
				 "ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPDLKVPVVQKVTKRLGVTSPD", null);
	}
	
	@Test
	void testVariantWithMulti2() {
		String ftLine =
				"FT   VARIANT     157    224       EGKGLSLPLDSFSVRLHQDGQVSIELPDSHSPCYIKTYEVD\n" +
				"FT                                PGYKMAVCAAHPDFPEDITMVSYEELL -> GRQRLIASA\n" +
				"FT                                (in strain 168 and its derivatives, non\n"+
				"FT                                surfactin-producing strains).\n";
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLine);
		 assertEquals(1, obj.fts.size());
		 System.out.println(obj.fts.get(0).ft_text);
		 verify(obj.fts.get(0), FTType.VARIANT, "157", "224",
				 "EGKGLSLPLDSFSVRLHQDGQVSIELPDSHSPCYIKTYEVDPGYKMAVCAAHPDFPEDITMVSYEELL"
				 + " -> GRQRLIASA(in strain 168 and its derivatives, non surfactin-producing strains)", null);
	}
	
	@Test
	void testVarSeqWithMulti() {
		String ftLine =
				"FT   VAR_SEQ     267    294       ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPD\n" +
				"FT                                LKVPVVQKVTKRLGVTSPD.\n";
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLine);
		 assertEquals(1, obj.fts.size());
		 System.out.println(obj.fts.get(0).ft_text);
		 verify(obj.fts.get(0), FTType.VAR_SEQ, "267", "294",
				 "ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPDLKVPVVQKVTKRLGVTSPD", null);

	}

	@Test
	void testMultiFeatures() {
		 String ftLines = "FT   MUTAGEN       2      2       B->A,N: Less than 1% residual activity.\n"
	                +"FT                                {ECO:0000313|EMBL:BAG16761.1,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT   TRANSMEM      5     26       Helix A (By similarity).\n"
	                +"FT   UNSURE        2      2       S or A. {ECO:0000269|PubMed:10433554}.\n"
	                +"FT   ZN_FING     262    288       C2H2-type 2; low DNA-binding affinity (By\n"
	                +"FT                                similarity). {ECO:0000303|Ref.6,\n"
	                +"FT                                ECO:0000313|EMBL:BAG16761.1}.\n"
	                +"FT   TURN        194    196       {ECO:0000313|PDB:3OW2,\n"
	                +"FT                                ECO:0000256|HAMAP-Rule:MF_00205}.\n"
	                +"FT   DNA_BIND    ?54     77       H-T-H motif (Potential).\n"
	                +"FT                                {ECO:0000313|EMBL:BAG16761.1}.\n"
	                +"FT   COILED       <1    525       By similarity. {ECO:0000303|Ref.6}.\n"
	                +"FT   COMPBIAS    163    169       His-rich (could be involved in\n"
	                +"FT                                coordination of cobalt ions).\n"
	                +"FT                                {ECO:0000269|PubMed:10433554,\n"
	                +"FT                                ECO:0000313|PDB:3OW2}.\n"
	                +"FT   CROSSLNK     76     76       Glycyl lysine isopeptide (Gly-Lys)\n"
	                +"FT                                (interchain with K-? in acceptor\n"
	                +"FT                                proteins) (By similarity).\n"
	                +"FT                                {ECO:0000256|HAMAP-Rule:MF_00205}.\n"
	                +"FT   DISULFID    240    301       {ECO:0000303|Ref.6,\n"
	                +"FT                                ECO:0000256|HAMAP-Rule:MF_00205}.\n"
	                +"FT   VAR_SEQ      46     46       R -> MLWRRKIGPQMTLSHAAG (in isoform\n"
	                +"FT                                Long). {ECO:0000313|EMBL:BAG16761.1,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT                                /FTId=VSP_005610.\n"
	                +"FT   VAR_SEQ     167    229       Missing (in isoform Alpha and isoformn\n"
	                +"FT                                Beta).\n"
	                +"FT                                /FTId=VSP_005610.\n"
	                +"FT   METAL        96     96       Cobalt 1 and 2 (By similarity).\n"
	                +"FT                                {ECO:0000313|EMBL:BAG16761.1,\n"
	                +"FT                                ECO:0000303|Ref.6}.\n"
	                +"FT   MOD_RES      87     87       Phosphoserine (By similarity).\n"
	                +"FT                                {ECO:0000269|PubMed:10433554,\n"
	                +"FT                                ECO:0000303|Ref.6}.\n"
	                +"FT   MOTIF        50     58       Effector region (Potential).\n"
	                +"FT   NON_STD     356    356       Pyrrolysine (By similarity).\n"
	                +"FT   NON_CONS    192    192       {ECO:0000313|PDB:3OW2,\n"
	                +"FT                                ECO:0000256|HAMAP-Rule:MF_00205}.\n"
	                +"FT   VARIANT     221    221       G -> E (in a breast cancer sample;\n"
	                +"FT                                somatic mutation; dbSNP:rs35514614).\n"
	                +"FT                                /FTId=VAR_038685.\n"
	                +"FT   CONFLICT      3      4       Missing (in Ref. 2; AAH09411).\n"
	                +"FT   VARIANT     221    221       G -> E (in a breast cancer sample;\n"
	                +"FT                                somatic mutation; dbSNP:rs35514614).\n"
	                +"FT                                {ECO:0000313|EMBL:BAG16761.1,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT                                /FTId=VAR_038685.\n"
	                +"FT   ACT_SITE   1691   1871       VWFA 3; main binding site for collagens\n"
	                +"FT                                type I and III.\n"
	                +"FT   BINDING      79    197       Response regulatory (By similarity).\n"
	                +"FT   CA_BIND     805    816       2; possibly ancestral (Potential).\n"
	                +"FT   CHAIN        61    386       Serine/threonine-protein phosphatase 2A\n"
	                +"FT                                56 kDa regulatory subunit gamma isoform.\n"
	                +"FT                                /FTId=PRO_0000071458.\n"
	                +"FT   NON_TER      83     84\n"
	                +"FT   DNA_BIND    ?54     77       H-T-H motif (Potential).\n"
	                +"FT   COILED       <1    525       By similarity.\n"
	                +"FT   COMPBIAS    163    169       His-rich (could be involved in\n"
	                +"FT                                coordination of cobalt ions).\n"
	                +"FT   CROSSLNK     76     76       Glycyl lysine isopeptide (Gly-Lys)\n"
	                +"FT                                (interchain with K-? in acceptor\n"
	                +"FT                                proteins) (By similarity).\n"
	                +"FT   DISULFID    240    301\n"
	                +"FT   DOMAIN        1    >35       Peptidase S1.\n"
	                +"FT   INIT_MET     50     50       For isoform HLF36 and isoform HLF17.\n"
	                +"FT   INTRAMEM      8     28       Potential.\n"
	                +"FT   LIPID        <1      1       N-myristoyl glycine (by host) (By\n"
	                +"FT                                similarity).\n"
	                +"FT   HELIX        50     52\n"
	                +"FT   METAL        96     96       Cobalt 1 and 2 (By similarity).\n"
	                +"FT   MOD_RES      87     87       Phosphoserine (By similarity).\n"
	                +"FT   MOTIF        50     58       Effector region (Potential).\n"
	                +"FT   NON_STD     356    356       Pyrrolysine (By similarity).\n"
	                +"FT   NON_CONS    192    192\n"
	                +"FT   NP_BIND       9     14       FAD (ADP part) (Probable).\n"
	                +"FT   PEPTIDE     110    123       Urotensin-2.\n"
	                +"FT                                /FTId=PRO_0000040767.\n"
	                +"FT   REGION      610    722       Interaction with SIN3A (By similarity).\n"
	                +"FT   REPEAT        5     97       Solcar 1.\n"
	                +"FT   PROPEP       20     37\n"
	                +"FT                                /FTId=PRO_0000033922.\n"
	                +"FT   SIGNAL        ?     22       Potential.\n"
	                +"FT   SITE        366    366       Necessary for preference for fructose\n"
	                +"FT                                1,6-bisphosphate over fructose\n"
	                +"FT                                1-phosphate.\n"
	                +"FT   TOPO_DOM     91    264       Forespore intermembrane space (Probable).\n"
	                +"FT   TRANSIT       1      ?       Mitochondrion (Potential).\n"
	                +"FT   STRAND        2      4\n"
	                +"FT   TRANSMEM      5     26       Helix A (By similarity).\n"
	                +"FT   UNSURE        2      2       S or A.\n"
	                +"FT   ZN_FING     262    288       C2H2-type 2; low DNA-binding affinity (By\n"
	                +"FT                                similarity).\n"
	                +"FT   TURN        194    196\n"
	                +"FT   CONFLICT      1      1       A -> Q (in Ref. 1; BAA37160/BAA37165 and\n"
	                +"FT                                2).\n"
	                +"FT   SIGNAL        ?     22       Potential. {ECO:0000313|EMBL:BAG16761.1}.\n"
	                +"FT   SITE        366    366       Necessary for preference for fructose\n"
	                +"FT                                1,6-bisphosphate over fructose\n"
	                +"FT                                1-phosphate. {ECO:0000313|PDB:3OW2}.\n"
	                +"FT   TOPO_DOM     91    264       Forespore intermembrane space (Probable).\n"
	                +"FT   TRANSIT       1      ?       Mitochondrion (Potential).\n"
	                +"FT                                {ECO:0000303|Ref.6,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT   STRAND        2      4       {ECO:0000256|HAMAP-Rule:MF_00205}.\n"
	                +"FT   CONFLICT      1      1       A -> Q (in Ref. 1; BAA37160/BAA37165 and\n"
	                +"FT                                2). {ECO:0000313|EMBL:BAG16761.1,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT   ACT_SITE   1691   1871       VWFA 3; main binding site for collagens\n"
	                +"FT                                type I and III. {ECO:0000303|Ref.6,\n"
	                +"FT                                ECO:0000313|EMBL:BAG16761.1}.\n"
	                +"FT   BINDING      79    197       Response regulatory (By similarity).\n"
	                +"FT                                {ECO:0000269|PubMed:10433554,\n"
	                +"FT                                ECO:0000313|PDB:3OW2}.\n"
	                +"FT   CA_BIND     805    816       2; possibly ancestral (Potential).\n"
	                +"FT                                {ECO:0000303|Ref.6}.\n"
	                +"FT   CHAIN        61    386       Serine/threonine-protein phosphatase 2A\n"
	                +"FT                                56 kDa regulatory subunit gamma isoform.\n"
	                +"FT                                {ECO:0000256|HAMAP-Rule:MF_00205}.\n"
	                +"FT                                /FTId=PRO_0000071458.\n"
	                +"FT   NON_TER      83     84       {ECO:0000303|Ref.6}.\n"
	                +"FT   CONFLICT      3      4       Missing (in Ref. 2; AAH09411).\n"
	                +"FT                                {ECO:0000313|EMBL:BAG16761.1,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT   VAR_SEQ      46     46       R -> MLWRRKIGPQMTLSHAAG (in isoform\n"
	                +"FT                                Long).\n"
	                +"FT                                /FTId=VSP_005610.\n"
	                +"FT   VAR_SEQ     103    222       GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEE\n"
	                +"FT                                EPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL\n"
	                +"FT                                RPTYIYGEGGPFLSASINEALNNNGILSSVGK -> FSTVN\n"
	                +"FT                                ELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF\n"
	                +"FT                                GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL\n"
	                +"FT                                SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ\n"
	                +"FT                                (in isoform 2).\n"
	                +"FT                                /FTId=VSP_037399.\n"
	                +"FT   MUTAGEN       2      2       B->A,N: Less than 1% residual activity.\n"
	                +"FT   CARBOHYD     61     61       N-linked (GlcNAc...); by host\n"
	                +"FT                                (Potential). {ECO:0000303|Ref.6,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT   VAR_SEQ     167    229       Missing (in isoform Alpha and isoform\n"
	                +"FT                                Beta). {ECO:0000313|EMBL:BAG16761.1,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT                                /FTId=VSP_005610.\n"
	                +"FT   DOMAIN        1    >35       Peptidase S1. {ECO:0000313|PDB:3OW2,\n"
	                +"FT                                ECO:0000313|EMBL:BAG16761.1}.\n"
	                +"FT   INIT_MET     50     50       For isoform HLF36 and isoform HLF17.\n"
	                +"FT                                {ECO:0000313|EMBL:BAG16761.1}.\n"
	                +"FT   INTRAMEM      8     28       Potential. {ECO:0000303|Ref.6,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT   LIPID        <1      1       N-myristoyl glycine (by host) (By\n"
	                +"FT                                similarity). {ECO:0000303|Ref.6}.\n"
	                +"FT   HELIX        50     52       {ECO:0000256|HAMAP-Rule:MF_00205}.\n"
	                +"FT   CARBOHYD     61     61       N-linked (GlcNAc...); by host\n"
	                +"FT                                (Potential).\n"
	                +"FT   VAR_SEQ     103    222       GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEE\n"
	                +"FT                                EPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL\n"
	                +"FT                                RPTYIYGEGGPFLSASINEALNNNGILSSVGK -> FSTVN\n"
	                +"FT                                ELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF\n"
	                +"FT                                GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL\n"
	                +"FT                                SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ\n"
	                +"FT                                (in isoform 2).\n"
	                +"FT                                {ECO:0000313|EMBL:BAG16761.1,\n"
	                +"FT                                ECO:0000269|PubMed:10433554}.\n"
	                +"FT                                /FTId=VSP_037399.\n"
	                +"FT   NP_BIND       9     14       FAD (ADP part) (Probable).\n"
	                +"FT                                {ECO:0000313|EMBL:BAG16761.1,\n"
	                +"FT                                ECO:0000303|Ref.6}.\n"
	                +"FT   PEPTIDE     110    123       Urotensin-2. {ECO:0000303|Ref.6}.\n"
	                +"FT                                /FTId=PRO_0000040767.\n"
	                +"FT   REGION      610    722       Interaction with SIN3A (By similarity).\n"
	                +"FT   REPEAT        5     97       Solcar 1. {ECO:0000313|PDB:3OW2}.\n"
	                +"FT   PROPEP       20     37       {ECO:0000269|PubMed:10433554,\n"
	                +"FT                                ECO:0000256|HAMAP-Rule:MF_00205}.\n"
	                +"FT                                /FTId=PRO_0000033922.\n"
                ;
		 UniprotLineParser<FtLineObject> parser = new DefaultUniprotLineParserFactory().createFtLineParser();
		 FtLineObject obj = parser.parse(ftLines);
		 assertEquals(84, obj.fts.size());


	}
	
	private void verify(FtLineObject.FT ft, FTType type, String start, String end, String description, String ftid) {
		assertEquals(type, ft.type);
		assertEquals(start, ft.location_start);
		assertEquals(end, ft.location_end);
		assertEquals(description, ft.ft_text);
		assertEquals(ftid, ft.ftId);
	}
	
	private void verifyEvidences( FtLineObject obj, Object name, List<String> evidences) {
		List<String> expected = obj.evidenceInfo.evidences.get(name);
		assertEquals(expected, evidences);

	}
}