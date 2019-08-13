package org.uniprot.core.xml.uniref;

import java.util.List;

import org.uniprot.core.flatfile.parser.impl.OrganismNameLineParser;
import org.uniprot.core.uniparc.builder.UniParcIdBuilder;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniprot.taxonomy.OrganismName;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.builder.AbstractUniRefMemberBuilder;
import org.uniprot.core.uniref.builder.OverlapRegionBuilder;
import org.uniprot.core.uniref.builder.UniRefEntryIdBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniref.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniref.MemberType;
import org.uniprot.core.xml.jaxb.uniref.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniref.PropertyType;

import com.google.common.base.Strings;

/**
 *
 * @author jluo
 * @date: 13 Aug 2019
 *
*/

public abstract class AbstractMemberConverter<T extends UniRefMember> implements Converter<MemberType, T> {
	public static final String PROPERTY_PROTEIN_NAME = "protein name";
	public static final String PROPERTY_NCBI_TAXONOMY = "NCBI taxonomy";
	public static final String PROPERTY_SOURCE_ORGANISM = "source organism";
	public static final String PROPERTY_SOURCE_UNIPARC = "UniParc ID";
	public static final String PROPERTY_SOURCE_UNIREF100 = "UniRef100 ID";
	public static final String PROPERTY_SOURCE_UNIREF90 = "UniRef90 ID";
	public static final String PROPERTY_SOURCE_UNIREF50 = "UniRef50 ID";
	public static final String PROPERTY_SOURCE_UNIPROT = "UniProtKB accession";
	public static final String PROPERTY_SOURCE_LENGTH = "length";
	public static final String PROPERTY_SOURCE_OVERLAP = "overlap region";
	public static final String PROPERTY_IS_SEED = "isSeed";
	protected final ObjectFactory jaxbFactory;
	public AbstractMemberConverter(ObjectFactory jaxbFactory) {
		this.jaxbFactory = jaxbFactory;
	}
	
	protected void updateMemberToXml(MemberType memberType, T uniObj){
		DbReferenceType xref = jaxbFactory.createDbReferenceType();
		memberType.setDbReference(xref);
		xref.setType(uniObj.getMemberIdType().toDisplayName());
		xref.setId(uniObj.getMemberId());
		if(uniObj.getTaxonomy() !=null) {
			xref.getProperty().add(createProperty(PROPERTY_NCBI_TAXONOMY, String.valueOf(uniObj.getTaxonomy().getTaxonId())));
			String organismName = getOrganismName(uniObj.getTaxonomy());
			if(!Strings.isNullOrEmpty(organismName)) {
				xref.getProperty().add(createProperty(PROPERTY_SOURCE_ORGANISM, organismName));
			}
		}
		if(!Strings.isNullOrEmpty(uniObj.getProteinName())) {
			xref.getProperty().add(createProperty(PROPERTY_PROTEIN_NAME, uniObj.getProteinName()));
		}
		if(uniObj.getUniParcId() !=null) {
			xref.getProperty().add(createProperty(PROPERTY_SOURCE_UNIPARC, uniObj.getUniParcId().getValue()));
		}
		if(uniObj.getUniRef100Id() !=null) {
			xref.getProperty().add(createProperty(PROPERTY_SOURCE_UNIREF100, uniObj.getUniRef100Id().getValue()));
		}
		if(uniObj.getUniRef90Id() !=null) {
			xref.getProperty().add(createProperty(PROPERTY_SOURCE_UNIREF90, uniObj.getUniRef90Id().getValue()));
		}
		if(uniObj.getUniRef50Id() !=null) {
			xref.getProperty().add(createProperty(PROPERTY_SOURCE_UNIREF50, uniObj.getUniRef50Id().getValue()));
		}
		if(uniObj.getUniProtAccession() !=null) {
			xref.getProperty().add(createProperty(PROPERTY_SOURCE_UNIPROT, uniObj.getUniProtAccession().getValue()));
		}
		if(uniObj.getSequenceLength()>0) {
			xref.getProperty().add(createProperty(PROPERTY_SOURCE_LENGTH, String.valueOf(uniObj.getSequenceLength())));
		}
		if(uniObj.getOverlapRegion() !=null) {
			  String overlap = uniObj.getOverlapRegion().getStart() + "-" + uniObj.getOverlapRegion().getEnd();
			  xref.getProperty().add(createProperty(PROPERTY_SOURCE_OVERLAP, overlap));
		}
		if(uniObj.isSeed()) {
			xref.getProperty().add(createProperty(PROPERTY_IS_SEED, String.valueOf(uniObj.isSeed())));
		}
	}
	
