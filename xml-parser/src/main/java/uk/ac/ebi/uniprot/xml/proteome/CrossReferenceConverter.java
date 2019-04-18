package uk.ac.ebi.uniprot.xml.proteome;

import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeXReferenceType;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.PropertyType;

public class CrossReferenceConverter implements Converter<DbReferenceType, DBCrossReference<ProteomeXReferenceType>> {
	private static final String GC_SET_ACC = "GCSetAcc";
	private final ObjectFactory xmlFactory;

	public CrossReferenceConverter() {
		this(new ObjectFactory());
	}

	public CrossReferenceConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
	}
	
	@Override
	public DBCrossReference<ProteomeXReferenceType> fromXml(DbReferenceType xmlObj) {
		DBCrossReferenceBuilder<ProteomeXReferenceType> builder =
		new DBCrossReferenceBuilder<ProteomeXReferenceType>()
		.databaseType(fromXml(xmlObj.getType())).id(xmlObj.getId());
		builder.properties(
		xmlObj.getProperty().stream()
		.map(this::fromXmlProperty)
		.collect(Collectors.toList()));
		
		return builder.build();
	}
	
	

	
	@Override
	public DbReferenceType toXml(DBCrossReference<ProteomeXReferenceType> uniObj) {
		DbReferenceType xmlObj = xmlFactory.createDbReferenceType();
		xmlObj.setType(toXml(uniObj.getDatabaseType()));
		xmlObj.setId(uniObj.getId());
		uniObj.getProperties().stream()
		.map(this::toXmlProperty)
		.forEach(val ->xmlObj.getProperty().add(val));
		
		return xmlObj;
	}
	
	private String toXml(ProteomeXReferenceType type) {
		if(type == ProteomeXReferenceType.GENOME_ASSEMBLY)
			return GC_SET_ACC;
		else
			return type.getName();
	
	} 
	private ProteomeXReferenceType fromXml(String type) {
		if(GC_SET_ACC.equals(type)) {
			return ProteomeXReferenceType.GENOME_ASSEMBLY;
		}else
			return ProteomeXReferenceType.fromValue(type);
	}
	private Property fromXmlProperty(PropertyType property) {
		return new Property(property.getType(), property.getValue());
	}
	
	private PropertyType toXmlProperty(Property property) {
		PropertyType ptype = xmlFactory.createPropertyType();
		ptype.setType(property.getKey());
		ptype.setValue(property.getValue());
		return ptype;
	}
}
