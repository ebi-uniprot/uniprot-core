package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.gene.*;

/** @author lgonzales */
public class GeneBuilder implements Builder<Gene> {

    private GeneName geneName = null;
    private List<GeneNameSynonym> synonyms = new ArrayList<>();
    private List<OrderedLocusName> orderedLocusNames = new ArrayList<>();
    private List<ORFName> orfNames = new ArrayList<>();

    public @Nonnull GeneBuilder geneName(GeneName geneName) {
        this.geneName = geneName;
        return this;
    }

    public @Nonnull GeneBuilder synonymsAdd(GeneNameSynonym synonym) {
        addOrIgnoreNull(synonym, this.synonyms);
        return this;
    }

    public @Nonnull GeneBuilder synonymsSet(List<GeneNameSynonym> synonyms) {
        this.synonyms = modifiableList(synonyms);
        return this;
    }

    public @Nonnull GeneBuilder orderedLocusNamesAdd(OrderedLocusName orderedLocusName) {
        addOrIgnoreNull(orderedLocusName, this.orderedLocusNames);
        return this;
    }

    public @Nonnull GeneBuilder orderedLocusNamesSet(List<OrderedLocusName> orderedLocusNames) {
        this.orderedLocusNames = modifiableList(orderedLocusNames);
        return this;
    }

    public @Nonnull GeneBuilder orfNamesAdd(ORFName orfName) {
        addOrIgnoreNull(orfName, this.orfNames);
        return this;
    }

    public @Nonnull GeneBuilder orfNamesSet(List<ORFName> orfNames) {
        this.orfNames = modifiableList(orfNames);
        return this;
    }

    public @Nonnull Gene build() {
        return new GeneImpl(geneName, synonyms, orderedLocusNames, orfNames);
    }

    public static @Nonnull GeneBuilder from(@Nonnull Gene instance) {
        return new GeneBuilder()
                .geneName(instance.getGeneName())
                .orfNamesSet(instance.getOrfNames())
                .orderedLocusNamesSet(instance.getOrderedLocusNames())
                .synonymsSet(instance.getSynonyms());
    }
}
