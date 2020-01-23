package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.gene.*;
import org.uniprot.core.uniprot.impl.GeneImpl;

/** @author lgonzales */
public class GeneBuilder implements Builder<GeneBuilder, Gene> {

    private GeneName geneName = null;
    private List<GeneNameSynonym> synonyms = new ArrayList<>();
    private List<OrderedLocusName> orderedLocusNames = new ArrayList<>();
    private List<ORFName> orfNames = new ArrayList<>();

    public @Nonnull GeneBuilder geneName(GeneName geneName) {
        this.geneName = geneName;
        return this;
    }

    public @Nonnull GeneBuilder addSynonyms(GeneNameSynonym synonym) {
        addOrIgnoreNull(synonym, this.synonyms);
        return this;
    }

    public @Nonnull GeneBuilder synonyms(List<GeneNameSynonym> synonyms) {
        if (synonyms != null) {
            this.synonyms = modifiableList(synonyms);
        }
        return this;
    }

    public @Nonnull GeneBuilder addOrderedLocusNames(OrderedLocusName orderedLocusName) {
        addOrIgnoreNull(orderedLocusName, this.orderedLocusNames);
        return this;
    }

    public @Nonnull GeneBuilder orderedLocusNames(List<OrderedLocusName> orderedLocusNames) {
        if (orderedLocusNames != null) {
            this.orderedLocusNames = modifiableList(orderedLocusNames);
        }
        return this;
    }

    public @Nonnull GeneBuilder addOrfNames(ORFName orfName) {
        addOrIgnoreNull(orfName, this.orfNames);
        return this;
    }

    public @Nonnull GeneBuilder orfNames(List<ORFName> orfNames) {
        if (orfNames != null) {
            this.orfNames = modifiableList(orfNames);
        }
        return this;
    }

    public @Nonnull Gene build() {
        return new GeneImpl(geneName, synonyms, orderedLocusNames, orfNames);
    }

    public static @Nonnull GeneBuilder from(@Nonnull Gene instance) {
        return new GeneBuilder()
        .geneName(instance.getGeneName())
        .orfNames(instance.getOrfNames())
        .orderedLocusNames(instance.getOrderedLocusNames())
        .synonyms(instance.getSynonyms());
    }
}
