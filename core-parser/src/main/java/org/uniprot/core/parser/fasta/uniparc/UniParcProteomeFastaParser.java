package org.uniprot.core.parser.fasta.uniparc;

import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.uniprot.core.parser.fasta.FastaUtils.parseSequence;

public class UniParcProteomeFastaParser {

    private static final Set<UniParcDatabase> uniProtDatabases = Set.of(
            UniParcDatabase.SWISSPROT, UniParcDatabase.TREMBL);

    public static String toFasta(UniParcEntry entry, String proteomeID) {
        String id = entry.getUniParcId().getValue();
        Set<String> sourceIds = new HashSet<>();
        Set<Organism> organisms = new HashSet<>();
        Set<String> component = new HashSet<>();
        entry.getUniParcCrossReferences().stream()
                .filter(UniParcCrossReference::isActive)
                .filter(xref -> Objects.nonNull(xref.getProteomeId()))
                .filter(xref -> xref.getProteomeId().equals(proteomeID))
                .forEach(xref -> {
                    if(xref.hasDatabase() && xref.getDatabase().isSource()){
                        sourceIds.add(xref.getDatabase().getName() + ":" + xref.getId());
                    }
                    if(Utils.notNull(xref.getOrganism())){
                        organisms.add(xref.getOrganism());
                    }
                    if(Utils.notNullNotEmpty(xref.getComponent())){
                        component.add(xref.getComponent());
                    }
                });
        StringBuilder sb = new StringBuilder();
        sb.append(">").append(id);
        if(!sourceIds.isEmpty()){
            sb.append("|").append(String.join("|", sourceIds));
        }
        sb.append(" UP=").append(proteomeID);
        if(!component.isEmpty()){
            sb.append(":").append(String.join("|", component));
        }
        sb.append(parseOrganismAndAccession(entry.getUniParcCrossReferences(), organisms));
        sb.append("\n");
        sb.append(parseSequence(entry.getSequence().getValue()));
        return sb.toString();
    }

    private static String parseOrganismAndAccession(List<UniParcCrossReference> xrefs, Set<Organism> organisms) {
        StringBuilder sb = new StringBuilder();
        if(!organisms.isEmpty()) {
            Organism organism = organisms.stream().findFirst().get();
            if (organism.getTaxonId() > 0L) {
                sb.append(" OX=").append(organism.getTaxonId());
            }
            if (organism.hasScientificName()) {
                sb.append(" OS=").append(organism.getScientificName());
            }
            if (organism.getTaxonId() > 0L) {
                Set<String> accessions = new HashSet<>();
                xrefs.stream()
                        .filter(xref -> filterOrganism(xref, organism))
                        .filter(xref -> uniProtDatabases.contains(xref.getDatabase()))
                        .map(UniParcCrossReference::getId)
                        .filter(Objects::nonNull)
                        .forEach(accessions::add);
                if(!accessions.isEmpty()) {
                    sb.append(" AC=").append(String.join("|", accessions));
                }
            }
        }
        return sb.toString();
    }

    private static boolean filterOrganism(UniParcCrossReference xref, Organism organism) {
        return xref.getOrganism() != null &&
                xref.getOrganism().getTaxonId() == organism.getTaxonId();
    }
}
