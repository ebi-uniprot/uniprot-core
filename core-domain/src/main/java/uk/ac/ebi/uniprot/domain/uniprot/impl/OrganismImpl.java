package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OrganismImpl  implements Organism {
	 private final String scientificName;
	    private final String commonName;
	    private final List<String> synonyms;
    public OrganismImpl( String scientificName, String commonName, List<String> synonyms) {
    	 this.scientificName = scientificName;
         this.commonName = commonName;
         if ((synonyms == null) || synonyms.isEmpty()) {
             this.synonyms = Collections.emptyList();
         } else {
             this.synonyms = Collections.unmodifiableList(synonyms);
         }

         if ((this.scientificName == null) || this.scientificName.isEmpty()) {
             throw new IllegalArgumentException("Scientific name is not defined.");
         }
       
    }

	@Override
	public String getScientificName() {
		return scientificName;
	}


	@Override
	public String getCommonName() {
		return commonName;
	}


	@Override
	public List<String> getSynonyms() {
		return synonyms;
	}


    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commonName == null) ? 0 : commonName.hashCode());
		result = prime * result + ((scientificName == null) ? 0 : scientificName.hashCode());
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
		OrganismImpl other = (OrganismImpl) obj;
		if (commonName == null) {
			if (other.commonName != null)
				return false;
		} else if (!commonName.equals(other.commonName))
			return false;
		if (scientificName == null) {
			if (other.scientificName != null)
				return false;
		} else if (!scientificName.equals(other.scientificName))
			return false;
		if (synonyms == null) {
			if (other.synonyms != null)
				return false;
		} else if (!synonyms.equals(other.synonyms))
			return false;
		return true;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getScientificName());
        String commonName = getCommonName();
        if ((commonName != null) && !commonName.isEmpty()) {
            sb.append(" (")
                    .append(commonName).append(")");
        }
        List<String> synonyms = this.getSynonyms();
        if (!synonyms.isEmpty()) {
            sb.append(" (")
                    .append(synonyms.stream().collect(Collectors.joining(", ")))
                    .append(")");
        }
        return sb.toString();
    }


}
