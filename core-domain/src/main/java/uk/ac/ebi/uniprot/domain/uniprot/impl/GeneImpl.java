package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GeneImpl implements Gene {
	@JsonInclude(JsonInclude.Include.NON_NULL)
    private final GeneName geneName;
    private final List<GeneNameSynonym> synonyms;
    private final List<OrderedLocusName> orderedLocusNames;
    private final List<ORFName> orfNames;
    private static final String ORF_NAMES = "ORFNames=";
    private static final String ORDERED_LOCUS_NAMES = "OrderedLocusNames=";
    private static final String SYNONYMS = "Synonyms=";
    private static final String NAME = "Name=";
	@JsonCreator
    public GeneImpl(
    		@JsonProperty("geneName") GeneName geneName,
    		@JsonProperty("synonyms") List<GeneNameSynonym> synonyms,
    		@JsonProperty("orderedLocusNames") List<OrderedLocusName> olnNames,
    		@JsonProperty("orfNames")  List<ORFName> orfNames) {
        this.geneName = geneName;
        if((synonyms !=null) && !synonyms.isEmpty())
            this.synonyms = Collections.unmodifiableList(synonyms);
        else
            this.synonyms = Collections.emptyList();
        if((olnNames !=null) && !olnNames.isEmpty())
            this.orderedLocusNames = Collections.unmodifiableList(olnNames);
        else
            this.orderedLocusNames =Collections.emptyList();
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
    public List<GeneNameSynonym> getSynonyms() {
        return this.synonyms;
    }

    @Override
    public List<OrderedLocusName> getOrderedLocusNames() {
        return this.orderedLocusNames;
    }

    @Override
    public List<ORFName> getORFNames() {
        return this.orfNames;
    }

    @Override
    public String toString() {
    	StringBuilder sb= new StringBuilder();
    	boolean hasData = false;
    	if(this.hasGeneName()) {
    		sb.append(NAME)
    		.append(this.geneName.getDisplayed(" "))
    		.append(";");
    		hasData = true;
    	}
    	if(!this.synonyms.isEmpty()) {
    		if(hasData) {
    			sb.append(" ");
    		}
    		sb.append(SYNONYMS)
    		.append(
    		synonyms.stream().map(val -> val.getDisplayed(" "))
    		.collect(Collectors.joining(", ")))
    		.append(";");
    		hasData = true;
    	}
    	if(!this.orderedLocusNames.isEmpty()) {
    		if(hasData) {
    			sb.append(" ");
    		}
    		sb.append(ORDERED_LOCUS_NAMES)
    		.append(
    				orderedLocusNames.stream().map(val -> val.getDisplayed(" "))
    		.collect(Collectors.joining(", ")))
    		.append(";");
    		hasData = true;
    	}
    	if(!this.orfNames.isEmpty()) {
    		if(hasData) {
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
    	@JsonCreator
        public ORFNameImpl(@JsonProperty("value") String value, 
    			@JsonProperty("evidences") List<Evidence> evidences) {
            super(value, evidences);      
        }
    }
    
    
    public static class OrderedLocusNameImpl extends EvidencedValueImpl implements OrderedLocusName {
    	@JsonCreator
        public OrderedLocusNameImpl(@JsonProperty("value") String value, 
    			@JsonProperty("evidences") List<Evidence> evidences) {
            super(value, evidences);      
        }
    }
    
    public static class GeneNameSynonymImpl extends EvidencedValueImpl implements GeneNameSynonym {
    	@JsonCreator
        public GeneNameSynonymImpl(@JsonProperty("value") String value, 
    			@JsonProperty("evidences") List<Evidence> evidences) {
            super(value, evidences);      
        }
    }
    
    public static class GeneNameImpl extends EvidencedValueImpl implements GeneName {
    	@JsonCreator
        public GeneNameImpl(@JsonProperty("value") String value, 
    			@JsonProperty("evidences") List<Evidence> evidences) {
            super(value, evidences);      
        }
    }
}
