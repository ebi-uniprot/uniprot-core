package org.uniprot.core.uniprot.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.gene.*;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;
import org.uniprot.core.util.Utils;

public class GeneImpl implements Gene {
    private static final long serialVersionUID = 6903566846628575244L;
    private static final String ORF_NAMES_PREFIX = "ORFNames=";
    private static final String ORDERED_LOCUS_NAMES_PREFIX = "OrderedLocusNames=";
    private static final String SYNONYMS_PREFIX = "Synonyms=";
    private static final String NAME_PREFIX = "Name=";
    private GeneName geneName;
    private List<GeneNameSynonym> synonyms;
    private List<OrderedLocusName> orderedLocusNames;
    private List<ORFName> orfNames;

    private GeneImpl() {
        synonyms = new ArrayList<>();
        orderedLocusNames = new ArrayList<>();
        orfNames = new ArrayList<>();
    }

    public GeneImpl(
            GeneName geneName,
            List<GeneNameSynonym> synonyms,
            List<OrderedLocusName> orderedLocusNames,
            List<ORFName> orfNames) {
        this.geneName = geneName;
        this.synonyms = Utils.nonNullUnmodifiableList(synonyms);
        this.orderedLocusNames = Utils.nonNullUnmodifiableList(orderedLocusNames);

        this.orfNames = Utils.nonNullUnmodifiableList(orfNames);

        if (!hasGeneName() && !this.synonyms.isEmpty()) {
            throw new IllegalArgumentException("There should be synonyms without gene name.");
        }
    }

    @Override
    public boolean hasGeneName() {
        return (this.geneName != null) && (!this.geneName.getValue().isEmpty());
    }

    @Override
    public GeneName getGeneName() {
        return this.geneName;
    }

    @Override
    public List<GeneNameSynonym> getSynonyms() {
        return this.synonyms;
    }

    @Override
    public List<OrderedLocusName> getOrderedLocusNames() {
        return this.orderedLocusNames;
    }

    @Override
    public List<ORFName> getOrfNames() {
        return this.orfNames;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean hasData = false;
        if (this.hasGeneName()) {
            sb.append(NAME_PREFIX).append(this.geneName.getDisplayed(" ")).append(";");
            hasData = true;
        }
        if (!this.synonyms.isEmpty()) {
            if (hasData) {
                sb.append(" ");
            }
            sb.append(SYNONYMS_PREFIX)
                    .append(
                            synonyms.stream()
                                    .map(val -> val.getDisplayed(" "))
                                    .collect(Collectors.joining(", ")))
                    .append(";");
            hasData = true;
        }
        if (!this.orderedLocusNames.isEmpty()) {
            if (hasData) {
                sb.append(" ");
            }
            sb.append(ORDERED_LOCUS_NAMES_PREFIX)
                    .append(
                            orderedLocusNames.stream()
                                    .map(val -> val.getDisplayed(" "))
                                    .collect(Collectors.joining(", ")))
                    .append(";");
            hasData = true;
        }
        if (!this.orfNames.isEmpty()) {
            if (hasData) {
                sb.append(" ");
            }
            sb.append(ORF_NAMES_PREFIX)
                    .append(
                            orfNames.stream()
                                    .map(val -> val.getDisplayed(" "))
                                    .collect(Collectors.joining(", ")))
                    .append(";");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((geneName == null) ? 0 : geneName.hashCode());
        result = prime * result + ((orderedLocusNames == null) ? 0 : orderedLocusNames.hashCode());
        result = prime * result + ((orfNames == null) ? 0 : orfNames.hashCode());
        result = prime * result + ((synonyms == null) ? 0 : synonyms.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GeneImpl other = (GeneImpl) obj;
        if (geneName == null) {
            if (other.geneName != null) return false;
        } else if (!geneName.equals(other.geneName)) return false;
        if (orderedLocusNames == null) {
            if (other.orderedLocusNames != null) return false;
        } else if (!orderedLocusNames.equals(other.orderedLocusNames)) return false;
        if (orfNames == null) {
            if (other.orfNames != null) return false;
        } else if (!orfNames.equals(other.orfNames)) return false;
        if (synonyms == null) {
            return other.synonyms == null;
        } else return synonyms.equals(other.synonyms);
    }

    public static class ORFNameImpl extends EvidencedValueImpl implements ORFName {

        private ORFNameImpl() {
            super(null, null);
        }

        public ORFNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }
    }

    public static class OrderedLocusNameImpl extends EvidencedValueImpl
            implements OrderedLocusName {

        private OrderedLocusNameImpl() {
            super(null, null);
        }

        public OrderedLocusNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }
    }

    public static class GeneNameSynonymImpl extends EvidencedValueImpl implements GeneNameSynonym {

        private GeneNameSynonymImpl() {
            super(null, null);
        }

        public GeneNameSynonymImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }
    }

    public static class GeneNameImpl extends EvidencedValueImpl implements GeneName {

        private GeneNameImpl() {
            super(null, null);
        }

        public GeneNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }
    }
}
