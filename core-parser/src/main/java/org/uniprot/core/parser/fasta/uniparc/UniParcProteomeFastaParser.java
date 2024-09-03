package org.uniprot.core.parser.fasta.uniparc;

import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.uniprot.core.parser.fasta.FastaUtils.parseSequence;

public class UniParcProteomeFastaParser {

    private static final Set<UniParcDatabase> uniProtDatabases = Set.of(
            UniParcDatabase.SWISSPROT, UniParcDatabase.TREMBL, UniParcDatabase.SWISSPROT_VARSPLIC);

    public static String toFasta(UniParcEntry entry, String proteomeID) {
        String id = entry.getUniParcId().getValue();

        List<String> proteinName = new ArrayList<>();
        List<String> geneNames = new ArrayList<>();
        List<String> accessions = new ArrayList<>();
        Set<String> sourceIds = new HashSet<>();
        Set<String> component = new HashSet<>();
        AtomicReference<Organism> organism = new AtomicReference<>();
        AtomicReference<String> proteomeIdValue = new AtomicReference<>();

        entry.getUniParcCrossReferences().stream()
                .filter(xref -> uniProtDatabases.contains(xref.getDatabase()))
                .sorted(Comparator.comparing(UniParcCrossReference::isActive,Comparator.reverseOrder()))
                .forEach(xref -> {
                    if(uniProtDatabases.contains(xref.getDatabase())) {
                        if (Utils.notNullNotEmpty(xref.getId())) {
                            accessions.add(xref.getId());
                        }
                        if (Utils.notNullNotEmpty(xref.getProteinName())) {
                            proteinName.add(xref.getProteinName());
                        }
                        if (Utils.notNullNotEmpty(xref.getGeneName())) {
                            geneNames.add(xref.getGeneName());
                        }
                        if (Utils.notNull(xref.getOrganism())) {
                            organism.set(xref.getOrganism());
                        }
                        if (Utils.notNullNotEmpty(xref.getProperties())) {
                            xref.getProperties().stream()
                                    .filter(p -> "source".equals(p.getKey()))
                                    .map(Property::getValue)
                                    .forEach(source -> {
                                        String[] sources = source.split(":");
                                        if(sources.length > 0){
                                            sourceIds.add(sources[0]);
                                        }
                                        if(sources.length > 1){
                                            proteomeIdValue.set(sources[1]);
                                        }
                                        if(sources.length > 2){
                                            component.add(sources[2]);
                                        }
                                    });
                        }
                    }
                });

        StringBuilder sb = new StringBuilder();
        sb.append(">").append(id);
        if(!proteinName.isEmpty()){
            sb.append(" ").append(String.join("|", proteinName));
        }
        if (Utils.notNull(organism.get())) {
            if (organism.get().hasScientificName()) {
                sb.append(" OS=").append(organism.get().getScientificName());
            }
            sb.append(" OX=").append(organism.get().getTaxonId());
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
        sb.append(" UP=").append(proteomeIdValue.get());
        if(!component.isEmpty()){
            sb.append(":").append(String.join("|", component));
        }

        sb.append("\n");
        sb.append(parseSequence(entry.getSequence().getValue()));
        return sb.toString();
    }
}
