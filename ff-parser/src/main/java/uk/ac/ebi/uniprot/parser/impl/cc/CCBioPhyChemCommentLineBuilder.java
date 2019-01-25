package uk.ac.ebi.uniprot.parser.impl.cc;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.HasEvidences;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

/**
 * 
 * @author jieluo
 *"""CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:
                 |CC       Kinetic parameters:
                 |CC         KM=71 uM for ATP;
                 |CC         KM=98 uM for ADP;
                 |CC         KM=1.5 mM for acetate;
                 |CC         KM=0.47 mM for acetyl phosphate;
                 |CC       Temperature dependence:
                 |CC         Optimum temperature is 65 degrees Celsius. Protected from
                 |CC         thermal inactivation by ATP;
 */

public class CCBioPhyChemCommentLineBuilder extends CCLineBuilderAbstr<BPCPComment> {

	private static final String TEMPERATURE_DEPENDENCE = "Temperature dependence:";
	private static final String REDOX_POTENTIAL = "Redox potential:";
	private static final String P_H_DEPENDENCE = "pH dependence:";
	private static final String VMAX = "Vmax=";
	private static final String FOR = " for ";
	private static final String KM2 = "KM=";
	private static final String KINETIC_PARAMETERS = "Kinetic parameters:";

	private static final String NM = " nm";
	private static final String ABS_MAX = "Abs(max)=";
	private static final String ABSORPTION2 = "Absorption:";
	@Override
	protected List<String> buildCommentLines(BPCPComment comment, boolean includeFlatFileMarkings, boolean showEvidence){
		List<String> lines =new ArrayList<>();
	
		//first line
	//	if(includeFlatFileMarkings)
			lines.add(buildStart(comment, includeFlatFileMarkings));

		if (comment.getAbsorption() !=null) {
			lines.addAll( buildAbsorptionLine(comment.getAbsorption(), includeFlatFileMarkings, showEvidence) );
		}
		if (comment.getKineticParameters() !=null) {
			lines.addAll(buildKineticLine(comment.getKineticParameters(), includeFlatFileMarkings, showEvidence) );
		}
		if (comment.getPhDependence()!=null) {
			lines.addAll( buildPHDepLine(comment.getPhDependence(),  includeFlatFileMarkings,  showEvidence));
		}
		if (comment.getRedoxPotential() !=null) {
			lines.addAll( buildRedoxLine(comment.getRedoxPotential(),  includeFlatFileMarkings,  showEvidence));
		}
		if (comment.getTemperatureDependence() !=null) {
			lines.addAll( buildTempLine(comment.getTemperatureDependence(),  includeFlatFileMarkings,  showEvidence));
		}
		

		//adding evidence and stop

		
		if(!showEvidence)
			return lines;
		StringBuilder sb =new StringBuilder();
		List<String> lines2 =new ArrayList<>();
		for(int i=0; i<lines.size(); i++){
			if(i== lines.size()-1){
				sb.append(lines.get(i));
			}else{
				lines2.add(lines.get(i));
			}
		}
		if(includeFlatFileMarkings){
			List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR, CC_PREFIX_INDENT);	
			lines2.addAll(lls);
		}else{
			lines2.add(sb.toString());
		}
		return lines2;
	}
	private StringBuilder buildStart(String name, boolean includeFlatFileMarkings ){
		StringBuilder start = new StringBuilder();
		if (includeFlatFileMarkings)
			start.append(this.linePrefix);
		start.append(name);
		return start;
	}
	private List<String> buildAbsorptionLine(Absorption absorption, boolean includeFlatFileMarkings, boolean showEvidence) {
		StringBuilder sb = new StringBuilder();
		List<String> lines =new ArrayList<>();
		lines.add(buildStart(ABSORPTION2,includeFlatFileMarkings ).toString());


		if (includeFlatFileMarkings)
			sb.append(CC_PREFIX_INDENT);
		sb.append(ABS_MAX);
		if (absorption.isApproximate()) {
			sb.append("~");
		}
		sb.append(absorption.getMax());

		sb.append(NM);
		lines.addAll(addEvidences(sb, absorption, includeFlatFileMarkings, showEvidence, SEMICOLON, SEMICOLON ));     
		if(isValidNote(absorption.getNote())) {
		    lines.addAll(buildNote(absorption.getNote(), includeFlatFileMarkings, showEvidence));
			
		}
		return lines;
	}
	
	private List<String> buildNote(FreeText note, boolean includeFlatFileMarkings, boolean showEvidence) {
	    StringBuilder noteBuilder = new StringBuilder();
        if (includeFlatFileMarkings)
            noteBuilder.append(CC_PREFIX_INDENT);
        noteBuilder.append(NOTE);
        String freeTextStr= buildFreeText(note, showEvidence, STOP, SEMICOLON);
        noteBuilder.append(freeTextStr);          
        if (includeFlatFileMarkings) {
            return FFLineWrapper.buildLines(noteBuilder, SEPARATOR, CC_PREFIX_INDENT);
        }else{
            List<String> lines = new ArrayList<>();
            lines.add(noteBuilder.toString());
            return lines;
        }    
	}

	private List<String>  buildKineticLine(KineticParameters kps, boolean includeFlatFileMarkings, boolean showEvidence) {
		List<String> lines =new ArrayList<>();
		lines.add(buildStart(KINETIC_PARAMETERS, includeFlatFileMarkings ).toString());
	
		if (null != kps.getMichaelisConstants()) {
			List<MichaelisConstant> michaelisConstants = kps.getMichaelisConstants();
			for (MichaelisConstant michaelisConstant : michaelisConstants) {
				StringBuilder km = new StringBuilder();
				if (includeFlatFileMarkings)
					km.append(CC_PREFIX_INDENT);
				km.append(KM2);
				String val = getSigDig(michaelisConstant.getConstant());
				String val2 = "" +michaelisConstant.getConstant();
				if(val.contains(STOP)){
					val =val2;
				}

				val = val.replace(",", "");
				km.append(val);
				km.append(SPACE);
				km.append(michaelisConstant.getUnit().toDisplayNameString());
				km.append(FOR);
				if(!Strings.isNullOrEmpty(michaelisConstant.getSubstrate()))
				km.append(michaelisConstant.getSubstrate());    
				lines.addAll(addEvidences(km, michaelisConstant, includeFlatFileMarkings, showEvidence, SEMICOLON, ";" ));

			}
		}

		if (null != kps.getMaximumVelocities()
				&&
				!kps.getMaximumVelocities().isEmpty()) {
			List<MaximumVelocity> maximumVelocities = kps.getMaximumVelocities();
			for (MaximumVelocity maximumVelocity : maximumVelocities) {
				StringBuilder vm = new StringBuilder();
				if (includeFlatFileMarkings)
					vm.append(CC_PREFIX_INDENT);
				vm.append(VMAX);
				String val =getSigDig(maximumVelocity.getVelocity());
				String val2 = "" +maximumVelocity.getVelocity();
				if(val.contains(STOP)){
					val =val2;
				}
				val = val.replace(",", "");
				vm.append(val);
				vm.append(SPACE);
				vm.append(maximumVelocity.getUnit());
				vm.append(SPACE);
				if(!Strings.isNullOrEmpty(maximumVelocity.getEnzyme()))
					vm.append(maximumVelocity.getEnzyme());
				lines.addAll(addEvidences(vm, maximumVelocity, includeFlatFileMarkings, showEvidence,  SEMICOLON, SEMICOLON));
			}
		}
		if(isValidNote(kps.getNote())) {
		    lines.addAll(buildNote(kps.getNote(), includeFlatFileMarkings, showEvidence));
			
		}
		return lines;
	}
	private List<String> addEvidences(StringBuilder sb, HasEvidences he, boolean includeFlatFileMarkings,
									  boolean showEvidence, String postfix, String postfixNoEvidence){
		List<String> lines =new ArrayList<>();
		sb =addEvidence(he, sb, showEvidence, postfix, postfixNoEvidence);
		
		if (includeFlatFileMarkings) {
			lines.addAll(FFLineWrapper.buildLines(sb, SEPARATOR, CC_PREFIX_INDENT));
		}else{
			lines.add(sb.toString());
		}
		return lines;
	}
	

	public static String getSigDig(Double number){
		String temp = number.toString();
		if (temp.indexOf(".") > 0){
			while (temp.endsWith("0")){
				temp = temp.substring(0, temp.length()-1);
			}
			if (temp.endsWith(".")){
				temp = temp.substring(0, temp.length()-1);
			}
		}
		return temp;
	}
	
	private List<String>  buildPHDepLine(PhDependence depend, boolean includeFlatFileMarkings, boolean showEvidence) {    
	    return buildDependence(depend, P_H_DEPENDENCE, includeFlatFileMarkings, showEvidence);	
	}
	private List<String>  buildRedoxLine(RedoxPotential redox, boolean includeFlatFileMarkings, boolean showEvidence) {
        return buildDependence(redox, REDOX_POTENTIAL, includeFlatFileMarkings, showEvidence);   
    }
    private List<String>  buildTempLine(TemperatureDependence temp, boolean includeFlatFileMarkings, boolean showEvidence) {
        return buildDependence(temp, TEMPERATURE_DEPENDENCE, includeFlatFileMarkings, showEvidence);   
    }

	private List<String>  buildDependence(FreeText depend, String prefix,  boolean includeFlatFileMarkings, boolean showEvidence) {
	    List<String> lines =new ArrayList<>();
        lines.add(buildStart(prefix, includeFlatFileMarkings ).toString());
        StringBuilder noteBuilder = new StringBuilder();
        if (includeFlatFileMarkings)
            noteBuilder.append(CC_PREFIX_INDENT);
        String freeTextStr= buildFreeText(depend, showEvidence, STOP, SEMICOLON);
        noteBuilder.append(freeTextStr);
        if (includeFlatFileMarkings) {
            lines.addAll(FFLineWrapper.buildLines(noteBuilder, SEPARATOR, CC_PREFIX_INDENT));
        }else{
            lines.add(noteBuilder.toString());
        }    
    
        return lines;
	}
	
}
