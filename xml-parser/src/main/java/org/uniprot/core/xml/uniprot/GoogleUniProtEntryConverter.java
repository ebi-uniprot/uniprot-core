package org.uniprot.core.xml.uniprot;

import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;
import org.uniprot.core.xml.jaxb.uniprot.Entry;

import java.util.Map;
import java.util.stream.Collectors;

public class GoogleUniProtEntryConverter extends UniProtEntryConverter {

    public GoogleUniProtEntryConverter() {
        super();
    }

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
        activeEntryBuilder.uniProtCrossReferencesSet(
                xmlEntry.getDbReference().stream()
                        .filter(val -> !val.getType().equals("EC"))
                        .map(this::fromXml)
                        .filter(val -> val != null)
                        .collect(Collectors.toList()));
        activeEntryBuilder.keywordsSet(
                xmlEntry.getKeyword().stream()
                        .map(this::fromXml)
                        .collect(Collectors.toList()));
        return activeEntryBuilder.build();
    }
}
