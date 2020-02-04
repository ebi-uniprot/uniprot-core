package org.uniprot.core.uniprot.taxonomy.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.taxonomy.OrganismName;

public abstract class AbstractOrganismNameBuilder<
                B extends AbstractOrganismNameBuilder<B, T>, T extends OrganismName>
        implements Builder<T> {
    protected String scientificName = "";
    protected String commonName = "";
    protected List<String> synonyms = new ArrayList<>();

    protected abstract @Nonnull B getThis();

    public @Nonnull B scientificName(String scientificName) {
        this.scientificName = scientificName;
        return getThis();
    }

    public @Nonnull B commonName(String commonName) {
        this.commonName = commonName;
        return getThis();
    }

    public @Nonnull B synonymsSet(List<String> synonyms) {
        this.synonyms = modifiableList(synonyms);
        return getThis();
    }

    public @Nonnull B synonymsAdd(String synonym) {
        addOrIgnoreNull(synonym, this.synonyms);
        return getThis();
    }

    protected static <B extends AbstractOrganismNameBuilder<B, T>, T extends OrganismName>
            void init(@Nonnull B builder, @Nonnull T organismName) {
        builder.synonymsSet(organismName.getSynonyms())
                .scientificName(organismName.getScientificName())
                .commonName(organismName.getCommonName());
    }
}
