package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescriptionBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

public class ProteinDescriptionConverter implements Converter<ProteinType, ProteinDescription>,
ToXmlDbReferences<ProteinDescription> {
	private final RecNameConverter recNameConverter;
	private final AltNameConverter altNameConverter;
	private final SubNameConverter subNameConverter;
	private final NameConverter nameConverter;
	private final DomainConverter domainConverter;
	private final ComponentConverter componentConverter;
	private final ObjectFactory xmlUniprotFactory;

	public ProteinDescriptionConverter(
			EvidenceIndexMapper evRefMapper,
			ObjectFactory xmlUniprotFactory) {
		 this.nameConverter = new NameConverter(evRefMapper, xmlUniprotFactory);
		ECConverter ecConverter = new ECConverter(evRefMapper, xmlUniprotFactory);
		this.recNameConverter =new RecNameConverter(nameConverter, ecConverter, xmlUniprotFactory);
		this.altNameConverter = new AltNameConverter(nameConverter, ecConverter, xmlUniprotFactory);
		this.subNameConverter =new SubNameConverter(nameConverter, ecConverter, xmlUniprotFactory);
		this.domainConverter =new DomainConverter(
				new RecNameConverter(nameConverter, ecConverter, xmlUniprotFactory),
				new AltNameConverter(nameConverter, ecConverter, xmlUniprotFactory),
				xmlUniprotFactory
				);
		this.componentConverter =new ComponentConverter(
				new RecNameConverter(nameConverter, ecConverter, xmlUniprotFactory),
				new AltNameConverter(nameConverter, ecConverter, xmlUniprotFactory),
				xmlUniprotFactory
				);
		this.xmlUniprotFactory = xmlUniprotFactory;
		
	}
	
	
	public ProteinDescriptionConverter(RecNameConverter recNameConverter, AltNameConverter altNameConverter,
			SubNameConverter subNameConverter, NameConverter nameConverter) {
		this(recNameConverter, altNameConverter, subNameConverter, nameConverter, new ObjectFactory());

	}

	public ProteinDescriptionConverter(RecNameConverter recNameConverter, AltNameConverter altNameConverter,
			SubNameConverter subNameConverter, NameConverter nameConverter, ObjectFactory xmlUniprotFactory) {
		this.recNameConverter = recNameConverter;
		this.altNameConverter = altNameConverter;
		this.subNameConverter = subNameConverter;
		this.nameConverter = nameConverter;
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.domainConverter = new DomainConverter(recNameConverter, altNameConverter, xmlUniprotFactory);
		this.componentConverter = new ComponentConverter(recNameConverter, altNameConverter, xmlUniprotFactory);

	}

	@Override
	public ProteinDescription fromXml(ProteinType xmlObj) {
		ProteinDescriptionBuilder builder =ProteinDescriptionBuilder.newInstance();
		if(xmlObj.getRecommendedName() !=null) {
			builder.recommendedName(recNameConverter.fromXml(xmlObj.getRecommendedName()));
		}
		if(!xmlObj.getAlternativeName().isEmpty()) {
			builder.alternativeNames(
			xmlObj.getAlternativeName().stream()
			.map(altNameConverter::fromXml)
			.collect(Collectors.toList()));
		}
		if(!xmlObj.getSubmittedName().isEmpty()) {
			builder.submissionNames(
			xmlObj.getSubmittedName().stream()
			.map(subNameConverter::fromXml)
			.collect(Collectors.toList()));
		}
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
		if(!xmlObj.getComponent().isEmpty()) {
			builder.contains(
			xmlObj.getComponent().stream()
			.map(componentConverter::fromXml)
			.collect(Collectors.toList()));
		}
		if(!xmlObj.getDomain().isEmpty()) {
			builder.includes(
			xmlObj.getDomain().stream()
			.map(domainConverter::fromXml)
			.collect(Collectors.toList()));
		}
		return builder.build();
	}

	@Override
	public ProteinType toXml(ProteinDescription uniObj) {
		ProteinType proteinType = xmlUniprotFactory.createProteinType();
		if (uniObj.getRecommendedName() != null) {
			proteinType.setRecommendedName(recNameConverter.toXml(uniObj.getRecommendedName()));
		}
		uniObj.getAlternativeNames().forEach(val -> proteinType.getAlternativeName().add(altNameConverter.toXml(val)));
		uniObj.getSubmissionNames().forEach(val -> proteinType.getSubmittedName().add(subNameConverter.toXml(val)));
		if (uniObj.getAllergenName() != null) {
			proteinType.setAllergenName(nameConverter.toXml(uniObj.getAllergenName()));
		}

		if (uniObj.getBiotechName() != null) {
			proteinType.setBiotechName(nameConverter.toXml(uniObj.getBiotechName()));
		}

		uniObj.getCdAntigenNames().forEach(val -> proteinType.getCdAntigenName().add(nameConverter.toXml(val)));

		uniObj.getInnNames().forEach(val -> proteinType.getInnName().add(nameConverter.toXml(val)));

		uniObj.getContains().forEach(val -> proteinType.getComponent().add(componentConverter.toXml(val)));
		
		uniObj.getIncludes().forEach(val -> proteinType.getDomain().add(domainConverter.toXml(val)));
		

		return proteinType;
	}


	@Override
	public List<DbReferenceType> toXmlDbReferences(ProteinDescription uniObj) {
		List<DbReferenceType> result = new ArrayList<>();
		if (uniObj.getRecommendedName() != null) {
			result.addAll(recNameConverter.toXmlDbReferences(uniObj.getRecommendedName()));
		}
		uniObj.getAlternativeNames().forEach(val -> {
			altNameConverter.toXmlDbReferences(val)
			.forEach(dbType -> {
				if(!result.contains(dbType)) {
					result.add(dbType);
				}
			});	
		});
		
		uniObj.getSubmissionNames().forEach(val -> {
			altNameConverter.toXmlDbReferences(val)
			.forEach(dbType -> {
				if(!result.contains(dbType)) {
					result.add(dbType);
				}
			});	
		});
		uniObj.getContains().forEach(val -> {
			componentConverter.toXmlDbReferences(val)
			.forEach(dbType -> {
				if(!result.contains(dbType)) {
					result.add(dbType);
				}
			});	
		});
		
		uniObj.getIncludes().forEach(val -> {
			domainConverter.toXmlDbReferences(val)
			.forEach(dbType -> {
				if(!result.contains(dbType)) {
					result.add(dbType);
				}
			});	
		});
		
		List<DbReferenceType> filtered =new ArrayList<>();
		for (DbReferenceType db : result) {
			Optional<DbReferenceType> opDb = filtered.stream().
					filter(val -> val.getId().equals(db.getId())).findFirst();
			if(opDb.isPresent()) {
				for(Integer ev: db.getEvidence()) {
					if(!opDb.get().getEvidence().contains(ev)) {
						opDb.get().getEvidence().add(ev);
					}
				}
			}else {
				filtered.add(db);
			}
		}
		return filtered;
	}
}
