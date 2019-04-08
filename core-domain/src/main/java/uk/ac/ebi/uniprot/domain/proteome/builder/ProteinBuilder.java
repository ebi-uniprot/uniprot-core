package uk.ac.ebi.uniprot.domain.proteome.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.proteome.GeneNameType;
import uk.ac.ebi.uniprot.domain.proteome.Protein;
import uk.ac.ebi.uniprot.domain.proteome.impl.ProteinImpl;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtAccessionBuilder;


public class ProteinBuilder implements Builder<ProteinBuilder,Protein> {
	private UniProtAccession accession;
	private UniProtEntryType entryType;
	private long sequenceLength;
	private String geneName;
	private GeneNameType geneNameType;
	
	public static ProteinBuilder newInstance() {
		return new ProteinBuilder();
	}
	
	@Override
	public Protein build() {
		return new  ProteinImpl( accession,  entryType,  sequenceLength,  geneName,
				 geneNameType);
		
	}
	public ProteinBuilder accession(String accession) {
		return accession(new UniProtAccessionBuilder(accession).build());
	}

	public ProteinBuilder accession(UniProtAccession accession) {
		this.accession = accession;
		return this;
	}
	public ProteinBuilder entryType(UniProtEntryType entryType) {
		this.entryType = entryType;
		return this;
	}
	public ProteinBuilder sequenceLength(long sequenceLength) {
		this.sequenceLength = sequenceLength;
		return this;
	}
	public ProteinBuilder geneName(String geneName) {
		this.geneName = geneName;
		return this;
	}
	public ProteinBuilder geneNameType(GeneNameType geneNameType) {
		this.geneNameType = geneNameType;
		return this;
	}
	@Override
	public ProteinBuilder from(Protein instance) {
		this.accession = instance.getAccession();
		this.entryType = instance.getEntryType();
		this.sequenceLength = instance.getSequenceLength();
		this.geneName = instance.getGeneName();
		this.geneNameType =instance.getGeneNameType();
		return this;
	}

}
