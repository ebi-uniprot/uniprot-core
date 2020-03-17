package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.UniProtKBAccession;

public interface Interactor extends Serializable {
	String getChainId();
	UniProtKBAccession getUniProtKBAccession();
	String getInterActId();
	boolean hasChain();
}
