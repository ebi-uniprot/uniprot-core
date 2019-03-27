package uk.ac.ebi.uniprot.xml.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.AbsorptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.EvidencedValueConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BPCPAbsorptionConverter implements Converter<CommentType.Absorption, Absorption> {
    private static final String APPROXIMATION_SYMBOL = "~";
    private static final String NANOMETERS_SYMBOL = "nm";
    private final ObjectFactory xmlUniprotFactory;
    private final EvidencedValueConverter evValueConverter;
    private final EvidenceIndexMapper evRefMapper;

    public BPCPAbsorptionConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public BPCPAbsorptionConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, false);
        this.evRefMapper = evRefMapper;
    }

    @Override
    public Absorption fromXml(CommentType.Absorption xmlObj) {
        boolean approximate = false;
        // AbsorptionMax
        int maxValue = 0;
        List<Evidence> evidences = new ArrayList<>();
        Note note = null;
        if (xmlObj.getMax() != null) {
            String max = xmlObj.getMax().getValue();
            int pos = 0;
            // The example could be: ~596 nm
            if (max.contains(APPROXIMATION_SYMBOL)) {
                pos = 1;
                approximate = true;
            }
            int index = max.indexOf(' ');
            String number = max.substring(pos, index);
            maxValue = Integer.parseInt(number);
            evidences = evRefMapper.parseEvidenceIds(xmlObj.getMax().getEvidence());
        }

        if (!xmlObj.getText().isEmpty()) {
            note = new NoteBuilder(xmlObj.getText().stream().map(evValueConverter::fromXml)
                                           .collect(Collectors.toList())).build();
        }

        return new AbsorptionBuilder()
                .max(maxValue).approximate(approximate).note(note).evidences(evidences).build();
    }

    @Override
    public CommentType.Absorption toXml(Absorption uniObj) {
        CommentType.Absorption absorptionXML = xmlUniprotFactory.createCommentTypeAbsorption();

        // AbsorptionMax
        EvidencedStringType maxAbsorption = xmlUniprotFactory.createEvidencedStringType();
        maxAbsorption.setValue(getXmlValueString(uniObj));
        absorptionXML.setMax(maxAbsorption);

        if (!uniObj.getEvidences().isEmpty()) {
            List<Integer> evs = evRefMapper.writeEvidences(uniObj.getEvidences());
            if (!evs.isEmpty())
                absorptionXML.getMax().getEvidence().addAll(evs);
        }

        // AbsorptionNote
        Note note = uniObj.getNote();
        if ((note != null) && (!note.getTexts().isEmpty())) {
            note.getTexts().forEach(val -> absorptionXML.getText().add(evValueConverter.toXml(val)));
        }

        return absorptionXML;
    }

    private String getXmlValueString(Absorption absorption) {
        StringBuilder sb = new StringBuilder();
        if (absorption.isApproximate()) {
            sb.append(APPROXIMATION_SYMBOL);
        }
        sb.append(absorption.getMax()).append(" ").append(NANOMETERS_SYMBOL);

        return sb.toString();
    }
}
