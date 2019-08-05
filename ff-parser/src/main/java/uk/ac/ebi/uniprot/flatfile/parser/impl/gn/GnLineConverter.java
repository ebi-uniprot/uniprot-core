package uk.ac.ebi.uniprot.flatfile.parser.impl.gn;

import uk.ac.ebi.uniprot.flatfile.parser.Converter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceConverterHelper;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineObject.GnName;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineObject.GnObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uniprot.core.gene.*;
import org.uniprot.core.uniprot.builder.*;
import org.uniprot.core.uniprot.evidence.Evidence;

public class GnLineConverter extends EvidenceCollector implements Converter<GnLineObject, List<Gene>> {
    @Override
    public List<Gene> convert(GnLineObject f) {
        List<Gene> genes = new ArrayList<>();
        for (GnObject gno : f.gnObjects) {
            GeneName geneName = null;
            List<GeneNameSynonym> synonyms = new ArrayList<>();
            List<ORFName> orfNames = new ArrayList<>();
            List<OrderedLocusName> olnNames = new ArrayList<>();
            for (GnName gn : gno.names) {
                Map<Object, List<Evidence>> evidenceMap = EvidenceConverterHelper
                        .convert(gn.getEvidenceInfo());
                this.addAll(evidenceMap.values());
                switch (gn.type) {
                    case GENAME:
                        if (!gn.names.isEmpty()) {
                            geneName = new GeneNameBuilder(gn.names.get(0), evidenceMap.get(gn.names.get(0))).build();
                        }
                        break;
                    case SYNNAME:
                        for (String name : gn.names) {
                            synonyms.add(new GeneNameSynonymBuilder(name, evidenceMap.get(name)).build());
                        }
                        break;
                    case ORFNAME:
                        for (String name : gn.names) {
                            orfNames.add(new ORFNameBuilder(name, evidenceMap.get(name)).build());
                        }
                        break;
                    case OLNAME:
                        for (String name : gn.names) {
                            olnNames.add(new OrderedLocusNameBuilder(name, evidenceMap.get(name)).build());
                        }
                        break;
                }

            }
            genes.add(new GeneBuilder()
                              .geneName(geneName)
                              .synonyms(synonyms)
                              .orderedLocusNames(olnNames)
                              .orfNames(orfNames)
                              .build());
        }
        return genes;
    }
}
