package org.uniprot.core.uniprot.comment.impl;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.IsoformName;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.builder.IsoformNameBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;

import static java.util.Collections.emptyList;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

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
