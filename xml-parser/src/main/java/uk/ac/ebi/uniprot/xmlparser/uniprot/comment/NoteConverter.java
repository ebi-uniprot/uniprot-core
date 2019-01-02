package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CofactorType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

public class NoteConverter implements Converter<List<EvidencedStringType>, Note> {
	private final ObjectFactory xmlUniprotFactory;
	private final EvidenceIndexMapper evRefMapper;
	private final EvidencedValueConverter evValueConverter;
	public NoteConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public NoteConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper =evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
		evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);

	}

	@Override
	public Note fromXml(List<EvidencedStringType> xmlObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EvidencedStringType> toXml(Note uniObj) {
		// TODO Auto-generated method stub
		return null;
	}

}
