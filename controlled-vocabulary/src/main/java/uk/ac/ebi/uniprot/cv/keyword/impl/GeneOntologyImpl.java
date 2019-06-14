package uk.ac.ebi.uniprot.cv.keyword.impl;

import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;

public class GeneOntologyImpl implements GeneOntology {
	private final String goId;
	private final String goTerm;

    private GeneOntologyImpl() {
        this(null, null);
    }
	public static GeneOntology create(String goId, String goTerm) {
		return new GeneOntologyImpl(goId, goTerm) ;
	}
	public GeneOntologyImpl(String goId, String goTerm) {
		this.goId = goId;
		this.goTerm = goTerm;
	}
	
	@Override
	public String getGoId() {
		return goId;
	}

	@Override
	public String getGoTerm() {
		return goTerm;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((goId == null) ? 0 : goId.hashCode());
		result = prime * result + ((goTerm == null) ? 0 : goTerm.hashCode());
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
		GeneOntologyImpl other = (GeneOntologyImpl) obj;
		if (goId == null) {
			if (other.goId != null)
				return false;
		} else if (!goId.equals(other.goId))
			return false;
		if (goTerm == null) {
			if (other.goTerm != null)
				return false;
		} else if (!goTerm.equals(other.goTerm))
			return false;
		return true;
	}

}
