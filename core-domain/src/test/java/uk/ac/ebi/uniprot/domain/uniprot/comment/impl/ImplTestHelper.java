package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.IsoformNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidencedValueBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;

/**
 * Created 17/01/19
 *
 * @author Edd
 */
public class ImplTestHelper {
    static Note createNote() {
        List<EvidencedValue> texts = new ArrayList<>();
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceBuilder()
                              .databaseName("Ensembl")
                              .databaseId("ENSP0001324")
                              .evidenceCode(EvidenceCode.ECO_0000313)
                              .build());
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("PIRNR")
                        .databaseId("PIRNR001361")
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .build());
        texts.add(new EvidencedValueBuilder("value 1", evidences).build());
        texts.add(new EvidencedValueBuilder("value2", emptyList()).build());
        return new NoteBuilder(texts).build();
    }

    static List<IsoformName> createSynonyms() {
        List<IsoformName> synonyms = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        synonyms.add(new IsoformNameBuilder("Syn 1", evidences).build());
        synonyms.add(new IsoformNameBuilder("Syn 2", evidences).build());
        return synonyms;
    }
}
