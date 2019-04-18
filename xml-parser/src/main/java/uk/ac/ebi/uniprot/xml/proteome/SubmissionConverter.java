package uk.ac.ebi.uniprot.xml.proteome;

import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.SubmissionType;

public class SubmissionConverter  implements Converter<ReferenceType, Submission> {
	private final ObjectFactory xmlFactory;

	public SubmissionConverter() {
		this(new ObjectFactory());
	}

	public SubmissionConverter(ObjectFactory xmlFactory) {
		this.xmlFactory = xmlFactory;
	}

	@Override
	public Submission fromXml(ReferenceType xmlObj) {
		SubmissionBuilder builder = new SubmissionBuilder();
		ReferenceConverterHelper.updateFromXmlCitaiton(xmlObj, builder);
		SubmissionType xmlSubmission = xmlObj.getSubmission();
		builder.submittedToDatabase(SubmissionDatabase.typeOf(xmlSubmission.getDb()));
		return builder.build();
	}

	@Override
	public ReferenceType toXml(Submission uniObj) {
		 ReferenceType xmlCitation = xmlFactory.createReferenceType();
		  ReferenceConverterHelper.updateToXmlCitatation(xmlFactory, xmlCitation, uniObj);
		  SubmissionType xmlSubmission = xmlFactory.createSubmissionType();
		  xmlSubmission.setDb(uniObj.getSubmissionDatabase().getName());
		  xmlCitation.setSubmission(xmlSubmission);
		  return xmlCitation;
	}

}
