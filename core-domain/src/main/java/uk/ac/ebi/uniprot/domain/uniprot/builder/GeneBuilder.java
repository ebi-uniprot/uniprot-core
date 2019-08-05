package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.uniprot.impl.GeneImpl;

import static org.uniprot.core.common.Utils.nonNullAdd;
import static org.uniprot.core.common.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lgonzales
 */
public class GeneBuilder implements Builder<GeneBuilder,Gene> {

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
            this.synonyms = nonNullList(synonyms);
        }
        return this;
    }

    public GeneBuilder addOrderedLocusNames(OrderedLocusName orderedLocusName) {
        nonNullAdd(orderedLocusName, this.orderedLocusNames);
        return this;
    }

    public GeneBuilder orderedLocusNames(List<OrderedLocusName> orderedLocusNames) {
        if(orderedLocusNames != null) {
            this.orderedLocusNames = nonNullList(orderedLocusNames);
        }
        return this;
    }

    public GeneBuilder addOrfNames(ORFName orfName) {
        nonNullAdd(orfName, this.orfNames);
        return this;
    }
    public GeneBuilder orfNames(List<ORFName> orfNames) {
        if(orfNames != null) {
            this.orfNames = nonNullList(orfNames);
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