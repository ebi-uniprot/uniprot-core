package uk.ac.ebi.uniprot.domain.proteome.impl;

import java.util.Objects;

import uk.ac.ebi.uniprot.domain.proteome.GeneNameType;
import uk.ac.ebi.uniprot.domain.proteome.Protein;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;

public class ProteinImpl implements Protein {

	private static final long serialVersionUID = 1L;
	private UniProtAccession accession;
	private UniProtEntryType entryType;
	private long sequenceLength;
	private String geneName;
	private GeneNameType geneNameType;

	private ProteinImpl() {

	}

	public ProteinImpl(UniProtAccession accession, UniProtEntryType entryType, long sequenceLength, String geneName,
			GeneNameType geneNameType) {
		this.accession = accession;
		this.entryType = entryType;
		this.sequenceLength = sequenceLength;
		this.geneName = geneName;
		this.geneNameType = geneNameType;
	}

	@Override
	public UniProtAccession getAccession() {
		return accession;
	}

	@Override
	public UniProtEntryType getEntryType() {
		return entryType;
	}

	@Override
	public long getSequenceLength() {
		return sequenceLength;
	}

	@Override
	public String getGeneName() {
		return geneName;
	}

	@Override
	public GeneNameType getGeneNameType() {
		return geneNameType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accession, entryType, geneName, geneNameType, sequenceLength);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProteinImpl other = (ProteinImpl) obj;
		return Objects.equals(accession, other.accession) && Objects.equals(entryType, other.entryType)
				&& Objects.equals(geneName, other.geneName) && Objects.equals(geneNameType, other.geneNameType)
				&& Objects.equals(sequenceLength, other.sequenceLength);

	}

}
