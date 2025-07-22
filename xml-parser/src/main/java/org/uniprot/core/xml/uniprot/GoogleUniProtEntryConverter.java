package org.uniprot.core.xml.uniprot;

import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;
import org.uniprot.core.xml.jaxb.uniprot.Entry;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class GoogleUniProtEntryConverter extends UniProtEntryConverter {

    public GoogleUniProtEntryConverter() {
        super();
    }

    /*
    Predicted values by ProtLM2:
    1. Protein name
    2. EC
    3. comment - Function
    4. comment - Catalytic activity/Rhea
    5. comment - Subcellular location
    6. Keywords
    7. GO
 */
    @Override
    public UniProtKBEntry fromXml(Entry xmlEntry) {
        Map<Evidence, Integer> evidenceIdMap = fromXmlForEvidences(xmlEntry);
        reset(evidenceIdMap);
        UniProtKBEntryBuilder activeEntryBuilder = createUniprotEntryBuilderFromXml(xmlEntry);
        ProteinDescription proteinDescription = fromXml(xmlEntry.getProtein());
        activeEntryBuilder.proteinDescription(fromXml(proteinDescription, xmlEntry.getSequence()));
        activeEntryBuilder.referencesSet(
                xmlEntry.getReference().stream()
                        .map(this::fromXml)
                        .collect(Collectors.toList()));
        activeEntryBuilder.commentsSet(fromXmlForComments(xmlEntry));
        activeEntryBuilder.uniProtCrossReferencesSet(
                xmlEntry.getDbReference().stream()
                        .filter(val -> !val.getType().equals("EC"))
                        .map(this::fromXml)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
        activeEntryBuilder.keywordsSet(
                xmlEntry.getKeyword().stream()
                        .map(this::fromXml)
                        .collect(Collectors.toList()));
        return activeEntryBuilder.build();
    }
}