	private String getOrganismName(Taxonomy tax) {
		 StringBuilder sb = new StringBuilder();
	        sb.append(tax.getScientificName());
	        String commonName = tax.getCommonName();
	        if (commonName != null && !commonName.isEmpty()) {
	            sb.append(" (")
	                    .append(commonName).append(")");
	        }
	        List<String> synonyms = tax.getSynonyms();
	        if (!synonyms.isEmpty()) {
	            sb.append(" (")
	                    .append(String.join(", ", synonyms))
	                    .append(")");
	        }
	        return sb.toString();
	        
	}
	private PropertyType createProperty(String type, String value) {
		PropertyType property = jaxbFactory.createPropertyType();
		property.setType(type);
		property.setValue(value);
		return property;
	}
	protected void updateMemberFromXml(AbstractUniRefMemberBuilder<? extends AbstractUniRefMemberBuilder<?,T>, T> builder , MemberType xmlObj ) {
		builder.memberIdType(UniRefMemberIdType.typeOf(xmlObj.getDbReference().getType()))
		.memberId(xmlObj.getDbReference().getId());
		
		String organismName="";
		Long taxId =null ;
		
		List<PropertyType> properties = xmlObj.getDbReference().getProperty();
		for (PropertyType property : properties) {
			if (property.getType().equals(PROPERTY_NCBI_TAXONOMY)) {
				taxId = Long.parseLong(property.getValue());
			} else if (property.getType().equals(PROPERTY_PROTEIN_NAME)) {
				builder.proteinName(property.getValue());
			} else if (property.getType().equals(PROPERTY_SOURCE_ORGANISM)) {
				organismName= property.getValue();
			} else if (property.getType().equals(PROPERTY_SOURCE_UNIPARC)) {
				builder.uniparcId(new UniParcIdBuilder(property.getValue()).build());
			} else if (property.getType().equals(PROPERTY_SOURCE_LENGTH)) {
				builder.sequenceLength(Integer.parseInt(property.getValue()));
			} else if (property.getType().equals(PROPERTY_SOURCE_OVERLAP)) {
				String strOverlap = property.getValue();
				String strStart = strOverlap.substring(0, strOverlap.indexOf('-'));
				String strEnd = strOverlap.substring(strOverlap.indexOf('-') + 1, strOverlap.length());
				builder.overlapRegion(
				new OverlapRegionBuilder()
					.start(Integer.parseInt(strStart))
					.end(Integer.parseInt(strEnd))
					.build()
					);
			} else if (property.getType().equals(PROPERTY_SOURCE_UNIREF100)) {
				builder.uniref100Id(new UniRefEntryIdBuilder(property.getValue()).build());				
			} else if (property.getType().equals(PROPERTY_SOURCE_UNIREF90)) {
				builder.uniref90Id(new UniRefEntryIdBuilder(property.getValue()).build());		
			} else if (property.getType().equals(PROPERTY_SOURCE_UNIREF50)) {
				builder.uniref50Id(new UniRefEntryIdBuilder(property.getValue()).build());	
			} else if (property.getType().equals(PROPERTY_SOURCE_UNIPROT)) {
				builder.accession(new UniProtAccessionBuilder(property.getValue()).build());
			} else if (property.getType().equals(PROPERTY_IS_SEED)) {
				builder.isSeed(Boolean.parseBoolean(property.getValue()));
			} else {
				System.out.println("property.getType() = " + property.getType());
			}
		}
		if(taxId !=null)
			builder.taxonomy(convertFromXml(taxId, organismName));
	}

	private Taxonomy convertFromXml(long taxId, String fullName) {
		OrganismName name =OrganismNameLineParser.createFromOrganismLine(fullName);
		return new TaxonomyBuilder().from(name).taxonId(taxId).build();				
	}
		
}

