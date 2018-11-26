package uk.ac.ebi.uniprot.parser.ffwriter.line.cc;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.BPCPComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RedoxPotential;
import uk.ac.ebi.uniprot.domain.uniprot.comment.TemperatureDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.BPCPCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;

public class CCBioPhyChemBuildTest extends CCBuildTestAbstr {
	@Test
	public void test1WithEvidence() {
		String ccLine =("CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"CC       pH dependence:\n" +
				"CC         Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n" +
				"CC       Redox potential:\n" +
				"CC         E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n" +
				"CC       Temperature dependence:\n" +
				"CC         Highly active at low temperatures, even at 0 degree Celsius.\n" +
				"CC         Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};");
		
		String ccLineString =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"pH dependence:\n" +
				"Optimum pH is 8-10.;\n" +
				"Redox potential:\n" +
				"E(0) is -448 mV.;\n" +
				"Temperature dependence:\n" +
				"Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.;");
		String ccLineStringEvidence =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"pH dependence:\n" +
				"Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n" +
				"Redox potential:\n" +
				"E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n" +
				"Temperature dependence:\n" +
				"Highly active at low temperatures, even at 0 degree Celsius. Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};");
		String ev1 ="ECO:0000313|EMBL:BAG16761.1";
		String ev3 ="ECO:0000303|Ref.6";
		String ev4 ="ECO:0000313|PDB:3OW2";
		String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
		String phValue = "Optimum pH is 8-10.";
		List<String> phEvs = new ArrayList<>();
		phEvs.add(ev1);
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		
		PhDependence phDep =buildPHDependence(phValue, phEvs);
		builder.pHDependence(phDep);
		String redoxValue = "E(0) is -448 mV.";
		List<String> redoxEvs = new ArrayList<>();
		redoxEvs.add(ev3);
		redoxEvs.add(ev4);
		RedoxPotential redox = buildRedoxPotential(redoxValue, redoxEvs);
		builder.redoxPotential(redox);
		
		String tempValue = "Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.";
		List<String> temEvs = new ArrayList<>();
		temEvs.add(ev5);
		
		TemperatureDependence tempDep = buildTemperatureDependence(tempValue, temEvs);
		builder.temperatureDependence(tempDep);
		BPCPComment comment = builder.build();
		doTest(ccLine, comment);
		doTestString(ccLineString, comment);
		doTestStringEv(ccLineStringEvidence, comment);
	}
	@Test
	public void test1WithoutEvidence() {
		String ccLine =("CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"CC       pH dependence:\n" +
				"CC         Optimum pH is 8-10.;\n" +
				"CC       Redox potential:\n" +
				"CC         E(0) is -448 mV.;\n" +
				"CC       Temperature dependence:\n" +
				"CC         Highly active at low temperatures, even at 0 degree Celsius.\n" +
				"CC         Thermolabile.;");
		
		String ccLineString =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"pH dependence:\n" +
				"Optimum pH is 8-10.;\n" +
				"Redox potential:\n" +
				"E(0) is -448 mV.;\n" +
				"Temperature dependence:\n" +
				"Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.;");
		
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		String phValue = "Optimum pH is 8-10.";
		List<String> phEvs = new ArrayList<>();
	//	phEvs.add(ev1);
		PhDependence phDep =buildPHDependence(phValue, phEvs);
		builder.pHDependence(phDep);

		
		String redoxValue = "E(0) is -448 mV.";
		List<String> redoxEvs = new ArrayList<>();
		RedoxPotential redox = buildRedoxPotential(redoxValue, redoxEvs);
		builder.redoxPotential(redox);
		
		String tempValue = "Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.";
		List<String> temEvs = new ArrayList<>();
	//	temEvs.add(ev5);
		
		TemperatureDependence tempDep = buildTemperatureDependence(tempValue, temEvs);
		builder.temperatureDependence(tempDep);
		BPCPComment comment = builder.build();
		doTest(ccLine, comment);
		doTestString(ccLineString, comment);
		doTestStringEv(ccLineString, comment);
		
	}
	@Test
	public void test2WithoutEvidence() {
		String ccLine =("CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"CC       Absorption:\n" +
				"CC         Abs(max)=465 nm;\n" +							
				"CC         Note=The above maximum is for the oxidized form. Shows a maximal\n" +
				"CC         peak at 330 nm in the reduced form. These absorption peaks are\n" +
				"CC         for the tryptophylquinone cofactor.;\n" +
				"CC       Kinetic parameters:\n" +
				"CC         KM=5.4 uM for tyramine;\n" +
				"CC         KM=688 uM for pyridoxal;\n" +
				"CC         Vmax=17 umol/min/mg enzyme;\n" +
				"CC         Note=The enzyme is substrate inhibited at high substrate\n" +
				"CC         concentrations (Ki=1.08 mM for tyramine).;");        
		
		String ccLineString =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"Absorption:\n" +
				"Abs(max)=465 nm;\n" +							
				"Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in the reduced form. These absorption peaks are for the tryptophylquinone cofactor.;\n" +
				"Kinetic parameters:\n" +
				"KM=5.4 uM for tyramine;\n" +
				"KM=688 uM for pyridoxal;\n" +
				"Vmax=17 umol/min/mg enzyme;\n" +
				"Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for tyramine).;");        
