package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;

import java.util.Collections;
import java.util.List;

public class GeneImpl implements Gene {
    private final GeneName geneName;
    private final List<GeneNameSynonym> synonyms;
    private final List<OrderedLocusName> olnNames;
    private final List<ORFName> orfNames;

    public GeneImpl(GeneName geneName,
        List<GeneNameSynonym> synonyms,
        List<OrderedLocusName> olnNames,
        List<ORFName> orfNames) {
        this.geneName = geneName;
        if((synonyms !=null) && !synonyms.isEmpty())
            this.synonyms = Collections.unmodifiableList(synonyms);
        else
            this.synonyms = Collections.emptyList();
        if((olnNames !=null) && !olnNames.isEmpty())
            this.olnNames = Collections.unmodifiableList(olnNames);
        else
            this.olnNames =Collections.emptyList();
        if((orfNames !=null) && !orfNames.isEmpty())
            this.orfNames = Collections.unmodifiableList(orfNames);
        else
            this.orfNames = Collections.emptyList();
        if(!hasGeneName() && !this.synonyms.isEmpty()){
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
    public List<GeneNameSynonym> getGeneNameSynonyms() {
        return this.synonyms;
    }

    @Override
    public List<OrderedLocusName> getOrderedLocusNames() {
        return this.olnNames;
    }

    @Override
    public List<ORFName> getORFNames() {
        return this.orfNames;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((geneName == null) ? 0 : geneName.hashCode());
        result = prime * result + ((olnNames == null) ? 0 : olnNames.hashCode());
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
        if (olnNames == null) {
            if (other.olnNames != null)
                return false;
        } else if (!olnNames.equals(other.olnNames))
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

}
