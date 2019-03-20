package uk.ac.ebi.uniprot.cv.go;

import java.util.List;


public interface GoRelation {
	List<String> getIsA(String goId) ;
	List<String> getPartOf(String goId);
	List<String> getChildren(String goId);
}
