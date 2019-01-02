package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

public class FreeTextCommentConverter implements Converter<CommentType, FreeTextComment> {
	private final ObjectFactory xmlUniprotFactory;
	private final EvidencedValueConverter eviValueConverter;

	public FreeTextCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public FreeTextCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.eviValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
	}

	@Override
	public FreeTextComment fromXml(CommentType xmlObj) {
		if ((xmlObj ==null) ||xmlObj.getText().isEmpty())
			return null;
		uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType type = 
				uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType.typeOf(xmlObj.getType());
		List<EvidencedValue> texts =
		xmlObj.getText().stream()
		.map(eviValueConverter::fromXml)
		.collect(Collectors.toList());
				
		return FreeTextCommentBuilder.buildFreeTextComment(type, texts);
	}

	@Override
	public CommentType toXml(FreeTextComment uniObj) {
		if (uniObj == null)
			return null;

		CommentType xmlObj = xmlUniprotFactory.createCommentType();
		// type
		xmlObj.setType(uniObj.getCommentType().toXmlDisplayName());
		uniObj.getTexts().forEach(val -> xmlObj.getText().add (eviValueConverter.toXml(val) ));
		return xmlObj;
	}


}
