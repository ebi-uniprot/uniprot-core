package uk.ac.ebi.uniprot.xml.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.TemperatureDependence;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.TemperatureDependenceBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.EvidencedValueConverter;

import java.util.stream.Collectors;

public class BPCPTempDependenceConverter implements Converter<CommentType.TemperatureDependence, TemperatureDependence> {
	private final ObjectFactory xmlUniprotFactory;
	private final EvidencedValueConverter evValueConverter;

	public BPCPTempDependenceConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public BPCPTempDependenceConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, false);
	}

	@Override
	public TemperatureDependence fromXml(CommentType.TemperatureDependence xmlObj) {
		if (xmlObj == null)
			return null;
		return new TemperatureDependenceBuilder(
				xmlObj.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList())).build();
	}

	@Override
	public CommentType.TemperatureDependence toXml(TemperatureDependence uniObj) {
		if (uniObj == null)
			return null;
		CommentType.TemperatureDependence tempDepXml = xmlUniprotFactory.createCommentTypeTemperatureDependence();
		uniObj.getTexts().forEach(val -> tempDepXml.getText().add(evValueConverter.toXml(val)));

		return tempDepXml;
	}


}
