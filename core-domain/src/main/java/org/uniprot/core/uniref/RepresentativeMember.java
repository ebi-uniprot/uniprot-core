package org.uniprot.core.uniref;

import org.uniprot.core.Sequence;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public interface RepresentativeMember extends UniRefMember {
	Sequence getSequence();
}

