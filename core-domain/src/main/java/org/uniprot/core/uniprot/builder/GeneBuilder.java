package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.gene.*;
import org.uniprot.core.uniprot.impl.GeneImpl;

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
        addOrIgnoreNull(synonym, this.synonyms);
        return this;
    }

    public GeneBuilder synonyms(List<GeneNameSynonym> synonyms) {
        if(synonyms != null) {
            this.synonyms = modifiableList(synonyms);
        }
        return this;
    }

    public GeneBuilder addOrderedLocusNames(OrderedLocusName orderedLocusName) {
        addOrIgnoreNull(orderedLocusName, this.orderedLocusNames);
        return this;
    }

    public GeneBuilder orderedLocusNames(List<OrderedLocusName> orderedLocusNames) {
        if(orderedLocusNames != null) {
            this.orderedLocusNames = modifiableList(orderedLocusNames);
        }
        return this;
    }

    public GeneBuilder addOrfNames(ORFName orfName) {
        addOrIgnoreNull(orfName, this.orfNames);
        return this;
    }
    public GeneBuilder orfNames(List<ORFName> orfNames) {
        if(orfNames != null) {
            this.orfNames = modifiableList(orfNames);
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