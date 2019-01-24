package uk.ac.ebi.uniprot.parser.ffwriter.impl;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class LineBuilderHelper {
//	static final private EvidenceIdComparator comparator =new EvidenceIdComparator();
	 private final static String SEPARATOR1=",";
	 private final static String SEPARATOR2=", ";
	 public static String export(List<Evidence> evs){
		String s="";
		List<Evidence> evIds =evs.stream().collect(Collectors.toList());
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
	 static public class EvidenceComparator implements Comparator<Evidence> {
			public int compare(Evidence ev1, Evidence ev2){
				String val1 =ev1.getValue();
				String val2=ev2.getValue();
				if(val1.substring(0, 2).compareTo(val2.substring(0, 2)) !=0)
						return val1.substring(0, 2).compareTo(val2.substring(0, 2));
				Integer intVa1= Integer.parseInt(val1.substring(2));
				Integer intVa2= Integer.parseInt(val2.substring(2));
				return intVa1.compareTo(intVa2);
			}
		}

}
