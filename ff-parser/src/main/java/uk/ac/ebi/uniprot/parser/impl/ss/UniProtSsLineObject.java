package uk.ac.ebi.uniprot.parser.impl.ss;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;


public class UniProtSsLineObject {
	public List<InternalLine> internalLines = new ArrayList<>();
	public List<SourceLine> sourceLines = new ArrayList<>();
	public List<Evidence> evidences =new ArrayList<>();
}

