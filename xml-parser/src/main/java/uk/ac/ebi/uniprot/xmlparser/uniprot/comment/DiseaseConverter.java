package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class DiseaseConverter  implements Converter<CommentType.Disease, Disease> {
	private final ObjectFactory xmlUniprotFactory;
	public DiseaseConverter() {
		this(new ObjectFactory());
	}

	public DiseaseConverter( ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;

	}

	@Override
	public Disease fromXml(CommentType.Disease xmlDisease) {
		if(xmlDisease == null)
			return null;
		DiseaseBuilder builder = DiseaseBuilder.newInstance();
		builder.acronym(xmlDisease.getAcronym())
		.diseaseAc(xmlDisease.getId())
		.diseaseId(xmlDisease.getName())
		.description(xmlDisease.getDescription());

		
		if(xmlDisease.getDbReference() != null){
			DiseaseReferenceType type = DiseaseReferenceType.typeOf(xmlDisease.getDbReference().getType());
			DBCrossReference<DiseaseReferenceType>  reference =
					UniProtFactory.INSTANCE.createDBCrossReference (type, xmlDisease.getDbReference().getId());
			builder.reference(reference);
	
		}
		return builder.build();
	}

	@Override
	public CommentType.Disease toXml(Disease disease) {
		if(disease == null)
			return null;
	
		CommentType.Disease xmlDisease = xmlUniprotFactory.createCommentTypeDisease();
		xmlDisease.setAcronym(disease.getAcronym());
		xmlDisease.setName(disease.getDiseaseId());
		xmlDisease.setDescription(disease.getDescription());
		xmlDisease.setId(disease.getDiseaseAccession());
			
		DbReferenceType dbReferenceType = xmlUniprotFactory.createDbReferenceType();
		dbReferenceType.setId(disease.getReference().getId());
		dbReferenceType.setType(disease.getReference().getDatabaseType().name());
		xmlDisease.setDbReference(dbReferenceType);
		
		return xmlDisease;
	}

}
