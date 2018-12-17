package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TaxonNameImpl implements TaxonName {
	private String scientificName;
	private String commonName;
	private List<String> synonyms;

	private TaxonNameImpl() {
		this.scientificName = "";
		this.commonName =  "";
		this.synonyms =Collections.emptyList();
	}

	public TaxonNameImpl(String scientificName) {
		this(scientificName, null);
		
	}

	public TaxonNameImpl(String scientificName, String commonName) {
		this(scientificName, commonName, Collections.emptyList());
	}

	public TaxonNameImpl(String scientificName, String commonName, List<String> synonyms) {
		this.scientificName = Utils.resetNull(scientificName);
		this.commonName =  Utils.resetNull(commonName);
		this.synonyms =Utils.unmodifierList(synonyms);
	
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
		TaxonNameImpl other = (TaxonNameImpl) obj;
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
        if(commonName != null && !commonName.isEmpty()) {
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
