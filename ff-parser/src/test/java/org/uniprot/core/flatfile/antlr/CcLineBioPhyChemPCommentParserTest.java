package org.uniprot.core.flatfile.antlr;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject.BiophysicochemicalProperties;

import static org.junit.Assert.*;

public class CcLineBioPhyChemPCommentParserTest {
	@Test
	public void testProperty1() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"CC       Kinetic parameters:\n"
				+"CC         KM=1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees\n"
				+"CC         Celsius and at pH 7.6);\n"
				+"CC         Vmax=1.9 mmol/min/mg enzyme;\n"
				+"CC       pH dependence:\n"
				+"CC         Optimum pH is 7.75.;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals(1, bp.kms.size());
		assertEquals("1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees Celsius and at pH 7.6)", bp.kms.get(0).value);
		assertEquals(1, bp.vmaxs.size());
		assertEquals("1.9 mmol/min/mg enzyme", bp.vmaxs.get(0).value);
		assertEquals("Optimum pH is 7.75.", bp.phDependence.get(0).value);
	}
	
	
	@Test
	public void testSmallVmaxValue() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n" + 
				"CC       Kinetic parameters:\n" + 
				"CC         KM=0.3913 uM for FAM fluorophore-coupled RNA substrate and a\n" + 
				"CC         quencher-coupled DNA primer {ECO:0000269|PubMed:26779609};\n" + 
				"CC         Vmax=0.000197 umol/sec/ug enzyme {ECO:0000269|PubMed:26779609};\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals(1, bp.kms.size());
		assertEquals("0.3913 uM for FAM fluorophore-coupled RNA substrate and a quencher-coupled DNA primer", bp.kms.get(0).value);
		assertEquals(1, bp.vmaxs.size());
		assertEquals("0.000197 umol/sec/ug enzyme", bp.vmaxs.get(0).value);
	}
	
	
	@Test
	public void testProperty2() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"CC       Kinetic parameters:\n"
				+"CC         KM=71 uM for ATP;\n"
				+"CC         KM=98 uM for ADP;\n"
				+"CC         KM=1.5 mM for acetate;\n"
				+"CC         KM=0.47 mM for acetyl phosphate;\n"
				+"CC       Temperature dependence:\n"
				+"CC         Optimum temperature is 65 degrees Celsius. Protected from\n"				
				+"CC         thermal inactivation by ATP.;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals(null, bp.bsorptionAbs);
		assertEquals(0, bp.bsorptionNote.size());
		assertEquals(4, bp.kms.size());
		assertEquals("71 uM for ATP", bp.kms.get(0).value);
		assertEquals("98 uM for ADP", bp.kms.get(1).value);
		assertEquals("1.5 mM for acetate", bp.kms.get(2).value);
		assertEquals("0.47 mM for acetyl phosphate", bp.kms.get(3).value);
		assertEquals(0, bp.vmaxs.size());

		assertEquals("Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by ATP.", bp.temperatureDependence.get(0).value);
	}
	
	@Test
	public void testTwoTempDependences() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"CC       Kinetic parameters:\n"
				+"CC         KM=71 uM for ATP;\n"
				+"CC         KM=98 uM for ADP;\n"
				+"CC         KM=1.5 mM for acetate;\n"
				+"CC         KM=0.47 mM for acetyl phosphate;\n"
				+"CC       Temperature dependence:\n"
				+"CC         Optimum temperature is 65 degrees Celsius. Protected from\n"			
				+"CC         thermal inactivation by ATP. {ECO:0000269|PubMed:10433555};\n"			
				+"CC         2 thermal inactivation by ATP. {ECO:0000269|PubMed:10433556};\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals(null, bp.bsorptionAbs);
		assertEquals(0, bp.bsorptionNote.size());
		assertEquals(4, bp.kms.size());
		assertEquals("71 uM for ATP", bp.kms.get(0).value);
		assertEquals("98 uM for ADP", bp.kms.get(1).value);
		assertEquals("1.5 mM for acetate", bp.kms.get(2).value);
		assertEquals("0.47 mM for acetyl phosphate", bp.kms.get(3).value);
		assertEquals(0, bp.vmaxs.size());
		assertEquals("Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by ATP.", bp.temperatureDependence.get(0).value);
		assertEquals("ECO:0000269|PubMed:10433555", bp.temperatureDependence.get(0).evidences.get(0));
		assertEquals("2 thermal inactivation by ATP.", bp.temperatureDependence.get(1).value);
		assertEquals("ECO:0000269|PubMed:10433556", bp.temperatureDependence.get(1).evidences.get(0));
	}
	  
	@Test
	public void testAbsorption() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"CC       Absorption:\n"
				+"CC         Abs(max)=578 nm;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals("578 nm", bp.bsorptionAbs.value);
		assertEquals(0, bp.bsorptionNote.size());
		assertEquals(0, bp.kms.size());
		assertEquals(0, bp.vmaxs.size());

	}
	
	public void testAbsorptionWithNote() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"CC       Absorption:\n"
				+"CC         Abs(max)=~596 nm;\n"
				+"CC         Note=In the presence of anions, the maximum absorption shifts to\n"
				+"CC         about 575 nm.;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals("596 nm", bp.bsorptionAbs.value);
		assertTrue(bp.bsorptionAbsApproximate);
		assertEquals(1, bp.bsorptionNote.size());
		assertEquals("In the presence of anions, the maximum absorption shifts to about 575 nm.", bp.bsorptionNote.get(0).value);
		assertEquals(0, bp.kms.size());
		assertEquals(0, bp.vmaxs.size());

	}
	
	public void testAbsorptionWithNoteEvidences() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"CC       Absorption:\n"
				+"CC         Abs(max)=~596 nm {ECO:0000313};\n"
				+"CC         Note=In the presence of anions, the maximum absorption shifts to\n"
				+"CC         about 575 nm. {ECO:0000314};\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals("596 nm", bp.bsorptionAbs.value);
		assertEquals("ECO:0000313", bp.bsorptionAbs.evidences.get(0));
		assertTrue(bp.bsorptionAbsApproximate);
		assertEquals(1, bp.bsorptionNote.size());
		assertEquals("In the presence of anions, the maximum absorption shifts to about 575 nm.", bp.bsorptionNote.get(0).value);
		assertEquals("ECO:0000314", bp.bsorptionNote.get(0).evidences.get(0));
		assertEquals(0, bp.kms.size());
		assertEquals(0, bp.vmaxs.size());

	}

	@Test
	public void testBPWithEvidences() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"CC       Absorption:\n"
				+"CC         Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n"
				+"CC         Note=The above maximum is for the oxidized form. Shows a maximal\n"
				+"CC         peak at 330 nm in the reduced form. These absorption peaks are\n"
				+"CC         for the tryptophylquinone cofactor. {ECO:0000303|Ref.6,\n"
				+"CC         ECO:0000269|PubMed:10433554};\n"
				+"CC       Kinetic parameters:\n"
				+"CC         KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n"
				+"CC         KM=688 uM for pyridoxal {ECO:0000313|EMBL:BAG16761.1,\n"
				+"CC         ECO:0000269|PubMed:10433554};\n"
				+"CC         Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n"
				+"CC         Note=The enzyme is substrate inhibited at high substrate\n"
				+"CC         concentrations (Ki=1.08 mM for tyramine).\n"
				+"CC         {ECO:0000256|HAMAP-Rule:MF_00205};\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals("465 nm", bp.bsorptionAbs.value);
		assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.bsorptionAbs.evidences.get(0));
		assertFalse(bp.bsorptionAbsApproximate);
		assertEquals(1, bp.bsorptionNote.size());
		assertEquals("The above maximum is for the oxidized form. Shows a maximal"
			      +" peak at 330 nm in the reduced form. These absorption peaks are"
			      +" for the tryptophylquinone cofactor.", bp.bsorptionNote.get(0).value);
		assertEquals("ECO:0000303|Ref.6", bp.bsorptionNote.get(0).evidences.get(0));
		assertEquals("ECO:0000269|PubMed:10433554", bp.bsorptionNote.get(0).evidences.get(1));
		assertEquals(2, bp.kms.size());
		
		assertEquals("5.4 uM for tyramine", bp.kms.get(0).value);
		assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.kms.get(0).evidences.get(0));
		
		assertEquals("688 uM for pyridoxal", bp.kms.get(1).value);
		assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.kms.get(1).evidences.get(0));
		assertEquals("ECO:0000269|PubMed:10433554", bp.kms.get(1).evidences.get(1));
		
		assertEquals(1, bp.vmaxs.size());
		
		assertEquals("17 umol/min/mg enzyme", bp.vmaxs.get(0).value);
		assertEquals("ECO:0000313|PDB:3OW2", bp.vmaxs.get(0).evidences.get(0));
		
		assertEquals("The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for tyramine).", bp.kpNote.get(0).value);
		assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", bp.kpNote.get(0).evidences.get(0));
		
	}

	
	@Test
	public void testBPWithEvidences2() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"CC       pH dependence:\n"
				+"CC         Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n"
				+"CC       Redox potential:\n"
				+"CC         E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
				+"CC       Temperature dependence:\n"
				+"CC         Highly active at low temperatures, even at 0 degree Celsius.\n"
				+"CC         Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};\n"

				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals("Optimum pH is 8-10.", bp.phDependence.get(0).value);	
		assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.phDependence.get(0).evidences.get(0));
	
		assertEquals("E(0) is -448 mV.", bp.rdoxPotential.get(0).value);	
		assertEquals("ECO:0000303|Ref.6", bp.rdoxPotential.get(0).evidences.get(0));
		assertEquals("ECO:0000313|PDB:3OW2", bp.rdoxPotential.get(0).evidences.get(1));
		
		assertEquals("Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.", bp.temperatureDependence.get(0).value);	
		assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", bp.temperatureDependence.get(0).evidences.get(0));
	
	  }
	@Test
	public void testBPSpecialChars() {
		String lines = "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"CC       Kinetic parameters:\n"
				+"CC         KM=19.7 uM for peptide substrate DABCYL-ARSGAKASGC(farnesyl)LVS-\n"
				+"CC         EDANS where EDANS is 5-[(2-aminoethyl)amino]naphthalene-1-\n"
				+"CC         sulphonic acid fluorophore and DABCYL is 4-{[4-\n"
				+"CC         (dimethylamino)phenyl]azo}benzoic acid quencher.\n"
				+"CC         {ECO:0000269|PubMed:24291792};\n"
				+"CC         Note=kcat is 0.175 s(-1) for peptide substrate DABCYL-\n" 			
				+"CC         ARSGAKASGC(farnesyl)LVS-EDANS. {ECO:0000269|PubMed:24291792};\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals("19.7 uM for peptide substrate DABCYL-ARSGAKASGC(farnesyl)LVS-EDANS where EDANS is 5-[(2-aminoethyl)amino]naphthalene-1-sulphonic acid fluorophore and DABCYL is 4-{[4-(dimethylamino)phenyl]azo}benzoic acid quencher.", bp.kms.get(0).value);

	}
	@Test
	public void testNoHeaderWithEvidence() {
		String ccLineStringEvidence =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"Absorption:\n" +
				"  Abs(max)=~465 nm {ECO:0000313|EMBL:BAG16761.1};\n" +							
				"  Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in"
				+ " the reduced form. {ECO:0000269|PubMed:10433554}. "
				+ "These absorption peaks are for the tryptophylquinone cofactor. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n" +
				"Kinetic parameters:\n" +
				"  KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n" +
				"  KM=688 uM for pyridoxal {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n" +
				"  Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n" +
				"  Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for tyramine)."
				+ " {ECO:0000256|HAMAP-Rule:MF_00205}. "
				+ "Another note is very very long. {ECO:0000256|HAMAP-Rule:MF_00205};");   
		CcLineFormater formater  =new CcLineFormater();
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		String lines = formater.format(ccLineStringEvidence);
		CcLineObject obj = parser.parse(lines);
		assertNotNull(obj);
	}
	@Test
	public void testNoHeaderWithEvidence2() {
		String ccLineStringEvidence =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"Absorption:\n" +
				"Abs(max)=~465 nm {ECO:0000313|EMBL:BAG16761.1};\n" +							
				"Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in"
				+ " the reduced form. {ECO:0000269|PubMed:10433554}. "
				+ "These absorption peaks are for the tryptophylquinone cofactor. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n" +
				"Kinetic parameters:\n" +
				"KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n" +
				"KM=688 uM for pyridoxal {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n" +
				"Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n" +
				"Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for tyramine)."
				+ " {ECO:0000256|HAMAP-Rule:MF_00205}. "
				+ "Another note is very very long. {ECO:0000256|HAMAP-Rule:MF_00205};");   
		CcLineFormater formater  =new CcLineFormater();
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		String lines = formater.format(ccLineStringEvidence);
		CcLineObject obj = parser.parse(lines);
		assertNotNull(obj);
	}
	@Test
	public void testNoHeaderWithEvidence3() {

			String linesNoHeader = "BIOPHYSICOCHEMICAL PROPERTIES:\n"
					+"pH dependence:\n"
					+"  Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n"
					+"Redox potential:\n"
					+"  E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
					+"Temperature dependence:\n"
					+"  Highly active at low temperatures, even at 0 degree Celsius.\n"
					+"  Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};\n"

					;
			CcLineFormater formater  =new CcLineFormater();
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			String lines = formater.format(linesNoHeader);
			CcLineObject obj = parser.parse(lines);
			assertNotNull(obj);
	}
	@Test
	public void testNoHeaderWithEvidence4() {

			String linesNoHeader = "BIOPHYSICOCHEMICAL PROPERTIES:\n"
					+"pH dependence:\n"
					+"  Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n"
					+"Redox potential:\n"
					+"  E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
					+"Temperature dependence:\n"
					+"Highly active at low temperatures, even at 0 degree Celsius.\n"
					+"Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};\n"

					;
			CcLineFormater formater  =new CcLineFormater();
			UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
			String lines = formater.format(linesNoHeader);
			CcLineObject obj = parser.parse(lines);
			assertNotNull(obj);
	}
	
	@Test
	public void testNoHeaderWithEvidences() {
		String linesNoHeader = "BIOPHYSICOCHEMICAL PROPERTIES:\n"
				+"Absorption:\n"
				+"Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n"
				+"Note=The above maximum is for the oxidized form. Shows a maximal\n"
				+"peak at 330 nm in the reduced form. These absorption peaks are\n"
				+"for the tryptophylquinone cofactor. {ECO:0000303|Ref.6,\n"
				+"ECO:0000269|PubMed:10433554};\n"
				+"Kinetic parameters:\n"
				+"KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n"
				+"KM=688 uM for pyridoxal {ECO:0000313|EMBL:BAG16761.1,\n"
				+"ECO:0000269|PubMed:10433554};\n"
				+"Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n"
				+"Note=The enzyme is substrate inhibited at high substrate\n"
				+"concentrations (Ki=1.08 mM for tyramine).\n"
				+"{ECO:0000256|HAMAP-Rule:MF_00205};\n"
				;
		CcLineFormater formater  =new CcLineFormater();
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		String lines = formater.format(linesNoHeader);
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof BiophysicochemicalProperties);
		BiophysicochemicalProperties bp = (BiophysicochemicalProperties)cc.object;
		assertEquals("465 nm", bp.bsorptionAbs.value);
		assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.bsorptionAbs.evidences.get(0));
		assertFalse(bp.bsorptionAbsApproximate);
		assertEquals(1, bp.bsorptionNote.size());
		assertEquals("The above maximum is for the oxidized form. Shows a maximal"
			      +" peak at 330 nm in the reduced form. These absorption peaks are"
			      +" for the tryptophylquinone cofactor.", bp.bsorptionNote.get(0).value);
		assertEquals("ECO:0000303|Ref.6", bp.bsorptionNote.get(0).evidences.get(0));
		assertEquals("ECO:0000269|PubMed:10433554", bp.bsorptionNote.get(0).evidences.get(1));
		assertEquals(2, bp.kms.size());
		
		assertEquals("5.4 uM for tyramine", bp.kms.get(0).value);
		assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.kms.get(0).evidences.get(0));
		
		assertEquals("688 uM for pyridoxal", bp.kms.get(1).value);
		assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.kms.get(1).evidences.get(0));
		assertEquals("ECO:0000269|PubMed:10433554", bp.kms.get(1).evidences.get(1));
		
		assertEquals(1, bp.vmaxs.size());
		
		assertEquals("17 umol/min/mg enzyme", bp.vmaxs.get(0).value);
		assertEquals("ECO:0000313|PDB:3OW2", bp.vmaxs.get(0).evidences.get(0));
		
		assertEquals("The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for tyramine).", bp.kpNote.get(0).value);
		assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", bp.kpNote.get(0).evidences.get(0));
		
	}

}