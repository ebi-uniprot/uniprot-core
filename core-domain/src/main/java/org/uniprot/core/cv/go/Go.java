package org.uniprot.core.cv.go;

/**
 *
 * @author jluo
 * @date: 8 Apr 2021
 *
*/

public interface Go extends GoTerm {
	GoAspect getAspect();
	GoEvidenceType getGoEvidenceType();
	String getGoEvidenceSource();
}

