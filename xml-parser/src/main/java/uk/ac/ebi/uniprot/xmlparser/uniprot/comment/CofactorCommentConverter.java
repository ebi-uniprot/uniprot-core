package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.MoleculeType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

import java.util.stream.Collectors;

public class CofactorCommentConverter implements CommentConverter< CofactorComment> {
	private final ObjectFactory xmlUniprotFactory;
	private final CofactorConverter cofactorConverter;
	private final EvidencedValueConverter evValueConverter;

	public CofactorCommentConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public CofactorCommentConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.cofactorConverter = new CofactorConverter(evRefMapper, xmlUniprotFactory);
		evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
	}

	@Override
	public CofactorComment fromXml(CommentType xmlComment) {
		if (xmlComment == null)
			return null;

		CofactorCommentBuilder builder = new CofactorCommentBuilder();

		// Molecule
		if (xmlComment.getMolecule() != null) {
			builder.molecule(xmlComment.getMolecule().getValue());
		}

		// cofactor note
		if (!xmlComment.getText().isEmpty()) {
			Note note = new NoteBuilder(
					xmlComment.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList())).build();
			builder.note(note);
		}
		if (!xmlComment.getCofactor().isEmpty()) {
			builder.cofactors(
					xmlComment.getCofactor().stream().map(cofactorConverter::fromXml).collect(Collectors.toList()));

		}
		return builder.build();
	}

	@Override
	public CommentType toXml(CofactorComment comment) {
		if (comment == null)
			return null;

		CommentType xmlComment = xmlUniprotFactory.createCommentType();

		// comment type
		xmlComment.setType(comment.getCommentType().toDisplayName().toLowerCase());
		if (!Strings.isNullOrEmpty(comment.getMolecule())) {
			MoleculeType mol = xmlUniprotFactory.createMoleculeType();
			mol.setValue(comment.getMolecule());
			xmlComment.setMolecule(mol);
		}
		// cofactor not
		if ((comment.getNote() != null) && (!comment.getNote().getTexts().isEmpty())) {
			comment.getNote().getTexts().forEach(val -> xmlComment.getText().add(evValueConverter.toXml(val)));
		}
		// cofactor
		if ((comment.getCofactors() != null) && (!comment.getCofactors().isEmpty())) {
			comment.getCofactors().forEach(val -> xmlComment.getCofactor().add(cofactorConverter.toXml(val)));

		}
		return xmlComment;
	}

}
