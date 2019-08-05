package uk.ac.ebi.uniprot.xml.uniprot.comment;

import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.EvidencedValueConverter;

import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.RedoxPotential;
import org.uniprot.core.uniprot.comment.builder.RedoxPotentialBuilder;

public class BPCPRedoxPotentialConverter implements Converter<CommentType.RedoxPotential, RedoxPotential> {
	private final ObjectFactory xmlUniprotFactory;
	private final EvidencedValueConverter evValueConverter;

	public BPCPRedoxPotentialConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public BPCPRedoxPotentialConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, false);
	}

	@Override
	public RedoxPotential fromXml(CommentType.RedoxPotential xmlObj) {
		if (xmlObj == null)
			return null;
		return new RedoxPotentialBuilder(
				xmlObj.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList())).build();
	}

	@Override
	public CommentType.RedoxPotential toXml(RedoxPotential uniObj) {
		if (uniObj == null)
			return null;
		CommentType.RedoxPotential redxoXml = xmlUniprotFactory.createCommentTypeRedoxPotential();
		uniObj.getTexts().forEach(val -> redxoXml.getText().add(evValueConverter.toXml(val)));

		return redxoXml;
	}


}
