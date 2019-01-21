package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lgonzales
 */
public class GeneBuilder implements Builder2<GeneBuilder,Gene> {

    private GeneName geneName = null;
    private List<GeneNameSynonym> synonyms = new ArrayList<>();
    private List<OrderedLocusName> orderedLocusNames = new ArrayList<>();
    private List<ORFName> orfNames = new ArrayList<>();

    public GeneBuilder geneName(GeneName geneName) {
        this.geneName = geneName;
        return this;
    }

    public GeneBuilder addSynonyms(GeneNameSynonym synonym) {
        this.synonyms.add(synonym);
        return this;
    }

    public GeneBuilder synonyms(List<GeneNameSynonym> synonyms) {
        if(synonyms != null) {
            this.synonyms.addAll(synonyms);
        }
        return this;
    }

    public GeneBuilder addOrderedLocusNames(OrderedLocusName orderedLocusName) {
        this.orderedLocusNames.add(orderedLocusName);
        return this;
    }

    public GeneBuilder orderedLocusNames(List<OrderedLocusName> orderedLocusNames) {
        if(orderedLocusNames != null) {
            this.orderedLocusNames.addAll(orderedLocusNames);
        }
        return this;
    }

    public GeneBuilder addOrfNames(ORFName orfName) {
        this.orfNames.add(orfName);
        return this;
    }
    public GeneBuilder orfNames(List<ORFName> orfNames) {
        if(orfNames != null) {
            this.orfNames.addAll(orfNames);
        }
        return this;
    }

    public Gene build() {
        return new GeneImpl(geneName, synonyms, orderedLocusNames, orfNames);
    }

    @Override
    public GeneBuilder from(Gene instance) {
        synonyms.clear();
        orderedLocusNames.clear();
        orfNames.clear();
        this.geneName(instance.getGeneName());
        this.orfNames(instance.getOrfNames());
        this.orderedLocusNames(instance.getOrderedLocusNames());
        this.synonyms(instance.getSynonyms());
        return this;
    }
}