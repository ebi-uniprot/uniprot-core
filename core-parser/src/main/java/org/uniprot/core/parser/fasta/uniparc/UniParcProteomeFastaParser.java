package org.uniprot.core.parser.fasta.uniparc;

import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import java.util.*;

import static org.uniprot.core.parser.fasta.FastaUtils.parseSequence;
import static org.uniprot.core.util.Utils.*;

public class UniParcProteomeFastaParser {

    private static final Set<UniParcDatabase> UNIPROTKB_DATABASES = Set.of(
            UniParcDatabase.SWISSPROT, UniParcDatabase.TREMBL, UniParcDatabase.SWISSPROT_VARSPLIC);
    private static final String DELIMITER = "|";

    /**
     * This method is responsible to convert UniParcEntry into UniParc Proteome FASTA format.
     * The FASTA HEADER will display cross-references (Xrefs) in the following order of precedence:
     * 1. Display active UniProtXrefs only.
     * 2. If no active UniProtXrefs are available, display inactive UniProtXrefs only.
     * 3. If no UniProtXrefs (active or inactive) are available, display active sources only.
     * 4. If no UniProtXrefs and no active sources are available, display inactive sources only.
     * @param UniParc Entry
     * @return FASTA format string
     */
    public static String toFasta(UniParcEntry entry) {
        String id = entry.getUniParcId().getValue();

        List<UniParcCrossReference> uniProtXrefs = new ArrayList<>();
        Map<String, UniParcCrossReference> sourceXrefs = new HashMap<>();
        boolean hasUniProtXrefsActive = false;
        boolean hasSourceActive = false;
        String proteomeId = null;
        for(UniParcCrossReference xref: entry.getUniParcCrossReferences()){
            if(UNIPROTKB_DATABASES.contains(xref.getDatabase())){
                uniProtXrefs.add(xref);
                if(proteomeId == null && xref.hasProperties()){
                    proteomeId = getProteomeId(xref.getProperties().get(0));
                }
                if(xref.isActive()){
                    hasUniProtXrefsActive = true;
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
            sb.append(getFastaHeader(uniProtXrefs, hasUniProtXrefsActive, id, proteomeId, sourceXrefs));
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
        Set<String> proteinNames = new LinkedHashSet<>();
        Set<String> geneNames = new LinkedHashSet<>();
        Set<String> accessions = new LinkedHashSet<>();
        Set<String> sourceIds = new LinkedHashSet<>();
        Set<String> components = new LinkedHashSet<>();
        Organism organism = null;
        for(UniParcCrossReference xref: xrefs) {
            if (xref.isActive() == hasActive) {
                addOrIgnoreNull(xref.getProteinName(), proteinNames);
                addOrIgnoreNull(xref.getGeneName(), geneNames);
                organism = xref.getOrganism();

                if (xref.getDatabase().isSource()) {
                    String source = xref.getDatabase().getName() + ":" + xref.getId();
                    addOrIgnoreNull(source, sourceIds);
                    addOrIgnoreNull(xref.getComponent(), components);
                } else {
                    addOrIgnoreNull(xref.getId(), accessions);
                    if (notNullNotEmpty(xref.getProperties())) {
                        List<String> sources = getSourceValues(xref);
                        for (String source : sources) {
                            String[] ids = source.split(":");
                            if (ids.length > 1 && proteomeId.equals(ids[1])) {
                                sourceIds.add(getSourceId(sourceXrefs, ids[0]));
                            }
                            if (ids.length > 2 && proteomeId.equals(ids[1])) {
                                components.add(ids[2]);
                            }
                        }
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(">").append(id);
        if(!proteinNames.isEmpty()){
            sb.append(" ").append(String.join(DELIMITER, proteinNames));
        }
        if (notNull(organism)) {
            if (organism.hasScientificName()) {
                sb.append(" OS=").append(organism.getScientificName());
            }
            sb.append(" OX=").append(organism.getTaxonId());
        }

        if(!geneNames.isEmpty()){
            sb.append(" GN=").append(String.join(DELIMITER, geneNames));
        }
        if(!accessions.isEmpty()){
            sb.append(" AC=").append(String.join(DELIMITER, accessions));
        }
        if(!sourceIds.isEmpty()){
            sb.append(" SS=").append(String.join(DELIMITER, sourceIds));
        }
        if(Utils.notNullNotEmpty(proteomeId)) {
            sb.append(" PC=").append(proteomeId);
            if (!components.isEmpty()) {
                sb.append(":").append(String.join(DELIMITER, components));
            }
        }
        return sb;
    }

    private static List<String> getSourceValues(UniParcCrossReference xref) {
        return xref.getProperties().stream()
                .filter(p -> UniParcCrossReference.PROPERTY_SOURCES.equals(p.getKey()))
                .map(Property::getValue)
                .toList();
    }

    private static String getSourceId(Map<String, UniParcCrossReference> sourceXrefs, String sourceId) {
        if(sourceXrefs.containsKey(sourceId)){
            sourceId = sourceXrefs.get(sourceId).getDatabase().getName() + ":" + sourceId;
        }
        return sourceId;
    }
}
