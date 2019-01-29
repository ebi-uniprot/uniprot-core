package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

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
        nonNullAdd(synonym, this.synonyms);
        return this;
    }

    public GeneBuilder synonyms(List<GeneNameSynonym> synonyms) {
        if(synonyms != null) {
            nonNullAddAll(synonyms, this.synonyms);
        }
        return this;
    }

    public GeneBuilder addOrderedLocusNames(OrderedLocusName orderedLocusName) {
        nonNullAdd(orderedLocusName, this.orderedLocusNames);
        return this;
    }

    public GeneBuilder orderedLocusNames(List<OrderedLocusName> orderedLocusNames) {
        if(orderedLocusNames != null) {
            nonNullAddAll(orderedLocusNames, this.orderedLocusNames);
        }
        return this;
    }

    public GeneBuilder addOrfNames(ORFName orfName) {
        nonNullAdd(orfName, this.orfNames);
        return this;
    }
    public GeneBuilder orfNames(List<ORFName> orfNames) {
        if(orfNames != null) {
            nonNullAddAll(orfNames, this.orfNames);
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