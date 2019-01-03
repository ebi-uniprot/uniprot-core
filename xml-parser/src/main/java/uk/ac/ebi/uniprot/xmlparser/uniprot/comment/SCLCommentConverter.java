package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.stream.Collectors;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.MoleculeType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

public class SCLCommentConverter implements CommentConverter<SubcellularLocationComment> {
	private final ObjectFactory xmlUniprotFactory;
	private final SubcellularLocationConverter locationConverter;
	private final EvidencedValueConverter evValueConverter;

	public SCLCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public SCLCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.locationConverter = new SubcellularLocationConverter(evRefMapper, xmlUniprotFactory);
		this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
	}

	@Override
	public SubcellularLocationComment fromXml(CommentType xmlObj) {
		if (xmlObj == null)
			return null;
		SubcellularLocationCommentBuilder builder = SubcellularLocationCommentBuilder.newInstance();

		// Molecule
		if (xmlObj.getMolecule() != null) {
			builder.molecule(xmlObj.getMolecule().getValue());
		}
		builder.subcellularLocations(
				xmlObj.getSubcellularLocation().stream().map(locationConverter::fromXml).collect(Collectors.toList()));
		if (!xmlObj.getText().isEmpty()) {
			Note note = CommentFactory.INSTANCE
					.createNote(xmlObj.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList()));
			builder.note(note);

		}
		return builder.build();
	}

	@Override
	public CommentType toXml(SubcellularLocationComment uniObj) {
		if (uniObj == null)
			return null;
		CommentType commentXML = xmlUniprotFactory.createCommentType();
		commentXML.setType(uniObj.getCommentType().toDisplayName().toLowerCase());
		if (!Strings.isNullOrEmpty(uniObj.getMolecule())) {
			MoleculeType mol = xmlUniprotFactory.createMoleculeType();
			mol.setValue(uniObj.getMolecule());
			commentXML.setMolecule(mol);
		}
		uniObj.getSubcellularLocations()
				.forEach(val -> commentXML.getSubcellularLocation().add(locationConverter.toXml(val)));
		Note note = uniObj.getNote();
		if ((note != null) && (!note.getTexts().isEmpty())) {
			note.getTexts().forEach(val -> commentXML.getText().add(evValueConverter.toXml(val)));
		}
		return commentXML;

	}

}
