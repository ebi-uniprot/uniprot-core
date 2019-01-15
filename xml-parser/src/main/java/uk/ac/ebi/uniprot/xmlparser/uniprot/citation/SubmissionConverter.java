package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import uk.ac.ebi.uniprot.domain.citation.Submission;
import uk.ac.ebi.uniprot.domain.citation.SubmissionDatabase;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class SubmissionConverter  implements Converter<CitationType, Submission> {

	private final ObjectFactory xmlUniprotFactory;

	public SubmissionConverter() {
		this(new ObjectFactory());
	}

	public SubmissionConverter(ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Submission fromXml(CitationType xmlObj) {
		SubmissionBuilder builder = SubmissionBuilder.newInstance();
		CitationConverterHelper.updateFromXmlCitaiton(xmlObj, builder);	
		builder.submittedToDatabase(SubmissionDatabase.typeOf(xmlObj.getDb()));
		return builder.build();
	}

	@Override
	public CitationType toXml(Submission uniObj) {
		CitationType xmlCitation = xmlUniprotFactory.createCitationType();
		CitationConverterHelper.updateToXmlCitatation(xmlUniprotFactory, xmlCitation, uniObj);
		xmlCitation.setType(uniObj.getCitationType().getValue());
		xmlCitation.setDb(uniObj.getSubmissionDatabase().getName());
		return xmlCitation;
	}

}
