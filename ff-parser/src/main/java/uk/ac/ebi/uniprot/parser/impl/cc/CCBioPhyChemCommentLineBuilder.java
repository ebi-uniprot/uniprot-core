package uk.ac.ebi.uniprot.parser.impl.cc;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SEMI_COMA;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SEPARATOR;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SPACE;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.STOP;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;
import uk.ac.ebi.uniprot.domain.uniprot.HasFreeText;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BioPhysicoChemicalPropertiesComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PHDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comments.RedoxPotential;
import uk.ac.ebi.uniprot.domain.uniprot.comments.TemperatureDependence;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineWrapper;

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

public class CCBioPhyChemCommentLineBuilder extends CCLineBuilderAbstr<BioPhysicoChemicalPropertiesComment> {

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
	protected List<String> buildCommentLines(BioPhysicoChemicalPropertiesComment comment, boolean includeFlatFileMarkings, boolean showEvidence){
		List<String> lines =new ArrayList<>();
	
		//first line
	//	if(includeFlatFileMarkings)
			lines.add(buildStart(comment, includeFlatFileMarkings));

		if (comment.getAbsorption().isPresent()) {
			lines.addAll( buildAbsorptionLine(comment.getAbsorption().get(), includeFlatFileMarkings, showEvidence) );
		}
		if (comment.getKineticParameters().isPresent()) {
			lines.addAll(buildKineticLine(comment.getKineticParameters().get(), includeFlatFileMarkings, showEvidence) );
		}
		if (comment.getPHDependence().isPresent()) {
			lines.addAll( buildPHDepLine(comment.getPHDependence().get(),  includeFlatFileMarkings,  showEvidence));
		}
		if (comment.getRedoxPotential().isPresent()) {
			lines.addAll( buildRedoxLine(comment.getRedoxPotential().get(),  includeFlatFileMarkings,  showEvidence));
		}
		if (comment.getTemperatureDependence().isPresent()) {
			lines.addAll( buildTempLine(comment.getTemperatureDependence().get(),  includeFlatFileMarkings,  showEvidence));
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
		if (absorption.isApproximation()) {
			sb.append("~");
		}
		sb.append(absorption.getMax());

		sb.append(NM);
		lines.addAll(addEvidences(sb, absorption, includeFlatFileMarkings, showEvidence, SEMI_COMA, SEMI_COMA ));     

		if (absorption.getNote().isPresent()) {
		    lines.addAll(buildNote(absorption.getNote().get(), includeFlatFileMarkings, showEvidence));
			
		}
		return lines;
	}
	
	private List<String> buildNote(HasFreeText note, boolean includeFlatFileMarkings, boolean showEvidence) {
	    StringBuilder noteBuilder = new StringBuilder();
        if (includeFlatFileMarkings)
            noteBuilder.append(CC_PREFIX_INDENT);
        noteBuilder.append(NOTE);
        String freeTextStr= buildFreeText(note, showEvidence, STOP, SEMI_COMA);
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
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		NumberFormat vmaxnf = NumberFormat.getNumberInstance(Locale.ENGLISH);
		if (null != kps.getMichaelisConstants()) {
			List<MichaelisConstant> michaelisConstants = kps.getMichaelisConstants();
			for (MichaelisConstant michaelisConstant : michaelisConstants) {
				StringBuilder km = new StringBuilder();
				if (includeFlatFileMarkings)
					km.append(CC_PREFIX_INDENT);
				km.append(KM2);
				String val = nf.format(michaelisConstant.getConstant());
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
				lines.addAll(addEvidences(km, michaelisConstant, includeFlatFileMarkings, showEvidence, SEMI_COMA, ";" ));

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
				String val = vmaxnf.format(maximumVelocity.getVelocity());
				String val2 = "" +maximumVelocity.getVelocity();
				if(val.contains(STOP)){
					val =val2;
				}
				val = val.replace(",", "");
				vm.append(val);
				vm.append(SPACE);
				vm.append(maximumVelocity.getVelocityUnit());
				vm.append(SPACE);
				if(!Strings.isNullOrEmpty(maximumVelocity.getEnzyme()))
					vm.append(maximumVelocity.getEnzyme());
				lines.addAll(addEvidences(vm, maximumVelocity, includeFlatFileMarkings, showEvidence,  SEMI_COMA, SEMI_COMA));
			}
		}
	
		if ( kps.getNote().isPresent()) {
		    lines.addAll(buildNote(kps.getNote().get(), includeFlatFileMarkings, showEvidence));
			
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
	

	
	
	private List<String>  buildPHDepLine(PHDependence depend, boolean includeFlatFileMarkings, boolean showEvidence) {    
	    return buildDependence(depend, P_H_DEPENDENCE, includeFlatFileMarkings, showEvidence);	
	}
	private List<String>  buildRedoxLine(RedoxPotential redox, boolean includeFlatFileMarkings, boolean showEvidence) {
        return buildDependence(redox, REDOX_POTENTIAL, includeFlatFileMarkings, showEvidence);   
    }
    private List<String>  buildTempLine(TemperatureDependence temp, boolean includeFlatFileMarkings, boolean showEvidence) {
        return buildDependence(temp, TEMPERATURE_DEPENDENCE, includeFlatFileMarkings, showEvidence);   
    }

	private List<String>  buildDependence(HasFreeText depend, String prefix,  boolean includeFlatFileMarkings, boolean showEvidence) {
	    List<String> lines =new ArrayList<>();
        lines.add(buildStart(prefix, includeFlatFileMarkings ).toString());
        StringBuilder noteBuilder = new StringBuilder();
        if (includeFlatFileMarkings)
            noteBuilder.append(CC_PREFIX_INDENT);
        String freeTextStr= buildFreeText(depend, showEvidence, STOP, SEMI_COMA);
        noteBuilder.append(freeTextStr);
        if (includeFlatFileMarkings) {
            lines.addAll(FFLineWrapper.buildLines(noteBuilder, SEPARATOR, CC_PREFIX_INDENT));
        }else{
            lines.add(noteBuilder.toString());
        }    
    
        return lines;
	}
	
}
