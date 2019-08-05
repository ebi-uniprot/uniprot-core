package org.uniprot.core.xml.uniprot;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.taxonomy.OrganismName;
import org.uniprot.core.uniprot.taxonomy.builder.AbstractOrganismNameBuilder;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.OrganismNameType;

public final class OrganismConverterUtil {
	public static final String CIENTIFICNAME_XMLTAG = "scientific";
	public static final String COMMONNAME_XMLTAG = "common";
	public static final String SYNONYM_XMLTAG = "synonym";
	public static final String DBCROSSREFNCBITAXONOMY_XMLTAG = "NCBI Taxonomy";

	public static DbReferenceType taxonIdToXmlDbRef(ObjectFactory xmlUniprotFactory, Long taxId) {
		DbReferenceType dbReferenceXML = xmlUniprotFactory.createDbReferenceType();
		dbReferenceXML.setType(DBCROSSREFNCBITAXONOMY_XMLTAG);
		dbReferenceXML.setId(taxId.toString());
		return dbReferenceXML;
	}

	public static void updateOrganismNameFromXml(List<OrganismNameType> organismNameTypes,
			AbstractOrganismNameBuilder<? extends AbstractOrganismNameBuilder<?,?>,? extends OrganismName> organismNameBuilder) {
		for (OrganismNameType xmlName : organismNameTypes) {
			if (xmlName.getType().equalsIgnoreCase(CIENTIFICNAME_XMLTAG)) {
				organismNameBuilder.scientificName(xmlName.getValue());				
			} else if (xmlName.getType().equalsIgnoreCase(COMMONNAME_XMLTAG)) {
				organismNameBuilder.commonName(xmlName.getValue());
			} else if (xmlName.getType().equalsIgnoreCase(SYNONYM_XMLTAG)) {
				organismNameBuilder.addSynonyms(xmlName.getValue());
			}
		}
	}
	public static List<OrganismNameType> organismNameToXml(ObjectFactory xmlUniprotFactory,
			  OrganismName organismName) {
		  List<OrganismNameType>  nametypes =new ArrayList<>();
		  nametypes.add(createOrganismNameType(xmlUniprotFactory, CIENTIFICNAME_XMLTAG,
				  organismName.getScientificName()));
			if(!Strings.isNullOrEmpty(organismName.getCommonName())) {
				  nametypes.add(createOrganismNameType(xmlUniprotFactory, COMMONNAME_XMLTAG,
						  organismName.getCommonName()));
			}
			if((organismName.getSynonyms() !=null) && !organismName.getSynonyms().isEmpty()){
				organismName.getSynonyms().forEach( val -> 
					 nametypes.add(createOrganismNameType(xmlUniprotFactory, SYNONYM_XMLTAG,
							  val))
				);
			}
		
		  return nametypes;
	  }

	private static OrganismNameType createOrganismNameType(ObjectFactory xmlUniprotFactory, String nameType,
			String value) {
		OrganismNameType organismNameXML = xmlUniprotFactory.createOrganismNameType();
		organismNameXML.setType(nameType);
		organismNameXML.setValue(value);
		return organismNameXML;
	}
}
