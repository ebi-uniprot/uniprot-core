package org.uniprot.core.interpro;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 *
 * @author jluo
 * @date: 12 Apr 2021
 *
 */

public interface InterProGroup extends Serializable {
	Abstract getEntryAbstract();

	String getName();

	InterProAc getInterProAc();

	String getShortName();

	UniProtKBAccession getUniProtAccession();

	InterProType getType();
}
