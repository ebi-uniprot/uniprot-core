package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.Collections;
import java.util.List;

public class KeywordImpl extends EvidencedValueImpl implements Keyword {

	private KeywordImpl(){
		super("", Collections.emptyList());
	}
	public KeywordImpl(String value, List<Evidence> evidences)  {
        super(value, evidences);
    }

}
