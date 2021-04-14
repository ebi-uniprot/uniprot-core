package org.uniprot.core.interpro;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 *
 * @author jluo
 * @date: 14 Apr 2021
 *
*/

public interface InterProMatchContainer extends Serializable {
	 long getId();
     UniProtKBAccession getUniProtAccession();
     List<InterProMatch> getInterProMatches();
}

