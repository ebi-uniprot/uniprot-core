package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.util.Utils;

public interface Interactor extends Serializable {
	UniProtKBAccession getUniProtkbAccession();
	String getGeneName();
	String getChainId();	
	String getIntActId();
	
	default boolean hasChainId() {
		return !Utils.nullOrEmpty(getChainId());
	}
	default boolean hasHasGeneName() {
		return !Utils.nullOrEmpty(getGeneName());
	}
}
