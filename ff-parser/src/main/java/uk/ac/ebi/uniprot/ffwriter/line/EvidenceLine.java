package uk.ac.ebi.uniprot.ffwriter.line;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;


public class EvidenceLine {
//	static final private EvidenceIdComparator comparator =new EvidenceIdComparator();
	 private final static String SEPARATOR1=",";
	 private final static String SEPARATOR2=", ";
	 public static String export(List<Evidence> evIds){
		String s="";
		Collections.sort(evIds);
		boolean first =true;
		boolean isEco =false;
		for(Evidence evid:evIds){
			if(evid.getValue().startsWith("ECO"))
				isEco =true;
			if(!first) {
				if(evid.getValue().startsWith("ECO")){
					s+=SEPARATOR2;
					
				}else
					s+=SEPARATOR1;
				
			}
			s+=evid.getValue();
			first =false;
		}
		if(s.length()>0){
			if(isEco){
				s=" {"+s+"}";
			}else{
				s="{"+s+"}";
			}
		}
		return s;
	}
	

}
