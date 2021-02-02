package org.uniprot.cv.taxonomy.impl;

import java.util.Objects;

import org.uniprot.cv.taxonomy.TaxonomicNode;

/**
 * Concrete implementation of a TaxonomicNode.
 *
 * <p>Contains a builder to aid in the creation of a TaxonomicNodeImpl.
 *
 * <p>Note: All information necessary to populate the node has to be provided at build time.
 */
public class TaxonomicNodeImpl implements TaxonomicNode {
    public static final String UNDEFINED_SCIENTIFIC_NAME = "Undefined";

    private final int id;
    private final String scientificName;
    private final String commonName;
    private final String synonymName;
    private final String mnemonic;
    private final boolean hidden;
    private final String rank;

    private final TaxonomicNode parent;

    private TaxonomicNodeImpl(
            int id,
            String scientificName,
            String commonName,
            String synonymName,
            String mnemonic,
            boolean hidden,
            String rank,
            TaxonomicNode parent) {
        this.id = id;
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.synonymName = synonymName;
        this.mnemonic = mnemonic;
        this.hidden = hidden;
        this.rank = rank;
        this.parent = parent;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public String scientificName() {
        return this.scientificName;
    }

    @Override
    public String commonName() {
        return this.commonName;
    }

    @Override
    public String synonymName() {
        return this.synonymName;
    }

    @Override
    public String mnemonic() {
        return this.mnemonic;
    }

    @Override
    public boolean hidden() {
        return hidden;
    }

    @Override
    public String rank() {
        return this.rank;
    }

    @Override
    public TaxonomicNode parent() {
        return this.parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public static class Builder {
        private final int id;
        private final String scientificName;
        private String commonName;
        private String synonymName;
        private String mnemonic;
        private boolean hidden;
        private String rank;
        private TaxonomicNode parent;

        public Builder(int id, String scientificName) {
            this.id = id;
            this.scientificName = scientificName;
        }

        public Builder withCommonName(String name) {
            this.commonName = name;
            return this;
        }

        public Builder withSynonymName(String name) {
            this.synonymName = name;
            return this;
        }

        public Builder withMnemonic(String name) {
            this.mnemonic = name;
            return this;
        }

        public Builder withHidden(boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        public Builder withRank(String rank) {
            this.rank = rank;
            return this;
        }

        public Builder childOf(TaxonomicNode parent) {
            this.parent = parent;
            return this;
        }

        public TaxonomicNodeImpl build() {
            return new TaxonomicNodeImpl(
                    id, scientificName, commonName, synonymName, mnemonic, hidden, rank, parent);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaxonomicNodeImpl that = (TaxonomicNodeImpl) o;
        return Objects.equals(this.id, that.id)
                && Objects.equals(this.commonName, that.commonName)
                && Objects.equals(this.parent, that.parent)
                && Objects.equals(this.scientificName, that.scientificName)
                && Objects.equals(this.synonymName, that.synonymName)
                && Objects.equals(this.hidden, that.hidden)
                && Objects.equals(this.rank, that.rank)
                && Objects.equals(this.mnemonic, that.mnemonic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.commonName,
                this.parent,
                this.scientificName,
                this.synonymName,
                this.mnemonic,
                this.hidden,
                this.rank);
    }

    @Override
    public String toString() {
        return "TaxonomicNodeImpl{"
                + "id="
                + id
                + ", scientificName='"
                + scientificName
                + '\''
                + ", commonName='"
                + commonName
                + '\''
                + ", synonymName='"
                + synonymName
                + '\''
                + ", hidden="
                + hidden
                + ", rank='"
                + rank
                + '\''
                + ", parent="
                + parent
                + '}';
    }
}
