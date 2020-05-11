package org.uniprot.core.xml.uniprot.comment;

import com.google.common.base.Strings;

import org.uniprot.core.uniprotkb.comment.Disease;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseBuilder;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.NoteBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.MoleculeType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.EvidencedValueConverter;

import java.util.List;
import java.util.stream.Collectors;

public class DiseaseCommentConverter implements CommentConverter<DiseaseComment> {
    private final ObjectFactory xmlUniprotFactory;
    private final DiseaseConverter diseaseConverter;
    private final EvidencedValueConverter evValueConverter;
    private final EvidenceIndexMapper evRefMapper;

    public DiseaseCommentConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public DiseaseCommentConverter(
            EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
        this.diseaseConverter = new DiseaseConverter(xmlUniprotFactory);
        evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, true);
    }

    @Override
    public DiseaseComment fromXml(CommentType xmlComment) {

        if (xmlComment == null) return null;

        DiseaseCommentBuilder builder = new DiseaseCommentBuilder();

        // Molecule
        if (xmlComment.getMolecule() != null) {
            builder.molecule(xmlComment.getMolecule().getValue());
        }

        // We do not want to se disease as null in kraken objects, but as empty.
        // Control vocabulary checks are based only on name and id.
        if (xmlComment.getDisease() != null) {
            Disease disease = diseaseConverter.fromXml(xmlComment.getDisease());
            if (!xmlComment.getEvidence().isEmpty()) {
                List<Evidence> evidences = evRefMapper.parseEvidenceIds(xmlComment.getEvidence());
                disease = diseaseSetEvidence(disease, evidences);
                builder.disease(disease);
            } else {
                builder.disease(disease);
            }
        }
        if (!xmlComment.getText().isEmpty()) {
            Note note =
                    new NoteBuilder(
                                    xmlComment.getText().stream()
                                            .map(evValueConverter::fromXml)
                                            .collect(Collectors.toList()))
                            .build();
            builder.note(note);
        }
        return builder.build();
    }

    private Disease diseaseSetEvidence(Disease disease, List<Evidence> evidences) {
        DiseaseBuilder builder = new DiseaseBuilder();
        builder.acronym(disease.getAcronym())
                .description(disease.getDescription())
                .diseaseAc(disease.getDiseaseAccession())
                .diseaseId(disease.getDiseaseId())
                .diseaseCrossReference(disease.getDiseaseCrossReference())
                .evidencesSet(evidences);
        return builder.build();
    }

    @Override
    public CommentType toXml(DiseaseComment comment) {
        if (comment == null) return null;

        CommentType xmlComment = xmlUniprotFactory.createCommentType();

        // comment type
        xmlComment.setType(comment.getCommentType().getDisplayName().toLowerCase());

        if (!Strings.isNullOrEmpty(comment.getMolecule())) {
            MoleculeType mol = xmlUniprotFactory.createMoleculeType();
            mol.setValue(comment.getMolecule());
            xmlComment.setMolecule(mol);
        }

        if (comment.hasDefinedDisease()) {
            xmlComment.setDisease(diseaseConverter.toXml(comment.getDisease()));
            List<Evidence> evidenceIds = comment.getDisease().getEvidences();
            if ((evidenceIds != null) && !evidenceIds.isEmpty()) {
                List<Integer> evs = evRefMapper.writeEvidences(evidenceIds);
                if (!evs.isEmpty()) xmlComment.getEvidence().addAll(evs);
            }
        }

        // Note
        Note note = comment.getNote();
        if ((note != null) && (!note.getTexts().isEmpty())) {
            note.getTexts().forEach(val -> xmlComment.getText().add(evValueConverter.toXml(val)));
        }
        return xmlComment;
    }
}
