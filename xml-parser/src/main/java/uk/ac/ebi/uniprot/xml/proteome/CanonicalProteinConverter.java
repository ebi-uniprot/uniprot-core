package uk.ac.ebi.uniprot.xml.proteome;

import java.util.stream.Collectors;

import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.builder.CanonicalProteinBuilder;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.CanonicalGene;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;

public class CanonicalProteinConverter implements Converter<CanonicalGene, CanonicalProtein>{
	private final ObjectFactory xmlFactory;
	private final ProteinConverter proteinConverter;

	public CanonicalProteinConverter() {
		this(new ObjectFactory());
	}

	public CanonicalProteinConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
		this.proteinConverter =new ProteinConverter(xmlFactory);
	}
	@Override
	public CanonicalProtein fromXml(CanonicalGene xmlObj) {
		CanonicalProteinBuilder builder = CanonicalProteinBuilder.newInstance();

		builder.canonicalProtein(proteinConverter.fromXml(xmlObj.getGene()))
				.relatedProteins(xmlObj.getRelatedGene().stream().map(proteinConverter::fromXml).collect(Collectors.toList()));

		return builder.build();
	}

	@Override
	public CanonicalGene toXml(CanonicalProtein uniObj) {
		CanonicalGene gene =xmlFactory.createCanonicalGene();
		gene.setGene(proteinConverter.toXml(uniObj.getCanonicalProtein()));	
		uniObj.getRelatedProteins().stream()
		.map(proteinConverter::toXml)
		.forEach(val -> gene.getRelatedGene().add(val));
		return gene;
	}


}
