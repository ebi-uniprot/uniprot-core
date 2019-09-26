package org.uniprot.core.proteome.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.proteome.impl.CanonicalProteinImpl;
import org.uniprot.core.util.Utils;

public class CanonicalProteinBuilder implements Builder<CanonicalProteinBuilder, CanonicalProtein> {
	private Protein canonicalProtein;
	private List<Protein> relatedProteins = new ArrayList<>();

	public static CanonicalProteinBuilder newInstance() {
		return new CanonicalProteinBuilder();
	}
	@Override
	public CanonicalProtein build() {
		return new CanonicalProteinImpl(canonicalProtein, relatedProteins);
	}

	public CanonicalProteinBuilder relatedProteins(List<Protein> relatedProteins) {
		this.relatedProteins = Utils.nonNullList(relatedProteins);
		return this;
	}

	public CanonicalProteinBuilder addRelatedProtein(Protein relatedProtein) {
		Utils.addOrIgnoreNull(relatedProtein, relatedProteins);
		return this;
	}

	public CanonicalProteinBuilder canonicalProtein(Protein canonicalProtein) {
		this.canonicalProtein = canonicalProtein;
		return this;
	}

	@Override
	public CanonicalProteinBuilder from(CanonicalProtein instance) {
		this.canonicalProtein = instance.getCanonicalProtein();
		relatedProteins.clear();
		relatedProteins.addAll(instance.getRelatedProteins());
		return this;
	}

}
