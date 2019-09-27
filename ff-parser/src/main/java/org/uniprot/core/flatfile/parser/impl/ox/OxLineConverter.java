package org.uniprot.core.flatfile.parser.impl.ox;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;

public class OxLineConverter extends EvidenceCollector
        implements Converter<OxLineObject, Organism> {
    @Override
    public Organism convert(OxLineObject f) {

        Map<Object, List<Evidence>> evidences =
                EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidences.values());
        return new OrganismBuilder()
                .taxonId(f.taxonomy_id)
                .evidences(evidences.getOrDefault(f.taxonomy_id, Collections.emptyList()))
                .build();
    }
}
