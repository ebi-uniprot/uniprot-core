package org.uniprot.core.parser.fasta.uniparc;

import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

import java.util.*;

import static org.uniprot.core.parser.fasta.FastaUtils.parseSequence;
import static org.uniprot.core.util.Utils.*;

public class UniParcProteomeFastaParser {

    private static final Set<UniParcDatabase> uniProtDatabases = Set.of(
            UniParcDatabase.SWISSPROT, UniParcDatabase.TREMBL, UniParcDatabase.SWISSPROT_VARSPLIC);

    public static String toFasta(UniParcEntry entry) {
        String id = entry.getUniParcId().getValue();

        List<UniParcCrossReference> uniProtXrefs = new ArrayList<>();
        Map<String, UniParcCrossReference> sourceXrefs = new HashMap<>();
        boolean hasActive = false;
        boolean hasSourceActive = false;
        String proteomeId = null;
        for(UniParcCrossReference xref: entry.getUniParcCrossReferences()){
            if(uniProtDatabases.contains(xref.getDatabase())){
                uniProtXrefs.add(xref);
                if(proteomeId == null && xref.hasProperties()){
                    proteomeId = getProteomeId(xref.getProperties().get(0));
                }
                if(xref.isActive()){
                    hasActive = true;
                }
            } else {
                sourceXrefs.put(xref.getId(), xref);
                proteomeId = xref.getProteomeId();
                if(xref.isActive()){
                    hasSourceActive = true;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        if(!uniProtXrefs.isEmpty()){
            sb.append(getFastaHeader(uniProtXrefs, hasActive, id, proteomeId, sourceXrefs));
        } else {
            sb.append(getFastaHeader(sourceXrefs.values(), hasSourceActive, id, proteomeId, sourceXrefs));
        }
        sb.append("\n");
        sb.append(parseSequence(entry.getSequence().getValue()));
        return sb.toString();
    }

    private static String getProteomeId(Property property) {
        String result = null;
        String[] sourcePropertyValues = property.getValue().split(",");
        if(sourcePropertyValues.length == 1){
            String[] propertyValue = sourcePropertyValues[0].split(":");
            if(propertyValue.length > 1){
                result = propertyValue[1];
            }
        }
        return result;
    }

    private static StringBuilder getFastaHeader(Collection<UniParcCrossReference> xrefs, boolean hasActive, String id, String proteomeId, Map<String, UniParcCrossReference> sourceXrefs) {
        Set<String> proteinName = new LinkedHashSet<>();
        Set<String> geneNames = new LinkedHashSet<>();
        Set<String> accessions = new LinkedHashSet<>();
        Set<String> sourceIds = new LinkedHashSet<>();
        Set<String> component = new LinkedHashSet<>();
        Organism organism = null;
        for(UniParcCrossReference xref: xrefs) {
            if (xref.isActive() == hasActive) {
                addOrIgnoreNull(xref.getProteinName(), proteinName);
                addOrIgnoreNull(xref.getGeneName(), geneNames);
                organism = xref.getOrganism();

                if (xref.getDatabase().isSource()) {
                    String source = xref.getDatabase().getName() + ":" + xref.getId();
                    addOrIgnoreNull(source, sourceIds);
                    addOrIgnoreNull(xref.getComponent(), component);
                } else {
                    addOrIgnoreNull(xref.getId(), accessions);
                    if (notNullNotEmpty(xref.getProperties())) {
                        xref.getProperties().stream()
                                .filter(p -> UniParcCrossReference.PROPERTY_SOURCES.equals(p.getKey()))
                                .map(Property::getValue)
                                .forEach(value -> {
                                    String[] sources = value.split(",");
                                    for (String source : sources) {
                                        String[] ids = source.split(":");
                                        if (ids.length > 1 && proteomeId.equals(ids[1])) {
                                            sourceIds.add(getSourceId(sourceXrefs, ids[0]));
                                        }
                                        if (ids.length > 2 && proteomeId.equals(ids[1])) {
                                            component.add(ids[2]);
                                        }
                                    }
                                });
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(">").append(id);
        if(!proteinName.isEmpty()){
            sb.append(" ").append(String.join("|", proteinName));
        }
        if (notNull(organism)) {
            if (organism.hasScientificName()) {
                sb.append(" OS=").append(organism.getScientificName());
            }
            sb.append(" OX=").append(organism.getTaxonId());
        }

        if(!geneNames.isEmpty()){
            sb.append(" GN=").append(String.join("|", geneNames));
        }
        if(!accessions.isEmpty()){
            sb.append(" AC=").append(String.join("|", accessions));
        }
        if(!sourceIds.isEmpty()){
            sb.append(" SS=").append(String.join("|", sourceIds));
        }
        sb.append(" PC=").append(proteomeId);
        if(!component.isEmpty()){
            sb.append(":").append(String.join("|", component));
        }
        return sb;
    }

    private static String getSourceId(Map<String, UniParcCrossReference> sourceXrefs, String sourceId) {
        if(sourceXrefs.containsKey(sourceId)){
            sourceId = sourceXrefs.get(sourceId).getDatabase().getName() + ":" + sourceId;
        }
        return sourceId;
    }
}
