package org.uniprot.core.xml.uniprot.comment;

import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.TemperatureDependence;
import org.uniprot.core.uniprot.comment.builder.TemperatureDependenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.EvidencedValueConverter;

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
