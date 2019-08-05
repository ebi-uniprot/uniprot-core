package org.uniprot.core.xml.uniprot.description;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.description.ProteinSection;
import org.uniprot.core.uniprot.description.builder.ProteinSectionBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ProteinType.Domain;

public class DomainConverter implements Converter<Domain, ProteinSection> ,ToXmlDbReferences<ProteinSection> {
    private final NameConverter nameConverter;
	private final RecNameConverter recNameConverter;
	private final AltNameConverter altNameConverter;
	private final ObjectFactory xmlUniprotFactory;
	public DomainConverter(NameConverter nameConverter,
			RecNameConverter recNameConverter, AltNameConverter altNameConverter) {
		this(nameConverter, recNameConverter, altNameConverter, new ObjectFactory());
	}
	public DomainConverter(NameConverter nameConverter,
			RecNameConverter recNameConverter, AltNameConverter altNameConverter,
			ObjectFactory xmlUniprotFactory) {
		this.nameConverter = nameConverter;
		this.recNameConverter = recNameConverter;
		this.altNameConverter = altNameConverter;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public ProteinSection fromXml(Domain xmlObj) {
		ProteinSectionBuilder builder =
				new ProteinSectionBuilder()
				.recommendedName(recNameConverter.fromXml(xmlObj.getRecommendedName()))
				.alternativeNames(xmlObj.getAlternativeName().stream()
										  .map(altNameConverter::fromXml).collect(Collectors.toList()));
		if(xmlObj.getAllergenName() !=null) {
			builder.allergenName(nameConverter.fromXml(xmlObj.getAllergenName()));
		}
		if(xmlObj.getBiotechName() !=null) {
			builder.biotechName(nameConverter.fromXml(xmlObj.getBiotechName()));
		}
		if(!xmlObj.getCdAntigenName().isEmpty()) {
			builder.cdAntigenNames(
			xmlObj.getCdAntigenName().stream()
			.map(nameConverter::fromXml)
			.collect(Collectors.toList()));
		}
		if(!xmlObj.getInnName().isEmpty()) {
			builder.innNames(
					xmlObj.getInnName().stream()
					.map(nameConverter::fromXml)
					.collect(Collectors.toList()));
		}
		
		return builder.build();
	}

	@Override
	public Domain toXml(ProteinSection uniObj) {
		Domain domain = xmlUniprotFactory.createProteinTypeDomain();
		domain.setRecommendedName(recNameConverter.toXml(uniObj.getRecommendedName()));
		uniObj.getAlternativeNames().forEach(val -> domain.getAlternativeName().add(altNameConverter.toXml(val)));		
		 if (uniObj.getAllergenName() != null) {
			 domain.setAllergenName(nameConverter.toXml(uniObj.getAllergenName()));
			}

			if (uniObj.getBiotechName() != null) {
				domain.setBiotechName(nameConverter.toXml(uniObj.getBiotechName()));
			}

			uniObj.getCdAntigenNames().forEach(val -> domain.getCdAntigenName().add(nameConverter.toXml(val)));

			uniObj.getInnNames().forEach(val -> domain.getInnName().add(nameConverter.toXml(val)));

		return domain;

	}
	@Override
	public List<DbReferenceType> toXmlDbReferences(ProteinSection t) {
		List<DbReferenceType> result = new ArrayList<>();
		result.addAll(
		recNameConverter.toXmlDbReferences(t.getRecommendedName()));
		t.getAlternativeNames().forEach(val -> {
			altNameConverter.toXmlDbReferences(val)
			.forEach(dbType -> {
				if(!result.contains(dbType)) {
					result.add(dbType);
				}
			});	
		});
		return result;
	}
}
