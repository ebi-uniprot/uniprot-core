package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.PhDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.PhDependenceBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

import java.util.stream.Collectors;

public class BPCPPhDependenceConverter implements Converter<CommentType.PhDependence, PhDependence> {
	private final ObjectFactory xmlUniprotFactory;
	private final EvidencedValueConverter evValueConverter;

	public BPCPPhDependenceConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public BPCPPhDependenceConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, false);
	}

	@Override
	public PhDependence fromXml(CommentType.PhDependence xmlObj) {
		if (xmlObj == null)
			return null;
		return new PhDependenceBuilder(
				xmlObj.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList())).build();

	}

	@Override
	public CommentType.PhDependence toXml(PhDependence uniObj) {
		if (uniObj == null)
			return null;
		CommentType.PhDependence phDepXml = xmlUniprotFactory.createCommentTypePhDependence();
		uniObj.getTexts().forEach(val -> phDepXml.getText().add(evValueConverter.toXml(val)));

		return phDepXml;
	}

}
