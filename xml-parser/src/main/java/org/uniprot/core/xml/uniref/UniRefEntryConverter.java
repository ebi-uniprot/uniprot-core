package org.uniprot.core.xml.uniref;

import java.util.stream.Collectors;

import org.uniprot.core.uniref.GoTerm;
import org.uniprot.core.uniref.GoTermType;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.builder.GoTermBuilder;
import org.uniprot.core.uniref.builder.UniRefEntryBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniref.EntryType;
import org.uniprot.core.xml.jaxb.uniref.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniref.PropertyType;
import org.uniprot.core.xml.uniprot.XmlConverterHelper;

import com.google.common.base.Strings;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
 */

public class UniRefEntryConverter implements Converter< EntryType, UniRefEntry> {
	public static final String PROPERTY_MEMBER_COUNT = "member count";
	public static final String PROPERTY_COMMON_TAXON = "common taxon";
	public static final String PROPERTY_COMMON_TAXON_ID = "common taxon ID";
	public static final String PROPERTY_GO_FUNCTION = "GO Molecular Function";
	public static final String PROPERTY_GO_COMPONENT = "GO Cellular Component";
	public static final String PROPERTY_GO_PROCESS = "GO Biological Process";

	
	private final MemberConverter memberConverter;
	private final RepresentativeMemberConverter representativeMemverConverter;
	private final ObjectFactory jaxbFactory;

	public UniRefEntryConverter() {
		this(new ObjectFactory());
	}

	public UniRefEntryConverter(ObjectFactory jaxbFactory) {
		this.jaxbFactory = jaxbFactory;
		memberConverter = new MemberConverter(jaxbFactory);
	    representativeMemverConverter = new RepresentativeMemberConverter(jaxbFactory);
	}

	@Override
	public UniRefEntry fromXml(EntryType xmlObj) {
		
		UniRefEntryBuilder builder = new UniRefEntryBuilder();
		builder.id(xmlObj.getId())
		.entryType(getTypeFromId(xmlObj.getId()))
		.name(xmlObj.getName())
		.updated(XmlConverterHelper.dateFromXml(xmlObj.getUpdated()))
		.representativeMember(representativeMemverConverter.fromXml(xmlObj.getRepresentativeMember()))
		.members(
				xmlObj.getMember()
				.stream().map(memberConverter::fromXml)
				.collect(Collectors.toList())			
				);
		
		updatePropertFromXml(builder, xmlObj);
		return builder.build();

	}
	private UniRefType getTypeFromId(String id) {
		if(!id.contains("_")) {
			throw new IllegalArgumentException("UniRef id:" + id + " is wrong format");
		}
		String val = id.substring(0, id.indexOf("_"));
		return UniRefType.valueOf(val);
	}
	private void updatePropertFromXml(UniRefEntryBuilder builder, EntryType jaxbEntry) {
		for (PropertyType property :jaxbEntry.getProperty()) {
			if (property.getType().equals(PROPERTY_COMMON_TAXON)) {
				builder.commonTaxonName(property.getValue());
			} else if (property.getType().equals(PROPERTY_COMMON_TAXON_ID)) {
				builder.commonTaxonId( Long.parseLong(property.getValue()));
			} else if (property.getType().equals(PROPERTY_GO_FUNCTION)) {
				builder.addGoTerm(createGoTerm(GoTermType.FUNCTION, property.getValue()));			
			} else if (property.getType().equals(PROPERTY_GO_COMPONENT)) {
				builder.addGoTerm(createGoTerm(GoTermType.COMPONENT, property.getValue()));
			} else if (property.getType().equals(PROPERTY_GO_PROCESS)) {
				builder.addGoTerm(createGoTerm(GoTermType.PROCESS, property.getValue()));
			} else if (property.getType().equals(PROPERTY_MEMBER_COUNT)) {
				
			} else {
				System.out.println("property.getType() = " + property.getType() +" not supported");
			}
		}
	}
	private GoTerm createGoTerm(GoTermType type, String id) {
		return new GoTermBuilder().type(type).id(id).build();
	}
	@Override
	public EntryType toXml(UniRefEntry uniObj) {
		EntryType jaxbEntry = jaxbFactory.createEntryType();
		jaxbEntry.setId(uniObj.getId().getValue());
		jaxbEntry.setName(uniObj.getName());
		
		jaxbEntry.setRepresentativeMember(representativeMemverConverter.toXml(uniObj.getRepresentativeMember()));
		jaxbEntry.setUpdated(XmlConverterHelper.dateToXml(uniObj.getUpdated()));
		uniObj.getMembers().stream().map(memberConverter::toXml	).forEach(jaxbEntry.getMember()::add);
		updatePropertyToXml(jaxbEntry, uniObj);
		return jaxbEntry;
	}
	private void updatePropertyToXml(EntryType jaxbEntry, UniRefEntry uniObj) {
		int count = uniObj.getMembers().size() +1;
		jaxbEntry.getProperty().add(createProperty(PROPERTY_MEMBER_COUNT, String.valueOf(count)));
		if(!Strings.isNullOrEmpty(uniObj.getCommonTaxonName())) {
			jaxbEntry.getProperty().add(createProperty(PROPERTY_COMMON_TAXON, uniObj.getCommonTaxonName()));
		}
		jaxbEntry.getProperty().add(createProperty(PROPERTY_COMMON_TAXON_ID, String.valueOf(uniObj.getCommonTaxonId())));
		uniObj.getGoTerms().stream().map(this::convert).forEach( jaxbEntry.getProperty()::add);
	}
	
	private PropertyType convert(GoTerm goTerm) {
		switch(goTerm.getType()) {
		case FUNCTION:
			return createProperty(PROPERTY_GO_FUNCTION, goTerm.getId());
		case COMPONENT:
			return createProperty(PROPERTY_GO_COMPONENT, goTerm.getId());
		case PROCESS:
			return createProperty(PROPERTY_GO_PROCESS, goTerm.getId());
		}
		return createProperty(goTerm.getType().toDisplayName(), goTerm.getId());
	}
	private PropertyType createProperty(String type, String value) {
		PropertyType property = jaxbFactory.createPropertyType();
		property.setType(type);
		property.setValue(value);
		return property;
	}
}
