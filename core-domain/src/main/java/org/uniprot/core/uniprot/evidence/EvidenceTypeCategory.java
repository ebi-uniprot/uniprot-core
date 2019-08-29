package org.uniprot.core.uniprot.evidence;

import org.uniprot.core.util.EnumDisplay;

/**
 *
 * @author jluo
 * @date: 29 Aug 2019
 *
*/

public enum EvidenceTypeCategory implements EnumDisplay<EvidenceCode> {
	I,
	C,
	A;

	@Override
	public String toDisplayName() {
		return name();
	}
}

