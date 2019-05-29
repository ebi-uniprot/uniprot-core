package uk.ac.ebi.uniprot.xml.uniparc;

import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDatabaseType;
import uk.ac.ebi.uniprot.domain.uniparc.builder.UniParcDBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.PropertyType;
import uk.ac.ebi.uniprot.xml.uniprot.XmlConverterHelper;

/**
 *
 * @author jluo
 * @date: 23 May 2019
 *
*/

public class UniParcDBCrossReferenceConverter implements Converter<DbReferenceType, UniParcDBCrossReference> {
	private final PropertyConverter propertyConverter;
	private final ObjectFactory xmlFactory;
	public UniParcDBCrossReferenceConverter() {
		this(new ObjectFactory());
	}

	public UniParcDBCrossReferenceConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
		this.propertyConverter =new PropertyConverter(xmlFactory);
	}

	@Override
	public UniParcDBCrossReference fromXml(DbReferenceType xmlObj) {
		UniParcDBCrossReferenceBuilder builder = new UniParcDBCrossReferenceBuilder();
		builder.databaseType(UniParcDatabaseType.typeOf(xmlObj.getType()))
		.id(xmlObj.getId())
		.active(xmlObj.getActive().equals("Y"))
		.versionI(xmlObj.getVersionI())
		.created( XmlConverterHelper.dateFromXml(xmlObj.getCreated()))
		.lastUpdated(XmlConverterHelper.dateFromXml(xmlObj.getLast()))
		.properties(
		xmlObj.getProperty().stream().map(propertyConverter::fromXml)
		.collect(Collectors.toList()));
		if(xmlObj.getVersion() !=null)
			builder.version(xmlObj.getVersion());
		return builder.build();
	}

	@Override
	public DbReferenceType toXml(UniParcDBCrossReference uniObj) {
		DbReferenceType xmlObj = xmlFactory.createDbReferenceType();
		xmlObj.setActive(uniObj.isActive()?"Y":"N");
		xmlObj.setId(uniObj.getId());
		xmlObj.setType(uniObj.getDatabaseType().toDisplayName());
		xmlObj.setVersionI(uniObj.getVersionI());
		if(uniObj.getVersion() !=null)
			xmlObj.setVersion(uniObj.getVersion());
		xmlObj.setCreated(XmlConverterHelper.dateToXml(uniObj.getCreated()));
		xmlObj.setLast(XmlConverterHelper.dateToXml(uniObj.getLastUpdated()));
		uniObj.getProperties()
		.stream().map(propertyConverter::toXml)
		.forEach(val -> xmlObj.getProperty().add(val));
		return xmlObj;
	}
	
	class PropertyConverter implements Converter<PropertyType, Property> {
		private final ObjectFactory xmlFactory;

		public PropertyConverter() {
			this(new ObjectFactory());
		}

		public PropertyConverter(ObjectFactory xmlFactory) {
			this.xmlFactory = xmlFactory;
		}
		
		@Override
		public Property fromXml(PropertyType xmlObj) {
			return new Property(xmlObj.getType(), xmlObj.getValue());
		}

		@Override
		public PropertyType toXml(Property uniObj) {
			PropertyType  xmlObj = xmlFactory.createPropertyType();
			xmlObj.setType(uniObj.getKey());
			xmlObj.setValue(uniObj.getValue());
			return xmlObj;
		}

	}
}

