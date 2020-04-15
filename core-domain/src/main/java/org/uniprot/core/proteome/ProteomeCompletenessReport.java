package org.uniprot.core.proteome;

import java.io.Serializable;

/**
 *
 * @author jluo
 * @date: 1 Apr 2020
 *
*/
public interface ProteomeCompletenessReport extends Serializable {

	BuscoReport getBuscoReport();

	CPDReport getCPDReport();

}

