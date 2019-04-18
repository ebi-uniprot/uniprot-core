package uk.ac.ebi.uniprot.xml.proteome;

import uk.ac.ebi.uniprot.domain.proteome.GeneNameType;
import uk.ac.ebi.uniprot.domain.proteome.Protein;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteinBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.EntryType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.GeneType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;

public class ProteinConverter implements Converter<GeneType, Protein> {
	private final ObjectFactory xmlFactory;

	public ProteinConverter() {
		this(new ObjectFactory());
	}

	public ProteinConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
	}
	
	@Override
	public Protein fromXml(GeneType xmlObj) {
		ProteinBuilder proteinBuilder = ProteinBuilder.newInstance();
		proteinBuilder.accession(xmlObj.getAccession()).geneName(xmlObj.getGeneName())
				.entryType(fromXml(xmlObj.getEntryType()))
				.geneNameType(GeneNameType.fromValue(xmlObj.getGeneNameType().value())).sequenceLength(xmlObj.getLength());

		return proteinBuilder.build();
	}

	@Override
	public GeneType toXml(Protein uniObj) {
		GeneType xmlObj = xmlFactory.createGeneType();
		xmlObj.setAccession(uniObj.getAccession().getValue());
		xmlObj.setEntryType(toXml(uniObj.getEntryType()));
		xmlObj.setGeneName(uniObj.getGeneName());
		xmlObj.setLength(uniObj.getSequenceLength());
		xmlObj.setGeneNameType(uk.ac.ebi.uniprot.xml.jaxb.proteome.GeneNameType.fromValue(uniObj.getGeneNameType().getName()));
		return xmlObj;
	}
	

	private UniProtEntryType fromXml(EntryType xmlObj) {
		if (xmlObj == EntryType.SWISS_PROT)
			return UniProtEntryType.SWISSPROT;
		else
			return UniProtEntryType.TREMBL;
	}

	private EntryType toXml(UniProtEntryType uniObj) {
		if(uniObj == UniProtEntryType.SWISSPROT)
			return EntryType.SWISS_PROT;
		else
			return EntryType.TR_EMBL;
	}
}
