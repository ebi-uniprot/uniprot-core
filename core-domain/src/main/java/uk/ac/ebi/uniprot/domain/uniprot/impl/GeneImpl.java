package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneImpl implements Gene {

    private static final String ORF_NAMES = "ORFNames=";
    private static final String ORDERED_LOCUS_NAMES = "OrderedLocusNames=";
    private static final String SYNONYMS = "Synonyms=";
    private static final String NAME = "Name=";
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
        this.synonyms = Utils.unmodifierList(synonyms);
        this.orderedLocusNames = Utils.unmodifierList(orderedLocusNames);

        this.orfNames = Utils.unmodifierList(orfNames);

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
            sb.append(NAME)
                    .append(this.geneName.getDisplayed(" "))
                    .append(";");
            hasData = true;
        }
        if (!this.synonyms.isEmpty()) {
            if (hasData) {
                sb.append(" ");
            }
            sb.append(SYNONYMS)
                    .append(
                            synonyms.stream().map(val -> val.getDisplayed(" "))
                                    .collect(Collectors.joining(", ")))
                    .append(";");
            hasData = true;
        }
        if (!this.orderedLocusNames.isEmpty()) {
            if (hasData) {
                sb.append(" ");
            }
            sb.append(ORDERED_LOCUS_NAMES)
                    .append(
                            orderedLocusNames.stream().map(val -> val.getDisplayed(" "))
                                    .collect(Collectors.joining(", ")))
                    .append(";");
            hasData = true;
        }
        if (!this.orfNames.isEmpty()) {
            if (hasData) {
                sb.append(" ");
            }
            sb.append(ORF_NAMES)
                    .append(
                            orfNames.stream().map(val -> val.getDisplayed(" "))
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
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GeneImpl other = (GeneImpl) obj;
        if (geneName == null) {
            if (other.geneName != null)
                return false;
        } else if (!geneName.equals(other.geneName))
            return false;
        if (orderedLocusNames == null) {
            if (other.orderedLocusNames != null)
                return false;
        } else if (!orderedLocusNames.equals(other.orderedLocusNames))
            return false;
        if (orfNames == null) {
            if (other.orfNames != null)
                return false;
        } else if (!orfNames.equals(other.orfNames))
            return false;
        if (synonyms == null) {
            if (other.synonyms != null)
                return false;
        } else if (!synonyms.equals(other.synonyms))
            return false;
        return true;
    }

    public static class ORFNameImpl extends EvidencedValueImpl implements ORFName {

        private ORFNameImpl() {
            super(null, null);
        }

        public ORFNameImpl(String value, List<Evidence> evidences) {
            super(value, evidences);
        }
    }


    public static class OrderedLocusNameImpl extends EvidencedValueImpl implements OrderedLocusName {

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
