package org.uniprot.core.proteome.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.util.Utils;

public class CanonicalProteinImpl implements CanonicalProtein {
	private static final long serialVersionUID = 767382493220005503L;
	private Protein canonicalProtein;
	private List<Protein> relatedProteins;
	private CanonicalProteinImpl() {
		relatedProteins =  Collections.emptyList();
	}
	public  CanonicalProteinImpl(Protein canonicalProtein, List<Protein> relatedProteins) {
		this.canonicalProtein = canonicalProtein;
		this.relatedProteins =Utils.unmodifiableList(relatedProteins);
	
	}
	@Override
	public Protein getCanonicalProtein() {
		return canonicalProtein;
	}

	@Override
	public List<Protein> getRelatedProteins() {
		return relatedProteins;
	}
	@Override
	public int hashCode() {
		return Objects.hash(canonicalProtein, relatedProteins);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CanonicalProteinImpl other = (CanonicalProteinImpl) obj;
		return Objects.equals(canonicalProtein, other.canonicalProtein)
				&& Objects.equals(relatedProteins, other.relatedProteins);
	}

}
