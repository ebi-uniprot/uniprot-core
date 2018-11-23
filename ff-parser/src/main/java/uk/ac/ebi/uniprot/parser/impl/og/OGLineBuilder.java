package uk.ac.ebi.uniprot.parser.impl.og;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

public class OGLineBuilder extends FFLineBuilderAbstr< List<Organelle> >
	implements FFLineBuilder<List<Organelle> > {

	final private static String SEPARATOR_AND1 ="and ";
	public OGLineBuilder(){
		super(LineType.OG);
	}
	@Override
	protected FFLine buildLine(List<Organelle> f, boolean showEvidence) {
		List<String> lls =new ArrayList<>();
		String[] seps = new String[] {SEPARATOR_COMA, SEPARATOR_AND};	
		List<String> lls1 = FFLineWrapper.buildLines(buildOrganelles(f, showEvidence, true, false ).toString(),
				seps, linePrefix, LINE_LENGTH);
		List<String> lls2 = FFLineWrapper.buildLines(buildOrganelles(f, showEvidence, true, true ).toString(),
				seps, linePrefix, LINE_LENGTH);
		lls.addAll(lls1);
		lls.addAll(lls2);
		
		return FFLines.create(lls);
	}
	@Override
	public String buildString(List<Organelle> f) {
		StringBuilder sb = buildOrganelles(f, false, false, false);
		StringBuilder plasmid = buildOrganelles(f, false, false, true);
		if(plasmid.length() >0){
			sb.append(" ").append(plasmid);
		}
		return sb.toString();
	}
	
	@Override
	public String buildStringWithEvidence(List<Organelle> f) {
		StringBuilder sb = buildOrganelles(f, true, false, false);
		StringBuilder plasmid = buildOrganelles(f, true, false, true);
		if(plasmid.length() >0){
			sb.append(" ").append(plasmid);
		}
		return sb.toString();
	}
	
	private StringBuilder buildOrganelles(List<Organelle> f, boolean showEvidence, boolean includeFFMarkup, boolean isPlasmid){
		List<Organelle> organelles =new ArrayList<>();
		for (Organelle organelle : f) {
			if(isPlasmid){
				if(organelle.getGeneEncodingType() ==GeneEncodingType.PLASMID){
					organelles.add(organelle);
				}
			}else{
				if(organelle.getGeneEncodingType() !=GeneEncodingType.PLASMID){
					organelles.add(organelle);
				}
			}
		}
		StringBuilder og = new StringBuilder();
		int size=  organelles.size();
		if(size ==0)
			return og;
    	if (includeFFMarkup)
    		og.append(linePrefix);
    	int inc=0;
    	for (Organelle organelle : organelles) {
    		if((inc>0)&&(inc ==(size-1)))
    			og.append(SEPARATOR_AND1);
    		og.append(toSwissprotString(organelle));
    		addEvidences(og, organelle, showEvidence);
    		
    		if ((size>1) && (inc !=(size-1)))
    			og.append(SEPARATOR_COMA); //see  Q9Z4N4 for an example
    		inc++;           
    	}
    	og.append(STOP);  	
		return og;
	}
	
	 private  String toSwissprotString(Organelle organelle) {

	        StringBuilder sb = new StringBuilder();
	        switch (organelle.getGeneEncodingType()) {
	          
	            case HYDROGENOSOME:
	            case MITOCHONDRION:
	            case NUCLEOMORPH:
	            case PLASTID:
	                sb.append(organelle.getGeneEncodingType().getName());
	                break;
	          
	            case PLASMID:
	                sb.append(organelle.getGeneEncodingType().getName());
	                if (!(organelle.getValue() == null || organelle.getValue().equals(""))) {
	                    sb.append(" ");
	                    sb.append(organelle.getValue());
	                }
	                break;
	            case APICOPLAST_PLASTID:
	            case CHLOROPLAST_PLASTID:
	            case CYANELLE_PLASTID:
	            case NON_PHOTOSYNTHETIC_PLASTID:
	            case CHROMATOPHORE_PLASTID:
	                sb.append("Plastid; ");
	                sb.append(organelle.getGeneEncodingType().getName());
	                break;
	            case UNKOWN:
	                break;
	        }

//				counter++;

	        return sb.toString();
	    }
}
