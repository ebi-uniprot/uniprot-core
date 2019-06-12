package uk.ac.ebi.uniprot.xml.proteome;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.proteome.Component;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeXReferenceType;
import uk.ac.ebi.uniprot.domain.proteome.builder.ComponentBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ComponentType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ComponentTypeType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;

public class ComponentConverter implements Converter<ComponentType, Component> {
	private final ObjectFactory xmlFactory;

	public ComponentConverter() {
		this(new ObjectFactory());
	}

	public ComponentConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
	}

	@Override
	public Component fromXml(ComponentType xmlObj) {
		ComponentBuilder builder = ComponentBuilder.newInstance();
		builder.name(xmlObj.getName());
		builder.description(xmlObj.getDescription());
		List<DBCrossReference<ProteomeXReferenceType>> xrefs = new ArrayList<>();
		if (!xmlObj.getGenomeAccession().isEmpty()) {
			xmlObj.getGenomeAccession().stream()
					.map(val -> new DBCrossReferenceBuilder<ProteomeXReferenceType>()
							.databaseType(ProteomeXReferenceType.GENOME_ACCESSION).id(val).build())
					.forEach(val -> xrefs.add(val));

		}
		if (!Strings.isNullOrEmpty(xmlObj.getBiosampleId())) {
			xrefs.add(new DBCrossReferenceBuilder<ProteomeXReferenceType>()
					.databaseType(ProteomeXReferenceType.BIOSAMPLE).id(xmlObj.getBiosampleId()).build());
		}
		builder.dbXReferences(xrefs).proteinCount(xmlObj.getCount());
		
		builder.type(uk.ac.ebi.uniprot.domain.proteome.ComponentType.fromValue(xmlObj.getType().value()));
	
		return builder.build();
	}

	@Override
	public ComponentType toXml(Component uniObj) {
		ComponentType xmlObj = xmlFactory.createComponentType();
		xmlObj.setName(uniObj.getName());
		xmlObj.setDescription(uniObj.getDescription());
		Optional<DBCrossReference<ProteomeXReferenceType>> biosample = uniObj.getDbXReferences().stream()
				.filter(val -> val.getDatabaseType() == ProteomeXReferenceType.BIOSAMPLE).findFirst();
		if (biosample.isPresent()) {
			xmlObj.setBiosampleId(biosample.get().getId());
		}
		uniObj.getDbXReferences().stream()
				.filter(val -> val.getDatabaseType() == ProteomeXReferenceType.GENOME_ACCESSION).map(val -> val.getId())
				.forEach(val -> xmlObj.getGenomeAccession().add(val));
		xmlObj.setCount(uniObj.getProteinCount());
		if(uniObj.getType() !=null) {
		ComponentTypeType type = ComponentTypeType.fromValue(uniObj.getType().getName());
		xmlObj.setType(type);
		}else {
			xmlObj.setType(ComponentTypeType.UNPLACED);
		}
		return xmlObj;
	}

}