//		String ev1 ="ECO:0000313|EMBL:BAG16761.1";
//		String ev2= "ECO:0000269|PubMed:10433554";
//		String ev3 ="ECO:0000303|Ref.6";
//		String ev4 ="ECO:0000313|PDB:3OW2";
//		String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		int abMax= 465;
		boolean isApprox =false;
		List<String> abEvs = new ArrayList<>();
		String abNote = "The above maximum is for the oxidized form. Shows a maximal "
				+ "peak at 330 nm in the reduced form. These absorption peaks are "
				+ "for the tryptophylquinone cofactor.";
		List<String> abNoteEvs = new ArrayList<>();
		Absorption absorption= buildAbsorption(abMax,  isApprox, abEvs, 
				abNote, abNoteEvs);
		builder.absorption(absorption);
		String kpNoteStr = "The enzyme is substrate inhibited at high substrate "
				+ "concentrations (Ki=1.08 mM for tyramine).";
		List<String> kpNoteEvs =new ArrayList<>();
		Note kpNote =buildNote(kpNoteStr, kpNoteEvs);
		List<MaximumVelocity> velocities = new ArrayList<>();
		float velocity1 = 17f;
		String enzyme1= "enzyme";
		List<String> mvEvs1 =new ArrayList<>();
		MaximumVelocity  vel1 = buildMaximumVelocity( velocity1, "umol/min/mg",
				 enzyme1, mvEvs1);
		velocities.add(vel1);
		
		List<MichaelisConstant> constants = new ArrayList<>();
		
		float constant1 = 5.4f;
		String substrate1 ="tyramine";
		List<String> consEvs = new ArrayList<>();
		MichaelisConstant constan1= buildMichaelisConstant( constant1, MichaelisConstantUnit.MICRO_MOL,
				 substrate1, consEvs);
		constants.add(constan1);
		
		float constant2 = 688f;
		String substrate2 ="pyridoxal";
		List<String> consEvs2 = new ArrayList<>();
		MichaelisConstant constan2= buildMichaelisConstant( constant2, MichaelisConstantUnit.MICRO_MOL,
				 substrate2, consEvs2);
		constants.add(constan2);
		KineticParameters kinetic= BPCPCommentBuilder.createKineticParameters(velocities, constants, kpNote);
		builder.kineticParameters(kinetic);
		BPCPComment comment = builder.build();
	
		doTest(ccLine, comment);
		doTestString(ccLineString, comment);
		doTestStringEv(ccLineString, comment);
	}
	
	@Test
	public void test2WithEvidence() {
		String ccLine =("CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"CC       Absorption:\n" +
				"CC         Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n" +							
				"CC         Note=The above maximum is for the oxidized form. Shows a maximal\n" +
				"CC         peak at 330 nm in the reduced form. These absorption peaks are\n" +
				"CC         for the tryptophylquinone cofactor.\n"+
				"CC         {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n" +
				"CC       Kinetic parameters:\n" +
				"CC         KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n" +
				"CC         KM=688 uM for pyridoxal {ECO:0000269|PubMed:10433554,\n" +
				"CC         ECO:0000313|EMBL:BAG16761.1};\n" +
				"CC         Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n" +
				"CC         Note=The enzyme is substrate inhibited at high substrate\n" +
				"CC         concentrations (Ki=1.08 mM for tyramine).\n" +
				"CC         {ECO:0000256|HAMAP-Rule:MF_00205};");        
		
		String ccLineString =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"Absorption:\n" +
				"Abs(max)=465 nm;\n" +							
				"Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in the reduced form. These absorption peaks are for the tryptophylquinone cofactor.;\n" +
				"Kinetic parameters:\n" +
				"KM=5.4 uM for tyramine;\n" +
				"KM=688 uM for pyridoxal;\n" +
				"Vmax=17 umol/min/mg enzyme;\n" +
				"Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for tyramine).;");    
		String ccLineStringEvidence =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"Absorption:\n" +
				"Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n" +							
				"Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in the reduced form. These absorption peaks are for the tryptophylquinone cofactor. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n" +
				"Kinetic parameters:\n" +
				"KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n" +
				"KM=688 uM for pyridoxal {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n" +
				"Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n" +
				"Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for tyramine). {ECO:0000256|HAMAP-Rule:MF_00205};");    
		String ev1 ="ECO:0000313|EMBL:BAG16761.1";
		String ev2= "ECO:0000269|PubMed:10433554";
		String ev3 ="ECO:0000303|Ref.6";
		String ev4 ="ECO:0000313|PDB:3OW2";
		String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		int abMax= 465;
		boolean isApprox =false;
		List<String> abEvs = new ArrayList<>();
		abEvs.add(ev1);
		String abNote = "The above maximum is for the oxidized form. Shows a maximal "
				+ "peak at 330 nm in the reduced form. These absorption peaks are "
				+ "for the tryptophylquinone cofactor.";
		List<String> abNoteEvs = new ArrayList<>();
		abNoteEvs.add(ev3);
		abNoteEvs.add(ev2);
		Absorption absorption= buildAbsorption(abMax,  isApprox, abEvs, 
				abNote, abNoteEvs);
		builder.absorption(absorption);
		String kpNoteStr = "The enzyme is substrate inhibited at high substrate "
				+ "concentrations (Ki=1.08 mM for tyramine).";
		List<String> kpNoteEvs =new ArrayList<>();
		kpNoteEvs.add(ev5);
		Note kpNote =buildNote(kpNoteStr, kpNoteEvs);
		List<MaximumVelocity> velocities = new ArrayList<>();
		float velocity1 = 17f;
		String enzyme1= "enzyme";
		List<String> mvEvs1 =new ArrayList<>();
		mvEvs1.add(ev4);
		MaximumVelocity  vel1 = buildMaximumVelocity( velocity1, "umol/min/mg",
				 enzyme1, mvEvs1);
		velocities.add(vel1);
		
		
		List<MichaelisConstant> constants = new ArrayList<>();
		
		float constant1 = 5.4f;
		String substrate1 ="tyramine";
		List<String> consEvs = new ArrayList<>();
		consEvs.add(ev1);
		MichaelisConstant constan1= buildMichaelisConstant( constant1, MichaelisConstantUnit.MICRO_MOL,
				 substrate1, consEvs);
		constants.add(constan1);
		
		float constant2 = 688f;
		String substrate2 ="pyridoxal";
		List<String> consEvs2 = new ArrayList<>();
		consEvs2.add(ev1);
		consEvs2.add(ev2);
		MichaelisConstant constan2= buildMichaelisConstant( constant2, MichaelisConstantUnit.MICRO_MOL,
				 substrate2, consEvs2);
		constants.add(constan2);
		KineticParameters kinetic= BPCPCommentBuilder.createKineticParameters(velocities, constants, kpNote);
		builder.kineticParameters(kinetic);
		BPCPComment comment = builder.build();
		
		doTest(ccLine, comment);
		doTestString(ccLineString, comment);
		doTestStringEv(ccLineStringEvidence, comment);
	}
	
	@Test
	public void test3WithEvidence() {
		String ccLine =("CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"CC       pH dependence:\n" +
				"CC         Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1}. Optimum pH is\n" +
				"CC         3-5. {ECO:0000313|EMBL:BAG16761.1};\n" +
				"CC       Redox potential:\n" +
				"CC         E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}. E(0)\n" +
				"CC         is -234 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n" +
				"CC       Temperature dependence:\n" +
				"CC         Highly active at low temperatures, even at 0 degree Celsius.\n" +
				"CC         Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205}. Another active\n" +
				"CC         at low temperatures.;");
		
		String ccLineString =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"pH dependence:\n" +
				"Optimum pH is 8-10.. Optimum pH is 3-5.;\n" +
				"Redox potential:\n" +
				"E(0) is -448 mV.. E(0) is -234 mV.;\n" +
				"Temperature dependence:\n" +
				"Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.."
				+ " Another active at low temperatures.;");
		String ccLineStringEvidence =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"pH dependence:\n" +
				"Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1}."
				+ " Optimum pH is 3-5. {ECO:0000313|EMBL:BAG16761.1};\n" +
				"Redox potential:\n" +
				"E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}."
				+ " E(0) is -234 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n" +
				"Temperature dependence:\n" +
				"Highly active at low temperatures, even at 0 degree Celsius. Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205}."
				+ " Another active at low temperatures.;");
		String ev1 ="ECO:0000313|EMBL:BAG16761.1";
		String ev3 ="ECO:0000303|Ref.6";
		String ev4 ="ECO:0000313|PDB:3OW2";
		String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		String phValue = "Optimum pH is 8-10.";
		List<String> phEvs = new ArrayList<>();
		String phValue2 = "Optimum pH is 3-5.";
		phEvs.add(ev1);
		List<Map.Entry<String, List<String>>> phDeps = new ArrayList<>();
		phDeps.add(new AbstractMap.SimpleEntry<>(phValue, phEvs));
		phDeps.add(new AbstractMap.SimpleEntry<>(phValue2, phEvs));
		PhDependence phDep =buildPHDependence(phDeps);

		builder.pHDependence(phDep);
		String redoxValue = "E(0) is -448 mV.";
		List<String> redoxEvs = new ArrayList<>();
		redoxEvs.add(ev3);
		redoxEvs.add(ev4);
		String redoxValue2 = "E(0) is -234 mV.";
		List<Map.Entry<String, List<String>>> redoxes = new ArrayList<>();
		redoxes.add(new AbstractMap.SimpleEntry<>(redoxValue, redoxEvs));
		redoxes.add(new AbstractMap.SimpleEntry<>(redoxValue2, redoxEvs));
		
		RedoxPotential redox = buildRedoxPotential(redoxes);
		builder.redoxPotential(redox);
		
		String tempValue = "Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.";
		List<String> temEvs = new ArrayList<>();
		temEvs.add(ev5);
		String tempValue2 = "Another active at low temperatures.";
		List<Map.Entry<String, List<String>>> tempDeps = new ArrayList<>();
		tempDeps.add(new AbstractMap.SimpleEntry<>(tempValue, temEvs));
		tempDeps.add(new AbstractMap.SimpleEntry<>(tempValue2, Collections.emptyList()));
		
		TemperatureDependence tempDep = buildTemperatureDependence(tempDeps);
		builder.temperatureDependence(tempDep);
		
		BPCPComment comment = builder.build();
		
		doTest(ccLine, comment);
		doTestString(ccLineString, comment);
		doTestStringEv(ccLineStringEvidence, comment);
	}
	

	@Test
	public void test4WithEvidence() {
		String ccLine =("CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"CC       Absorption:\n" +
				"CC         Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n" +							
				"CC         Note=The above maximum is for the oxidized form. Shows a maximal\n" +
				"CC         peak at 330 nm in the reduced form.\n"+
				"CC         {ECO:0000269|PubMed:10433554}. These absorption peaks are for\n" +
				"CC         the tryptophylquinone cofactor. {ECO:0000269|PubMed:10433554,\n" +
				"CC         ECO:0000303|Ref.6};\n" +
				"CC       Kinetic parameters:\n" +
				"CC         KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n" +
				"CC         KM=688 uM for pyridoxal {ECO:0000269|PubMed:10433554,\n" +
				"CC         ECO:0000313|EMBL:BAG16761.1};\n" +
				"CC         Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n" +
				"CC         Note=The enzyme is substrate inhibited at high substrate\n" +
				"CC         concentrations (Ki=1.08 mM for tyramine).\n" +
				"CC         {ECO:0000256|HAMAP-Rule:MF_00205}. Another note is very very\n"+
				"CC         long. {ECO:0000256|HAMAP-Rule:MF_00205};");        
		
		String ccLineString =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"Absorption:\n" +
				"Abs(max)=465 nm;\n" +							
				"Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in"
				+ " the reduced form.. These absorption peaks are for the tryptophylquinone cofactor.;\n" +
				"Kinetic parameters:\n" +
				"KM=5.4 uM for tyramine;\n" +
				"KM=688 uM for pyridoxal;\n" +
				"Vmax=17 umol/min/mg enzyme;\n" +
				"Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for tyramine).."
				+ " Another note is very very long.;");    
		String ccLineStringEvidence =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
				"Absorption:\n" +
				"Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n" +							
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
		String ev1 ="ECO:0000313|EMBL:BAG16761.1";
		String ev2= "ECO:0000269|PubMed:10433554";
		String ev3 ="ECO:0000303|Ref.6";
		String ev4 ="ECO:0000313|PDB:3OW2";
		String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
		BPCPCommentBuilder builder = BPCPCommentBuilder.newInstance();
		int abMax= 465;
		boolean isApprox =false;
		List<String> abEvs = new ArrayList<>();
		abEvs.add(ev1);
		String abNote = "The above maximum is for the oxidized form. Shows a maximal "
				+ "peak at 330 nm in the reduced form.";
		List<String> abNoteEvs = new ArrayList<>();
		String abNote2 = "These absorption peaks are "
				+ "for the tryptophylquinone cofactor.";
		List<String> abNoteEvs2 = new ArrayList<>();
		abNoteEvs2.add(ev3);
		abNoteEvs2.add(ev2);
		abNoteEvs.add(ev2);
		List<Map.Entry<String, List<String>>> notes = new ArrayList<>();
		notes.add(new AbstractMap.SimpleEntry<>(abNote, abNoteEvs));
		
		notes.add(new AbstractMap.SimpleEntry<>(abNote2, abNoteEvs2));
		
		
		Absorption absorption= buildAbsorption(abMax,  isApprox, abEvs, 
				notes);
		
		builder.absorption(absorption);
		String kpNoteStr = "The enzyme is substrate inhibited at high substrate "
				+ "concentrations (Ki=1.08 mM for tyramine).";
		List<String> kpNoteEvs =new ArrayList<>();
		kpNoteEvs.add(ev5);
		notes = new ArrayList<>();
		String kpNote2= "Another note is very very long.";
		List<String> kpNoteEvs2 =new ArrayList<>();
		kpNoteEvs2.add(ev5);
		notes.add(new AbstractMap.SimpleEntry<>(kpNoteStr, kpNoteEvs));
		
		notes.add(new AbstractMap.SimpleEntry<>(kpNote2, kpNoteEvs2));
		
		Note kpNote =buildNote(notes);

		List<MaximumVelocity> velocities = new ArrayList<>();
		float velocity1 = 17f;
		String enzyme1= "enzyme";
		List<String> mvEvs1 =new ArrayList<>();
		mvEvs1.add(ev4);
		MaximumVelocity  vel1 = buildMaximumVelocity( velocity1, "umol/min/mg",
				 enzyme1, mvEvs1);
		velocities.add(vel1);
		
		List<MichaelisConstant> constants = new ArrayList<>();
		
		float constant1 = 5.4f;
		String substrate1 ="tyramine";
		List<String> consEvs = new ArrayList<>();
		consEvs.add(ev1);
		MichaelisConstant constan1= buildMichaelisConstant( constant1, MichaelisConstantUnit.MICRO_MOL,
				 substrate1, consEvs);
		constants.add(constan1);
		
		float constant2 = 688f;
		String substrate2 ="pyridoxal";
		List<String> consEvs2 = new ArrayList<>();
		consEvs2.add(ev1);
		consEvs2.add(ev2);
		MichaelisConstant constan2= buildMichaelisConstant( constant2, MichaelisConstantUnit.MICRO_MOL,
				 substrate2, consEvs2);
		constants.add(constan2);
		KineticParameters kinetic= BPCPCommentBuilder.createKineticParameters(velocities, constants, kpNote);
		builder.kineticParameters(kinetic);
		BPCPComment comment = builder.build();
		
		doTest(ccLine, comment);
		doTestString(ccLineString, comment);
		doTestStringEv(ccLineStringEvidence, comment);
	}
	
	BPCPCommentBuilder buildComment(){
		return BPCPCommentBuilder.newInstance();
		
	}
	
	Absorption buildAbsorption(int max, boolean isApprox, List<String> evs, 
			String note, List<String> noteEvs){
	Note abNote = null;
		if(!Strings.isNullOrEmpty(note)) {
			List<EvidencedValue> evidencedValues =new ArrayList<>();
			evidencedValues.add(createEvidencedValue(note, noteEvs));
			abNote = CommentFactory.INSTANCE.createNote(evidencedValues);
		}
		return BPCPCommentBuilder.createAbsorption(max, isApprox, abNote, createEvidence(evs));
		
	}
	
	Absorption buildAbsorption(int max, boolean isApprox, List<String> evs, 
			List<Map.Entry<String, List<String>>> notes){
		Note abNote = null;
		if(!notes.isEmpty()) {
			List<EvidencedValue> evidencedValues =
					notes
					.stream().map(entry ->createEvidencedValue(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
			abNote = CommentFactory.INSTANCE.createNote(evidencedValues);
		}
		return BPCPCommentBuilder.createAbsorption(max, isApprox, abNote, createEvidence(evs));
		
	}
	
	PhDependence buildPHDependence(String value, List<String> evs){
		List<EvidencedValue> evidencedValues =new ArrayList<>();
		evidencedValues.add(createEvidencedValue(value, evs));
		return BPCPCommentBuilder.createPHDependence(evidencedValues);

		
	}
	PhDependence buildPHDependence(List<Map.Entry<String, List<String>>> notes){
		if(!notes.isEmpty()) {
			List<EvidencedValue> evidencedValues =
					notes
					.stream().map(entry ->createEvidencedValue(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
			return BPCPCommentBuilder.createPHDependence(evidencedValues);
		}
		return null;
	
	}
	
	RedoxPotential buildRedoxPotential(String value, List<String> evs){
		List<EvidencedValue> evidencedValues =new ArrayList<>();
		evidencedValues.add(createEvidencedValue(value, evs));
		return BPCPCommentBuilder.createRedoxPotential(evidencedValues);
	
	}
	RedoxPotential buildRedoxPotential(List<Map.Entry<String, List<String>>> notes){
		if(!notes.isEmpty()) {
			List<EvidencedValue> evidencedValues =
					notes
					.stream().map(entry ->createEvidencedValue(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
			return BPCPCommentBuilder.createRedoxPotential(evidencedValues);
		}
		return null;
	}
	
	TemperatureDependence buildTemperatureDependence(String value, List<String> evs){
		List<EvidencedValue> evidencedValues =new ArrayList<>();
		evidencedValues.add(createEvidencedValue(value, evs));
		return BPCPCommentBuilder.createTemperatureDependence(evidencedValues);
	}
	TemperatureDependence buildTemperatureDependence(List<Map.Entry<String, List<String>>> notes){
		if(!notes.isEmpty()) {
			List<EvidencedValue> evidencedValues =
					notes
					.stream().map(entry ->createEvidencedValue(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());
			return BPCPCommentBuilder.createTemperatureDependence(evidencedValues);
		}
		return null;
	}
	

	MichaelisConstant buildMichaelisConstant(float constant, MichaelisConstantUnit unit,
			String substrate, List<String> evs){
		return BPCPCommentBuilder.createMichaelisConstant(constant, unit, substrate, createEvidence(evs));
	}
	MaximumVelocity buildMaximumVelocity(float velocity, String unit,
			String enzyme,  List<String> evs){
		return BPCPCommentBuilder.createMaximumVelocity(velocity, unit, enzyme, createEvidence(evs));

	}

}
