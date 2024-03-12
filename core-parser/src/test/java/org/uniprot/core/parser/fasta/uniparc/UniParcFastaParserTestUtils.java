package org.uniprot.core.parser.fasta.uniparc;

import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.*;
import org.uniprot.core.uniparc.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.util.ArrayList;
import java.util.List;

public class UniParcFastaParserTestUtils {

    static UniParcEntry create() {
        List<UniParcCrossReference> xrefs = getUniProtXrefs(true);
        Organism organism = getOrganism(9606L, "Homo Sapiens");
        xrefs.add(getXref(UniParcDatabase.EMBL,"CQR81549", true, organism, "UP000005640", "Chromosome 1"));
        return new UniParcEntryBuilder()
                        .uniParcId(new UniParcIdBuilder("UPI0000083A08").build())
                        .uniParcCrossReferencesSet(xrefs)
                        .sequence(getSequence())
                        .build();
    }

    static Sequence getSequence() {
        String seq = "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT" +
                     "LLRAIDWFRDNGYFNA";
        return new SequenceBuilder(seq).build();
    }

    static List<UniParcCrossReference> getUniProtXrefs(boolean active) {
        List<UniParcCrossReference> result = new ArrayList<>();
        Organism human = getOrganism(9606, "Homo sapiens");

        result.add(getXref(UniParcDatabase.SWISSPROT, "P12345", active, human));

        return result;
    }

    static Organism getOrganism(long taxonId, String name) {
        return new OrganismBuilder().taxonId(taxonId).scientificName(name).build();
    }

    static UniParcCrossReference getXref(UniParcDatabase database, String id, boolean active, Organism organism) {
        return getXref(database, id, active, organism, null, null);
    }

    static UniParcCrossReference getXref(UniParcDatabase database, String id, boolean active, Organism organism, String proteomeId, String component) {
        return new UniParcCrossReferenceBuilder()
                .database(database)
                .id(id)
                .active(active)
                .organism(organism)
                .proteomeId(proteomeId)
                .component(component)
                .build();
    }
}
